package compilador;

public class Errores {

    public enum erroresEnum {
        FALTA_NUMERO, FALTA_PARENTESIS_A, FALTA_PARENTESIS_C, FALTA_IGUAL, FALTA_SIGNO_TERMINO,
        FALTA_SIGNO_COND, FALTA_SIGNO_EXP, FALTA_IDENTIFICADOR, FALTA_PUNTO_Y_COMA, FALTA_DO, FALTA_ODD, FALTA_THEN,
        FALTA_ASIGNACION, DESCONOCIDO, FALTA_END, IDENTIFICADOR_DUPLICADO, IDENTIFICADOR_NOIDENTIFICADO
    }

    public static void mostrarError(erroresEnum error, Token token) {

        switch (error) {
            //sintacticos
            case FALTA_IDENTIFICADOR -> {
                System.out.println("Error :se esperaba un identificador");
                break;
            }
            case FALTA_PARENTESIS_A -> {
                System.out.println("Error :se esperaba un parentesis (  ");
                break;
            }
            case FALTA_PARENTESIS_C -> {
                System.out.println("Error :se esperaba un parentesis )  ");
                break;
            }
            case FALTA_NUMERO -> {
                System.out.println("Error :se esperaba un Numero");
                break;
            }
            case FALTA_IGUAL -> {
                System.out.println("Error :se esperaba un igual (=)");
                break;
            }
            case FALTA_ASIGNACION -> {
                System.out.println("Error :se esperaba una asignacion (:=)");
                break;
            }
            case FALTA_PUNTO_Y_COMA -> {
                System.out.println("Error :se esperaba un Punto y coma (;)");
                break;
            }
            case DESCONOCIDO -> {
                System.out.println("Error :DESCONOCIDO TOKEN:" + token.getValor());
                break;
            }
            case FALTA_END -> {
                System.out.println("Error :se esperaba la palabra reservada END");
                break;
            }
            case FALTA_THEN -> {
                System.out.println("Error :se esperaba la palabra reservada THEN");
                break;
            }
            case FALTA_DO -> {
                System.out.println("Error :se esperaba la palabra reservada DO");
                break;
            }
            case FALTA_ODD -> {
                System.out.println("Error :se esperaba la palabra reservada ODD");
                break;
            }
            case FALTA_SIGNO_COND -> {
                System.out.println("Error :se esperaba un signo de los siguientes <,>,<=,>=,=,<>");
                break;
            }
            case FALTA_SIGNO_EXP -> {
                System.out.println("Error :se esperaba un signo de los siguientes +,-");
                break;
            }
            case FALTA_SIGNO_TERMINO -> {
                System.out.println("Error :se esperaba un signo de los siguientes *,/");
                break;
            }
            //semanticos
            case IDENTIFICADOR_DUPLICADO -> {
                System.out.println("Error :el identificador '" + token.getValor() + "' ya se encuentra declarado");
                break;
            }
            case IDENTIFICADOR_NOIDENTIFICADO -> {
                System.out.println("Error : identificador '" + token.getValor() + "' no identificado");
                break;
            }
        }
        System.exit(error.ordinal());

    }

}
