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
        this.setToken(this.lex.escanear(this.token.getNroLinea()));
    }

    private enum ErroresSintacticos {
        FALTA_NUMERO, FALTA_PARENTESIS_A, FALTA_PARENTESIS_C, FALTA_IGUAL, FALTA_SIGNO_TERMINO, FALTA_SIGNO_COND, FALTA_SIGNO_EXP, FALTA_IDENTIFICADOR, FALTA_DO, FALTA_ODD, FALTA_THEN, FALTA_PTOYCOMA, FALTA_ASIGNACION, DESCONOCIDO, FALTA_END
    }

    private void mostrarError(ErroresSintacticos error, int linea) {

        switch (error) {
            case ErroresSintacticos.FALTA_IDENTIFICADOR -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un identificador");
                break;
            }
            case ErroresSintacticos.FALTA_PARENTESIS_A -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un parentesis (  ");
                break;
            }
            case ErroresSintacticos.FALTA_PARENTESIS_C -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un parentesis )  ");
                break;
            }
            case ErroresSintacticos.FALTA_NUMERO -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un Numero");
                break;
            }
            case ErroresSintacticos.FALTA_IGUAL -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un igual (=)");
                break;
            }
            case ErroresSintacticos.FALTA_ASIGNACION -> {
                System.out.println("Error linea: " + linea + " : Se esperaba una asignacion (:=)");
                break;
            }
            case ErroresSintacticos.FALTA_PTOYCOMA -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un Punto y coma (;)");
                break;
            }
            case ErroresSintacticos.DESCONOCIDO -> {
                System.out.println("Error linea: " + linea + " : DESCONOCIDO TOKEN:" + this.token.getValor());
                break;
            }
            case ErroresSintacticos.FALTA_END -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada END");
                break;
            }
            case ErroresSintacticos.FALTA_THEN -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada THEN");
                break;
            }
            case ErroresSintacticos.FALTA_DO -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada DO");
                break;
            }
            case ErroresSintacticos.FALTA_ODD -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada ODD");
                break;
            }
            case ErroresSintacticos.FALTA_SIGNO_COND -> {
                System.out.println("Error linea: " + linea + " : se esperaba un signo de los siguientes <,>,<=,>=,=,<>");
                break;
            }
            case ErroresSintacticos.FALTA_SIGNO_EXP -> {
                System.out.println("Error linea: " + linea + " : se esperaba un signo de los siguientes +,-");
                break;
            }
            case ErroresSintacticos.FALTA_SIGNO_TERMINO -> {
                System.out.println("Error linea: " + linea + " : se esperaba un signo de los siguientes *,/");
                break;
            }
        }
        System.exit(error.ordinal());

    }

    public void programa() throws IOException {
// REGLA PROGRAMA  BLOQUE() -> Terminal(".")
        // Primer token inicial 
        bloque();
        if (this.token.getValor().equals(".")) {
//Obtenego proximo token
            leerProximoToken();
        } else {
            mostrarError(ErroresSintacticos.DESCONOCIDO, this.token.getNroLinea());
        }
    }

    private void bloque() throws IOException {

        if (this.token.esConstrante()) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
            }
            if (this.token.getValor().equals("=")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_IGUAL, this.token.getNroLinea());
            }
            if (this.token.esNumero()) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_NUMERO, this.token.getNroLinea());
            }
            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    leerProximoToken();
                } else {
                    mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                }
                if (this.token.getValor().equals("=")) {
                    leerProximoToken();
                } else {
                    mostrarError(ErroresSintacticos.FALTA_IGUAL, this.token.getNroLinea());
                }
                if (this.token.esNumero()) {
                    leerProximoToken();
                } else {
                    mostrarError(ErroresSintacticos.FALTA_NUMERO, this.token.getNroLinea());
                }
            } //fin while

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
            }

        } //FIN CONSTANTE

        if (this.token.getValor().equals("VAR")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
            }

            while (token.getValor().equals(",")) { //while var ident, ident ;
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    leerProximoToken();
                } else {
                    mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                }
            }   //while var ident, ident ;

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
            }
        } //fin VAR

        while (token.getValor().equals("PROCEDURE")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
            }
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
            }
            bloque();
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
            }
        } //FIN WHILE

        Proposicion();
    } // FIN FUNCION BLOQUE

    private void Proposicion() throws IOException {
        if (this.token.esIdentificador()) {
            leerProximoToken();
            if (this.token.getValor().equals(":=")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_ASIGNACION, this.token.getNroLinea());
            }
            expresion();
        }
        if (this.token.getValor().equals("CALL")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
            }
        }
        if (this.token.getValor().equals("BEGIN")) {
            leerProximoToken();
            Proposicion();
            while (this.token.getValor().equals(";")) {
                leerProximoToken();
                Proposicion();
            }
            if (this.token.getValor().equals("END")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_END, this.token.getNroLinea());
            }
        }
        if (this.token.getValor().equals("IF")) {
            leerProximoToken();
            condicion();
            if (this.token.getValor().equals("THEN")) {
                leerProximoToken();
                Proposicion();
            } else {
                mostrarError(ErroresSintacticos.FALTA_THEN, this.token.getNroLinea());
            }
        }
        if (this.token.getValor().equals("WHILE")) {
            leerProximoToken();
            condicion();
            if (this.token.getValor().equals("DO")) {
                leerProximoToken();
                Proposicion();
            } else {
                mostrarError(ErroresSintacticos.FALTA_DO, this.token.getNroLinea());
            }
        }
        if (this.token.getValor().equals("READLN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PARENTESIS_A, this.token.getNroLinea());
            }

            if (this.token.esIdentificador()) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
            }

            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    leerProximoToken();
                } else {
                    mostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                }
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PARENTESIS_C, this.token.getNroLinea());
            }            
        }
        if (this.token.getValor().equals("WRITE")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(ErroresSintacticos.FALTA_PARENTESIS_A, this.token.getNroLinea());
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
                mostrarError(ErroresSintacticos.FALTA_PARENTESIS_C, this.token.getNroLinea());
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
                    mostrarError(ErroresSintacticos.FALTA_PARENTESIS_C, this.token.getNroLinea());
                }

            }
        }
    } //FIN PROPOSICION

    private void condicion() throws IOException {

        if (this.token.getValor().equals("ODD")) {
            leerProximoToken();
            expresion();            
            return ;
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
            mostrarError(ErroresSintacticos.FALTA_SIGNO_COND, this.token.getNroLinea());
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

        Factor();
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
            Factor();
        }

    }

    private void Factor() throws IOException {

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
                mostrarError(ErroresSintacticos.FALTA_PARENTESIS_C, this.token.getNroLinea());
            }
        }
    }

}
