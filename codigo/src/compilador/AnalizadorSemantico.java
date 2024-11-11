package compilador;

import java.util.ArrayList;

public class AnalizadorSemantico {

    static RegistroSemantico[] tabla = new RegistroSemantico[Constantes.DECLARACIONES_MAX - 1];    
    String arhivoSalidaLog;
    static int posicion = 0;

    public AnalizadorSemantico(String archSalidaLog) {
        this.arhivoSalidaLog = archSalidaLog;
    }

    public void declarar(RegistroSemantico identificador, int desde, int hasta) {
        validarPosicion();

        RegistroSemantico encontrado = buscar(identificador.getNombre(), hasta, desde);

        if (encontrado != null) {
            System.out.println("Error: El identificador " + identificador.getNombre()
                    + " ya ha sido declarado");
            System.exit(1);
        }

        tabla[hasta] = identificador;
        // System.out.println(hasta + identificador.toString());
    }

    static void validarTipo(String token, TipoIdentificador tipo, int hasta) {
        RegistroSemantico identificador = buscar(token, hasta, 0);
        switch (tipo) {
            case TipoIdentificador.CONST:
                if (!identificador.getTipo().equals(TipoIdentificador.CONST)) {
                    mostrarMensajeDeError(token, identificador.getTipo(), tipo);
                    System.exit(1);
                }
                break;
            case TipoIdentificador.VAR:
                if (!identificador.getTipo().equals(TipoIdentificador.CONST)
                        && !identificador.getTipo().equals(TipoIdentificador.VAR)) {
                    mostrarMensajeDeError(token, identificador.getTipo(), tipo);
                    System.exit(1);
                }
                break;
            case TipoIdentificador.PROCEDURE:
                if (!identificador.getTipo().equals(TipoIdentificador.PROCEDURE)) {
                    mostrarMensajeDeError(token, identificador.getTipo(), tipo);
                    System.exit(1);
                }
                break;

            default:
                break;
        }
    }

    static void mostrarMensajeDeError(String token, TipoIdentificador tipoEsperado, TipoIdentificador tipoRecibido) {
        System.err.println("Error: " + token + " es del tipo " + tipoRecibido + " pero se esperaba " + tipoEsperado);
    }

    static RegistroSemantico buscar(String token, int desde, int hasta) {
        for (int i = desde-1; i >= hasta; i--) {
            if (tabla[i].getNombre().equals(token)) {
                return tabla[i];
            }
        }
        return null;
    }

    static int getSize() {
        return posicion;
    }

    private void validarPosicion() {
        if (posicion >= Constantes.DECLARACIONES_MAX - 1) {
            System.out.println(
                    "Error: Se ha alcanzado el límite de declaraciones (" + Constantes.DECLARACIONES_MAX + ")");
            System.exit(1);
        }
    }
}
