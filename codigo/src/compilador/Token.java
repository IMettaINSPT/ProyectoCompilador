package compilador;

public class Token {
    private String Tipo;
    private String valor;
    
    public Token(String tipo, String valor) {
        this.Tipo = tipo;
        this.valor = valor;
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

    @Override
    public String toString() {
        return "Token{" + "type=" + this.Tipo + ", value='" +this.valor   + '\'' + '}';
    }
    
}
