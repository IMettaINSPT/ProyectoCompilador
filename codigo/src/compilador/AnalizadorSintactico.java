package compilador;

import java.io.IOException;

public class AnalizadorSintactico {

    private Token token;
    private final AnalizadorLexico lex;
    private final AnalizadorSemantico asem;
    private final GeneradorDeCodigo genCod;
    private int cantVariablesDeclaradas;
    private boolean huboHant;

    public void setToken(Token token) {
        this.token = token;
    }

    public AnalizadorSintactico(AnalizadorLexico scanner, AnalizadorSemantico semantic, GeneradorDeCodigo gen) {
        this.lex = scanner;
        this.asem = semantic;
        this.genCod = gen;
        cantVariablesDeclaradas = 0;
        this.huboHant = false;
    }

    private void mostrarError(Errores.erroresEnum enumError, Token token) {
        Errores.mostrarError(enumError, token, lex.getPathFile());
    }

    private void leerProximoToken() throws IOException {
        this.setToken(this.lex.escanear());
    }

    public void programa() throws IOException {
        // Primero cargo EDI EN O para que apunte a la inicializacion de las varibless
        genCod.cargarEDI();
        System.out.println("EDI cargado");
        //Me guardo la direccion donde arranco 
        if (!huboHant) {
            bloque(0);
            if (this.token.getValor().equals(".")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PTO, this.token);
            }
            if (!token.esEOF()) {
                mostrarError(Errores.erroresEnum.FALTA_EOF, this.token);

            }
        }
        genCod.finalDePrograma(cantVariablesDeclaradas);
        genCod.generarArchivoExe();
        System.out.println("\nArchivo generado en " + genCod.getFileOutPath() + ".exe");
    }

    private void bloque(int base) throws IOException {
        int desplazamiento = 0;
        genCod.reservarJUMP_E9____(); // Se reserva un JUMP para el bloque y se rellena antes de la proposición E9 _ _
        // _ _ (5 bytes)

        int comienzoBloque = genCod.getPosicionActual();
        RegistroSemantico identificador = null;

        //CONSTANTE
        if (this.token.esConstrante()) {
            Boolean esNegado = false;
            leerProximoToken();

            if (this.token.esIdentificador()) {
                identificador = new RegistroSemantico(TipoIdentificador.CONST, token.getValor());
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            if (this.token.getValor().equals("=")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IGUAL, this.token);
            }
            if (this.token.getValor().equals("-")) {
                esNegado = true;
                leerProximoToken();
            }

            if (this.token.esNumero()) {
                if (esNegado) {
                    int tokenNegado = Integer.parseInt(token.getValor());
                    identificador.setValor(tokenNegado * -1);
                    esNegado = false;
                } else {
                    identificador.setValor(Integer.parseInt(token.getValor()));
                }
                asem.declarar(identificador, base, base + desplazamiento++);
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
            }

            while (token.getValor().equals(",")) {
                esNegado = false;
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    identificador = new RegistroSemantico(TipoIdentificador.CONST, token.getValor());
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
                    int tk = Integer.parseInt(this.token.getValor());

                    if (esNegado) {
                        identificador.setValor(tk * -1);
                        esNegado = false;
                    } else {
                        identificador.setValor(tk);
                    }
                    identificador.setValor(Integer.parseInt(token.getValor()));;
                    asem.declarar(identificador, base, base + desplazamiento++);
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

        //VAR
        if (this.token.getValor().equals("VAR")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                identificador = new RegistroSemantico(TipoIdentificador.VAR, token.getValor(), cantVariablesDeclaradas++);

                asem.declarar(identificador, base, base + desplazamiento++);

                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }

            while (token.getValor().equals(",")) { //while var ident, ident ;
                leerProximoToken();
                if (this.token.esIdentificador()) {
                    identificador = new RegistroSemantico(TipoIdentificador.VAR, token.getValor(), cantVariablesDeclaradas++);
                    asem.declarar(identificador, base, base + desplazamiento++);

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
        } //fin CONST

        //PROCEDURE
        while (token.getValor().equals("PROCEDURE")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                identificador = new RegistroSemantico(TipoIdentificador.PROCEDURE, token.getValor(), genCod.getPosicionActual());
                asem.declarar(identificador, base, base + desplazamiento++);
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }

            System.out.println("Reservado el JUMP para PROCEDURE " + identificador.getNombre() + " --> "
                    + genCod.toHexa(comienzoBloque - 4)); // -4 porque actualmente estoy parado en el último byte de la
            // instrucción E9 _ _ _ x <--

            bloque(desplazamiento + base);

            genCod.ret_C3(); // Al finalizar el bloque de este procedimiento se agrega un RET

            if (this.token.getValor().equals(";")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PUNTO_Y_COMA, this.token);
            }
        } //FIN WHILE PROCEDURE

        int finaleBloque = genCod.getPosicionActual();
        System.out.println("\nFinal de bloque. Actualizamos el JUMP del inicio del bloque actual:");
        genCod.cargarIntEn(finaleBloque - comienzoBloque, (comienzoBloque - 4)); // Se llena el JUMP reservado al
        // comienzo del bloque calculando la
        // distancia entre la posición actual y
        // el comienzo del bloque. Se carga en
        // la posición del comienzoBloque - 4
        // para quedar despues del E9

        proposicion(desplazamiento + base);

    } // FIN FUNCION BLOQUE

    //A PARTIR DE ACA SOLO USO LOS IDENTIFICADORES PARA BUSCARLOS NO LOS DECLARO
    private void proposicion(int hasta) throws IOException {
        RegistroSemantico identificador;

        if (this.token.esIdentificador()) {
            genCod.mostrarInicioDeProposicion("ASIGNAR (" + token + ")");
            identificador = AnalizadorSemantico.buscar(token.getValor(), hasta, 0);
            if (!identificador.getTipo().equals(TipoIdentificador.VAR)) {
                mostrarError(Errores.erroresEnum.FALTA_VAR, this.token);
            }

            leerProximoToken();
            if (this.token.getValor().equals(":=")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_ASIGNACION, this.token);
            }
            expresion(hasta);
            genCod.asignarAVariable(identificador.getValor() * 4); // Se multiplica por 4 porque cada variable ocupa 4
            // bytes

            genCod.mostrarFinalDeProposicion("ASIGNAR");

        }
        if (this.token.getValor().equals("HALT")) {
            huboHant = true;
            return;
        }
        if (this.token.getValor().equals("CALL")) {
            leerProximoToken();
            if (this.token.esIdentificador()) {
                AnalizadorSemantico.validarTipo(token.getValor(), TipoIdentificador.PROCEDURE, hasta);
                identificador = asem.buscar(token.getValor(), hasta, 0);
                int posicionActual = genCod.getPosicionActual();
                int puntoSalto = identificador.getValor() - (posicionActual + 5); // Valor del procedimiento - (posición
                // actual + 5 bytes)

                genCod.mostrarInicioDeProposicion("CALL (" + token + ")");
                genCod.callA_E8_____(puntoSalto); // Llama al procedimiento en la posición donde fue declarado
                genCod.mostrarFinalDeProposicion("CALL");
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
        }
        if (this.token.getValor().equals("BEGIN")) {
            genCod.mostrarInicioDeProposicion("BEGIN");

            leerProximoToken();
            proposicion(hasta);
            if (this.token.getValor().equals("HALT")) {
                return;
            }
            while (this.token.getValor().equals(";")) {
                leerProximoToken();
                proposicion(hasta);
            }
            if (this.token.getValor().equals("END")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_END, this.token);
            }
            genCod.mostrarFinalDeProposicion("BEGIN");
        }
        if (this.token.getValor().equals("IF")) {
            // IF (cond) [inicioProposicion] THEN (proposicion) [finProposicion]
            genCod.mostrarInicioDeProposicion("IF");
            leerProximoToken();

            condicion(hasta);

            if (this.token.getValor().equals("THEN")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_THEN, this.token);
            }
            int inicioDeProposicion = genCod.getPosicionActual();
            this.proposicion(hasta);
            int finDeProposicion = genCod.getPosicionActual();

            int distanciaDeSalto = finDeProposicion - inicioDeProposicion; // Se calcula la distancia entre el
            // inicio de la proposición y el final de
            // la proposición para luego cargarla en
            // el JUMP

            genCod.cargarIntEn(distanciaDeSalto, inicioDeProposicion - 4); // Se carga la distancia en el JUMP
            // reservado al inicio de la proposición
            // (el JUMP se encuentra en la condición)
            // - 4 para quedar despues del JUMP E9

            genCod.mostrarFinalDeProposicion("IF");
        }
        if (this.token.getValor().equals("WHILE")) {
            // WHILE [inicioCondicion] (condicion) [finCondicion] DO (proposicion)
            // [finProposicion]          
            genCod.mostrarInicioDeProposicion("WHILE");

            leerProximoToken();
            int inicioCondicion = genCod.getPosicionActual(); // Aca se vuelve para volver a evaluar la condicion (happy path)
            this.condicion(hasta); // TERMINA CON UN E9 _ _ _ _ (5 bytes) que se salta a la proposición
            int finCondicion = genCod.getPosicionActual(); // Esto me sirve para corregir el jump anterior (que se encuentra
            // en el final de la condicion) Estoy E9 _ _ _ _ x <-

            if (this.token.getValor().equals("DO")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_DO, this.token);
            }
            this.proposicion(hasta);
            int finProposicion = genCod.getPosicionActual() + 5; // Se suma 5 porque el siguiente JUMP E9 _ _ _ _ ocupa 5
            // bytes

            genCod.jumpA_E9____(inicioCondicion - finProposicion); // Vuelve a la condición para evaluarla
            // nuevamente (happy path)

            int distanciaSalto = finProposicion - finCondicion;
            genCod.cargarIntEn(distanciaSalto, finCondicion - 4); // Se carga la distancia en el JUMP reservado al
            // final de la condición - 4 para quedar despues
            // del JUMP E9

            genCod.mostrarFinalDeProposicion("WHILE");

        }
        if (this.token.getValor().equals("FOR")) {
            genCod.mostrarInicioDeProposicion("FOR");

            leerProximoToken();

            if (!this.token.esIdentificador()) {
                mostrarError(Errores.erroresEnum.FALTA_IDENTIFICADOR, this.token);
            }
            String nombreVar = token.getValor();

            this.proposicion(hasta);

            if (!this.token.getValor().equals("TO")) {
                mostrarError(Errores.erroresEnum.FALTA_TO, this.token);
            }
            leerProximoToken();

            if (!this.token.esNumero()) {
                mostrarError(Errores.erroresEnum.FALTA_NUMERO, this.token);
            }
            int repetirHasta = Integer.parseInt(this.token.getValor());
            leerProximoToken();

            if (!this.token.getValor().equals("DO")) {
                mostrarError(Errores.erroresEnum.FALTA_DO, this.token);
            }
            leerProximoToken();

            int inicioDeFor = genCod.getPosicionActual();

            identificador = AnalizadorSemantico.buscar(nombreVar, hasta, 0);
            genCod.cargarFor(identificador.getValor() * 4, repetirHasta);
            // JUMP E9 _ _ _ _

            int inicioProposicionFor = genCod.getPosicionActual();
            this.proposicion(hasta);
            int finProposicionFor = genCod.getPosicionActual();

            int distanciaSaltoFor = (finProposicionFor + 29) - inicioProposicionFor; // 29 = Corrección de todo lo que viene después

            genCod.cargarIntEn(distanciaSaltoFor, inicioProposicionFor - 4);

            genCod.aumentarUno(identificador.getValor() * 4);

            genCod.jumpA_E9____(inicioDeFor - (genCod.getPosicionActual() + 5));

            genCod.mostrarFinalDeProposicion("FOR");
        }

        if (this.token.getValor().equals("READLN")) {
            leerProximoToken();

            if (this.token.getValor().equals("(")) {

                do {
                    leerProximoToken();
                    identificador = AnalizadorSemantico.buscar(this.token.getValor(), hasta, 0);
                    if (!identificador.getTipo().equals(TipoIdentificador.VAR)) {
                        mostrarError(Errores.erroresEnum.FALTA_VAR, this.token);
                    }

                    genCod.cargarReadLn(identificador.getValor() * 4); // Se carga la dirección de la variable en la
                    // que se guardará el valor leído. Se
                    // multiplica por 4 porque cada variable
                    // ocupa 4 bytes

                    leerProximoToken();
                    if (!this.token.getValor().equals(",")) {
                        if (this.token.getValor().equals(")")) {
                            leerProximoToken();
                        } else {
                            mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
                        }
                    }

                } while (this.token.getValor().equals(","));

            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
        }
        if (this.token.getValor().equals("WRITE")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_A, this.token);
            }
            bucleWrite(hasta);
        }
        if (this.token.getValor().equals("WRITELN")) {
            leerProximoToken();
            if (this.token.getValor().equals("(")) {
                leerProximoToken();
                bucleWrite(hasta);
            }
            genCod.writeln();
        }
    } //FIN PROPOSICION

    private void condicion(int hasta) throws IOException {
        TipoToken operador = null;
        if (this.token.getValor().equals("ODD")) {
            leerProximoToken();
            expresion(hasta);
            genCod.generarOdd();
            return;
        } else {
            expresion(hasta);
            if (this.token.esSignoCondicion()) {
                switch (this.token.getValor()) {
                    case "=" -> {
                        operador = TipoToken.IGUAL;
                    }
                    case "<>" -> {
                        operador = TipoToken.DIFERENTE;
                    }
                    case "<" -> {
                        operador = TipoToken.MENOR;
                    }
                    case "<=" -> {
                        operador = TipoToken.MENOR_O_IGUAL;
                    }
                    case ">" -> {
                        operador = TipoToken.MAYOR;
                    }
                    case ">=" -> {
                        operador = TipoToken.MAYOR_O_IGUAL;
                    }
                }
                leerProximoToken();
            } else {

                mostrarError(Errores.erroresEnum.FALTA_SIGNO_COND_ODD, this.token);

            }
        }
        expresion(hasta);
        genCod.generarExpresionCondicional(operador);
    }

    private void expresion(int hasta) throws IOException {
        String operador = token.getValor();

        if (token.esSignoExpresion()) {
            operador = token.getValor();
            leerProximoToken();
        }
        termino(hasta);

        if (operador.equals("-")) {
            genCod.negarTermino();
        }
        while (token.esSignoExpresion()) {
            operador = token.getValor();
            leerProximoToken();
            termino(hasta);
            switch (operador) {
                case "+" -> {
                    genCod.generarSumar();
                }
                case "-" -> {
                    genCod.generarRestar();
                }
            }
        }
    }

    private void termino(int hasta) throws IOException {
        factor(hasta);
        while (this.token.esSignoTermino()) {
            String operador = this.token.getValor();
            leerProximoToken();
            factor(hasta);
            switch (operador) {
                case "*" -> {
                    genCod.generarMultiplicacion();
                }
                case "/" -> {
                    genCod.generarDividicion();
                }
            }
        }
    }

    private void factor(int hasta) throws IOException {
        // NO SE GENERA EL CODIGO ACA, SE GENERA EN EXPRESION
        if (this.token.getValor().equals("(")) {
            leerProximoToken();
            expresion(hasta);
            if (this.token.getValor().equals(")")) {
                leerProximoToken();
            } else {
                mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
            }

        } else if (this.token.esIdentificador()) {
            RegistroSemantico identificador = AnalizadorSemantico.buscar(token.getValor(), hasta, 0);

            if (identificador.getTipo() == TipoIdentificador.CONST) {
                genCod.cargarDireccionamientoInmediato_B8(identificador.getValor());
                genCod.pushEAX_50();
            }
            if (identificador.getTipo() == TipoIdentificador.VAR) {
                genCod.cargarDireccionamientoIndexado_8B_87(identificador.getValor() * 4);
                genCod.pushEAX_50();
            }
            leerProximoToken();

        } else if (this.token.esNumero()) {
            // cargo el numero con mov eax, _____
            //cargo el push eax ->50
            genCod.cargarDireccionamientoInmediato_B8(Integer.parseInt(token.getValor()));
            genCod.pushEAX_50();
            leerProximoToken();

        } else {
            mostrarError(Errores.erroresEnum.FALTA_IDENT_NUMERO, this.token);
        }

    }

    private void bucleWrite(int hasta) throws IOException {
        if (this.token.esCadena()) {
            genCod.writeCadena(token.getValor());
            leerProximoToken();
        } else {
            this.expresion(hasta);
            genCod.writeEntero();
        }

        while (this.token.getValor().equals(",")) {
            leerProximoToken();
            if (this.token.esCadena()) {
                genCod.writeCadena(token.getValor());
                leerProximoToken();
            } else {
                this.expresion(hasta);
                genCod.writeEntero();
            }
        }

        if (!this.token.getValor().equals(")")) {
            mostrarError(Errores.erroresEnum.FALTA_PARENTESIS_C, this.token);
        }
        leerProximoToken();
    }

}
