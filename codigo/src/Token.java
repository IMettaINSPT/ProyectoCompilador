public class Token {
 public enum Type {
        IDENTIFICADOR, PALABRARESERVADA, NUMERO, IGUAL, MENORIGUAL, DISTINTO,MENOR, MAYORIGUAL, MAYOR, ERROR
 ,MULTIPLICACION, DIVISION, SUMA, RESTA, PUNTOYCOMA, COMA, PUNTO , PARENTESIS, ASIGNACION
 }

    private final Type type;
    private final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}
