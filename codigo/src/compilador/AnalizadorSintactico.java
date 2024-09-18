package compilador;

import java.io.IOException;

public class AnalizadorSintactico {

    private Token token;
    private final AnalizadorLexico lex;

    public void setToken(Token token) {
        this.token = token;
    }

    public AnalizadorSintactico(AnalizadorLexico scanner) {
        this.lex = scanner;
    }

    private void leerProximoToken() throws IOException {
        this.setToken(this.lex.escanear());
    }

    public void programa() throws IOException {
// REGLA PROGRAMA  BLOQUE() -> Terminal(".")
        // Primer token inicial 
        bloque();
        if (this.token.getValor().equals(".")) {
//Obtenego proximo token
            leerProximoToken();
        } else {
            Errores.mostrarError(Errores.erroresEnum.DESCONOCIDO, this.token);
        }
    }

    private void bloque() throws IOException {
        if (this.token.esConstrante()) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals("=")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
            }
            if (this.token.esNumero()) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
            }
            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    leerProximoToken();
                } else {
                    Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
                if (this.token.getValor().equals("=")) {
                    leerProximoToken();
                } else {
                    Errores.mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
                }
                if (this.token.esNumero()) {
                    leerProximoToken();
                } else {
                    Errores.mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
                }
            } //fin while

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }

        } //FIN CONSTANTE

        if (this.token.getValor().equals("VAR")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (token.getValor().equals(",")) { //while var ident, ident ;
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    leerProximoToken();
                } else {
                    Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }   //while var ident, ident ;

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
        } //fin VAR

        while (token.getValor().equals("PROCEDURE")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
            bloque();
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
        } //FIN WHILE

        proposicion();
    } // FIN FUNCION BLOQUE

    //A PARTIR DE ACA SOLO USO LOS IDENTIFICADORES PARA BUSCARLOS NO LOS DECLARO
    
    private void proposicion() throws IOException {
        if (this.token.esIdentificador()) {
            leerProximoToken();
            if (this.token.getValor().equals(":=")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_ASIGNACION, this.token);
            }
            expresion();
        }
        if (this.token.getValor().equals("CALL")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
        }
        if (this.token.getValor().equals("BEGIN")) {
            leerProximoToken();
            proposicion();
            while (this.token.getValor().equals(";")) {
                leerProximoToken();
                proposicion();
            }
            if (this.token.getValor().equals("END")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_END, this.token);
            }
        }
        if (this.token.getValor().equals("IF")) {
            leerProximoToken();
            condicion();
            if (this.token.getValor().equals("THEN")) {
                leerProximoToken();
                proposicion();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_THEN, this.token);
            }
        }
        if (this.token.getValor().equals("WHILE")) {
            leerProximoToken();
            condicion();
            if (this.token.getValor().equals("DO")) {
                leerProximoToken();
                proposicion();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_DO, this.token);
            }
        }
        if (this.token.getValor().equals("READLN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }

            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    leerProximoToken();
                } else {
                    Errores.mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }
        }
        if (this.token.getValor().equals("WRITE")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
            if (this.token.esCadena()) {
                leerProximoToken();
            }
            expresion();
            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    leerProximoToken();
                }
                expresion();
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }
        }
        if (this.token.getValor().equals("WRITELN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    leerProximoToken();
                }
                expresion();
                while (this.token.getValor().equals(",")) {
                    leerProximoToken();
                    if (this.token.esCadena()) {
                        leerProximoToken();
                    }
                    expresion();
                }
                if (this.token.getValor().equals(")")) {
                    leerProximoToken();
                } else {
                    Errores.mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
                }

            }
        }
    } //FIN PROPOSICION

    private void condicion() throws IOException {
        if (this.token.getValor().equals("ODD")) {
            leerProximoToken();
            expresion();
            return;
        }
        expresion();
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
            Errores.mostrarError(Errores.erroresEnum.FALTA_SIGNO_COND, this.token);
        }
        expresion();
    }

    private void expresion() throws IOException {
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

        termino();

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
            termino();
        }

    }

    private void termino() throws IOException {
        factor();
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
            factor();
        }

    }

    private void factor() throws IOException {
        if (this.token.esIdentificador()) {
            leerProximoToken();
        }
        if (this.token.esNumero()) {
            leerProximoToken();
        }
        if (this.token.getValor().equals("(")) {
            leerProximoToken();
            expresion();
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                Errores.mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }
        }
    }

}
