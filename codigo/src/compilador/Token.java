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
    
    public boolean esIdentificador()
    {
        return "IDENTIFICADOR".equals(this.getTipo());
    }
    public boolean esCadena()
    {
        return "CADENA".equals(this.getTipo());
    }
    
    public boolean esNumero()
    {
        return "NUMERO".equals(this.getTipo());
    }
    public boolean esSignoCondicion(){
        return ("=".equals(this.getValor()) || "<>".equals(this.getValor()) || "<".equals(this.getValor()) 
                || "<=".equals(this.getValor())  || ">".equals(this.getValor()) || ">=".equals(this.getValor())
                );
    }
    public boolean esSignoExpresion(){ return ("+".equals(this.getValor()) || "-".equals(this.getValor()));}
    
    public boolean esSignoTermino(){ return ("*".equals(this.getValor()) || "/".equals(this.getValor()));}
      
    public boolean esConstrante()
    {
        return "CONST".equals(this.getValor());
    }
    public int getNroLinea() {
        return nroLinea;
    }
    public void setNroLinea(int nroLinea) {
        this.nroLinea = nroLinea;
    }
    @Override
    public String toString() {
        return "Token{" + "type=" + this.Tipo + ", value='" +this.valor  + ", Linea='" +this.getNroLinea()  + '\'' + '}';
    }
    
}
