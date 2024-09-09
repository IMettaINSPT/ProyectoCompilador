package compilador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String nomArchivo = "C:\\IMetta\\Sistemas1\\test.pl0";
        FileReader ruta = new FileReader(nomArchivo);
        Token fToken ;    
        AnalizadorLexico alex = new AnalizadorLexico(ruta);
        AnalizadorSintactico aSint = new AnalizadorSintactico(alex);
        fToken = alex.escanear(1);
       if(!aSint.Programa(fToken)){
        System.out.println("OK");
       }
//
//        do{
//            
//            fToken = alex.escanear(numeroLinea);
//            numeroLinea = fToken.getNroLinea();
//            System.out.println(fToken);
//            
//        }while(!fToken.getValor().equals("EOF"));
    }
    
}
