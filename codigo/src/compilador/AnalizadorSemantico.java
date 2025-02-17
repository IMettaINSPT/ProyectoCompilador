package compilador;

public class AnalizadorSemantico {

    private RegistroSemantico tablaRegistroSemanticoes[] = new RegistroSemantico[100]; //muy berreta, lograr almacenamiento dinamico
    private int ultimaPosicion = 0;
    private int contadorVariables = 0;

    /**
     *
     * @param base
     * @param desplazamiento
     * @param nombre
     * @param tipo
     * @param valor
     * @return
     */
    public int nuevoIdentificador(int base, int desplazamiento, String nombre, String tipo, int valor) {

        RegistroSemantico ident = new RegistroSemantico(nombre, tipo, valor);
        if (getValorIdent(base + desplazamiento - 1, base, nombre) == -1) {

            tablaRegistroSemanticoes[ultimaPosicion] = ident;

            if (ident.getTipo().equals("var")) {
                contadorVariables++;
            }

            //System.out.println("NUEVO IDENTIFICADOR AGREGADO: " + nombre + " " + tipo + " " + valor + " en la posicion " + ultimaPosicion);
            ultimaPosicion++;
        }

        return 0;
    }

    public String verificarTipoIdentificador(int posicion, int base, String nombre) {

        String tipoIdent = new String();

        for (int i = posicion; i > base; i--) {
            RegistroSemantico ident = tablaRegistroSemanticoes[i - 1];
            if (ident.getNombre().equals(nombre)) {
                return ident.getTipo().toUpperCase();
            }
        }

        return "identificador no encontrado";
    }

    public int getValorIdent(int posicion, int base, String nombre) {
        int salida = -1;
        //String tipo=new String();   
        for (int i = posicion; i > base; i--) {
            RegistroSemantico ident = tablaRegistroSemanticoes[i - 1];
            if (ident.getNombre().equals(nombre)) {
                salida = ident.getValor();
                return salida;
            }
        }
        return salida;
    }

    public int busquedaPosIdent(int posicion, int base, String nombre) {
        int salida = -1;

        for (int i = posicion - 1; i >= base; i--) {
            RegistroSemantico ident = tablaRegistroSemanticoes[i];
            if (ident.getNombre().equals(nombre)) {
                return i;
            }
        }
        return salida;
    }

    public int getUltimaPosicion() {
        return ultimaPosicion;
    }

    public int getCantVar() {
        return contadorVariables;
    }
}
