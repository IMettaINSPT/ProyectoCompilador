package compilador;

import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

public class AnalizadorLexico {

    private String cadena;
    private FileReader lector;
    private FileOutputStream escritor;
    private char caracter;
    private String pathFile ;
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
        PALABRAS_RESERVADAS.add("END");
        PALABRAS_RESERVADAS.add("IF");
        PALABRAS_RESERVADAS.add("THEN");
        PALABRAS_RESERVADAS.add("ELSE");
        PALABRAS_RESERVADAS.add("WHILE");
        PALABRAS_RESERVADAS.add("DO");
        PALABRAS_RESERVADAS.add("READ");
        PALABRAS_RESERVADAS.add("READLN");
        PALABRAS_RESERVADAS.add("WRITE");
        PALABRAS_RESERVADAS.add("WRITELN");

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
            return new Token("EOF", "EOF");
            //   return "EOF";
        } else {
            char currentChar = (char) ch;

            // Verificar si es un símbolo
            if (SIMBOLOS.contains(currentChar)) {
                cadena += currentChar;

                if (String.valueOf(currentChar).equals(":") || String.valueOf(currentChar).equals("<") || String.valueOf(currentChar).equals(">")) {
                    while ((ch = leerChar()) != -1 && SIMBOLOS.contains((char) ch)) {
                        cadena += (char) ch;
                    }
                    ultimoCaracterLeido = ch;

                }
                return new Token("SIMBOLOS", cadena);
                // return "SIMBOLOS: " + cadena;
            }

            if (currentChar == '\'') {
                cadena += currentChar;
                while ((ch = leerChar()) != -1) {
                    currentChar = (char) ch;
                    cadena += currentChar;
                    if (currentChar == '\'') {
                        ultimoCaracterLeido = -1;
                        return new Token("CADENA", cadena);
                    }
                    if (System.lineSeparator().equals(ch)) {
                        ultimoCaracterLeido = -1;
                        return new Token("DESCONOCIDO", cadena);
                    }
                }
                if (System.lineSeparator().equals(ch)) {
                    ultimoCaracterLeido = -1;
                    return new Token("DESCONOCIDO", cadena);
                }

                if (ch == -1) {
                    ultimoCaracterLeido = -1;
                    return new Token("DESCONOCIDO", cadena);
                }
                ultimoCaracterLeido = -1; // Guardar el último carácter leído
                return new Token("CADENA", cadena);
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
                    return new Token("PALABRA RESERVADA", cadena);
                    // return "PALABRA RESERVADA: " + cadena;
                } else {
                    return new Token("IDENTIFICADOR", cadena);

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
                return new Token("NUMERO", cadena);

                //  return "" + cadena;
            }

            // Si el carácter no es reconocido
            return new Token("NO IDENTIFICADO ", String.valueOf(currentChar)); //"" + currentChar;
        }
    }
}
