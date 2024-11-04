package compilador;

import java.util.ArrayList;

public class AnalizadorSemantico {

    RegistroSemantico tablaSemantica[];
    String arhivoSalidaLog;

    public AnalizadorSemantico(String archSalidaLog) {
        tablaSemantica = new RegistroSemantico[1024];
        this.arhivoSalidaLog = archSalidaLog;
    }

    public void agregar(String identificador, String tipoIdent, int valorIdent, int base, int desplazamiento, String scope) {
        RegistroSemantico aux = leer(identificador, base, desplazamiento, scope);
        if (aux != null) {
            Errores.mostrarError(Errores.erroresEnum.IDENTIFICADOR_DUPLICADO, new Token(identificador, identificador), this.arhivoSalidaLog);

            tablaSemantica[base + desplazamiento] = new RegistroSemantico(identificador, tipoIdent, valorIdent);
        }
    }

    public int getValorRegistro(String identificador, int base, int desplazamiento, String scope) {
            return leer(identificador, base, desplazamiento, scope).getValor();        
    }

    private RegistroSemantico leer(String identificador, int base, int desplazamiento, String scope) {
        if (scope.equals("DECLARACION")) {
            for (int i = base; i <= (base + desplazamiento - 1); i++) {
                if (tablaSemantica[i].getNombre().equals(identificador)) {
                    return tablaSemantica[i];
                }
            }
        } else {
            for (int i = (base + desplazamiento - 1); i >= 0; i--) {
                if (tablaSemantica[i].getNombre().equals(identificador)) {
                    return tablaSemantica[i];
                }
            }
        }
        return null;

    }

    public boolean busquedaYchequeo(String identificador, int base, int desplazamiento, ArrayList<String> tipoIdentificadoresEsperados, String scope, Token tokenIdent) {
        RegistroSemantico aux = leer(identificador, base, desplazamiento, scope);
        String tipos = "";
        if (aux == null) {
            Errores.mostrarError(Errores.erroresEnum.IDENTIFICADOR_NOIDENTIFICADO, tokenIdent, arhivoSalidaLog);
        }

        for (int i = 0; i < tipoIdentificadoresEsperados.size(); i++) {
            if (i > 0) {
                tipos += ",";
            }
            tipos += tipoIdentificadoresEsperados.get(i);

            if (tipoIdentificadoresEsperados.get(i).equals(aux.getTipo())) {
                return true;
            }
        }
        tokenIdent.setTiposEsperados(tipos);
        Errores.mostrarError(Errores.erroresEnum.TIPO_IDENT_ERROR, tokenIdent, arhivoSalidaLog);

        return false;
    }
}
