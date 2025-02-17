package compilador;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String nomArchivo = "C:\\INSPT2\\S1\\RECUTN.PL0";
        AnalizadorLexico alex;
        try {
            alex = new AnalizadorLexico(nomArchivo);
//        Token token;
//        int numeroLinea = 1;
            AnalizadorSemantico aSem = new AnalizadorSemantico();
            GeneradorDeCodigo genCod = new GeneradorDeCodigo(nomArchivo);

            AnalizadorSintactico aSint = new AnalizadorSintactico(alex, aSem, genCod);

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
