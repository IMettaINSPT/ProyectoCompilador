package compilador;

public class RegistroSemantico {


    private TipoIdentificador tipo;
    private String nombre;
    private Integer valor;

    public RegistroSemantico(TipoIdentificador tipo) {
        this.tipo = tipo;
    }

    public RegistroSemantico(TipoIdentificador tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public RegistroSemantico(TipoIdentificador tipo, String nombre, Integer valor) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoIdentificador getTipo() {
        return tipo;
    }

    public Integer getValor() {
        return valor;
    }

    public String toString() {
        return "------------------------------------------------\nIdentificador: " + tipo + " " + nombre + " = " + valor + "\n------------------------------------------------";
    }
}
