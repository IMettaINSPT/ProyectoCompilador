package compilador;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Errores {

    public enum erroresEnum {
        FALTA_NUMERO, FALTA_PARENTESIS_A, FALTA_PARENTESIS_C, FALTA_IGUAL, FALTA_SIGNO_TERMINO,
        FALTA_SIGNO_COND_ODD, FALTA_SIGNO_EXP, FALTA_IDENTIFICADOR, FALTA_PUNTO_Y_COMA, FALTA_PUNTO_Y_COMA_COMA, FALTA_DO, FALTA_ODD, FALTA_THEN,
        FALTA_ASIGNACION, DESCONOCIDO, FALTA_END, IDENTIFICADOR_DUPLICADO, IDENTIFICADOR_NOIDENTIFICADO, TIPO_IDENT_ERROR,
        FALTA_FACTOR_IDENT_PARA, FALTA_CADENA_FACTOR_IDENT_PARA, FALTA_TO, FALTA_VAR, FALTA_EOF, FALTA_PTO, FALTA_IDENT_NUMERO
    }

    public static void mostrarErrorTipo(String token, TipoIdentificador tipoEsperado, TipoIdentificador tipoRecibido, String archivoSalida) {
        String erro = "Error: " + token + " es del tipo " + tipoRecibido + " pero se esperaba " + tipoEsperado;
        System.out.println(erro);
        escribirErrorLog(erro, new Token(TipoToken.IDENTIFICADOR, token), archivoSalida);
        System.exit(1);
    }

    public static void mostrarError(erroresEnum error, Token tokenRecibido, String archivoSalida) {
        String err;
        switch (error) {
            //sintacticos
            case FALTA_IDENTIFICADOR -> {
                err = "Error :se esperaba un identificador";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_PTO -> {
                err = "Error :se esperaba un '.'";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_IDENT_NUMERO -> {
                err = "Error :se esperaba un Identificador o un Numero";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_EOF -> {
                err = "Error :se esperaba EOF";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }

            case FALTA_PARENTESIS_A -> {
                err = "Error :se esperaba un parentesis (  ";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_FACTOR_IDENT_PARA -> {
                err = "Error : se esperaba un factor (identificador,numero, abrir parentesis)  ";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_CADENA_FACTOR_IDENT_PARA -> {
                err = "Error : se esperaba unA cadena o un factor (identificador,numero, abrir parentesis)  ";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_PARENTESIS_C -> {
                err = "Error :se esperaba un parentesis )  ";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_NUMERO -> {
                err = "Error :se esperaba un Numero";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_IGUAL -> {
                err = "Error :se esperaba un igual (=)";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_ASIGNACION -> {
                err = "Error :se esperaba una asignacion (:=)";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_PUNTO_Y_COMA -> {
                err = "Error :se esperaba un Punto y coma (;)";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_PUNTO_Y_COMA_COMA -> {
                err = "Error :se esperaba una coma o un Punto y coma (;)";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case DESCONOCIDO -> {
                err = "Error :DESCONOCIDO TOKEN:" + tokenRecibido.getValor();
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_END -> {
                err = "Error :se esperaba un END o un Punto y coma";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_VAR -> {
                err = "Error :se esperaba una variable VAR";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_THEN -> {
                err = "Error :se esperaba la palabra reservada THEN";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_TO -> {
                err = "Error :se esperaba la palabra reservada TO";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_DO -> {
                err = "Error :se esperaba la palabra reservada DO";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_ODD -> {
                err = "Error :se esperaba la palabra reservada ODD";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_SIGNO_COND_ODD -> {
                err = "Error :se esperaba un signo de los siguientes <,>,<=,>=,=,<>. ò ODD";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_SIGNO_EXP -> {
                err = "Error :se esperaba un signo de los siguientes +,-";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case FALTA_SIGNO_TERMINO -> {
                err = "Error :se esperaba un signo de los siguientes *,/";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            //semanticos
            case IDENTIFICADOR_DUPLICADO -> {
                err = "Error :el identificador '" + tokenRecibido.getValor() + "' ya se encuentra declarado";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }
            case IDENTIFICADOR_NOIDENTIFICADO -> {
                err = "Error : identificador '" + tokenRecibido.getValor() + "' no identificado";
                System.out.println(err);
                escribirErrorLog(err, tokenRecibido, archivoSalida);
                break;
            }

        }
        System.exit(error.ordinal());

    }

    private static void escribirErrorLog(String error, Token token, String pathFile) {
        try (RandomAccessFile raf = new RandomAccessFile(pathFile.toUpperCase().replace(".PL0", "_LOG.txt"), "rw")) {
            // Mueva el puntero al final del archivo
            raf.seek(raf.length());
            // Retroceder 4 posiciones
            long nuevaPosicion = raf.getFilePointer() - (token.getValor().length() + 1);

            // Verifica que la nueva posición no sea negativa
            if (nuevaPosicion >= 0) {
                raf.seek(nuevaPosicion); // Mueve el puntero a la nueva posición
                raf.writeBytes("^" + token.getValor().trim());
                raf.seek(raf.length());
                raf.writeBytes(System.lineSeparator());
                raf.writeBytes(error);
            } else {
                System.out.println("No se puede retroceder más allá del inicio del archivo.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }

}
