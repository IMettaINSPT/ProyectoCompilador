package compilador;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AnalizadorLexico {

    private String cadena;
    private FileReader lector;
    private FileOutputStream escritor;
    private char caracter;
    private String pathFile;
    private int ultimoCaracterLeido = -1; // Almacena el último carácter leído

    // Conjuntos de palabras reservadas y símbolos del lenguaje PL/0
    private static final Set<String> PALABRAS_RESERVADAS = new HashSet<>();
    private static final Set<Character> SIMBOLOS = new HashSet<>();

    static {
        // Inicialización de palabras reservadas del lenguaje PL/0
        PALABRAS_RESERVADAS.add("CONST");
        PALABRAS_RESERVADAS.add("PROCEDURE");
        PALABRAS_RESERVADAS.add("VAR");
        PALABRAS_RESERVADAS.add("CALL");
        PALABRAS_RESERVADAS.add("BEGIN");
        PALABRAS_RESERVADAS.add("NEWLINE");
        PALABRAS_RESERVADAS.add("END");
        PALABRAS_RESERVADAS.add("IF");
        PALABRAS_RESERVADAS.add("FOR");
        PALABRAS_RESERVADAS.add("THEN");
        PALABRAS_RESERVADAS.add("ELSE");
        PALABRAS_RESERVADAS.add("WHILE");
        PALABRAS_RESERVADAS.add("DO");
        PALABRAS_RESERVADAS.add("TO");
        PALABRAS_RESERVADAS.add("REPEAT");
        PALABRAS_RESERVADAS.add("SWITCH");
        PALABRAS_RESERVADAS.add("TIMES");
        PALABRAS_RESERVADAS.add("READ");
        PALABRAS_RESERVADAS.add("READLN");
        PALABRAS_RESERVADAS.add("WRITE");
        PALABRAS_RESERVADAS.add("WRITELN");
        PALABRAS_RESERVADAS.add("HALT");

        // Inicialización de símbolos del lenguaje PL/0
        SIMBOLOS.add('+');
        SIMBOLOS.add('-');
        SIMBOLOS.add('*');
        SIMBOLOS.add('/');
        SIMBOLOS.add('(');
        SIMBOLOS.add(')');
        SIMBOLOS.add('=');
        SIMBOLOS.add('<');
        SIMBOLOS.add('>');
        SIMBOLOS.add(':');
        SIMBOLOS.add(';');
        SIMBOLOS.add(',');
        SIMBOLOS.add('.');
        SIMBOLOS.add('#');
    }

    public String getPathFile() {
        return pathFile;
    }

    public AnalizadorLexico(String path) throws IOException {
        this.pathFile = path;
        this.lector = new FileReader(path);
        this.escritor = new FileOutputStream(path.toUpperCase().replace(".PL0", "_LOG.txt"));
        cadena = "";

    }

    private int leerChar() throws IOException {
        int ch = this.lector.read();
        if (ch != -1) {
            this.escritor.write(ch);
        }
        return ch;
    }

    public Token escanear() throws IOException {
        cadena = "";
        int ch;
        // Usar último carácter leído si existe, de lo contrario leer uno nuevo
        if (ultimoCaracterLeido != -1) {
            ch = ultimoCaracterLeido;
            ultimoCaracterLeido = -1; // Resetea para que no se use de nuevo
        } else {
            ch = leerChar();
        }
        // Ignorar espacios en blanco y caracteres de control
        while (ch != -1 && Character.isWhitespace((char) ch)) {
            ch = leerChar();
        }

        if (ch == -1) {
            return new Token(TipoToken.EOF, "EOF");
            //   return "EOF";
        } else {
            char currentChar = (char) ch;

            // Verificar si es un símbolo
            if (SIMBOLOS.contains(currentChar)) {
                cadena += currentChar;

                if (String.valueOf(currentChar).equals(":") || String.valueOf(currentChar).equals("<") || String.valueOf(currentChar).equals(">") || String.valueOf(currentChar).equals("+") || String.valueOf(currentChar).equals("-")) {
                    while ((ch = leerChar()) != -1 && SIMBOLOS.contains((char) ch)) {
                        if (String.valueOf(currentChar).equals("-")) {
                            if (!String.valueOf((char) ch).equals("-")) {
                                ultimoCaracterLeido = ch;
                                return new Token(identificarSimbolo(cadena), cadena);

                            }
                        }

                        cadena += (char) ch;
                        if (cadena.equals("++") || cadena.equals("--")) {
                            return new Token(identificarSimbolo(cadena), cadena);

                        }

                    }
                    ultimoCaracterLeido = ch;

                }

                return new Token(identificarSimbolo(cadena), cadena);
                // return "SIMBOLOS: " + cadena;
            }

            if (currentChar == '\'') {
                cadena += currentChar;
                while ((ch = leerChar()) != -1) {
                    currentChar = (char) ch;
                    cadena += currentChar;
                    if (currentChar == '\'') {
                        ultimoCaracterLeido = -1;
                        //tecnicamente seria un palabra reservada y simbolo de puntuacion.
                        if (cadena.equals("ODD")) {
                            return new Token(TipoToken.ODD, cadena);
                        }

                        return new Token(TipoToken.CADENA, cadena);
                    }
                    if (System.lineSeparator().equals(ch)) {
                        ultimoCaracterLeido = -1;
                        return new Token(TipoToken.DESCONOCIDO, cadena);
                    }
                }
                if (System.lineSeparator().equals(ch)) {
                    ultimoCaracterLeido = -1;
                    return new Token(TipoToken.DESCONOCIDO, cadena);
                }

                if (ch == -1) {
                    ultimoCaracterLeido = -1;
                    return new Token(TipoToken.DESCONOCIDO, cadena);
                }
                ultimoCaracterLeido = -1; // Guardar el último carácter leído
                return new Token(TipoToken.CADENA, cadena);
            }
            // Verificar si es una letra o dígito para construir palabras o números
            if (Character.isLetter(currentChar)) {
                cadena += currentChar;
                while ((ch = leerChar()) != -1 && (Character.isLetterOrDigit((char) ch))) {
                    currentChar = (char) ch;
                    cadena += currentChar;
                }
                ultimoCaracterLeido = ch; // Guardar el último carácter leído

                // Verificar si la palabra es reservada
                if (PALABRAS_RESERVADAS.contains(cadena.toUpperCase())) {
                    return new Token(TipoToken.PALABRA_RESERVADA, cadena);
                    // return "PALABRA RESERVADA: " + cadena;
                } else {
                    return new Token(TipoToken.IDENTIFICADOR, cadena);

                    //  return "IDENTIFICADOR: " + cadena;
                }
            }

            // Verificar si es un número
            if (Character.isDigit(currentChar)) {
                cadena += currentChar;
                while ((ch = leerChar()) != -1 && Character.isDigit((char) ch)) {
                    cadena += (char) ch;
                }
                ultimoCaracterLeido = ch; // Guardar el último carácter leído
                return new Token(TipoToken.NUMERO, cadena);

                //  return "" + cadena;
            }

            // Si el carácter no es reconocido
            return new Token(TipoToken.DESCONOCIDO, String.valueOf(currentChar)); //"" + currentChar;
        }
    }

    private TipoToken identificarSimbolo(String simbolo) {
        switch (simbolo) {
            case "=" -> {
                return TipoToken.IGUAL;
            }
            case "<>" -> {
                return TipoToken.DIFERENTE;
            }
            case "<" -> {
                return TipoToken.MENOR;
            }
            case "<=" -> {
                return TipoToken.MENOR_O_IGUAL;
            }
            case ">" -> {
                return TipoToken.MAYOR;
            }
            case ">=" -> {
                return TipoToken.MAYOR_O_IGUAL;
            }
            case "+" -> {
                return TipoToken.SUMA;
            }
            case "-" -> {
                return TipoToken.RESTA;
            }
            case "*" -> {
                return TipoToken.MULTIPLICACION;
            }
            case "/" -> {
                return TipoToken.DIVISION;
            }
            case "(" -> {
                return TipoToken.PARENTESIS_APERTURA;
            }
            case ")" -> {
                return TipoToken.PARENTESIS_CIERRE;
            }
            case "." -> {
                return TipoToken.PUNTO;
            }
            case ":" -> {
                return TipoToken.DOS_PUNTOS;
            }
            case ";" -> {
                return TipoToken.PUNTO_Y_COMA;
            }
            case ":=" -> {
                return TipoToken.ASIGNACION;
            }
            case "ODD" -> {
                return TipoToken.ODD;
            }
            case "CADENA" -> {
                return TipoToken.CADENA;
            }
            case "EOF" -> {
                return TipoToken.EOF;
            }
            default -> {
                return TipoToken.DESCONOCIDO;
            }
        }

    }
}
