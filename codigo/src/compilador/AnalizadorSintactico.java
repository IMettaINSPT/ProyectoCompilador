package compilador;

import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorSintactico {

    private Token token;
    private String scope = "";
    private final AnalizadorLexico lex;
    private final AnalizadorSemantico semantica;
    private final GeneradorDeCodigo genCod;
    public static int TAMANO_HEADER = 244;
    public static int SECTION_ALIGNMENT = 216;

    private int cantVariables;

    public void setToken(Token token) {
        this.token = token;
    }

    public AnalizadorSintactico(AnalizadorLexico scanner, AnalizadorSemantico semantic, GeneradorDeCodigo gen) {
        this.lex = scanner;
        this.semantica = semantic;
        this.genCod = gen;
        cantVariables = 0;
    }

    private void mostrarError(Errores.erroresEnum enumError, Token token) {
        Errores.mostrarError(enumError, token, lex.getPathFile());
    }

    private void leerProximoToken() throws IOException {
        this.setToken(this.lex.escanear());
    }

    public void programa() throws IOException {
        // Primero cargo EDI EN O para que apunte a la inicializacion de las varibless
        genCod.cargarInicializacionRegistroEDI();
        //Me guardo la direccion donde arranco 
        int direccionEDI = genCod.obtenerPosicion();

        bloque(0);
        if (this.token.getValor().equals(".")) {
           
            leerProximoToken();
            this.genCod.generarArchivoExe();

        } else {
            mostrarError(Errores.erroresEnum.DESCONOCIDO, this.token);
        }
    }

    private void bloque(int base) throws IOException {
        scope = "DECLARACION";
        int desplazamiento = 0;
        genCod.cargarJMP(0);
        int comienzoBloque = genCod.obtenerPosicion();
        if (this.token.esConstrante()) {
            String nomCte = "";
            leerProximoToken();
            if (this.token.esIdentificador()) {
                nomCte = this.token.getValor();
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals("=")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
            }
            if (this.token.esNumero()) {
                semantica.agregar(nomCte, "CONST", Integer.parseInt(this.token.getValor()), base, desplazamiento, scope);
                desplazamiento++;
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
            }
            while (token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    nomCte = this.token.getValor();
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
                if (this.token.getValor().equals("=")) {
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
                }
                if (this.token.esNumero()) {
                    semantica.agregar(nomCte, "CONST", Integer.parseInt(this.token.getValor()), base, desplazamiento, scope);
                    desplazamiento++;
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
                }
            } //fin while

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA_COMA, this.token);
            }

        } //FIN CONSTANTE

        if (this.token.getValor().equals("VAR")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                cantVariables++;
                semantica.agregar(this.token.getValor(), "VAR", this.cantVariables * 4, base, desplazamiento, scope);
                desplazamiento++;
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (token.getValor().equals(",")) { //while var ident, ident ;
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    semantica.agregar(this.token.getValor(), "VAR", this.cantVariables * 4, base, desplazamiento, scope);
                    desplazamiento++;
                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }   //while var ident, ident ;

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA_COMA, this.token);
            }
        } //fin VAR

        while (token.getValor().equals("PROCEDURE")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                int posMemo = this.genCod.obtenerPosicion();
                semantica.agregar(this.token.getValor(), "PROCEDURE", posMemo, base, desplazamiento, scope);
                desplazamiento++;
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
            bloque(desplazamiento + base);

            // Al finalizar el bloque de este procedimiento se agrega un RET
            genCod.cargarByte(0xC3);

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
        } //FIN WHILE

        int finaleBloque = genCod.obtenerPosicion();
        System.out.println("\nFinal de bloque. Actualizamos el JUMP del inicio del bloque actual:");
        genCod.cargarIntEn(finaleBloque - comienzoBloque, (comienzoBloque - 4)); // Se llena el JUMP reservado al comienzo del bloque calculando la distancia entre la posición actual y el comienzo del bloque. Se carga en la posición del comienzoBloque - 4 para quedar despues del E9

        proposicion(base, desplazamiento);
    } // FIN FUNCION BLOQUE

    //A PARTIR DE ACA SOLO USO LOS IDENTIFICADORES PARA BUSCARLOS NO LOS DECLARO
    private void proposicion(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        ArrayList<String> tiposEsperados = new ArrayList<>();

        if (this.token.esIdentificador()) {
            tiposEsperados.add("VAR");
            this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);
            int valorVar = semantica.getValorRegistro(this.token.getValor(), base, desplazamiento, scope);

            leerProximoToken();
            if (this.token.getValor().equals(":=")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_ASIGNACION, this.token);
            }
            expresion(base, desplazamiento);

            // muevo el valor de la pila a la memoria donde esta el identificador
            //saco el numero de la pila pop eax, mov[edi +posicion de la variable],eax
            genCod.cargarIdent(valorVar);
        }
        if (this.token.getValor().equals("CALL")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                tiposEsperados.clear();
                tiposEsperados.add("PROCEDURE");
                this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);
                //BUSCO EL VALOR DONDE ESTA EN MEMORIA EL PROCEDIMIENTO 
                int posMemoriaProcedure = semantica.getValorRegistro(this.token.getValor(), base, desplazamiento, scope);
                genCod.cargarCallDeProcedimiento(posMemoriaProcedure);

                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
        }
        if (this.token.getValor().equals("BEGIN")) {
            leerProximoToken();
            proposicion(base, desplazamiento);
            while (this.token.getValor().equals(";")) {
                leerProximoToken();
                proposicion(base, desplazamiento);
            }
            if (this.token.getValor().equals("END")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_END, this.token);
            }
        }
        if (this.token.getValor().equals("IF")) {
            leerProximoToken();
            condicion(base, desplazamiento);
            //insetar JMP 00
            //genCod.cargarByte(0xE9);
            //genCod.cargarInt(valor);                                                    
            //En el caaso del if, directamente hago un salto despues de la proposicion si la condicion es falsa.            
            //guardar en una variable local el .size, la posicion donde esta despues del JMP 00
            if (this.token.getValor().equals("THEN")) {
                leerProximoToken();
                int inicioDeProposicion = genCod.obtenerPosicion();

                proposicion(base, desplazamiento);
                int finDeProposicion = genCod.obtenerPosicion();
                //FIX UP de la posicion del salto del condicion. En este caso el salto es a despues de la proposicion
                //guardar el .size en una variable y guardar la posicion en la variable local -4
                int distanciaDeSalto = finDeProposicion - inicioDeProposicion; // Se calcula la distancia entre el inicio de la proposición y el final de la proposición para luego cargarla en el JUMP

                genCod.cargarIntEn(distanciaDeSalto, inicioDeProposicion - 4);
                // genCod.cargarSaltosCondicionales(finCondicionIF);

            } else {
                mostrarError(Errores.erroresEnum.FALTA_THEN, this.token);
            }
        }
        if (this.token.getValor().equals("WHILE")) {
            leerProximoToken();
            //guardar la posicion con el size antes de evaluar la condicion para volver despues de la proposicion
            int inicioCondicionWhile = genCod.obtenerPosicion();

            condicion(base, desplazamiento);

            int finCondicionWhile = genCod.obtenerPosicion();

            if (this.token.getValor().equals("DO")) {
                leerProximoToken();
                proposicion(base, desplazamiento);

                //cargar otro salto para atras
                int finProposicion = genCod.obtenerPosicion() + 5; // Se suma 5 porque el siguiente JUMP E9 _ _ _ _ ocupa 5 bytes;
                int distanciaDeSalto = inicioCondicionWhile - finProposicion;
                // SALTO condicional para volver a evaluar la condicion
                genCod.cargarJMP(distanciaDeSalto);
                // FIX UP, si condicion es falsa salto al fin de la proposicion
                int distanciaSalto = finProposicion - finCondicionWhile;
                // le resto 4 para sobreescribir los 0 puestos en el jmp
                genCod.cargarIntEn(distanciaSalto, finCondicionWhile - 4); // Se carga la distancia en el JUMP reservado al final de la condición - 4 para quedar despues del JUMP E9

            } else {
                mostrarError(Errores.erroresEnum.FALTA_DO, this.token);
            }
        }
        if (this.token.getValor().equals("READLN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }

            if (this.token.esIdentificador()) {
                tiposEsperados.clear();
                tiposEsperados.add("VAR");
                tiposEsperados.add("CONST");
                this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);

                int valorVariable = semantica.getValorRegistro(this.token.getValor(), base, desplazamiento, scope);
                genCod.cargaConsola(valorVariable);

                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    tiposEsperados.clear();
                    tiposEsperados.add("VAR");
                    tiposEsperados.add("CONST");
                    this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);

                    int valorVariable = semantica.getValorRegistro(this.token.getValor(), base, desplazamiento, scope);
                    genCod.cargaConsola(valorVariable);

                    leerProximoToken();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
                }
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_CADENA_FACTOR_IDENT_PARA, this.token);
            }
        }
        if (this.token.getValor().equals("WRITE")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
            if (this.token.esCadena()) {
                genCod.cargarCadena(this.token.getValor());

                leerProximoToken();
            } else {
                expresion(base, desplazamiento);
                // POP EAX
                // llamar a call 0420 
                genCod.cargarResultadoExpresion();
            }
            while (this.token.getValor().equals(",")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    genCod.cargarCadena(this.token.getValor());

                    leerProximoToken();
                } else {
                    expresion(base, desplazamiento);
                    // POP EAX
                    // llamar a call 0420 
                    genCod.cargarResultadoExpresion();
                }
            }
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_CADENA_FACTOR_IDENT_PARA, this.token);
            }
        }
        if (this.token.getValor().equals("WRITELN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
                if (this.token.esCadena()) {
                    genCod.cargarCadena(this.token.getValor());

                    leerProximoToken();
                } else {
                    expresion(base, desplazamiento);
                    // POP EAX
                    // llamar a call 0420 
                    genCod.cargarResultadoExpresion();
                }
                while (this.token.getValor().equals(",")) {
                    leerProximoToken();
                    if (this.token.esCadena()) {
                        genCod.cargarCadena(this.token.getValor());

                        leerProximoToken();
                    } else {
                        expresion(base, desplazamiento);
                        // POP EAX
                        // llamar a call 0420 
                        genCod.cargarResultadoExpresion();
                    }
                }
                if (this.token.getValor().equals(")")) {
                    leerProximoToken();
                    //salto de linea call 0410
                    genCod.cargarSaltoDeLinea();
                } else {
                    mostrarError(Errores.erroresEnum.FALTA_FACTOR_IDENT_PARA, this.token);
                }

            }
        }
    } //FIN PROPOSICION

    private void condicion(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        if (this.token.getValor().equals("ODD")) {
            leerProximoToken();
            expresion(base, desplazamiento);
            genCod.cargarExpresionODD();

            return;
        }
        expresion(base, desplazamiento);
        if (this.token.esSignoCondicion()) {
            switch (this.token.getValor()) {
                case "=" -> {
                    //break;
                }
                case "<>" -> {
                    // break;
                }
                case "<" -> {
                    // break;
                }
                case "<=" -> {
                    // break;
                }
                case ">" -> {
                    //  break;
                }
                case ">=" -> {
                    // break;
                }
            }
            leerProximoToken();
        } else {
            mostrarError(Errores.erroresEnum.FALTA_SIGNO_COND, this.token);
        }
        expresion(base, desplazamiento);
        genCod.cargarCondicion(this.token.getValor());
    }

    private void expresion(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        if (token.esSignoExpresion()) {

            switch (this.token.getValor()) {
                case "+" -> {
                    //  break;
                }
                case "-" -> {
                    genCod.negarTermino();
                }
            }
            leerProximoToken();
        }

        termino(base, desplazamiento);

        while (token.esSignoExpresion()) {
            switch (this.token.getValor()) {
                case "+" -> {
                    genCod.cargarSuma();
                }
                case "-" -> {
                    genCod.cargarResta();
                }
            }
            leerProximoToken();
            termino(base, desplazamiento);
        }

    }

    private void termino(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        factor(base, desplazamiento);
        while (this.token.esSignoTermino()) {
            switch (this.token.getValor()) {
                case "*" -> {
                    genCod.cargarMultiplicacion();
                }
                case "/" -> {
                    genCod.cargarDivision();
                }
            }
            leerProximoToken();
            factor(base, desplazamiento);
        }

    }

    private void factor(int base, int desplazamiento) throws IOException {
        scope = "BUSQUEDA";
        ArrayList<String> tiposEsperados = new ArrayList<>();
        if (this.token.esIdentificador()) {
            tiposEsperados.clear();
            tiposEsperados.add("VAR");
            tiposEsperados.add("CONST");
            this.semantica.busquedaYchequeo(this.token.getValor(), base, desplazamiento, tiposEsperados, scope, this.token);

            //si es una constante es el valor de la misma, si es variable tiene la cant de variables * 4
            int valor = this.semantica.getValorRegistro(this.token.getValor(), base, desplazamiento, scope);

            if (this.token.getTipo().equals("CONST")) {

                genCod.cargarConst(valor);
            } else {
                genCod.cargarVar(valor);
            }
            //identificar si es variable o constante para cargar en el generador de codigo
            //si es const B8 
            //si es var 8B 87
            //cargo el push eax -> 50
            leerProximoToken();
        }
        if (this.token.esNumero()) {
            // cargo el numero con mov eax, _____
            //cargo el push eax ->50
            genCod.cargarNumero(token.getValor());
            leerProximoToken();
        }
        // NO SE GENERA EL CODIGO ACA, SE GENERA EN EXPRESION

        if (this.token.getValor().equals("(")) {
            leerProximoToken();
            expresion(base, desplazamiento);
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }
        }
    }

}
