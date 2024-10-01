package compilador;

import static compilador.Errores.erroresEnum.FALTA_CADENA_FACTOR_IDENT_PARA;
import static compilador.Errores.erroresEnum.FALTA_PUNTO_Y_COMA_COMA;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorSintactico {

    private Token token;
    private String scope = "";
    private final AnalizadorLexico lex;
    private final AnalizadorSemantico semantica;
    private final GeneradorDeCodigo genCod;
    public void setToken(Token token) {
        this.token = token;
    }

    public AnalizadorSintactico(AnalizadorLexico scanner, AnalizadorSemantico semantic, GeneradorDeCodigo gen) {
        this.lex = scanner;
        this.semantica = semantic;
        this.genCod = gen;
    }

    private void mostrarError(Errores.erroresEnum enumError, Token token) {
       Errores.mostrarError(enumError, token, lex.getPathFile());
    }

    private void leerProximoToken() throws IOException {
        this.setToken(this.lex.escanear());
    }

    public void programa() throws IOException {
// REGLA PROGRAMA  BLOQUE() -> Terminal(".")
        // Primer token inicial 
        bloque(0);
        if (this.token.getValor().equals(".")) {
//Obtenego proximo token
            leerProximoToken();
            this.genCod.dump();
            
        } else {
            mostrarError(Errores.erroresEnum.DESCONOCIDO, this.token);
        }
    }

    private void bloque(int base) throws IOException {
        scope = "DECLARACION";
        int desplazamiento = 0;
        if (this.token.esConstrante()) {
            String nomCte = "";
            leerProximoToken();
            if (this.token.esIdentificador()) {
                nomCte = this.token.getValor();
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals("=")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
            }
            if (this.token.esNumero()) {
                semantica.agregar(nomCte, "CONST", Integer.parseInt(this.token.getValor()), base, desplazamiento, scope);
                desplazamiento++;
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
            }
            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    nomCte = this.token.getValor();
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
                if (this.token.getValor().equals("=")) {
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
                }
                if (this.token.esNumero()) {
                    semantica.agregar(nomCte, "CONST", Integer.parseInt(this.token.getValor()), base, desplazamiento, scope);
                    desplazamiento++;
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
                }
            } //fin while

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA_COMA, this.token);
            }

        } //FIN CONSTANTE

        if (this.token.getValor().equals("VAR")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                semantica.agregar(this.token.getValor(), "VAR", 0, base, desplazamiento, scope);
                desplazamiento++;
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (token.getValor().equals(",")) { //while var ident, ident ;
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    semantica.agregar(this.token.getValor(), "VAR", 0, base, desplazamiento, scope);
                    desplazamiento++;
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }   //while var ident, ident ;

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA_COMA, this.token);
            }
        } //fin VAR

        while (token.getValor().equals("PROCEDURE")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                semantica.agregar(this.token.getValor(), "PROCEDURE", 0, base, desplazamiento, scope);
                desplazamiento++;
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
            bloque(desplazamiento + base);
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
        } //FIN WHILE

        proposicion(base, desplazamiento);
    } // FIN FUNCION BLOQUE

    //A PARTIR DE ACA SOLO USO LOS IDENTIFICADORES PARA BUSCARLOS NO LOS DECLARO
    private void proposicion(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        ArrayList<String> tiposEsperados = new ArrayList<>();

        if (this.token.esIdentificador()) {
            tiposEsperados.add("VAR");
            this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);
            leerProximoToken();
            if (this.token.getValor().equals(":=")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_ASIGNACION, this.token);
            }
            expresion(base, desplazamiento);
        }
        if (this.token.getValor().equals("CALL")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                tiposEsperados.clear();
                tiposEsperados.add("PROCEDURE");
                this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
        }
        if (this.token.getValor().equals("BEGIN")) {
            leerProximoToken();
            proposicion(base, desplazamiento);
            while (this.token.getValor().equals(";")) {
                leerProximoToken();
                proposicion(base, desplazamiento);
            }
            if (this.token.getValor().equals("END")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_END, this.token);
            }
        }
        if (this.token.getValor().equals("IF")) {
            leerProximoToken();
            condicion(base, desplazamiento);
            if (this.token.getValor().equals("THEN")) {
                leerProximoToken();
                proposicion(base, desplazamiento);
            } else {
                mostrarError(Errores.erroresEnum.FALTA_THEN, this.token);
            }
        }
        if (this.token.getValor().equals("WHILE")) {
            leerProximoToken();
            condicion(base, desplazamiento);
            if (this.token.getValor().equals("DO")) {
                leerProximoToken();
                proposicion(base, desplazamiento);
            } else {
                mostrarError(Errores.erroresEnum.FALTA_DO, this.token);
            }
        }
        if (this.token.getValor().equals("READLN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }

            if (this.token.esIdentificador()) {
                tiposEsperados.clear();
                tiposEsperados.add("VAR");
                tiposEsperados.add("CONST");
                this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);

                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    tiposEsperados.clear();
                    tiposEsperados.add("VAR");
                    tiposEsperados.add("CONST");
                    this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_CADENA_FACTOR_IDENT_PARA, this.token);
            }
        }
        if (this.token.getValor().equals("WRITE")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
            if (this.token.esCadena()) {
                leerProximoToken();
            }
            expresion(base, desplazamiento);
            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    leerProximoToken();
                }
                expresion(base, desplazamiento);
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_CADENA_FACTOR_IDENT_PARA, this.token);
            }
        }
        if (this.token.getValor().equals("WRITELN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    leerProximoToken();
                }
                expresion(base, desplazamiento);
                while (this.token.getValor().equals(",")) {
                    leerProximoToken();
                    if (this.token.esCadena()) {
                        leerProximoToken();
                    }
                    expresion(base, desplazamiento);
                }
                if (this.token.getValor().equals(")")) {
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_FACTOR_IDENT_PARA, this.token);
                }

            }
        }
    } //FIN PROPOSICION

    private void condicion(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        if (this.token.getValor().equals("ODD")) {
            leerProximoToken();
            expresion(base, desplazamiento);
            return;
        }
        expresion(base, desplazamiento);
        if (this.token.esSignoCondicion()) {
            switch (this.token.getValor()) {
                case "=" -> {
                    //break;
                }
                case "<>" -> {
                    // break;
                }
                case "<" -> {
                    // break;
                }
                case "<=" -> {
                    // break;
                }
                case ">" -> {
                    //  break;
                }
                case ">=" -> {
                    // break;
                }
            }
            leerProximoToken();
        } else {
            mostrarError(Errores.erroresEnum.FALTA_SIGNO_COND, this.token);
        }
        expresion(base, desplazamiento);
    }

    private void expresion(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        if (token.esSignoExpresion()) {

            switch (this.token.getValor()) {
                case "+" -> {
                    //  break;
                }
                case "-" -> {
                    // break;
                }
            }
            leerProximoToken();
        }

        termino(base, desplazamiento);

        while (token.esSignoExpresion()) {
            switch (this.token.getValor()) {
                case "+" -> {
                    //  break;
                }
                case "-" -> {
                    // break;
                }
            }
            leerProximoToken();
            termino(base, desplazamiento);
        }

    }

    private void termino(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        factor(base, desplazamiento);
        while (this.token.esSignoTermino()) {
            switch (this.token.getValor()) {
                case "*" -> {
                    // break;
                }
                case "/" -> {
                    //  break;
                }
            }
            leerProximoToken();
            factor(base, desplazamiento);
        }

    }

    private void factor(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        ArrayList<String> tiposEsperados = new ArrayList<>();
        if (this.token.esIdentificador()) {
            tiposEsperados.clear();
            tiposEsperados.add("VAR");
            tiposEsperados.add("CONST");
            this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);

            leerProximoToken();
        }
        if (this.token.esNumero()) {
            leerProximoToken();
        }
        if (this.token.getValor().equals("(")) {
            leerProximoToken();
            expresion(base, desplazamiento);
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }
        }
    }

}
