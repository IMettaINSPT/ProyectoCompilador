
import java.util.*;

public class ScannerToken {

    public List<Token> GetTokens(String cadena) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        //lo uso para indicar si hubo error de sintaxis
        Token.Type tipo = Token.Type.ERROR;
        char currentChar;

        if (String.valueOf(cadena).equals("begin")) {
            tokens.add(createToken(cadena, Token.Type.PALABRARESERVADA));
            return tokens;
        }

        for (int i = 0; i < cadena.length(); i++) {
            currentChar = cadena.charAt(i);

            // Ignorar espacios en blanco, pero es un caracter delimitador (me corta el token)
            if (Character.isWhitespace(currentChar)) {

                Token.Type tipoOrig = tipo;
                if (currentToken.length() > 0) {

                    tipo = switch (String.valueOf(currentToken)) {
                        case "end" ->
                            Token.Type.PALABRARESERVADA;
                        case "begin" ->
                            Token.Type.PALABRARESERVADA;
                        case "call" ->
                            Token.Type.PALABRARESERVADA; 
                        case "procedure" ->
                            Token.Type.PALABRARESERVADA;
                        case "while" ->
                            Token.Type.PALABRARESERVADA;                      
                        default ->
                            tipoOrig;
                    };
                    tokens.add(createToken(currentToken.toString(), tipo));
                    currentToken.setLength(0); // Reiniciar el token actual
                }
                continue;
            }
            // si tengo un token antes de la coma, lo separo onda a,x
            if (String.valueOf(currentChar).equals(",")) {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString(), tipo));
                    currentToken.setLength(0); // Reiniciar el token actual
                }
                currentToken.append(currentChar);
                //agrego  el token coma
                tipo = Token.Type.COMA;
                tokens.add(createToken(currentToken.toString(), tipo));
                currentToken.setLength(0); // Reiniciar el token actual
                continue;
            }

            // si tengo un token antes de la coma, lo separo onda a,x
            if (String.valueOf(currentChar).equals(";")) {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString(), tipo));
                    currentToken.setLength(0); // Reiniciar el token actual
                }
                currentToken.append(currentChar);
                //agrego  el token coma
                tipo = Token.Type.PUNTOYCOMA;
                tokens.add(createToken(currentToken.toString(), tipo));
                currentToken.setLength(0); // Reiniciar el token actual
                continue;
            }

            // si token esta vacio es que arranco un token nuevo
            if (Character.isLetter(currentChar)) {
                currentToken.append(currentChar);
                //si la token que estoy armando no esta vacio y NO empieza con una letra es un error s/ automata
                if (currentToken.length() > 0) {
                    if (!Character.isAlphabetic(currentToken.charAt(0))) {
                        tipo = Token.Type.ERROR;
                    } else {
                        // sigo agregando caracteres al tokem
                        tipo = Token.Type.IDENTIFICADOR;
                    }
                }

            } else if (Character.isDigit(currentChar)) {
                currentToken.append(currentChar);
                //si la token que estoy armando no esta vacio y  NO con una numero es un error s/ automata
                if (currentToken.length() > 0 && !Character.isDigit(currentToken.charAt(0))) {
                    tipo = Token.Type.ERROR;
                } else {
                    tipo = Token.Type.NUMERO;
                }
            } else if (String.valueOf(currentChar).equals(":")) {
                currentToken.append(currentChar);
                if (currentToken.length() > 0) {
                    tipo = Token.Type.ERROR;
                } 
                
                //DOS PUNTOS SOLO NO ES VALIDO

            } else if (String.valueOf(currentChar).equals("=")) {
                currentToken.append(currentChar);
                if (currentToken.length() > 0) {
                    tipo = switch (String.valueOf(currentToken.charAt(0))) {
                        case "<" ->
                            Token.Type.MENORIGUAL;
                        case ">" ->
                            Token.Type.MAYORIGUAL;
                         case ":" ->
                            Token.Type.ASIGNACION;
                        default ->
                            Token.Type.ERROR;
                    };
                } else {
                    tipo = Token.Type.IGUAL;
                }
            } else if (String.valueOf(currentChar).equals("<")) {
                currentToken.append(currentChar);
                if (currentToken.length() > 0) {
                    tipo = Token.Type.ERROR;
                } else {
                    tipo = Token.Type.MENOR;
                }
            } else if (String.valueOf(currentChar).equals(">")) {
                currentToken.append(currentChar);
                if (currentToken.length() > 0) {
                    if (String.valueOf(currentToken.charAt(0)).equals("<")) {
                        tipo = Token.Type.DISTINTO;
                    } else {
                        tipo = Token.Type.ERROR;
                    }
                } else {
                    tipo = Token.Type.MAYOR;
                }
            } else if (String.valueOf(currentChar).equals(".")) {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString(), tipo));
                    currentToken.setLength(0); // Reiniciar el token actual
                }
                currentToken.append(currentChar);
                //agrego  el token coma
                tipo = Token.Type.PUNTO;
                tokens.add(createToken(currentToken.toString(), tipo));
                currentToken.setLength(0); // Reiniciar el token actual

            } else if (String.valueOf(currentChar).equals("(")) {
                if (currentToken.length() > 0) {
                    if (String.valueOf(currentToken).equals("readln") || String.valueOf(currentToken).equals("writeln")) {
                        tipo = Token.Type.PALABRARESERVADA;
                        tokens.add(createToken(currentToken.toString(), tipo));
                        currentToken.setLength(0); // Reiniciar el token actual
                        tokens.add(createToken("(", Token.Type.PARENTESIS));
                        currentToken.setLength(0); // Reiniciar el token actual
                    } else {
                        currentToken.append(currentChar);
                        tipo = Token.Type.ERROR;
                    }
                } else {
                    currentToken.append(currentChar);
                    tipo = Token.Type.PARENTESIS;
                }
            } else if (String.valueOf(currentChar).equals(")")) {
                if (currentToken.length() > 0) {
                    if (tipo == Token.Type.IDENTIFICADOR) {
                        tokens.add(createToken(String.valueOf(currentToken), tipo));
                        currentToken.setLength(0); // Reiniciar el token actual
                        tokens.add(createToken(")", Token.Type.PARENTESIS));
                        currentToken.setLength(0); // Reiniciar el token actual
                    }
                }
            } else if (isOperator(currentChar)) {
                if (currentToken.length() > 0) {
                    if (tipo == Token.Type.IDENTIFICADOR) {
                        tokens.add(createToken(String.valueOf(currentToken), tipo));
                        currentToken.setLength(0); // Reiniciar el token actual
                          tipo = switch (String.valueOf(currentChar)) {
                        case "+" ->
                            Token.Type.SUMA;
                        case "-" ->
                            Token.Type.RESTA;
                        case "/" ->
                            Token.Type.DIVISION;
                        default ->
                            Token.Type.MULTIPLICACION;
                    };                     
                        tokens.add(createToken(String.valueOf(currentChar), tipo));
                        currentToken.setLength(0); // Reiniciar el token actual
                    }
                }
            }

        }
        return tokens;
    }

    private boolean isOperator(char c) {
        char[] OPERATORS = {'+', '-', '*', '/'};

        for (char op : OPERATORS) {
            if (c == op) {
                return true;
            }
        }
        return false;
    }

    private Token createToken(String tokenStr, Token.Type t) {

        // Incorpora un caso si se necesita más logica
        return new Token(t, tokenStr);
    }

}
