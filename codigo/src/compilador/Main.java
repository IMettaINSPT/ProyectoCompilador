package compilador;

import java.io.IOException;
public class Main {

    public static void main(String[] args) {

        String nomArchivo = "";
        AnalizadorLexico alex;
        try {
            alex = new AnalizadorLexico(nomArchivo);
//        Token token;
//        int numeroLinea = 1;
//
            AnalizadorSintactico aSint = new AnalizadorSintactico(alex);
            aSint.setToken(alex.escanear());
            aSint.programa();
            System.out.println("OK");

////
//        do{
//            
//            token = alex.escanear(numeroLinea);
//            numeroLinea = token.getNroLinea();
//            System.out.println(token);
//            
//        }while(!token.getValor().equals("EOF"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
           // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    
}
