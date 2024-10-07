package compilador;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String nomArchivo = "C:\\INSPT2\\S1\\ProyectoCompilador\\MAL-02.PL0";
        AnalizadorLexico alex;
        try {
            alex = new AnalizadorLexico(nomArchivo);
//        Token token;
//        int numeroLinea = 1;
            AnalizadorSemantico aSem = new AnalizadorSemantico(nomArchivo);
            GeneradorDeCodigo genCod = new GeneradorDeCodigo(nomArchivo);
            //   el 0X indica que es un nro en hexa
            genCod.cargarInt(0X2F7E9145);

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
