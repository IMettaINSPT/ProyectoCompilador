package compilador;

public class Token {
    private String Tipo;
    private String valor;
    private int nroLinea; 
    
    public Token(String tipo, String valor, int NroLinea) {
        this.Tipo = tipo;
        this.valor = valor;
        this.nroLinea = NroLinea;
    }

    public enum TipoToken {
        PALABRA_RESERVADA, IDENTIFICADOR, NUMERO, OPERADOR, SIMBOLO, DESCONOCIDO
    }
    
    
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    public String getValor() {
        return  valor.toUpperCase();
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public boolean EsIdentificador()
    {
        return "IDENTIFICADOR".equals(this.getTipo());
    }
    
    public boolean EsNumero()
    {
        return "NUMERO".equals(this.getTipo());
    }
    public boolean EsSignoCondicion(){
        return ("=".equals(this.getTipo()) || "<>".equals(this.getTipo()) || "<".equals(this.getTipo()) 
                || "<=".equals(this.getTipo())  || ">".equals(this.getTipo()) || ">=".equals(this.getTipo())
                );
    }
    public boolean EsSignoExpresion(){ return ("+".equals(this.getTipo()) || "-".equals(this.getTipo()));}
    
    public boolean EsSignoTermino(){ return ("*".equals(this.getTipo()) || "/".equals(this.getTipo()));}
    
    public int getNroLinea() {
        return nroLinea;
    }
    public void setNroLinea(int nroLinea) {
        this.nroLinea = nroLinea;
    }
    public boolean EsConstrante()
    {
        return "CONST".equals(this.getTipo());
    }
    
    @Override
    public String toString() {
        return "Token{" + "type=" + this.Tipo + ", value='" +this.valor  + ", Linea='" +this.getNroLinea()  + '\'' + '}';
    }
    
}
