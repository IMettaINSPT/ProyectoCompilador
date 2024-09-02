
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class main {
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        String rutaArchivo = "C:\\IMetta\\Sistemas1\\sum.PL0"; // Cambia esto por la ruta de tu archivo
        ScannerToken sc = new ScannerToken();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            List<Token> list;
            while ((linea = br.readLine()) != null) {
                if(!linea.isBlank()){
                    System.out.println(ANSI_GREEN + linea + ANSI_GREEN);
                    list = sc.GetTokens(linea);                    
                    for(Token t :  list)
                    {
                        System.out.println(t.toString());
                    }                    
                }
            }
        } catch (IOException e) {
            System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }

}
