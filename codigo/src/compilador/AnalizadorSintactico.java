package compilador;

import java.io.IOException;

public class AnalizadorSintactico {

    private Token token;
    private final AnalizadorLexico lex;

    public AnalizadorSintactico(AnalizadorLexico scanner) {
        this.lex = scanner;
    }

    private enum ErroresSintacticos {
        FALTA_NUMERO, FALTA_PARENTESIS,FALTA_IGUAL,FALTA_SIGNO_TERMINO,FALTA_SIGNO_COND,FALTA_SIGNO_EXP, FALTA_IDENTIFICADOR,FALTA_DO, FALTA_ODD, FALTA_THEN,FALTA_PTOYCOMA, FALTA_ASIGNACION, DESCONOCIDO, FALTA_END
    }

    private void MostrarError(ErroresSintacticos error, int linea) {

        switch (error) {
            case ErroresSintacticos.FALTA_IDENTIFICADOR -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un identificador");
            }
            case ErroresSintacticos.FALTA_PARENTESIS -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un parentesis ( ,) ");
            }
            case ErroresSintacticos.FALTA_NUMERO -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un Numero");
            }
            case ErroresSintacticos.FALTA_IGUAL -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un igual (=)");
            }
            case ErroresSintacticos.FALTA_ASIGNACION -> {
                System.out.println("Error linea: " + linea + " : Se esperaba una asignacion (:=)");
            }
            case ErroresSintacticos.FALTA_PTOYCOMA -> {
                System.out.println("Error linea: " + linea + " : Se esperaba un Punto y coma (;)");
            }
            case ErroresSintacticos.DESCONOCIDO -> {
                System.out.println("Error linea: " + linea + " : DESCONOCIDO");
            }
            case ErroresSintacticos.FALTA_END -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada END");
            }
            case ErroresSintacticos.FALTA_THEN -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada THEN");
            }
             case ErroresSintacticos.FALTA_DO -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada DO");
            }
            case ErroresSintacticos.FALTA_ODD -> {
                System.out.println("Error linea: " + linea + " : se esperaba la palabra reservada ODD");
            }
            case ErroresSintacticos.FALTA_SIGNO_COND -> {
                System.out.println("Error linea: " + linea + " : se esperaba un signo de los siguientes <,>,<=,>=,=,<>");
            }
            case ErroresSintacticos.FALTA_SIGNO_EXP -> {
                System.out.println("Error linea: " + linea + " : se esperaba un signo de los siguientes +,-");
            }
            case ErroresSintacticos.FALTA_SIGNO_TERMINO -> {
                System.out.println("Error linea: " + linea + " : se esperaba un signo de los siguientes *,/");
            }
        }

    }

    public boolean Programa(Token tokenInicial) throws IOException {
// REGLA PROGRAMA  BLOQUE() -> Terminal(".")
        boolean huboError;
        // Primer token inicial 
        this.token = tokenInicial;
        huboError = Bloque();
        if (huboError) {
            return huboError;
        }
        if (this.token.getValor().equals(".")) {
            //Obtenego proximo token
            this.token = this.lex.escanear(token.getNroLinea());
        } else {
             MostrarError(ErroresSintacticos.DESCONOCIDO, this.token.getNroLinea());
              return true;
        }
        return huboError;
    }

    ;
  private boolean Bloque() throws IOException {

        switch (this.token.getValor()) {
            case "CONST" -> {
                // R 7 leo un termina const 
                // R 7 tengo que leer un identificador 
                this.token = this.lex.escanear(token.getNroLinea());

                if (this.token.EsIdentificador()) {
                    // R 7 tengo que leer un terminal "=" 
                    this.token = this.lex.escanear(token.getNroLinea());

                    if (this.token.getValor().equals("=")) {
                        //R7 , espero un numero 
                        this.token = this.lex.escanear(token.getNroLinea());

                        if (this.token.EsNumero()) {
                            //CONST a = 3, b = 7:
                            //R7 ACA PUEDO TERMINAR CON ; O SEGUIR CON BUCLE DE identificador ,
                            this.token = this.lex.escanear(token.getNroLinea());
                            if (this.token.getValor().equals(";")) {
                                //R7 
                                this.token = this.lex.escanear(token.getNroLinea());
                            }
                            while (token.getValor().equals(",")) {
                                // INICIO BUCLE  IDENT = NUMERO ,
                                this.token = this.lex.escanear(token.getNroLinea());
                                if (this.token.EsIdentificador()) {
                                    this.token = this.lex.escanear(token.getNroLinea());
                                    if (this.token.getValor().equals("=")) {
                                        this.token = this.lex.escanear(token.getNroLinea());
                                        if (this.token.EsNumero()) {
                                            this.token = this.lex.escanear(token.getNroLinea());
                                        } else {
                                            MostrarError(ErroresSintacticos.FALTA_NUMERO, this.token.getNroLinea());
                                            //System.out.println("SE esperaba NUMERO");
                                            return true;
                                        }
                                    } else {
                                        // System.out.println("SE ESPERABA =");
                                        MostrarError(ErroresSintacticos.FALTA_IGUAL, this.token.getNroLinea());
                                        return true;
                                    }
                                } else {
                                    MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                                    return true;
                                    //System.out.println("Se esperaba ident");
                                }
                            } //FIN BUCLE  IDENT = NUMERO ,
                            //TIENE QUE TERMINAR CON ; , SINO ERROR
                            if (this.token.getValor().equals(";")) {
                                this.token = this.lex.escanear(token.getNroLinea());
                            } else {
                                MostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
                                return true;
                                // System.out.println("SE ESPERABA ;");
                            }

                        } else {
                            MostrarError(ErroresSintacticos.FALTA_NUMERO, this.token.getNroLinea());
                            return true;
                            // System.out.println("ERROR SE ESPERABA UN NUMERO");
                        }

                    } else {
                        MostrarError(ErroresSintacticos.FALTA_IGUAL, this.token.getNroLinea());
                        return true;
                        // System.out.println("ERROR SE ESPERABA UN SIMBOLO =");
                    }

                } else {
                    MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                    return true;
                    // System.out.println("ERROR SE ESPERABA UN IDENTIFICADOR");
                }
            }

            case "VAR" -> {
                // R 7 tengo que leer un identificador 
                this.token = this.lex.escanear(token.getNroLinea());
                if (this.token.EsIdentificador()) {
                    this.token = this.lex.escanear(token.getNroLinea());

                    if (this.token.getValor().equals(";")) {
                        //R7 
                        this.token = this.lex.escanear(token.getNroLinea());
                    } else {
                        while (token.getValor().equals(",")) { //while var ident, ident ;
                            this.token = this.lex.escanear(token.getNroLinea());
                            if (this.token.EsIdentificador()) {
                                this.token = this.lex.escanear(token.getNroLinea());
                            } else {
                                MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                                return true;
                                // System.out.println("ERROR SE ESPERABA UN IDENTIFICADOR");
                            }
                        }  //while var ident, ident ;

                        if (this.token.getValor().equals(";")) {
                            MostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
                            return true;
                            //System.out.println("ERROR SE ESPERABA ;");
                        }
                    }
                } else {
                    MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                    return true;
                    // System.out.println("ERROR SE ESPERABA UN IDENTIFICADOR");
                }
            }
            case "PROCEDURE" -> {
                this.token = this.lex.escanear(token.getNroLinea());
                if (this.token.EsIdentificador()) {
                    this.token = this.lex.escanear(token.getNroLinea());
                    if (this.token.getValor().equals(";")) {
                        Bloque();
                        if (this.token.getValor().equals(";")) {
                            this.token = this.lex.escanear(token.getNroLinea());
                        } else {
                            MostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
                            return true;
                            // System.out.println("ERROR SE ESPERABA UN ;");
                        }
                    } else {
                        MostrarError(ErroresSintacticos.FALTA_PTOYCOMA, this.token.getNroLinea());
                        return true;
                        // System.out.println("ERROR SE ESPERABA UN ;");
                    }

                } else {
                    MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                    return true;
                    // System.out.println("ERROR SE ESPERABA UN IDENTIFICADOR");
                }

            }
            default ->
                Proposicion();

        }
        return false;
    }   ;
  
 private boolean Proposicion() throws IOException {
        boolean huboError = false;
        if (this.token.EsIdentificador()) {
            this.token = this.lex.escanear(token.getNroLinea());
            if (this.token.getValor().equals(":=")) {
                this.token = this.lex.escanear(token.getNroLinea());
                huboError = Expresion();
                if (huboError) {
                    return huboError;
                }
            } else {
                MostrarError(ErroresSintacticos.FALTA_ASIGNACION, this.token.getNroLinea());
                return true;
            }
        } else {
            switch (this.token.getValor()) {
                case "CALL" -> {
                    this.token = this.lex.escanear(token.getNroLinea());
                    if (this.token.EsIdentificador()) {
                        this.token = this.lex.escanear(token.getNroLinea());
                    } else {
                        MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                        return true;
                    }

                }
                case "BEGIN" -> {
                    this.token = this.lex.escanear(token.getNroLinea());
                    huboError = Proposicion();
                    if (huboError) {
                        return huboError;
                    }
                    while (this.token.getValor().equals(";")) {
                        this.token = this.lex.escanear(token.getNroLinea());
                        huboError = Proposicion();
                        if (huboError) {
                            return huboError;
                        }
                    }
                    if (this.token.getValor().equals("END")) {
                        this.token = this.lex.escanear(token.getNroLinea());
                    } else {
                        MostrarError(ErroresSintacticos.FALTA_IDENTIFICADOR, this.token.getNroLinea());
                        return true;
                    }
                }
                case "IF" -> {
                     this.token = this.lex.escanear(token.getNroLinea());
                     huboError = Condicion();
                     if (huboError) {  return huboError;   }
                     if(this.token.getValor().equals("THEN")){
                         huboError = Proposicion();
                         if (huboError) { return huboError; }
                     }else {
                        MostrarError(ErroresSintacticos.FALTA_THEN, this.token.getNroLinea());
                        return true;
                    }
                }
                case "WHILE" -> {
                    this.token = this.lex.escanear(token.getNroLinea());
                     huboError = Condicion();
                     if (huboError) {  return huboError;   }
                     if(this.token.getValor().equals("DO")){
                         huboError = Proposicion();
                         if (huboError) { return huboError; }
                     }else {
                        MostrarError(ErroresSintacticos.FALTA_DO, this.token.getNroLinea());
                        return true;
                    }
                }
            }
        }
        return huboError;
    }  ;
  private boolean Condicion() throws IOException {
      boolean huboError;
      this.token = this.lex.escanear(token.getNroLinea());
      if(this.token.getValor().equals("ODD"))
      {
           this.token = this.lex.escanear(token.getNroLinea());
           huboError =Expresion();
           if(huboError){return huboError;}
      }
      else{
          huboError =Expresion();
          if(huboError){return huboError;}
          if(this.token.EsSignoCondicion()){
            this.token = this.lex.escanear(token.getNroLinea());
            huboError =Expresion();
            if(huboError){return huboError;}
          }
          else {
            MostrarError(ErroresSintacticos.FALTA_DO, this.token.getNroLinea());
             return true;
          }
      }
      return huboError;
    }    ;
  private boolean Expresion() throws IOException {
      boolean huboError = false;
      this.token = this.lex.escanear(token.getNroLinea());
      if(token.EsSignoExpresion())
      {
          this.token = this.lex.escanear(token.getNroLinea());
      }
      else{
          huboError =Termino();
          if(huboError){return huboError;}
      }

      while(token.EsSignoExpresion())
      {
          this.token = this.lex.escanear(token.getNroLinea());
           huboError =Termino();
          if(huboError){return huboError;}      
      }    
        return huboError;
    }    ;
  private boolean Termino() throws IOException {
      boolean huboError ;
      this.token = this.lex.escanear(token.getNroLinea());
      huboError = Factor();
      if(huboError){return huboError;} 
      
      while(this.token.EsSignoTermino()){
        this.token = this.lex.escanear(token.getNroLinea());
        huboError = Factor();
        if(huboError){return huboError;} 
      }      
      return huboError;
    }    ;
  
  private boolean Factor() throws IOException {
      boolean huboError = false;
      this.token = this.lex.escanear(token.getNroLinea());
      
      if(this.token.EsIdentificador()){
          this.token = this.lex.escanear(token.getNroLinea());
      }
      else {
       if(this.token.EsNumero()){
           this.token = this.lex.escanear(token.getNroLinea());
       }
       else{
          if(this.token.getValor().equals("("))
          {
              this.token = this.lex.escanear(token.getNroLinea());
              huboError = Expresion();
             if(this.token.getValor().equals(")"))
             {
                this.token = this.lex.escanear(token.getNroLinea());
             } 
             else {
            MostrarError(ErroresSintacticos.FALTA_PARENTESIS, this.token.getNroLinea());
             return true;
          }
          }
          else {
            MostrarError(ErroresSintacticos.FALTA_PARENTESIS, this.token.getNroLinea());
             return true;
          }
       
       }
      }
      
      return huboError;
    };

}
