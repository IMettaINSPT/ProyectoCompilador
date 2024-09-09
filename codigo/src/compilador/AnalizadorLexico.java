package compilador;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AnalizadorLexico {
    private String cadena;
    private FileReader lector;
    private char caracter;
    private int ultimoCaracterLeido = -1; // Almacena el último carácter leído
    
    // Conjuntos de palabras reservadas y símbolos del lenguaje PL/0
    private static final Set<String> PALABRAS_RESERVADAS = new HashSet<>();
    private static final Set<Character> SIMBOLOS = new HashSet<>();

    static {
        // Inicialización de palabras reservadas del lenguaje PL/0
        PALABRAS_RESERVADAS.add("const");
        PALABRAS_RESERVADAS.add("var");
        PALABRAS_RESERVADAS.add("procedure");
        PALABRAS_RESERVADAS.add("call");
        PALABRAS_RESERVADAS.add("begin");
        PALABRAS_RESERVADAS.add("end");
        PALABRAS_RESERVADAS.add("if");
        PALABRAS_RESERVADAS.add("then");
        PALABRAS_RESERVADAS.add("else");
        PALABRAS_RESERVADAS.add("while");
        PALABRAS_RESERVADAS.add("do");
        PALABRAS_RESERVADAS.add("read");
        PALABRAS_RESERVADAS.add("readln");
        PALABRAS_RESERVADAS.add("write");
        PALABRAS_RESERVADAS.add("writeln");

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
    }

    public AnalizadorLexico(FileReader lector) {
        this.lector = lector;
        cadena = "";
    }

    public Token escanear(int NroLineaAnt) throws IOException {
        cadena = "";
        int ch;
        // Usar último carácter leído si existe, de lo contrario leer uno nuevo
        if (ultimoCaracterLeido != -1) {
            ch = ultimoCaracterLeido;
            ultimoCaracterLeido = -1; // Resetea para que no se use de nuevo
        } else {
            ch = lector.read();
        }

        if(ch ==13 || ch == 10)
        {
            NroLineaAnt+=1;
        }
        // Ignorar espacios en blanco y caracteres de control
        while (ch != -1 && Character.isWhitespace((char) ch)) {
            ch = lector.read();
        }

        if (ch == -1) {
            return new Token("EOF","EOF",NroLineaAnt);            
         //   return "EOF";
        } else {
            char currentChar = (char) ch;

            // Verificar si es un símbolo
            if (SIMBOLOS.contains(currentChar)) {
                cadena += currentChar;
                
                if( String.valueOf(currentChar).equals(":") || String.valueOf(currentChar).equals("<") || String.valueOf(currentChar).equals(">")   )
                {
                      while ((ch = lector.read()) != -1 && !(Character.isWhitespace((char) ch))) {
                          cadena += (char) ch;
                        }                                
                }       
                 return new Token("SIMBOLOS",cadena,NroLineaAnt);
               // return "SIMBOLOS: " + cadena;
            }

            // Verificar si es una letra o dígito para construir palabras o números
            if (Character.isLetter(currentChar)) {
                cadena += currentChar;
                while ((ch = lector.read()) != -1 && (Character.isLetterOrDigit((char) ch))) {
                    cadena += (char) ch;
                }
                ultimoCaracterLeido = ch; // Guardar el último carácter leído

                // Verificar si la palabra es reservada
                if (PALABRAS_RESERVADAS.contains(cadena)) {
                     return new Token("PALABRA RESERVADA",cadena,NroLineaAnt);
                   // return "PALABRA RESERVADA: " + cadena;
                } else {
                    return new Token("IDENTIFICADOR",cadena,NroLineaAnt);

                  //  return "IDENTIFICADOR: " + cadena;
                }
            }

            // Verificar si es un número
            if (Character.isDigit(currentChar)) {
                cadena += currentChar;
                while ((ch = lector.read()) != -1 && Character.isDigit((char) ch)) {
                    cadena += (char) ch;
                }
                ultimoCaracterLeido = ch; // Guardar el último carácter leído
                return new Token("NUMERO",cadena,NroLineaAnt);

              //  return "" + cadena;
            }

            // Si el carácter no es reconocido
            return new Token("NO IDENTIFICADO ",String.valueOf(currentChar),NroLineaAnt); //"" + currentChar;
        }
    }
}
