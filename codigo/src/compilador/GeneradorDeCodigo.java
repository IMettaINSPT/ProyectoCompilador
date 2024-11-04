package compilador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class GeneradorDeCodigo {

    private ArrayList<Byte> memoria;
    private String fileOutPath;
    private int EDI;
    private int finalDeCodigoCargado;

    public static int BASE_OF_CODE = 204;
    public static int IMAGE_BASE = 212;
    public static int VIRTUAL_SIZE = 416;
    public static int TAMANO_HEADER = 244;
    public static int FILE_ALIGNMENT = 220;
    public static int SIZE_OF_CODE_SECTION = 188;
    public static int SIZE_OF_RAW_DATA = 424;
    public static int SIZE_OF_IMAGE = 240;
    public static int BASE_OF_DATA = 208;
    public static int SECTION_ALIGNMENT = 216;
    public static int SALTO_DE_LINEA = 1040;
    public static final int IMPRIMIR_CADENA = 992;
    public static final int IMPRIMIR_ENTERO_DE_EAX = 1056;
    public static final int FINALIZAR_PROGRAMA = 1416;

    public GeneradorDeCodigo(String fileOutPath) {
        this.fileOutPath = fileOutPath.toUpperCase().replace(".PL0", ".exe");
        memoria = new ArrayList<>();
        memoria.add(((byte) 0x4D));
        memoria.add(((byte) 0x5A));
        memoria.add(((byte) 0x60));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x60));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xA0));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0E));
        memoria.add(((byte) 0x1F));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x0E));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xB4));
        memoria.add(((byte) 0x09));
        memoria.add(((byte) 0xCD));
        memoria.add(((byte) 0x21));
        memoria.add(((byte) 0xB8));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x4C));
        memoria.add(((byte) 0xCD));
        memoria.add(((byte) 0x21));
        memoria.add(((byte) 0x54));
        memoria.add(((byte) 0x68));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x70));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x67));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x6D));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x57));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x33));
        memoria.add(((byte) 0x32));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x63));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x70));
        memoria.add(((byte) 0x70));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x63));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x2E));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x49));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x63));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x62));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x4D));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0x2D));
        memoria.add(((byte) 0x44));
        memoria.add(((byte) 0x4F));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0x2E));
        memoria.add(((byte) 0x0D));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0x24));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0x45));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x4C));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0x4C));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xE0));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x0B));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x1C));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x28));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x1C));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x2E));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x78));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0C));
        memoria.add(((byte) 0x06));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xE0));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x98));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xA4));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xB6));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x44));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x4B));
        memoria.add(((byte) 0x45));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0x4E));
        memoria.add(((byte) 0x45));
        memoria.add(((byte) 0x4C));
        memoria.add(((byte) 0x33));
        memoria.add(((byte) 0x32));
        memoria.add(((byte) 0x2E));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x98));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xA4));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xB6));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x45));
        memoria.add(((byte) 0x78));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x63));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x47));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x48));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x46));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x57));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x46));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x47));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x43));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x4D));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x43));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6E));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x4D));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0xA2));
        memoria.add(((byte) 0x1C));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x31));
        memoria.add(((byte) 0xC0));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0x2C));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x0D));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0xF5));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xA3));
        memoria.add(((byte) 0x2C));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x68));
        memoria.add(((byte) 0x30));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x68));
        memoria.add(((byte) 0x1C));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x0C));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x09));
        memoria.add(((byte) 0xC0));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x30));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0xEC));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x57));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x69));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x60));
        memoria.add(((byte) 0x31));
        memoria.add(((byte) 0xC0));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x37));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0xF6));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xA3));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x68));
        memoria.add(((byte) 0xD0));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0x25));
        memoria.add(((byte) 0xD0));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x35));
        memoria.add(((byte) 0xD0));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x35));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x14));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xA1));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x68));
        memoria.add(((byte) 0xD4));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x68));
        memoria.add(((byte) 0xBE));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x09));
        memoria.add(((byte) 0xC0));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x90));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x6A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0xB6));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0xBE));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0xD4));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0xB8));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0x65));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x6F));
        memoria.add(((byte) 0x72));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x60));
        memoria.add(((byte) 0x89));
        memoria.add(((byte) 0xC6));
        memoria.add(((byte) 0x30));
        memoria.add(((byte) 0xC0));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x06));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x46));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xE1));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xEB));
        memoria.add(((byte) 0xF2));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x90));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x30));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xC9));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x0D));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xB9));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xB2));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x4E));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x2D));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xA2));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xCB));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xC4));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xBD));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x07));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xB6));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xAF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xA8));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xA1));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x06));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x9A));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x93));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7D));
        memoria.add(((byte) 0x0B));
        memoria.add(((byte) 0x50));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x2D));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x4C));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xD8));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0xEF));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0xD1));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x27));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x8C));
        memoria.add(((byte) 0x95));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0xA0));
        memoria.add(((byte) 0x86));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x7B));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x42));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x61));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0x96));
        memoria.add(((byte) 0x98));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x47));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xE1));
        memoria.add(((byte) 0xF5));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x2D));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xCA));
        memoria.add(((byte) 0x9A));
        memoria.add(((byte) 0x3B));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0x13));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xCA));
        memoria.add(((byte) 0x9A));
        memoria.add(((byte) 0x3B));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x18));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xE1));
        memoria.add(((byte) 0xF5));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x05));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0x96));
        memoria.add(((byte) 0x98));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xF2));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x42));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xDF));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0xA0));
        memoria.add(((byte) 0x86));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x27));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xB9));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xA6));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x64));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x93));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x52));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x58));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x7A));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x15));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x10));
        memoria.add(((byte) 0x40));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xB9));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x51));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xA2));
        memoria.add(((byte) 0xFD));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x5B));
        memoria.add(((byte) 0x59));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x0D));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0x34));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0x94));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x2D));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0x09));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x30));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0xDB));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x39));
        memoria.add(((byte) 0x7F));
        memoria.add(((byte) 0xD7));
        memoria.add(((byte) 0x2C));
        memoria.add(((byte) 0x30));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0xD0));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x0C));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0xBF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0x3C));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xEB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0xCC));
        memoria.add(((byte) 0x0C));
        memoria.add(((byte) 0x7F));
        memoria.add(((byte) 0xA8));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x34));
        memoria.add(((byte) 0x33));
        memoria.add(((byte) 0x33));
        memoria.add(((byte) 0xF3));
        memoria.add(((byte) 0x7C));
        memoria.add(((byte) 0xA0));
        memoria.add(((byte) 0x88));
        memoria.add(((byte) 0xC7));
        memoria.add(((byte) 0xB8));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0xF8));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x7F));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x13));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x07));
        memoria.add(((byte) 0x7E));
        memoria.add(((byte) 0x0E));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0x7F));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x8F));
        memoria.add(((byte) 0x76));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB9));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x88));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x74));
        memoria.add(((byte) 0x04));
        memoria.add(((byte) 0x01));
        memoria.add(((byte) 0xC1));
        memoria.add(((byte) 0xEB));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x29));
        memoria.add(((byte) 0xC8));
        memoria.add(((byte) 0x91));
        memoria.add(((byte) 0x88));
        memoria.add(((byte) 0xF8));
        memoria.add(((byte) 0x51));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xC3));
        memoria.add(((byte) 0xFD));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x5B));
        memoria.add(((byte) 0x59));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0x4A));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x51));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x7A));
        memoria.add(((byte) 0xFC));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x20));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x73));
        memoria.add(((byte) 0xFC));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x6C));
        memoria.add(((byte) 0xFC));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x5B));
        memoria.add(((byte) 0x59));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x07));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0x25));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x07));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0x11));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x89));
        memoria.add(((byte) 0xC8));
        memoria.add(((byte) 0xB9));
        memoria.add(((byte) 0x0A));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0xBA));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x3D));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x7D));
        memoria.add(((byte) 0x08));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xD8));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xD8));
        memoria.add(((byte) 0xEB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0xF7));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x89));
        memoria.add(((byte) 0xC1));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x85));
        memoria.add(((byte) 0xE6));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0xDD));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0xD6));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x85));
        memoria.add(((byte) 0xCD));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xB0));
        memoria.add(((byte) 0x2D));
        memoria.add(((byte) 0x51));
        memoria.add(((byte) 0x53));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0xFD));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x5B));
        memoria.add(((byte) 0x59));
        memoria.add(((byte) 0xB3));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0xE9));
        memoria.add(((byte) 0xBB));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x03));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0xB2));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x80));
        memoria.add(((byte) 0xFB));
        memoria.add(((byte) 0x02));
        memoria.add(((byte) 0x75));
        memoria.add(((byte) 0x0C));
        memoria.add(((byte) 0x81));
        memoria.add(((byte) 0xF9));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x00));
        memoria.add(((byte) 0x0F));
        memoria.add(((byte) 0x84));
        memoria.add(((byte) 0xA1));
        memoria.add(((byte) 0xFE));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x51));
        memoria.add(((byte) 0xE8));
        memoria.add(((byte) 0x14));
        memoria.add(((byte) 0xFD));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0xFF));
        memoria.add(((byte) 0x59));
        memoria.add(((byte) 0x89));
        memoria.add(((byte) 0xC8));
        memoria.add(((byte) 0xC3));
    }

    public void cargarByte(int valor) {
        memoria.add((byte) valor);
    }

    public void cargarInt(int valor) {
        memoria.add((byte) valor);               // Byte menos significativo
        memoria.add((byte) (valor >> 8));    // Tercer byte
        memoria.add((byte) (valor >> 16));  // Segundo byte
        memoria.add((byte) (valor >> 24));  // Byte más significativo
    }

    public void cargarByteEn(int posicion, int valor) {
        memoria.set(posicion, (byte) valor);
    }

    public void cargarIntEn(int posicion, int valor) {
        if (posicion < 0 || posicion + 3 >= memoria.size()) {
            throw new IndexOutOfBoundsException("La posición está fuera del rango de la memoria");
        }
        memoria.set(posicion, (byte) valor);                 // Byte menos significativo
        memoria.set(posicion + 1, (byte) (valor >> 8));      // Tercer byte
        memoria.set(posicion + 2, (byte) (valor >> 16));    // Segundo byte
        memoria.set(posicion + 3, (byte) (valor >> 24));    // Byte mas significativo
    }

    //vuelca dummp
    public void generarArchivoExe() {
        File file = new File(this.fileOutPath);
        try (FileOutputStream salida = new FileOutputStream(file)) {
            for (byte b : memoria) {
                salida.write(b);
            }
            salida.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int obtenerPosicion() {
        return memoria.size();
    }

    public void cargarInicializacionRegistroEDI() {
        // MOV EDI, abcdefgh = BF gh ef cd ab --> (COPIA EL SEGUNDO OPERANDO EN EL
        // PRIMERO)
        // Luego se asignará el valor de EDI a la variable correspondiente (SIEMPRE ES
        // 0040157A)
        cargarByte(0xBF);
        cargarInt(0);
        this.EDI = this.obtenerPosicion() - 4; // - 4 para quedar en BF x<- _ _ _ 

    }

    public void cargarMultiplicacion() {
        cargarByte(0x58); //POP EAX
        cargarByte(0x5B); //POP EBX
        cargarByte(0xF7); //IMUL EBX
        cargarByte(0xEB); //IMUL EBX
        cargarByte(0x50); // PUSH EAX
    }

    public void cargarDivision() {
        cargarByte(0x58); //POP EAX
        cargarByte(0x5B); //POP EBX
        cargarByte(0x93); //XCHG EAX, EBX INTERCAMBIA LOS VALORES DE LOS OPERANDOS
        cargarByte(0x99); //CDQ LLENA TODOS LOS BITS DE EDX CON EL VALOR DEL BIT DEL SIGNO DE EAX
        /*DIVIDE EDX:EAX POR EL OPERANDO Y
        COLOCA EL COCIENTE EN EAX Y EL
        RESTO EN EDX*/
        cargarByte(0xF7); //IDIV EBX Parte1
        cargarByte(0xFB); //IDIV EBX Parte2
        cargarByte(0x50); // PUSH EAX
    }

    public void negarTermino() {
        cargarByte(0x58); //POP EAX 
        cargarByte(0xF7); //NEG EAX 
        cargarByte(0xD8); //NEG EAX 
        cargarByte(0x50); // PUSH EAX
    }

    public void cargarSuma() {
        cargarByte(0x58); //POP EAX
        cargarByte(0x5B); //POP EBX
        cargarByte(0x01); //ADD EAX, EBX
        cargarByte(0xD8); //ADD EAX, EBX
        cargarByte(0x50); // PUSH EAX
    }

    public void cargarResta() {
        cargarByte(0x58); //POP EAX
        cargarByte(0x5B); //POP EBX
        cargarByte(0x93); //XCHG EAX, EBX
        cargarByte(0x29); //SUB EAX, EBX
        cargarByte(0xD8); //SUB EAX, EBX
        cargarByte(0x50); // PUSH EAX
    }

    public void cargarExpresionODD() {
        cargarByte(0x58); //POP EAX 
        cargarByte(0xA8); //POP EBX 
        cargarByte(0x01); //
        cargarByte(0x7B); // JPO (JUMP IF PARITY ODD)
        cargarByte(0x05); //
        cargarJMP(0); //E9 00 00 00 00
    }

    public void cargarCondicion(String operador) {
        cargarByte(0x58); //POP EAX 
        cargarByte(0x5B); //POP EBX 
        // CMP EBX, EAX 
        cargarByte(0x39);
        cargarByte(0xC3);

        switch (operador) {
            case "=":
                cargarByte(0x74);
                break;
            case "<>":
                cargarByte(0x75);
                break;
            case "<":
                cargarByte(0x7C);
                break;
            case "<=":
                cargarByte(0x7E);
                break;
            case ">":
                cargarByte(0x7F);
                break;
            case ">=":
                cargarByte(0x7D);
                break;
        }
        cargarByte(0x05);
        cargarJMP(0);
    }

    public void cargarJMP(int valor) {
        cargarByte(0xE9); //JUMP
        cargarInt(valor);  //VALOR DEL JUMP
    }

    public void cargarIdent(int valor) {
        cargarByte(0x58); //POP EAX
        cargarByte(0x89); //MOV [EDI+abcdefgh], EAX
        cargarByte(0x87); //MOV [EDI+abcdefgh], EAX
        cargarInt(valor);
    }

    public void cargaConsola(int valorVar) {
        int valorSize = obtenerPosicion();
        int valorSalto = 1424 - (valorSize + 5); // + 5 porqué se tienen en cuenta los 5 bytes de la instrucción siguiente (CALL)
        cargarByte(0xE8); //CALL
        cargarInt(valorSalto);
        cargarByte(0x89); //MOV [EDI+abcdefgh], EAX direccionamiento indexado
        cargarByte(0x87); //MOV [EDI+abcdefgh], EAX
        cargarInt(valorVar);

    }

    public void cargarConst(int valor) {
        cargarByte(0xB8);  //MOV EAX, abcdefgh
        cargarInt(valor);      // gh ef cd ab
        cargarByte(0x50);   //PUSH EAX
    }

    public void cargarVar(int valor) {
        cargarByte(0x8B);  //MOV EAX, [EDI+abcdefgh]
        cargarByte(0x87);  //MOV EAX, [EDI+abcdefgh]
        cargarInt(valor);     // gh ef cd ab
        cargarByte(0x50);   //PUSH EAX
    }

    public void cargarNumero(String numero) {
        cargarByte(0xB8);            //MOV EAX, abcdefgh
        int valor = Integer.parseInt(numero);
        cargarInt(valor);                  //gh ef cd ab
        cargarByte(0x50);             //PUSH EAX
    }

    public void cargarSaltosCondicionales(int finCondicion) {
        int finwhile = obtenerPosicion();
        int distanciaDeSalto = finwhile - finCondicion;
        cargarIntEn(finCondicion - 4, distanciaDeSalto);
    }

    public void cargarResultadoExpresion() {
        cargarByte(0x58); //POP EAX
        int posicion = obtenerPosicion();
        int distancia = IMPRIMIR_ENTERO_DE_EAX - (posicion + 5);
        cargarByte(0xE8);
        cargarInt(distancia);
    }

    public void cargarSaltoDeLinea() {
        int posicion = obtenerPosicion();
        int distancia = SALTO_DE_LINEA - (posicion + 5);
        cargarByte(0xE8);
        cargarInt(distancia);
    }

    public void cargarCallDeProcedimiento(int valorProcedure) {
        int inicioDeProcedimiento = obtenerPosicion();
        int valorDeSalto = valorProcedure - (inicioDeProcedimiento + 5);
        // Hago la cuenta para que me salte al bloque donde esta el procedimiento 
        // (direccion procedure cargado en tabla de analisiS semantico) - ( posicion actual del salto  + 5 ( me salteo las 5 instruccion del llamado al procedure e8 (1 byte) + dire (4 bytes)))
        System.out.println("valor de salto " + valorDeSalto);
        cargarByte(0xE8);
        cargarInt(valorDeSalto);
    }

    public void cargarCadena(String cadena) {
        int BaseOfCode = buscarEnteroEn(BASE_OF_CODE);
        int ImageBase = buscarEnteroEn(IMAGE_BASE);
        int inicioCadena = obtenerPosicion();
        int tamanoHeader = buscarEnteroEn(TAMANO_HEADER);
        int direccionDeCadena = inicioCadena + 15 + BaseOfCode + ImageBase - tamanoHeader;

        cargarByte(0xB8); //MOV EAX
        cargarInt(direccionDeCadena);      //direccion cadena en EAX                                            // preguntar
        cargarCallDeProcedimiento(IMPRIMIR_CADENA);
        int tamanoCadena = cadena.length();
        int comillasSimples = 2; // La cadena tiene comillas simples al principio y al final (se las sacamos)
        int ceroFinal = 1; // Tiene un cero al final de la cadena (se lo agregamos)

        int tamanoFinalDeCadena = tamanoCadena - comillasSimples + ceroFinal;
        cargarJMP(tamanoFinalDeCadena);
        System.out.println(cadena);
        for (int i = 1; i < cadena.length() - 1; i++) {
            cargarByte(cadena.charAt(i));
        }
        cargarByte(0x00);
    }

    public void cargarSaltoProcedureAProposicion(int posActual) {
        int aca = obtenerPosicion();
        int dist = aca - posActual;
        cargarIntEn(posActual - 4, dist);
    }

    public int buscarEnteroEn(int pos) {
        return memoria.get(pos) + memoria.get(pos + 1) * 0x100 + memoria.get(pos + 2) * 0x10000 + memoria.get(pos + 3) * 0x1000000;
    }

    public int componer(int posicion) {
        return (memoria.get(posicion) + 256 * memoria.get(posicion + 1) + 256 * 256 * memoria.get(posicion + 2) + 256 * 256 * 256 * memoria.get(posicion + 3));
    }

    public String toHexa(int valor) {
        return "0x" + Integer.toHexString(valor).toUpperCase();
    }

    private void actualizarHeader() {
        System.out.println("\n0. Cargando tamaño de programa en HEADER " + finalDeCodigoCargado + " bytes (" + toHexa(finalDeCodigoCargado) + ")\n");
        int distancia = FINALIZAR_PROGRAMA - (finalDeCodigoCargado + 5);
        cargarJMP(distancia);
    }

    private void fixupEDI() {
        /*
         * A continuación, se debe hacer un fix-up de la primera instrucción de
         * la parte de longitud variable de la sección text (MOV EDI, 00000000), ya
         * que el desplazamiento actual en el archivo ejecutable indica el comienzo
         * del área de almacenamiento de las variables.
         */

        System.out.println("\n1. Actualizando EDI\n");

        int posicionActual = this.obtenerPosicion();
        int baseOfCodePosicion = buscarEnteroEn(BASE_OF_CODE);
        int imageBasePosicion = buscarEnteroEn(IMAGE_BASE);
        int tamanoHeader = buscarEnteroEn(TAMANO_HEADER);

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
        System.out.println("\nDesde --> " + toHexa(obtenerPosicion() + 1)); // + 1 para que no se incluya el byte actual
        for (int i = 0; i < espacioReservado; i++) {
            cargarByte(0);
        }
        System.out.println("Hasta --> " + toHexa(obtenerPosicion()));
    }

    private void actualizarVirtualSize() {
        /*
         * Ahora se debe realizar el ajuste del campo VirtualSize del encabezado
         * de la sección text (posiciones 416-419, o 01A0-01A3 en hexadecimal),
         * colocando allí el tamaño de la sección text (hasta el momento).
         */

        int sizeTextSection = obtenerPosicion() - buscarEnteroEn(TAMANO_HEADER);
        System.out.println("\n3. Actualizando VirtualSize\n");
        cargarIntEn(sizeTextSection, VIRTUAL_SIZE);
    }

    private void rellenarMultiploDeFileAlignment() {
        /*
         * Después, debe rellenarse el archivo con ceros, para que su tamaño sea
         * múltiplo del campo FileAlignment del encabezado opcional específico para
         * Windows (posiciones 220-223, o 00DC-00DF en hexadecimal).
         */

        int fileAlignment = buscarEnteroEn(FILE_ALIGNMENT);
        System.out.println("\n4. Rellenando múltiplo de FileAlignment (" + fileAlignment + " bytes)\n");
        int posicionActual = obtenerPosicion();
        int cantidadDeCeros = 0;

        System.out.println("Desde --> " + toHexa(obtenerPosicion() + 1)); // + 1 para que no se incluya el byte actual
        while (posicionActual % fileAlignment != 0) {
            cargarByte(0x00);
            posicionActual++;
            cantidadDeCeros++;
        }
        System.out.println("Hasta --> " + toHexa(obtenerPosicion()));
        System.out.println("\nSe rellenaron " + cantidadDeCeros + " bytes con 00");

    }

    private void ajustarSizoOfCodeSection() {
        // SizeOfCodeSection (posiciones 188-191, o 00BC-00BF en hexadecimal)

        System.out.println("\n5. Ajustando SizeOfCodeSection\n");
        int sizeTextSection = obtenerPosicion() - buscarEnteroEn(TAMANO_HEADER);
        cargarIntEn(sizeTextSection, SIZE_OF_CODE_SECTION);
    }

    private void ajustarSizeOfRawData() {
        // SizeOfRawData (posiciones 424-427, o 01A8-01AB en hexadecimal)

        System.out.println("\n5. Ajustando SizeOfRawData\n");
        int sizeTextSection = finalDeCodigoCargado - buscarEnteroEn(TAMANO_HEADER);
        cargarIntEn(sizeTextSection, SIZE_OF_RAW_DATA);
    }

    private void ajustarSizeOfImage() {
        // SizeOfImage (posiciones 240-243, o 00F0-00F3 en hexadecimal)

        System.out.println("\n6. Ajustando SizeOfImage\n");
        int sizeOfCodeSection = buscarEnteroEn(SIZE_OF_CODE_SECTION);
        int sectionAlignment = buscarEnteroEn(SECTION_ALIGNMENT);

        cargarIntEn((2 + sizeOfCodeSection / sectionAlignment) * sectionAlignment, SIZE_OF_IMAGE);
    }

    private void ajustarSizeOfData() {
        // SizeOfImage (posiciones 240-243, o 00F0-00F3 en hexadecimal)

        System.out.println("\n6. Ajustando BaseOfData\n");
        int sizeOfRawData = buscarEnteroEn(SIZE_OF_RAW_DATA);
        int sectionAlignment = buscarEnteroEn(SECTION_ALIGNMENT);

        cargarIntEn((2 + sizeOfRawData / sectionAlignment) * sectionAlignment, BASE_OF_DATA);
    }

    public void finalDePrograma(int cantVariables) {
        System.out.println("---------------------- FINAL DE PROGRAMA ----------------------");
        this.finalDeCodigoCargado = this.obtenerPosicion();

        actualizarHeader(); // 0 - Actualizar Header
        fixupEDI(); // 1 - Actualizar EDI
        reservarMemoriaParaVariables(cantVariables); // 2 - Reservar memoria para variables
        actualizarVirtualSize(); // 3 - Actualizar VirtualSize
        rellenarMultiploDeFileAlignment(); // 4 - Rellenar con 0 múltiplo de FileAlignment
        ajustarSizoOfCodeSection(); // 5 - Ajustar SizeOfCodeSection
        ajustarSizeOfRawData(); // 5 - Ajustar SizeOfRawData
        ajustarSizeOfImage(); // 6 - Ajustar SizeOfImage
        ajustarSizeOfData(); // 6 - Ajustar BaseOfData

        int programaFinalizado = this.obtenerPosicion();

        System.out.println("\n--------- Programa finalizado en " + programaFinalizado + " bytes (" + toHexa(programaFinalizado) + ") ---------");
    }

}
