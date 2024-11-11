package compilador;

public class Token {

    private TipoToken Tipo;
    private String valor;
    public Token(TipoToken tipo, String valor) {
        this.Tipo = tipo;
        this.valor = valor;
    }

    public TipoToken getTipo() {
        return Tipo;
    }

    public void setTipo(TipoToken Tipo) {
        this.Tipo = Tipo;
    }

    public String getValor() {
        return valor.toUpperCase();
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean esIdentificador() {
        return this.Tipo.equals(TipoToken.IDENTIFICADOR);
    }
    public Boolean esEOF() {
        return this.Tipo.equals(TipoToken.EOF);
    }
    public boolean esCadena() {
        return this.Tipo.equals(TipoToken.CADENA);
    }

    public boolean esNumero() {
        return this.Tipo.equals(TipoToken.NUMERO);
    }

    public boolean esSignoCondicion() {
        return this.Tipo.equals(TipoToken.MENOR)
                || this.Tipo.equals(TipoToken.MENOR_O_IGUAL)
                || this.Tipo.equals(TipoToken.MAYOR)
                || this.Tipo.equals(TipoToken.MAYOR)
                || this.Tipo.equals(TipoToken.IGUAL)
                || this.Tipo.equals(TipoToken.DIFERENTE);
    }

    public boolean esSignoExpresion() {
      return this.Tipo.equals(TipoToken.SUMA);
    }

    public boolean esSignoTermino() {
        return this.Tipo.equals(TipoToken.MULTIPLICACION) || this.Tipo.equals(TipoToken.DIVISION);
    }

    public boolean esConstrante() {
        return this.getTipo().equals(TipoToken.PALABRA_RESERVADA) && "CONST".equals(this.getValor());
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + this.Tipo + ", value='" + this.valor + '\'' + '}';
    }

}
