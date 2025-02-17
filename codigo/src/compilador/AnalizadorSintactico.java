package compilador;

import compilador.GeneradorDeCodigo;
import compilador.Errores;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalizadorSintactico {

    private Token token;
    private int valor;
    private final AnalizadorLexico lex;
    private final AnalizadorSemantico asem;
    private GeneradorDeCodigo genCod;
    private String nombreIdentificador;
    private String signo_leido;

    int posicionHalt = 0;
    int linea;

    public void setToken(Token token) {
        this.token = token;
    }

    public AnalizadorSintactico(AnalizadorLexico scanner, AnalizadorSemantico semantic, GeneradorDeCodigo gen) {
        this.lex = scanner;
        this.asem = semantic;
        this.genCod = gen;

    }

    private void mostrarError(Errores.erroresEnum enumError, Token token) {
        Errores.mostrarError(enumError, token, lex.getPathFile());
    }

    private void leerProximoToken() throws IOException {
        this.setToken(this.lex.escanear());
    }

    public void programa() throws FileNotFoundException, IOException {
        genCod.cargarEDI();
        bloque(0);

        if (this.token.getValor().equals(".")) {
            leerProximoToken();

            System.out.println("programa valido el PL0");
            if (posicionHalt != 0) {
                genCod.cargarIntEn(posicionHalt - 4, genCod.getPosActual() - posicionHalt);
            }
            genCod.cargarFinalDelPrograma(asem.getCantVar());
            genCod.GenerarArchivo();
        } else {

            mostrarError(Errores.erroresEnum.FALTA_PTO, this.token);
        }

    }

    /*
   bloque  ::= 
   ('CONST''ident' '=' 'numero' (',' 'ident' '=' 'numero')* ';' )? 
   ('VAR' 'ident'(',''ident')* ';')? 
   ('PROCEDURE' 'ident' ';' boque ';')* 
   propocicion 
     */
    public void bloque(int base) throws FileNotFoundException, IOException {
        int desplazamiento = 0;
        genCod.generar_JMP(0);//  pisarase
        int posSalto = genCod.getPosActual();

        /*
        *               CONST
         */
        if (this.token.esConstrante()) {

            leerProximoToken();

            if (this.token.esIdentificador()) {
                nombreIdentificador = token.getValor();
                leerProximoToken();

            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            if (this.token.getValor().equals("=") || this.token.getValor().equals(":=")) {
                leerProximoToken();

            } else {

                mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);

            }
            if (this.token.getValor().equals("-")) {
                leerProximoToken();
                if (this.token.esNumero()) {
                    valor = 0 - token.getNumber();
                }
            } else if (this.token.esNumero()) {
                valor = token.getNumber();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
            }

            asem.cargarIdentificadorEnTabla(base, desplazamiento, nombreIdentificador, "const", valor);
            desplazamiento++;
            leerProximoToken();

            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    nombreIdentificador = token.getValor();
                    leerProximoToken();
                } else {

                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
                if (token.getValor().equals("=") || token.getValor().equals(":=")) {
                    leerProximoToken();
                } else {

                    mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
                }
                if (token.getValor().equals("-")) {
                    leerProximoToken();
                    if (this.token.esNumero()) {
                        valor = 0 - token.getNumber();
                    }
                } else if (this.token.esNumero()) {
                    valor = token.getNumber();
                } else {

                    mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
                }

                asem.cargarIdentificadorEnTabla(base, desplazamiento, nombreIdentificador, "const", valor);
                desplazamiento++;
                leerProximoToken();
            }

            if (token.getValor().equals(";")) {
                leerProximoToken();

            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA_COMA, this.token);
            }
        }

        /*
        *                           VAR
         */
        if (this.token.getValor().equals("VAR")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                nombreIdentificador = token.getValor(); 
                asem.cargarIdentificadorEnTabla(base, desplazamiento, nombreIdentificador, "var", asem.getCantVar());
                desplazamiento++;
                leerProximoToken();
            } else {

                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    nombreIdentificador = token.getValor(); 
                    asem.cargarIdentificadorEnTabla(base, desplazamiento, nombreIdentificador, "var", asem.getCantVar());
                    desplazamiento++;
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }
            if (token.getValor().equals(";")) {
                leerProximoToken();
                
            }

        }

        /*
        *       PROCEDURE
         */
        while (token.getValor().equals("PROCEDURE")) {
            leerProximoToken();

            if (this.token.esIdentificador()) {
                nombreIdentificador = token.getValor();
                asem.cargarIdentificadorEnTabla(base, desplazamiento, nombreIdentificador, "procedure", genCod.getPosActual());
                desplazamiento++;
                leerProximoToken();

            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (token.getValor().equals(";")) {
                leerProximoToken();

                bloque(base + desplazamiento);

                genCod.cargarRET();

                if (token.getValor().equals(";")) {
                    leerProximoToken();
                }
            }
        }
        int finalDeBloque = genCod.getPosActual();

        genCod.cargarIntEn(posSalto - 4, (finalDeBloque - posSalto));

        propocicion(base, desplazamiento);

    }

    /*
    *
    *            PROPOSICION
    *
    *
   proposicion ::=
  ('ident' ':=' expresion)?
| ('CALL' 'ident')? 
|('BEGIN' proposicion (';' proposicion)* 'END')?
| ('IF' condicion 'THEN' proposicion)?
| ('while' condicion 'DO' proposicion)?
|('READLN' '(' 'ident' (',' 'ident')* ')')?
|('WRITELN' ('(' ('cadena'|expresion) (','('cadena'|expresion))*')')?)?
|('WRITE' '(' ('cadena'|expresion) (','('cadena'|expresion))*')')?*/
    public void propocicion(int base, int desplazamiento) throws FileNotFoundException, IOException {

        /*
      *       ASIGNACION
         */
        if (this.token.esIdentificador()) {
            String nombreAsignando = token.getValor(); 

            leerProximoToken();

            if (token.getValor().equals(":=") || token.getValor().equals("=")) {   //ACA SE DECLARA UN IDENTIFICADOR
                if (!asem.verificadorIdentificadorXtipo(asem.getUltimaPosicion(), 0, nombreAsignando).equals("VAR")) {  //probablemente haya que tcar las pos de busqueda
                    mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
                }
                leerProximoToken();

                expresion(base, desplazamiento);

                genCod.cargarAsignacion(asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, nombreAsignando) * 4);
                // System.out.println(asem.buscarPosIdentificador(asem.getUltimaPosicion(), 0, nombreAsignando));
            } else if (this.token.getValor().equals("++")) {
                genCod.incrementar1(asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, nombreAsignando) * 4);
                leerProximoToken();

            } else if (this.token.getValor().equals("--")) {
                genCod.decrementar1(asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, nombreAsignando) * 4);
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);

            }
        }

        /*
             *       CALL
         */
        if (this.token.getValor().equals("CALL")) {
            leerProximoToken();

            if (this.token.esIdentificador()) {
                String nombre = token.getValor();
                int valor = asem.getValorIdentificadorEnTabla(asem.getUltimaPosicion(), 0, token.getValor());
                //System.out.println(nombre + " " + valor);
                String nombreProcedure = token.getValor();
                if (!asem.verificadorIdentificadorXtipo(asem.getUltimaPosicion(), 0, nombreProcedure).equals("PROCEDURE")) {  //probablemente haya que tcar las pos de busqueda

                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
                genCod.cargar_Call(valor);
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
        }

        /*
             *              BEGIN
         */
        if (this.token.getValor().equals("BEGIN")) {
            leerProximoToken();

            propocicion(base, desplazamiento);

            if (token.getValor().equals(";")) {
                while (token.getValor().equals(";")) {
                    leerProximoToken();

                    propocicion(base, desplazamiento);

                }

            } else if (this.token.getValor().equals("END")) {
                leerProximoToken();
                return;
            } else {
                leerProximoToken();
            }

            if (this.token.getValor().equals("HALT")) {

                genCod.generar_JMP(0);
                posicionHalt = genCod.getPosActual();
                leerProximoToken();
            }

            if (this.token.getValor().equals("END")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_END, this.token);
            }

        }

        /*
        *          IF
        *   // IF (condicion) [inicioProposicion] THEN (proposicion) [finProposicion]     
         */
        if (this.token.getValor().equals("IF")) {
            int inicioDeProposicion = 0;
            int finDeProposicion = 0;

            condicion(base, desplazamiento);

            if (this.token.getValor().equals("THEN")) {

                inicioDeProposicion = genCod.getPosActual();
                leerProximoToken();
                propocicion(base, desplazamiento);
                finDeProposicion = genCod.getPosActual();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_THEN, this.token);
            }

            int distanciaDeSalto = finDeProposicion - inicioDeProposicion; // Se calcula la distancia entre el inicio de la proposición y el final de la proposición para luego cargarla en el JUMP

            genCod.cargarIntEn(inicioDeProposicion - 4, distanciaDeSalto); // Se carga la distancia en el JUMP reservado al inicio de la proposición (el JUMP se encuentra en la condición) - 4 para quedar despues del JUMP E9
        }

        /*
    *           WHILE
    *           [inicioCondicion] (condicion) [finCondicion] DO (proposicion) [finProposicion]
    *                
         */
        if (this.token.getValor().equals("WHILE")) {

            int finProposicion = 0;//inicializo fuera del if
            int inicioCondicion = genCod.getPosActual();
            condicion(base, desplazamiento);
            int finCondicion = genCod.getPosActual();

            if (this.token.getValor().equals("DO")) {

                leerProximoToken();
                propocicion(base, desplazamiento);
                finProposicion = genCod.getPosActual();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_DO, this.token);
            }

            int distanciaSalto = finProposicion - finCondicion + 5;

            genCod.cargarIntEn(finCondicion - 4, distanciaSalto);

            genCod.generar_JMP(inicioCondicion - (finProposicion + 5));

        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*
            *
            *                    FOR
            *   _for ::identificador:: signo := :: expresion :: (_to|_downto) :: expresion ::_do:: propocicion
         */
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (this.token.getValor().equals("FOR")) {
            leerProximoToken();
            if (!this.token.esIdentificador()) {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            String nombreAsignando = token.getValor();
            if (!asem.verificadorIdentificadorXtipo(asem.getUltimaPosicion(), 0, nombreAsignando).equals("VAR")) {
                mostrarError(Errores.erroresEnum.FALTA_VAR, this.token);
            }
            leerProximoToken();

            if (!token.getValor().equals(":=")) {

                mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
            }

            leerProximoToken();
            expresion(base, desplazamiento);
            int varControlFor = asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, nombreAsignando) * 4;
            int posEvaluacionyPaso = genCod.getPosActual();
            genCod.cargarAsignacion(varControlFor);

            if (!token.getValor().equals("TO") && !token.getValor().equals("DOWNTO")) {

                mostrarError(Errores.erroresEnum.GENERICO, this.token);
            }

            String paso = token.getValor();
            leerProximoToken();
            expresion(base, desplazamiento);

            genCod.cargarJmpFor(varControlFor, paso);// distanciaAFinalPropocicion - (genCod.getPosActual()+5));
            //System.out.println("POSICION: "+Integer.toHexString(genCod.getPosActual()) );  
            int posFinCondicion = genCod.getPosActual();

            if (this.token.getValor().equals("DO")) {

                leerProximoToken();

                propocicion(base, desplazamiento);
                genCod.incrementarContVueltasFor(varControlFor, paso);

                int posFinalPropocicion = genCod.getPosActual();
                int distanciaSaltoFOR = posFinalPropocicion + 5 - posFinCondicion;
                //System.out.println("POSICION: "+Integer.toHexString(genCod.getPosActual()) );    

                genCod.cargarIntEn(posFinCondicion - 4, distanciaSaltoFOR); // Se carga la distancia en el JUMP reservado al final de la condición - 4 para quedar despues del JUMP E9
                genCod.generar_JMP(posEvaluacionyPaso - ((genCod.getPosActual() + 5)));

            } else {

                mostrarError(Errores.erroresEnum.FALTA_DO, this.token);

            }

        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*
             *
             *      Repeat
             *   repeat::expresion::times::propocicion
             *
         */

        if (this.token.getValor().equals("DO") || this.token.getValor().equals("REPEAT")) {

            leerProximoToken();
            expresion(base, desplazamiento);

            if (!this.token.getValor().equals("TIMES")) {
                mostrarError(Errores.erroresEnum.GENERICO, this.token);
            }

            int posEvaluacionRepeat = genCod.getPosActual();
            genCod.cargarEvaluacionRepeat();
            int posFinCondicionRepeat = genCod.getPosActual();

            leerProximoToken();
            propocicion(base, desplazamiento);

            int posFinProposicionRep = genCod.getPosActual();
            int distanciaSaltoRep = posFinProposicionRep + 5 - posFinCondicionRepeat;

            genCod.cargarIntEn(posFinCondicionRepeat - 4, distanciaSaltoRep);
            genCod.generar_JMP(posEvaluacionRepeat - (genCod.getPosActual() + 5));
            genCod.cargarPopEAX();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*
            *
            *                  SWITCH
            *   _switch :: signo ( ::identificador:: signo ) :: (_case :: expresion ::_do:: propocicion)*::(_defoult ::_do:: propocicion)?
         */
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (this.token.getValor().equals("SWITCH")) {
            int evaluacionSwitch = 0;
            List<Integer> switchRealizado = new ArrayList<>();
            leerProximoToken();
            if (!this.token.esIdentificador()) {

                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            String nombreVarSwitch = token.getValor();
            if (!asem.verificadorIdentificadorXtipo(asem.getUltimaPosicion(), 0, nombreVarSwitch).equals("VAR")) {

                mostrarError(Errores.erroresEnum.FALTA_VAR, this.token);
            }
            int varSwitch = asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, nombreVarSwitch) * 4;
            leerProximoToken();

            while (this.token.getValor().equals("CASE")) {
                genCod.generarFactorVar(varSwitch);
                leerProximoToken();
                expresion(base, desplazamiento);
                genCod.cargarCondicion("=");
                evaluacionSwitch = genCod.getPosActual();
                if (this.token.getValor().equals("DO")) {
                    leerProximoToken();
                    propocicion(base, desplazamiento);
                    genCod.generar_JMP(0);
                    switchRealizado.add(genCod.getPosActual());
                    System.out.println("POSICION: " + Integer.toHexString(genCod.getPosActual()));

                }

                genCod.cargarIntEn(evaluacionSwitch - 4, genCod.getPosActual() - evaluacionSwitch);

            }

            if (this.token.getValor().equals("DEFAULT")) {
                leerProximoToken();
                if (this.token.getValor().equals("DO")) {
                    leerProximoToken();
                    propocicion(base, desplazamiento);
                    for (int pos : switchRealizado) {
                        genCod.cargarIntEn(pos - 4, genCod.getPosActual() - pos);
                    }
                }

                //...  
            }
            // genCod.cargarIntEn(SwitchRealizado - 4, genCod.getPosActual() - SwitchRealizado); //...  
        }

        /*
    *          READLINE
    *                
         */
        if (this.token.getValor().equals("READLN")) {

            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {

                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
            if (this.token.esIdentificador()) {
                if (!asem.verificadorIdentificadorXtipo(base + desplazamiento, 0, token.getValor()).equals("VAR")) {  //probablemente haya que tcar las pos de busqueda

                    mostrarError(Errores.erroresEnum.FALTA_VAR, this.token);
                }
                genCod.generarReadln(asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, token.getValor()) * 4);
                leerProximoToken();
            } else {

                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    genCod.generarReadln(asem.getValorIdentificadorEnTabla(base + desplazamiento, 0, token.getValor()) * 4);
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }

            if (this.token.getValor().equals(")")) {
                leerProximoToken();

            } else {

                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }

        }

        /*
    *          WRITELINE
    *                
         */
        if (this.token.getValor().equals("NEWLINE")) {
            genCod.generarWriteString("");
             genCod.genSaltoLinea();
             leerProximoToken();
        }

        if (this.token.getValor().equals("WRITELN")) {

            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    genCod.generarWriteString(token.getValor());

                    leerProximoToken();
                } else {
                    expresion(base, desplazamiento);
                    genCod.cargarWriteResultado();
                }

                while (token.getValor().equals(",")) {
                    leerProximoToken();
                    if (this.token.esCadena()) {
                        genCod.generarWriteString(token.getValor());

                        leerProximoToken();
                    } else {
                        expresion(base, desplazamiento);
                        genCod.cargarWriteResultado();
                    }
                }

                if (this.token.getValor().equals(")")) {
                    genCod.genSaltoLinea();
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
                }

            } else {
                genCod.genSaltoLinea();
            }
        }
        /*
    *          WRITE
    *                
         */

        if (this.token.getValor().equals("WRITE")) {

            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    genCod.generarWriteString(token.getValor());//_______gencod
                    leerProximoToken();
                } else {
                    expresion(base, desplazamiento);
                    genCod.cargarWriteResultado();
                }

                while (token.getValor().equals(",")) {
                    leerProximoToken();
                    if (this.token.esCadena()) {
                        genCod.generarWriteString(token.getValor());//_______gencod
                        //System.out.println("!!!!!!!"+signo_leido+"-"+token.getValor());
                        //System.out.println("POSICION: "+Integer.toHexString(genCod.getPosActual()) );
                        leerProximoToken();

                    } else {
                        expresion(base, desplazamiento);
                        genCod.cargarWriteResultado();
                    }

                }

                if (this.token.getValor().equals(")")) {
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
                }

            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
        }
    }

    /*
 condición ::= ('ODD' expresion)
    | (expresion ('='|'<>'|'<'|'<='|'>'|'>=') expresion)  
     */
    public void condicion(int base, int desplazamiento) throws FileNotFoundException, IOException {
        String comparador = new String();
        leerProximoToken();

        if (this.token.getValor().equals("NOT")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {

                leerProximoToken();
                expresion(base, desplazamiento);
            }

            String signo_inverso = new String();
            switch (token.getValor()) {
                case "=":
                    signo_inverso = signo_inverso.concat("<>");
                    comparador = comparador.concat(signo_inverso);
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;

                case "<>":
                    signo_inverso = signo_inverso.concat("=");
                    comparador = comparador.concat(signo_inverso);
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;
                case "<":
                    signo_inverso = signo_inverso.concat(">=");
                    comparador = comparador.concat(signo_inverso);
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;
                case "<=":
                    signo_inverso = signo_inverso.concat(">");
                    comparador = comparador.concat(signo_inverso);
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;
                case ">":
                    signo_inverso = signo_inverso.concat("<=");
                    comparador = comparador.concat(signo_inverso);
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;
                case ">=":

                    signo_inverso = signo_inverso.concat("<");
                    comparador = comparador.concat(signo_inverso);
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;

                default:

                    mostrarError(Errores.erroresEnum.FALTA_SIGNO, this.token);
                    break;
            }
            if (this.token.getValor().equals(")")) {

                leerProximoToken();
            }
        } else if (this.token.getValor().equals("ODD")) {
            leerProximoToken();
            expresion(base, desplazamiento);
            genCod.cargarODD();//_____________________________gencod

        } else {

            expresion(base, desplazamiento);

            switch (token.getValor()) {
                case "=":
                case "<>":
                case "<":
                case "<=":
                case ">":
                case ">=":

                    comparador = comparador.concat(token.getValor());
                    leerProximoToken();
                    expresion(base, desplazamiento);

                    genCod.cargarCondicion(comparador);//______________gencode
                    break;
                default:
                    mostrarError(Errores.erroresEnum.FALTA_SIGNO, this.token);

            }

        }

    }

    /*
   expresion ::=(('+')?|('-')?)? término ((('+')|('-')) término)*
     */
    public void expresion(int base, int desplazamiento) throws FileNotFoundException, IOException {

        if (token.getValor().equals("+") || token.getValor().equals("-")) {
            if (token.getValor().equals("+")) {
                genCod.generar_suma();
            } else {

                leerProximoToken();

                termino(base, desplazamiento);
                genCod.cargar_menosUnario();

            }
        } else {

            termino(base, desplazamiento);

        }

        while (token.getValor().equals("+") || token.getValor().equals("-")) {

            String operacion = token.getValor();

            leerProximoToken();

            termino(base, desplazamiento);

            if (operacion.equals("+")) {
                genCod.generar_suma();

            } else {
                genCod.generar_resta();
                //leerProximoToken();
            }

        }

    }

    /*
   término ::= factor (('*'|'/') factor)*
     */
    public void termino(int base, int desplazamiento) throws FileNotFoundException, IOException {

        factor(base, desplazamiento);

        while (token.getValor().equals("*") || token.getValor().equals("/")) {
            String operacion = token.getValor();

            leerProximoToken();
            factor(base, desplazamiento);

            if (operacion.equals("*")) {
                genCod.cargar_multiplicacion();
            } else {

                genCod.cargar_division();

            } 

        }
    }

    /*
   factor ::= 'ident'
    |'número'
    |('(' expresion ')')
     */
    public void factor(int base, int desplazamiento) throws FileNotFoundException, IOException {

        if (this.token.esIdentificador()) {
            nombreIdentificador = token.getValor(); 

            if (asem.verificadorIdentificadorXtipo(desplazamiento + base, 0, nombreIdentificador).equals("CONST")) {
                genCod.generarFactorConst(asem.getValorIdentificadorEnTabla(desplazamiento + base, 0, nombreIdentificador));

            } else if (asem.verificadorIdentificadorXtipo(desplazamiento + base, 0, nombreIdentificador).equals("VAR")) {
                genCod.generarFactorVar(asem.getValorIdentificadorEnTabla(desplazamiento + base, 0, nombreIdentificador) * 4);

// System.out.println("valor var= " + asem.getValorIdentificadorEnTabla(desplazamiento + base - 1, 0, nombreIdentificador));
//System.out.println(asem.verificadorIdentificadorXtipo(asem.getUltimaPosicion(),0, "uno"));
            }

            leerProximoToken();                                //                  !!!!!!!

        } else if (this.token.esNumero()) {
            int num = token.getNumber();

            genCod.generarFactorNum(num);
            leerProximoToken();

        } else if (this.token.getValor().equals("(")) {

            leerProximoToken();
            // System.out.println(signo_leido+"-"+token.getValor());
            expresion(base, desplazamiento);

            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {

                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }

        }

    }

}
