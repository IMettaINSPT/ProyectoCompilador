package compilador;

public class RegistroSemantico {
    private final String _nombre;
    private  int _valor;
    private final String _tipo;

    /**
 *
 *
 * @param nombre.
 * @param tipo.
 * @param valor .
 */
    public RegistroSemantico(String nombre,String tipo, int valor) {
        this._nombre = nombre;
        this._tipo = tipo;
        this._valor = valor;
    }

    public String getNombre() {
        return _nombre;
    }

    public int getValor() {
        return _valor;
    }

    public String getTipo() {
        return _tipo;
    }
    public void setValor(int _valor) {
        this._valor = _valor;
    }
}
