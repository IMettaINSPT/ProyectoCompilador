package compilador;

import java.util.ArrayList;

public class AnalizadorSemantico {
    
    int cantVariables = 0;
    RegistroSemantico tablaSemantica[];
    String arhivoSalidaLog ;
    
    public AnalizadorSemantico(String archSalidaLog) {
        tablaSemantica = new RegistroSemantico[1024];
        this.arhivoSalidaLog = archSalidaLog;
    }
    
    public void agregar(String identificador, String tipoIdent, int valorIdent, int base, int desplazamiento, String scope) {
        RegistroSemantico aux = leer(identificador, base, desplazamiento, scope);
        if (aux != null) {
            Errores.mostrarError(Errores.erroresEnum.IDENTIFICADOR_DUPLICADO, new Token(identificador, identificador), this.arhivoSalidaLog);
        }
        
        if (tipoIdent.equals("VAR")) {
            valorIdent = (this.cantVariables * 4) - 1;
            
            if(valorIdent<0){valorIdent = 0;}
            
            cantVariables++;
        }
        
        //ACA SE GUARDA EL SIZE DEL VECTOR DEL MOMENTO AL CARGAR EL VECTOR

        if (tipoIdent.equals("PROCEDURE")) {
            valorIdent = 0;
        }
        tablaSemantica[base + desplazamiento] = new RegistroSemantico(identificador, tipoIdent, valorIdent);
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
        Errores.mostrarError(Errores.erroresEnum.TIPO_IDENT_ERROR, tokenIdent,arhivoSalidaLog);
        
        return false;
    }
}
