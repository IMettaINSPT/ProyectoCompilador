package compilador;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class GeneradorDeCodigo {

    public String getFileOutPath() {
        return fileOutPath;
    }

    public void setFileOutPath(String fileOutPath) {
        this.fileOutPath = fileOutPath;
    }

    private ArrayList<Byte> memoria = new ArrayList<>();
    private int EDI;
    private int finalDeCodigoCargado;
    private String fileOutPath;
    private Boolean MOSTRAR_FINAL_DE_PROGRAMA = true;
    private Boolean MOSTRAR_CARACTERES_ASCII = false;
    private Boolean MOSTRAR_TOKENS = true;
    private Boolean MOSTRAR_LINEA = false;

    public GeneradorDeCodigo(String fileOutPath) {
        this.fileOutPath =fileOutPath;
        memoria.add((byte) 0x4D);
        memoria.add((byte) 0x5A);
        memoria.add((byte) 0x60);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x60);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xA0);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0E);
        memoria.add((byte) 0x1F);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x0E);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xB4);
        memoria.add((byte) 0x09);
        memoria.add((byte) 0xCD);
        memoria.add((byte) 0x21);
        memoria.add((byte) 0xB8);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x4C);
        memoria.add((byte) 0xCD);
        memoria.add((byte) 0x21);
        memoria.add((byte) 0x54);
        memoria.add((byte) 0x68);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x70);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x67);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x6D);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x57);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x33);
        memoria.add((byte) 0x32);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x63);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x70);
        memoria.add((byte) 0x70);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x63);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x2E);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x49);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x63);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x62);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x4D);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0x2D);
        memoria.add((byte) 0x44);
        memoria.add((byte) 0x4F);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0x2E);
        memoria.add((byte) 0x0D);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0x24);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0x45);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x4C);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0x4C);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xE0);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x0B);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x1C);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x28);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x1C);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x2E);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x78);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0C);
        memoria.add((byte) 0x06);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xE0);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x98);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xA4);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xB6);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x44);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x4B);
        memoria.add((byte) 0x45);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0x4E);
        memoria.add((byte) 0x45);
        memoria.add((byte) 0x4C);
        memoria.add((byte) 0x33);
        memoria.add((byte) 0x32);
        memoria.add((byte) 0x2E);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x98);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xA4);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xB6);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x45);
        memoria.add((byte) 0x78);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x63);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x47);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x48);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x46);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x57);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x46);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x47);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x43);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x4D);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x43);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6E);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x4D);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0xA2);
        memoria.add((byte) 0x1C);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x31);
        memoria.add((byte) 0xC0);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0x2C);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x0D);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0xF5);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xA3);
        memoria.add((byte) 0x2C);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x68);
        memoria.add((byte) 0x30);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x68);
        memoria.add((byte) 0x1C);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x0C);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x09);
        memoria.add((byte) 0xC0);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x30);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0xEC);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x57);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x69);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x60);
        memoria.add((byte) 0x31);
        memoria.add((byte) 0xC0);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x37);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0xF6);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xA3);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x68);
        memoria.add((byte) 0xD0);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0x25);
        memoria.add((byte) 0xD0);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x35);
        memoria.add((byte) 0xD0);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x35);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x14);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xA1);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x68);
        memoria.add((byte) 0xD4);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x68);
        memoria.add((byte) 0xBE);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x09);
        memoria.add((byte) 0xC0);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x90);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x6A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0xB6);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0xBE);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0xD4);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0xB8);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0x65);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x6F);
        memoria.add((byte) 0x72);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x60);
        memoria.add((byte) 0x89);
        memoria.add((byte) 0xC6);
        memoria.add((byte) 0x30);
        memoria.add((byte) 0xC0);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x06);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x46);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xE1);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xEB);
        memoria.add((byte) 0xF2);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x90);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x30);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xC9);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x0D);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xB9);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xB2);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x4E);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x2D);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xA2);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xCB);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xC4);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xBD);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x07);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xB6);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xAF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xA8);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xA1);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x06);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x9A);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x93);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7D);
        memoria.add((byte) 0x0B);
        memoria.add((byte) 0x50);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x2D);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x4C);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xD8);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0xEF);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0xD1);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x27);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x8C);
        memoria.add((byte) 0x95);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0xA0);
        memoria.add((byte) 0x86);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x7B);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x42);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x61);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0x96);
        memoria.add((byte) 0x98);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x47);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xE1);
        memoria.add((byte) 0xF5);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x2D);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xCA);
        memoria.add((byte) 0x9A);
        memoria.add((byte) 0x3B);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0x13);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xCA);
        memoria.add((byte) 0x9A);
        memoria.add((byte) 0x3B);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x18);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xE1);
        memoria.add((byte) 0xF5);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x05);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0x96);
        memoria.add((byte) 0x98);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xF2);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x42);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xDF);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0xA0);
        memoria.add((byte) 0x86);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x27);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xB9);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xA6);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x64);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x93);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x52);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x58);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x7A);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x15);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x10);
        memoria.add((byte) 0x40);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xB9);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x51);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xA2);
        memoria.add((byte) 0xFD);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x5B);
        memoria.add((byte) 0x59);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x0D);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0x34);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0x94);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x2D);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0x09);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x30);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0xDB);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x39);
        memoria.add((byte) 0x7F);
        memoria.add((byte) 0xD7);
        memoria.add((byte) 0x2C);
        memoria.add((byte) 0x30);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0xD0);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x0C);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0xBF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0x3C);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xEB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0xCC);
        memoria.add((byte) 0x0C);
        memoria.add((byte) 0x7F);
        memoria.add((byte) 0xA8);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x34);
        memoria.add((byte) 0x33);
        memoria.add((byte) 0x33);
        memoria.add((byte) 0xF3);
        memoria.add((byte) 0x7C);
        memoria.add((byte) 0xA0);
        memoria.add((byte) 0x88);
        memoria.add((byte) 0xC7);
        memoria.add((byte) 0xB8);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0xF8);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x7F);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x13);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x07);
        memoria.add((byte) 0x7E);
        memoria.add((byte) 0x0E);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0x7F);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x8F);
        memoria.add((byte) 0x76);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB9);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x88);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x74);
        memoria.add((byte) 0x04);
        memoria.add((byte) 0x01);
        memoria.add((byte) 0xC1);
        memoria.add((byte) 0xEB);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x29);
        memoria.add((byte) 0xC8);
        memoria.add((byte) 0x91);
        memoria.add((byte) 0x88);
        memoria.add((byte) 0xF8);
        memoria.add((byte) 0x51);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xC3);
        memoria.add((byte) 0xFD);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x5B);
        memoria.add((byte) 0x59);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0x4A);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x51);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x7A);
        memoria.add((byte) 0xFC);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x20);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x73);
        memoria.add((byte) 0xFC);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x6C);
        memoria.add((byte) 0xFC);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x5B);
        memoria.add((byte) 0x59);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x07);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0x25);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x07);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0x11);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x89);
        memoria.add((byte) 0xC8);
        memoria.add((byte) 0xB9);
        memoria.add((byte) 0x0A);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0xBA);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x3D);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x7D);
        memoria.add((byte) 0x08);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xD8);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xD8);
        memoria.add((byte) 0xEB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0xF7);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x89);
        memoria.add((byte) 0xC1);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x85);
        memoria.add((byte) 0xE6);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0xDD);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0xD6);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x85);
        memoria.add((byte) 0xCD);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xB0);
        memoria.add((byte) 0x2D);
        memoria.add((byte) 0x51);
        memoria.add((byte) 0x53);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0xFD);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x5B);
        memoria.add((byte) 0x59);
        memoria.add((byte) 0xB3);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0xE9);
        memoria.add((byte) 0xBB);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x03);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0xB2);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x80);
        memoria.add((byte) 0xFB);
        memoria.add((byte) 0x02);
        memoria.add((byte) 0x75);
        memoria.add((byte) 0x0C);
        memoria.add((byte) 0x81);
        memoria.add((byte) 0xF9);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x00);
        memoria.add((byte) 0x0F);
        memoria.add((byte) 0x84);
        memoria.add((byte) 0xA1);
        memoria.add((byte) 0xFE);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x51);
        memoria.add((byte) 0xE8);
        memoria.add((byte) 0x14);
        memoria.add((byte) 0xFD);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0xFF);
        memoria.add((byte) 0x59);
        memoria.add((byte) 0x89);
        memoria.add((byte) 0xC8);
        memoria.add((byte) 0xC3);
        System.out.println("----------------- HEADER CARGADO -----------------");
    }

    public void generarArchivoExe() {
        try {
            FileOutputStream archivo = new FileOutputStream(getFileOutPath().replace(".exe", "_mio.exe"));
            for (byte b : memoria) {
                archivo.write(b);
            }
            archivo.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int getPosicionActual() {
        return memoria.size();
    }

    public void mostrarByteCargado() {
        System.out.println("0x"
                + String.format("%02X --> %02X", memoria.size() - 1, memoria.get(memoria.size() - 1) & 0xFF)
                        .toUpperCase());

    }

    public int buscarEnteroEn(int pos) {
        return memoria.get(pos)
                + memoria.get(pos + 1) * 0x100
                + memoria.get(pos + 2) * 0x10000
                + memoria.get(pos + 3) * 0x1000000;
    }

    public void cargarByte(int valor) {
        memoria.add((byte) valor);
        mostrarByteCargado();
    }

    public void cargarByte() {
        memoria.add((byte) 0x00);
        // mostrarByteCargado();
    }

    public void cargarInt(int valor) {
        cargarByte((byte) (valor)); // Máscara para el byte menos significativo
        cargarByte((byte) ((valor) >> 8)); // Máscara para el tercer byte
        cargarByte((byte) ((valor) >> 16)); // Máscara para el segundo byte
        cargarByte((byte) ((valor) >> 24)); // Máscara para el byte más significativo
    }

    public void cargarIntEn(int valor, int posicion) {
        System.out.println("-- Cargando INT en " + toHexa(posicion) + " --> " + valor + " (" + toHexa(valor) + ") --");

        memoria.set(posicion, (byte) (valor));
        memoria.set(posicion + 1, (byte) ((valor) >> 8));
        memoria.set(posicion + 2, (byte) ((valor) >> 16));
        memoria.set(posicion + 3, (byte) ((valor) >> 24));

        System.out.println("0x" + String.format("%02X --> %02X", posicion, memoria.get(posicion) & 0xFF).toUpperCase());
        System.out.println(
                "0x" + String.format("%02X --> %02X", posicion + 1, memoria.get(posicion + 1) & 0xFF).toUpperCase());
        System.out.println(
                "0x" + String.format("%02X --> %02X", posicion + 2, memoria.get(posicion + 2) & 0xFF).toUpperCase());
        System.out.println(
                "0x" + String.format("%02X --> %02X", posicion + 3, memoria.get(posicion + 3) & 0xFF).toUpperCase());

    }

    public void cargarByteEn(int valor, int posicion) {
        System.out.println("\nCargando byte en " + posicion + ": " + toHexa(posicion));
        memoria.set(posicion, (byte) valor);
        System.out.println(String.format("%02X --> %02X", posicion, memoria.get(posicion) & 0xFF).toUpperCase());
    }

    public void cargarDireccionamientoInmediato_B8(int valor) {
        // MOV EAX, abcdefgh = B8 gh ef cd ab (COPIA EL SEGUNDO OPERANDO EN EL PRIMERO)

        mostrarInicioDeInstruccion("MOV INMEDIATO [ B8 _ _ _ _ ]", 5);

        cargarByte(0xB8);
        cargarInt(valor);
    }

    public void cargarDireccionamientoIndexado_8B_87(int valor) {
        // MOV EAX, [EDI+abcdefgh] = 8B 87 gh ef cd ab
        mostrarInicioDeInstruccion("MOV INDEXADO [ 8B 87 _ _ _ _ ]", 6);

        cargarByte(0x8B);
        cargarByte(0x87);
        cargarInt(valor);
    }

    public void asignarAVariable(int valorVar) {

        popEAX_58();
        cargarDireccionamientoIndexadoEAX_89_87(valorVar);
    }

    private void cargarDireccionamientoIndexadoEAX_89_87(int valorVar) {
        // MOV [EDI+abcdefgh], EAX = 89 87 gh ef cd ab
        mostrarInicioDeInstruccion("MOV INDEXADO EAX  [ 89 87 _ _ _ _ ]", 6);

        cargarByte(0x89);
        cargarByte(0x87);
        cargarInt(valorVar);
    }

    public void pushEAX_50() {
        // PUSH EAX = 50 --> MANDA EL VALOR DEL OPERANDO A LA PILA
        mostrarInicioDeInstruccion("PUSH EAX [ 50 ]", 1);
        cargarByte(0x50);
    }

    public void popEAX_58() {
        // POP EAX = 58 --> EXTRAE EL VALOR DE LA PILA Y LO COLOCA EN EL OPERANDO
        mostrarInicioDeInstruccion("POP EAX [ 58 ]", 1);
        cargarByte(0x58);
    }

    public void popEBX_5B() {
        // POP EBX = 5B --> EXTRAE EL VALOR DE LA PILA Y LO COLOCA EN EL OPERANDO
        mostrarInicioDeInstruccion("POP EBX [ 5B ]", 1);
        cargarByte(0x5B);
    }

    public void ret_C3() {
        // RET = C3 --> RETORNA AL PUNTO DESDE DONDE SE LLAMÓ UNA SUBRUTINA
        mostrarInicioDeInstruccion("RET [ C3 ]", 1);
        cargarByte(0xC3);
    }

    public void callA_E8_____(int distancia) {
        System.out.println("-- Cargando CALL a " + toHexa(getPosicionActual() + (distancia + 5)) + " --");
        cargarByte(0xE8); // CALL
        cargarInt(distancia);
    }

    public void cargarFor(int dirVar, int hasta) {

        System.out.println(" -- Cargando FOR desde " + toHexa(getPosicionActual()) + " --" + " (dirVar: " + dirVar + " hasta: " + hasta + ")");

        mostrarInicioDeProposicion("FOR");

//ident del for
        cargarDireccionamientoIndexado_8B_87(dirVar);
        pushEAX_50();
// numero del hasta
        cargarDireccionamientoInmediato_B8(hasta);
        pushEAX_50();

        generarExpresionCondicional(TipoToken.MENOR_O_IGUAL);
        // JUMP _ _ _ _

        mostrarFinalDeProposicion("FOR");
    }

    public void aumentarUno(int dirVar) {
        int incrementador = 01;

        cargarDireccionamientoIndexado_8B_87(dirVar); // 6 bytes
        pushEAX_50(); // 1 byte

        cargarDireccionamientoInmediato_B8(incrementador); // 5 bytes
        pushEAX_50(); // 1 byte

        generarSumar(); // 5 bytes

        popEAX_58(); // 1 byte
        cargarDireccionamientoIndexadoEAX_89_87(dirVar); // 6 bytes

    }

    public void imulEBX_F7_EB() {
        // IMUL EBX = F7 EB --> COLOCA EN EDX:EAX EL PRODUCTO DE EAX POR EBX

        mostrarInicioDeInstruccion("IMUL EBX [ F7 EB ]", 2);
        cargarByte(0xF7);
        cargarByte(0xEB);
    }

    public void generarMultiplicacion() {
        mostrarInicioDeInstruccion("MULTIPLICAR [ 58 5B F7 EB 50 ]:", 5);
        popEAX_58();
        popEBX_5B();
        imulEBX_F7_EB();
        pushEAX_50();
    }

    public void generarDividicion() {
        mostrarInicioDeInstruccion("DIVIDIR [ 58 6b 93 99 F7 FB 50 ]", 7);
        popEAX_58();
        popEBX_5B();
        exchange_93();
        cargarByte(0x99); // CDQ
        cargarByte(0xF7); // IDIV EBX
        cargarByte(0xFB);
        pushEAX_50();
    }

    public void generarSumar() {
        mostrarInicioDeInstruccion("SUMAR [ 58 6B 01 D8 50 ]", 5);
        popEAX_58();
        popEBX_5B();
        add_01_D8();
        pushEAX_50();
    }

    public void generarRestar() {
        mostrarInicioDeInstruccion("RESTAR [ 58 5B 93 D8 50 ]", 5);
        popEAX_58();
        popEBX_5B();
        exchange_93();
        sub_29_D8();
        pushEAX_50();
    }

    public void add_01_D8() {
        // ADD EAX, EBX = 01 D8 --> SUMA AMBOS OPERANDOS Y COLOCA EL RESULTADO EN EL
        // PRIMERO (EAX)

        mostrarInicioDeInstruccion("ADD [ 01 D8 ]", 2);
        cargarByte(0x01);
        cargarByte(0xD8);
    }

    public void sub_29_D8() {
        // SUB EAX, EBX = 29 D8 --> LE RESTA EL SEGUNDO OPERANDO AL PRIMERO Y COLOCA EL
        // RESULTADO EN EL PRIMER OPERANDO

        mostrarInicioDeInstruccion("ADD [ 01 D8 ]", 2);
        cargarByte(0x29);
        cargarByte(0xD8);
    }

    public void negarTermino() {
        mostrarInicioDeInstruccion("NEGAR [ 58 F7 D8 50 ]", 4);
        popEAX_58();
        cambiarSignoEAX_F7_D8();
        pushEAX_50();
    }

    private void cambiarSignoEAX_F7_D8() {
        // NEG EAX = F7 D8 --> CAMBIA EL SIGNO DE EAX
        mostrarInicioDeInstruccion("CAMBIAR SIGNO [ F7 D8 ]", 2);
        cargarByte(0xF7); // F7 D8 CAMBIA EL SIGNO DE EAX
        cargarByte(0xD8);
    }

    private void exchange_93() {
        // XCHG EAX, EBX = 93 --> INTERCAMBIA LOS VALORES DE LOS OPERANDOS

        mostrarInicioDeInstruccion("EXCHANGE [ 93 ]", 1);
        cargarByte(0x93);
    }

    public void generarOdd() {
        // ODD = 58 A8 01 7B 05 E9 00 00 00 00

        mostrarInicioDeInstruccion("ODD [ 58 A8 01 7B 05 E9 00 00 00 00 ]", 10);
        popEAX_58();
        cargarByte(0xA8); // TEST AL, ab
        cargarByte(0x01);
        cargarByte(0x7B); // JPO dir (si es impar)
        cargarByte(0x05); // Saltar 5 (para saltear el jump en caso de que tenga que saltar toda la
        // proposición)
        reservarJUMP_E9____();
    }

    public void cargarReadLn(int valorVar) {
        // CALL, _ _ _ _ (función en el header)
        mostrarInicioDeProposicion("READLN [ E/S ]");

        int posicionActual = getPosicionActual();
        int distanciaHaciaES = Constantes.LEER_ENTERO_Y_GUARDAR_EN_EAX - (posicionActual + 5); // + 5 porqué se tienen
        // en cuenta los 5 bytes
        // de la instrucción
        // siguiente (CALL)
        callA_E8_____(distanciaHaciaES);

        cargarDireccionamientoIndexadoEAX_89_87(valorVar);

        mostrarFinalDeProposicion("READLN");
    }

    public void writeEntero() {
        // CALL, _ _ _ _ (función en el header)
        mostrarInicioDeProposicion("WRITE ENTERO [ E/S ]");

        popEAX_58();

        int posicionActual = getPosicionActual();
        int distanciaHaciaES = Constantes.IMPRIMIR_ENTERO_DE_EAX - (posicionActual + 5);
        callA_E8_____(distanciaHaciaES);

        mostrarFinalDeProposicion("WRITE ENTERO");
    }

    public void writeln() {
        // CALL, _ _ _ _ (función en el header)

        mostrarInicioDeProposicion("WRITELN [ E/S ]");

        int posicionActual = getPosicionActual();
        int distanciaHaciaES = Constantes.IMPRIMIR_SALTO_DE_LINEA - (posicionActual + 5);
        callA_E8_____(distanciaHaciaES);

        mostrarFinalDeProposicion("WRITELN");
    }

    public void writeCadena(String cadena) {

        mostrarInicioDeProposicion("WRITE CADENA [ E/S ]");

        /*
         * 1. Se genera la inicialización de EAX con la ubicación absoluta que
         * tendrá la cadena (se conoce porque la longitud de los pasos 2 y 3
         * es fija), usando para calcularla los campos BaseOfCode (posiciones
         * 204-207, o 00CC-00CF en hexadecimal) e ImageBase (posiciones 212-
         * 215, o 00D4-00D7 en hexadecimal) del encabezado del archivo
         * ejecutable;
         */
        int baseOfCodePosicion = buscarEnteroEn(Constantes.BASE_OF_CODE_POSICION); // Inicio de la sección de código
        int imageBasePosicion = buscarEnteroEn(Constantes.IMAGE_BASE_POSICION); // Base de la imagen
        int posicionActual = getPosicionActual(); // Posición actual en la memoria
        int inicioDeCadena = 15; // Posición de inicio de la cadena (después de las siguientes 3 instrucciones de
        // 5 bytes c/u)
        int tamanoHeader = buscarEnteroEn(Constantes.TAMANO_HEADER_POSICION); // Tamaño del header

        int ubiCadena = baseOfCodePosicion + imageBasePosicion + posicionActual + inicioDeCadena - tamanoHeader;

        cargarDireccionamientoInmediato_B8(ubiCadena);

        // 2. Se genera la invocación a la rutina de E/S que mostrará la cadena;
        int distanciaHaciaES = Constantes.IMPRIMIR_CADENA - (getPosicionActual() + 5); // + 5 porqué se tienen en cuenta los 5
        // bytes de la instrucción siguiente (CALL)
        callA_E8_____(distanciaHaciaES);

        // 3. Se genera un salto incondicional E9 00 00 00 00;
        // reservarJUMP();
        int tamanoCadena = cadena.length();
        int comillasSimples = 2; // La cadena tiene comillas simples al principio y al final (se las sacamos)
        int ceroFinal = 1; // Tiene un cero al final de la cadena (se lo agregamos)

        int tamanoFinalDeCadena = tamanoCadena - comillasSimples + ceroFinal;

        // cargarByte(0xe9);
        // cargarInt(tamanoFinalDeCadena);
        jumpA_E9____(tamanoFinalDeCadena);
        // int jumpPosicion = getPosicionActual();

        // 4. Se generan los bytes de la cadena, seguidos de un cero;
        mostrarInicioDeInstruccion("ASCII CADENA", tamanoFinalDeCadena);
        for (int i = 1; i < tamanoCadena - 1; i++) {
            char c = cadena.charAt(i);
            if (MOSTRAR_CARACTERES_ASCII) {
                System.out.println(c);
            }
            cargarByte(c);
        }
        cargarByte(0);

        // 5. Se realiza el fix-up del salto colocado en el paso 3. (No hace falta
        // porque se cargo bien de inicio)
        // int cadenaFinal = getPosicionActual();
        // cargarIntEn(cadenaFinal, jumpPosicion - 4);
        mostrarFinalDeProposicion("WRITE CADENA");
    }

    public void reservarJUMP_E9____() {
        // RESERVAR JUMP = E9 00 00 00 00 --> RESERVA ESPACIO PARA EL JUMP YA QUE NO SE
        // CONOCE LA CANTIDAD DE BYTES
        mostrarInicioDeInstruccion("JUMP | E9 _ _ _ _ |", 5);
        cargarByte(0xE9); // JMP dir
        cargarByte(0x00);
        cargarByte(0x00);
        cargarByte(0x00);
        cargarByte(0x00);
    }

    public void jumpA_E9____(int valor) {
        // CUANDO SE CONOCE EL VALOR DEL JUMP
        System.out.println("-- Cargando JUMP con valor: " + valor + " (" + toHexa(valor) + ") --");
        cargarByte(0xE9); // JMP dir
        cargarInt(valor);
    }

    public void comparereEAXyBAX_39_C3() {
        // CMP EAX, EBX = 39 C3 --> COMPARA EL PRIMER OPERANDO CON EL SEGUNDO PARA QUE,
        // SEGÚN EL RESULTADO DE LA COMPARACIÓN, PUEDAN HACERSE SALTOS CONDICIONALES A
        // CONTINUACIÓN

        mostrarInicioDeInstruccion("CMP AEX BEX", EDI);
        cargarByte(0x39); // CMP EAX, EBX
        cargarByte(0xC3);
    }

    public void generarExpresionCondicional(TipoToken expCond) {
        // EXPRESION CONDICIONAL = 58 5B 39 C3

        mostrarInicioDeInstruccion("EXPRESION CONDICIONAL", 11);
        popEAX_58();
        popEBX_5B();
        comparereEAXyBAX_39_C3();

        switch (expCond) {
            case TipoToken.IGUAL:
                cargarByte(0x74);
                break;
            case TipoToken.DIFERENTE:
                cargarByte(0x75);
                break;
            case TipoToken.MENOR:
                cargarByte(0x7C);
                break;
            case TipoToken.MENOR_O_IGUAL:
                cargarByte(0x7E);
                break;
            case TipoToken.MAYOR:
                cargarByte(0x7F);
                break;
            case TipoToken.MAYOR_O_IGUAL:
                cargarByte(0x7D);
                break;
            default:
                System.out.println("Error: expresión condicional no válida");
                break;
        }

        cargarByte(0x05);
        reservarJUMP_E9____();
    }

    void cargarEDI() {
        System.out.println("---------------------- INICIO DE CARGA DE PROGRAMA (" + toHexa(getPosicionActual() + 1)
                + ") ----------------------");

        // MOV EDI, abcdefgh = BF gh ef cd ab --> (COPIA EL SEGUNDO OPERANDO EN EL
        // PRIMERO)
        // Luego se asignará el valor de EDI a la variable correspondiente (SIEMPRE ES
        // 0040157A)
        reservarEDI();

        this.EDI = getPosicionActual() - 4; // - 4 para quedar en BF x<- _ _ _
    }

    private void reservarEDI() {
        mostrarInicioDeInstruccion("Reservando EDI [ BF _ _ _ _ ]", 5);

        cargarByte(0xBF);
        cargarByte(0x00);
        cargarByte(0x00);
        cargarByte(0x00);
        cargarByte(0x00);
    }

    public void finalDePrograma(int cantVariables) {
        System.out.println("---------------------- FINAL DE PROGRAMA ----------------------");
        this.finalDeCodigoCargado = getPosicionActual();

        actualizarHeader(); // 0 - Actualizar Header
        fixupEDI(); // 1 - Actualizar EDI
        reservarMemoriaParaVariables(cantVariables); // 2 - Reservar memoria para variables
        actualizarVirtualSize(); // 3 - Actualizar VirtualSize
        rellenarMultiploDeFileAlignment(); // 4 - Rellenar con 0 múltiplo de FileAlignment
        ajustarSizoOfCodeSection(); // 5 - Ajustar SizeOfCodeSection
        ajustarSizeOfRawData(); // 5 - Ajustar SizeOfRawData
        ajustarSizeOfImage(); // 6 - Ajustar SizeOfImage
        ajustarSizeOfData(); // 6 - Ajustar BaseOfData

        int programaFinalizado = getPosicionActual();

        System.out.println("\n--------- Programa finalizado en " + programaFinalizado + " bytes ("
                + toHexa(programaFinalizado) + ") ---------");
    }

    private void actualizarHeader() {
        System.out.println("\n0. Cargando tamaño de programa en HEADER " + finalDeCodigoCargado + " bytes ("
                + toHexa(finalDeCodigoCargado) + ")\n");
        int distancia = Constantes.FINALIZAR_PROGRAMA - (finalDeCodigoCargado + 5);
        jumpA_E9____(distancia);
    }

    private void fixupEDI() {
        /*
         * A continuación, se debe hacer un fix-up de la primera instrucción de
         * la parte de longitud variable de la sección text (MOV EDI, 00000000), ya
         * que el desplazamiento actual en el archivo ejecutable indica el comienzo
         * del área de almacenamiento de las variables.
         */

        System.out.println("\n1. Actualizando EDI\n");

        int posicionActual = getPosicionActual();
        int baseOfCodePosicion = buscarEnteroEn(Constantes.BASE_OF_CODE_POSICION);
        int imageBasePosicion = buscarEnteroEn(Constantes.IMAGE_BASE_POSICION);
        int tamanoHeader = buscarEnteroEn(Constantes.TAMANO_HEADER_POSICION);

        int posicion = baseOfCodePosicion + imageBasePosicion + posicionActual
                - tamanoHeader;

        System.out.println("\n\t=== Calculando posición de EDI ===");
        System.out.println("\tPosición actual     : " + toHexa(posicionActual) + " (" + posicionActual + ")");
        System.out.println("\tBaseOfCode          : " + toHexa(baseOfCodePosicion) + " (" + baseOfCodePosicion + ")");
        System.out.println("\tImageBase           : " + toHexa(imageBasePosicion) + " (" + imageBasePosicion + ")");
        System.out.println("\tTamaño del header   : " + toHexa(tamanoHeader) + " (" + tamanoHeader + ")");
        System.out.println("\tPosición de EDI     : " + toHexa(posicion) + " (" + posicion + ")\n");

        cargarIntEn(posicion, EDI);
        System.out.println("\nEDI actualizado a   : " + toHexa(posicion));

    }

    private void reservarMemoriaParaVariables(int cantVariables) {
        /*
         * Luego, deben grabarse ceros al final del archivo ejecutable, a razón
         * de cuatro bytes por cada variable (a esta altura de la compilación, el
         * número de variables que fueron declaradas ya es conocido).
         */

        int espacioReservado = 4 * cantVariables;
        System.out.println("\n2. Reservando espacio para variables: " + cantVariables + " variables\n");
        System.out.println("\nDesde --> " + toHexa(getPosicionActual() + 1)); // + 1 para que no se incluya el byte actual
        for (int i = 0; i < espacioReservado; i++) {
            cargarByte(0);
        }
        System.out.println("Hasta --> " + toHexa(getPosicionActual()));
    }

    private void actualizarVirtualSize() {
        /*
         * Ahora se debe realizar el ajuste del campo VirtualSize del encabezado
         * de la sección text (posiciones 416-419, o 01A0-01A3 en hexadecimal),
         * colocando allí el tamaño de la sección text (hasta el momento).
         */

        int sizeTextSection = getPosicionActual() - buscarEnteroEn(Constantes.TAMANO_HEADER_POSICION);
        System.out.println("\n3. Actualizando VirtualSize\n");
        cargarIntEn(sizeTextSection, Constantes.VIRTUAL_SIZE_POSICION);
    }

    private void rellenarMultiploDeFileAlignment() {
        /*
         * Después, debe rellenarse el archivo con ceros, para que su tamaño sea
         * múltiplo del campo FileAlignment del encabezado opcional específico para
         * Windows (posiciones 220-223, o 00DC-00DF en hexadecimal).
         */

        int fileAlignment = buscarEnteroEn(Constantes.FILE_ALIGNMENT_POSICION);
        System.out.println("\n4. Rellenando múltiplo de FileAlignment (" + fileAlignment + " bytes)\n");
        int posicionActual = getPosicionActual();
        int cantidadDeCeros = 0;

        System.out.println("Desde --> " + toHexa(getPosicionActual() + 1)); // + 1 para que no se incluya el byte actual
        while (posicionActual % fileAlignment != 0) {
            cargarByte();
            posicionActual++;
            cantidadDeCeros++;
        }
        System.out.println("Hasta --> " + toHexa(getPosicionActual()));
        System.out.println("\nSe rellenaron " + cantidadDeCeros + " bytes con 00");

    }

    private void ajustarSizoOfCodeSection() {
        // SizeOfCodeSection (posiciones 188-191, o 00BC-00BF en hexadecimal)

        System.out.println("\n5. Ajustando SizeOfCodeSection\n");
        int sizeTextSection = getPosicionActual() - buscarEnteroEn(Constantes.TAMANO_HEADER_POSICION);
        cargarIntEn(sizeTextSection, Constantes.SIZE_OF_CODE_SECTION_POSICION);
    }

    private void ajustarSizeOfRawData() {
        // SizeOfRawData (posiciones 424-427, o 01A8-01AB en hexadecimal)

        System.out.println("\n5. Ajustando SizeOfRawData\n");
        int sizeTextSection = getPosicionActual() - buscarEnteroEn(Constantes.TAMANO_HEADER_POSICION);
        cargarIntEn(sizeTextSection, Constantes.SIZE_OF_RAW_DATA_POSICION);
    }

    private void ajustarSizeOfImage() {
        // SizeOfImage (posiciones 240-243, o 00F0-00F3 en hexadecimal)

        System.out.println("\n6. Ajustando SizeOfImage\n");
        int sizeOfCodeSection = buscarEnteroEn(Constantes.SIZE_OF_CODE_SECTION_POSICION);
        int sectionAlignment = buscarEnteroEn(Constantes.SECTION_ALIGNMENT_POSICION);

        cargarIntEn((2 + sizeOfCodeSection / sectionAlignment) * sectionAlignment, Constantes.SIZE_OF_IMAGE_POSICION);
    }

    private void ajustarSizeOfData() {
        // SizeOfImage (posiciones 240-243, o 00F0-00F3 en hexadecimal)

        System.out.println("\n6. Ajustando BaseOfData\n");
        int sizeOfRawData = buscarEnteroEn(Constantes.SIZE_OF_RAW_DATA_POSICION);
        int sectionAlignment = buscarEnteroEn(Constantes.SECTION_ALIGNMENT_POSICION);

        cargarIntEn((2 + sizeOfRawData / sectionAlignment) * sectionAlignment, Constantes.BASE_OF_DATA_POSICION);
    }

    public void mostrarInicioDeProposicion(String instruccion) {
        System.out.println("\n\n------------ [Inicio] proposición " + instruccion + " ------------\n");
    }

    public void mostrarFinalDeProposicion(String instruccion) {
        System.out.println("\n------------ [Fin] proposición " + instruccion + " ------------\n");
    }

    public void mostrarInicioDeInstruccion(String instruccion, int tamano) {
        System.out.println("\n-- [Cargando] instrucción " + instruccion + " (" + tamano + " bytes):");
    }

    public String toHexa(int valor) {
        return "0x" + Integer.toHexString(valor).toUpperCase();
    }

}
