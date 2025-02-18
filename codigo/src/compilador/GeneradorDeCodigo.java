package compilador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Alumno
 */
public class GeneradorDeCodigo {

    ArrayList<Byte> memoria = new ArrayList<Byte>();
    FileOutputStream archivoWin32;
    static final int MAXIDENT = 0x100;
    static final int TAMANO_BEAN = 0x4;
    static final int SALTO_DE_LINEA = 0x410;
    static final int MUESTRA_INT_EAX = 0x420;
    static final int POSICION_MEMORIA_READLN = 0x590;
    static final int DESPLAZAMIENTO_STRING = 0xf;
    static final int POSICION_CODIGO_BASE = 0xcc;
    static final int POSICION_IMAGEN_BASE = 0xd4;
    static final int POSICION_TAMANO_HEADER =0xf4;
    static final int MUESTRA_CADENA = 0x3e0;
    static final int POSICION_FINAL_DEL_PROGRAMA = 0x588;
    static final int POSICION_MOV_EDI = 0x700;
    static final int POSICION_TAMANO_VIRTUAL = 0x1a0;
    static final int POSICION_ALINEAMIENTO_ARCHIVO =0xdc;
    static final int POSICION_SECCION_CODIGO = 0xbc;
    static final int POSICION_DATA_PURA = 0x1a8;
    static final int POSICION_ALINEAMIENTO_SECCION = 0xd8;
    static final int POSICION_TAMANO_IMAGEN = 0xf0;
    static final int POSICION_BASE_DATOS = 0xd0;
    private  String pathFile="";
    
    private int EDI;

    public int getPosActual() {
        return memoria.size();
    }

    public GeneradorDeCodigo(String path) {
        cargarEncabezadosYRutinas();
        pathFile = path;
    }

    public void cargarFinalDelPrograma(int cantVar) {
        //System.out.println(cantVar);
        //POSICION_FINAL_DEL_PROGRAMA
        int distancia = POSICION_FINAL_DEL_PROGRAMA - (memoria.size() + 5); //CALL dir
        cargarByte(0xE9); //CALL dir
        cargarInt(distancia); //:)
        fixupEDI();
        iniciarVars(cantVar);
        reemplazarTamanoVirtual();
        reemplazarSizeOfCodSection();
        reemplazarSizeOfRawData();
        reemplazarSizeOfImgyBaseOfData();
        llenarCeros();
    }

    public void GenerarArchivo() throws IOException {
        // Volcar el ArrayList en el archivo binario
FileOutputStream archivoWin32 = new FileOutputStream(pathFile.replace(".PL0", ".exe"));
        

        for (Byte bytes : memoria) {
            archivoWin32.write(bytes);  // Escribir cada byte en el archivo .exe
        }

        archivoWin32.close();
        System.out.println("Archivo generado exitosamente");
    }
  
    public void cargarByte(int valor) {
        memoria.add((byte) valor);
    }

    public void cargarByteEn(int posicion, int valor) {
        memoria.set(posicion, (byte) valor);
    }

    public void cargarInt(int valor) {
        memoria.add((byte) valor);           // Byte menos significativo
        memoria.add((byte) (valor >> 8));
        memoria.add((byte) (valor >> 16));
        memoria.add((byte) (valor >> 24));  // Byte más significativo
    }

    public void cargarIntEn(int posicion, int valor) {
        if (posicion < 0 || posicion + 3 >= memoria.size()) {
            throw new IndexOutOfBoundsException("posicion de memoria fuera del rango");
        }
        memoria.set(posicion, (byte) valor);                 // Byte menos significativo
        memoria.set(posicion + 1, (byte) (valor >> 8));      // Tercer byte
        memoria.set(posicion + 2, (byte) (valor >> 16));    // Segundo byte
        memoria.set(posicion + 3, (byte) (valor >> 24));    // Byte mas significativo
    }

    public void cargarPushEAX_50() {
        cargarByte(0x50); //PUSH EAX
    }

    public void cargarPopEAX() {
        cargarByte(0x58); //POP EAX
    }

    public void cargarPopEBX_5B() {
        cargarByte(0x5B); //POP EAX
    }

    public void cargarMovEAX_var_8B_87(int valor) {
        cargarByte(0x8B);
        cargarByte(0x87);
        cargarInt(valor);
    }

    public void cargarMovEAX_num_B8(int valor) {
        cargarByte(0xB8);
        cargarInt(valor);
    }

    public void cargarMovVar_EAX_89_87(int valor) {
        cargarByte(0x89);
        cargarByte(0x87);
        cargarInt(valor);
    }

    public void cargarXchgEAX_EBX_93() {
        cargarByte(0x93);
    }

    public void generar_JMP(int valor) {
        cargarByte(0xE9); //JUMP
        cargarInt(valor);  //VALOR DEL JUMP
    }

    public void generar_call(int valor) {
        cargarByte(0xE8); //JUMP
        cargarInt(valor);  //VALOR DEL JUMP
    }

    public void cargarEncabezadosYRutinas() {
        int[] encabezado = {0x4D, 0x5A, 0x60, 0x01, 0x01, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x60, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA0, 0x00, 0x00, 0x00, 0x0E, 0x1F, 0xBA, 0x0E, 0x00, 0xB4, 0x09, 0xCD, 0x21, 0xB8, 0x01, 0x4C, 0xCD, 0x21, 0x54, 0x68, 0x69, 0x73, 0x20, 0x70, 0x72, 0x6F, 0x67, 0x72, 0x61, 0x6D, 0x20, 0x69, 0x73, 0x20, 0x61, 0x20, 0x57, 0x69, 0x6E, 0x33, 0x32, 0x20, 0x63, 0x6F, 0x6E, 0x73, 0x6F, 0x6C, 0x65, 0x20, 0x61, 0x70, 0x70, 0x6C, 0x69, 0x63, 0x61, 0x74, 0x69, 0x6F, 0x6E, 0x2E, 0x20, 0x49, 0x74, 0x20, 0x63, 0x61, 0x6E, 0x6E, 0x6F, 0x74, 0x20, 0x62, 0x65, 0x20, 0x72, 0x75, 0x6E, 0x20, 0x75, 0x6E, 0x64, 0x65, 0x72, 0x20, 0x4D, 0x53, 0x2D, 0x44, 0x4F, 0x53, 0x2E, 0x0D, 0x0A, 0x24, 0x00, 0x00, 0x00, 0x00, 0x50, 0x45, 0x00, 0x00, 0x4C, 0x01, 0x01, 0x00, 0x00, 0x00, 0x53, 0x4C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xE0, 0x00, 0x02, 0x01, 0x0B, 0x01, 0x01, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x15, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1C, 0x10, 0x00, 0x00, 0x28, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2E, 0x74, 0x65, 0x78, 0x74, 0x00, 0x00, 0x00, 0x0C, 0x06, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0xE0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6E, 0x10, 0x00, 0x00, 0x7C, 0x10, 0x00, 0x00, 0x8C, 0x10, 0x00, 0x00, 0x98, 0x10, 0x00, 0x00, 0xA4, 0x10, 0x00, 0x00, 0xB6, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x52, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x44, 0x10, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x4B, 0x45, 0x52, 0x4E, 0x45, 0x4C, 0x33, 0x32, 0x2E, 0x64, 0x6C, 0x6C, 0x00, 0x00, 0x6E, 0x10, 0x00, 0x00, 0x7C, 0x10, 0x00, 0x00, 0x8C, 0x10, 0x00, 0x00, 0x98, 0x10, 0x00, 0x00, 0xA4, 0x10, 0x00, 0x00, 0xB6, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x45, 0x78, 0x69, 0x74, 0x50, 0x72, 0x6F, 0x63, 0x65, 0x73, 0x73, 0x00, 0x00, 0x00, 0x47, 0x65, 0x74, 0x53, 0x74, 0x64, 0x48, 0x61, 0x6E, 0x64, 0x6C, 0x65, 0x00, 0x00, 0x00, 0x00, 0x52, 0x65, 0x61, 0x64, 0x46, 0x69, 0x6C, 0x65, 0x00, 0x00, 0x00, 0x00, 0x57, 0x72, 0x69, 0x74, 0x65, 0x46, 0x69, 0x6C, 0x65, 0x00, 0x00, 0x00, 0x47, 0x65, 0x74, 0x43, 0x6F, 0x6E, 0x73, 0x6F, 0x6C, 0x65, 0x4D, 0x6F, 0x64, 0x65, 0x00, 0x00, 0x00, 0x00, 0x53, 0x65, 0x74, 0x43, 0x6F, 0x6E, 0x73, 0x6F, 0x6C, 0x65, 0x4D, 0x6F, 0x64, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x50, 0xA2, 0x1C, 0x11, 0x40, 0x00, 0x31, 0xC0, 0x03, 0x05, 0x2C, 0x11, 0x40, 0x00, 0x75, 0x0D, 0x6A, 0xF5, 0xFF, 0x15, 0x04, 0x10, 0x40, 0x00, 0xA3, 0x2C, 0x11, 0x40, 0x00, 0x6A, 0x00, 0x68, 0x30, 0x11, 0x40, 0x00, 0x6A, 0x01, 0x68, 0x1C, 0x11, 0x40, 0x00, 0x50, 0xFF, 0x15, 0x0C, 0x10, 0x40, 0x00, 0x09, 0xC0, 0x75, 0x08, 0x6A, 0x00, 0xFF, 0x15, 0x00, 0x10, 0x40, 0x00, 0x81, 0x3D, 0x30, 0x11, 0x40, 0x00, 0x01, 0x00, 0x00, 0x00, 0x75, 0xEC, 0x58, 0xC3, 0x00, 0x57, 0x72, 0x69, 0x74, 0x65, 0x20, 0x65, 0x72, 0x72, 0x6F, 0x72, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x60, 0x31, 0xC0, 0x03, 0x05, 0xCC, 0x11, 0x40, 0x00, 0x75, 0x37, 0x6A, 0xF6, 0xFF, 0x15, 0x04, 0x10, 0x40, 0x00, 0xA3, 0xCC, 0x11, 0x40, 0x00, 0x68, 0xD0, 0x11, 0x40, 0x00, 0x50, 0xFF, 0x15, 0x10, 0x10, 0x40, 0x00, 0x80, 0x25, 0xD0, 0x11, 0x40, 0x00, 0xF9, 0xFF, 0x35, 0xD0, 0x11, 0x40, 0x00, 0xFF, 0x35, 0xCC, 0x11, 0x40, 0x00, 0xFF, 0x15, 0x14, 0x10, 0x40, 0x00, 0xA1, 0xCC, 0x11, 0x40, 0x00, 0x6A, 0x00, 0x68, 0xD4, 0x11, 0x40, 0x00, 0x6A, 0x01, 0x68, 0xBE, 0x11, 0x40, 0x00, 0x50, 0xFF, 0x15, 0x08, 0x10, 0x40, 0x00, 0x09, 0xC0, 0x61, 0x90, 0x75, 0x08, 0x6A, 0x00, 0xFF, 0x15, 0x00, 0x10, 0x40, 0x00, 0x0F, 0xB6, 0x05, 0xBE, 0x11, 0x40, 0x00, 0x81, 0x3D, 0xD4, 0x11, 0x40, 0x00, 0x01, 0x00, 0x00, 0x00, 0x74, 0x05, 0xB8, 0xFF, 0xFF, 0xFF, 0xFF, 0xC3, 0x00, 0x52, 0x65, 0x61, 0x64, 0x20, 0x65, 0x72, 0x72, 0x6F, 0x72, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x60, 0x89, 0xC6, 0x30, 0xC0, 0x02, 0x06, 0x74, 0x08, 0x46, 0xE8, 0xE1, 0xFE, 0xFF, 0xFF, 0xEB, 0xF2, 0x61, 0x90, 0xC3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x30, 0xE8, 0xC9, 0xFE, 0xFF, 0xFF, 0xC3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB0, 0x0D, 0xE8, 0xB9, 0xFE, 0xFF, 0xFF, 0xB0, 0x0A, 0xE8, 0xB2, 0xFE, 0xFF, 0xFF, 0xC3, 0x00, 0x3D, 0x00, 0x00, 0x00, 0x80, 0x75, 0x4E, 0xB0, 0x2D, 0xE8, 0xA2, 0xFE, 0xFF, 0xFF, 0xB0, 0x02, 0xE8, 0xCB, 0xFF, 0xFF, 0xFF, 0xB0, 0x01, 0xE8, 0xC4, 0xFF, 0xFF, 0xFF, 0xB0, 0x04, 0xE8, 0xBD, 0xFF, 0xFF, 0xFF, 0xB0, 0x07, 0xE8, 0xB6, 0xFF, 0xFF, 0xFF, 0xB0, 0x04, 0xE8, 0xAF, 0xFF, 0xFF, 0xFF, 0xB0, 0x08, 0xE8, 0xA8, 0xFF, 0xFF, 0xFF, 0xB0, 0x03, 0xE8, 0xA1, 0xFF, 0xFF, 0xFF, 0xB0, 0x06, 0xE8, 0x9A, 0xFF, 0xFF, 0xFF, 0xB0, 0x04, 0xE8, 0x93, 0xFF, 0xFF, 0xFF, 0xB0, 0x08, 0xE8, 0x8C, 0xFF, 0xFF, 0xFF, 0xC3, 0x3D, 0x00, 0x00, 0x00, 0x00, 0x7D, 0x0B, 0x50, 0xB0, 0x2D, 0xE8, 0x4C, 0xFE, 0xFF, 0xFF, 0x58, 0xF7, 0xD8, 0x3D, 0x0A, 0x00, 0x00, 0x00, 0x0F, 0x8C, 0xEF, 0x00, 0x00, 0x00, 0x3D, 0x64, 0x00, 0x00, 0x00, 0x0F, 0x8C, 0xD1, 0x00, 0x00, 0x00, 0x3D, 0xE8, 0x03, 0x00, 0x00, 0x0F, 0x8C, 0xB3, 0x00, 0x00, 0x00, 0x3D, 0x10, 0x27, 0x00, 0x00, 0x0F, 0x8C, 0x95, 0x00, 0x00, 0x00, 0x3D, 0xA0, 0x86, 0x01, 0x00, 0x7C, 0x7B, 0x3D, 0x40, 0x42, 0x0F, 0x00, 0x7C, 0x61, 0x3D, 0x80, 0x96, 0x98, 0x00, 0x7C, 0x47, 0x3D, 0x00, 0xE1, 0xF5, 0x05, 0x7C, 0x2D, 0x3D, 0x00, 0xCA, 0x9A, 0x3B, 0x7C, 0x13, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x00, 0xCA, 0x9A, 0x3B, 0xF7, 0xFB, 0x52, 0xE8, 0x18, 0xFF, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x00, 0xE1, 0xF5, 0x05, 0xF7, 0xFB, 0x52, 0xE8, 0x05, 0xFF, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x80, 0x96, 0x98, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0xF2, 0xFE, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x40, 0x42, 0x0F, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0xDF, 0xFE, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0xA0, 0x86, 0x01, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0xCC, 0xFE, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x10, 0x27, 0x00, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0xB9, 0xFE, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0xE8, 0x03, 0x00, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0xA6, 0xFE, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x64, 0x00, 0x00, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0x93, 0xFE, 0xFF, 0xFF, 0x58, 0xBA, 0x00, 0x00, 0x00, 0x00, 0xBB, 0x0A, 0x00, 0x00, 0x00, 0xF7, 0xFB, 0x52, 0xE8, 0x80, 0xFE, 0xFF, 0xFF, 0x58, 0xE8, 0x7A, 0xFE, 0xFF, 0xFF, 0xC3, 0x00, 0xFF, 0x15, 0x00, 0x10, 0x40, 0x00, 0x00, 0x00, 0xB9, 0x00, 0x00, 0x00, 0x00, 0xB3, 0x03, 0x51, 0x53, 0xE8, 0xA2, 0xFD, 0xFF, 0xFF, 0x5B, 0x59, 0x3C, 0x0D, 0x0F, 0x84, 0x34, 0x01, 0x00, 0x00, 0x3C, 0x08, 0x0F, 0x84, 0x94, 0x00, 0x00, 0x00, 0x3C, 0x2D, 0x0F, 0x84, 0x09, 0x01, 0x00, 0x00, 0x3C, 0x30, 0x7C, 0xDB, 0x3C, 0x39, 0x7F, 0xD7, 0x2C, 0x30, 0x80, 0xFB, 0x00, 0x74, 0xD0, 0x80, 0xFB, 0x02, 0x75, 0x0C, 0x81, 0xF9, 0x00, 0x00, 0x00, 0x00, 0x75, 0x04, 0x3C, 0x00, 0x74, 0xBF, 0x80, 0xFB, 0x03, 0x75, 0x0A, 0x3C, 0x00, 0x75, 0x04, 0xB3, 0x00, 0xEB, 0x02, 0xB3, 0x01, 0x81, 0xF9, 0xCC, 0xCC, 0xCC, 0x0C, 0x7F, 0xA8, 0x81, 0xF9, 0x34, 0x33, 0x33, 0xF3, 0x7C, 0xA0, 0x88, 0xC7, 0xB8, 0x0A, 0x00, 0x00, 0x00, 0xF7, 0xE9, 0x3D, 0x08, 0x00, 0x00, 0x80, 0x74, 0x11, 0x3D, 0xF8, 0xFF, 0xFF, 0x7F, 0x75, 0x13, 0x80, 0xFF, 0x07, 0x7E, 0x0E, 0xE9, 0x7F, 0xFF, 0xFF, 0xFF, 0x80, 0xFF, 0x08, 0x0F, 0x8F, 0x76, 0xFF, 0xFF, 0xFF, 0xB9, 0x00, 0x00, 0x00, 0x00, 0x88, 0xF9, 0x80, 0xFB, 0x02, 0x74, 0x04, 0x01, 0xC1, 0xEB, 0x03, 0x29, 0xC8, 0x91, 0x88, 0xF8, 0x51, 0x53, 0xE8, 0xC3, 0xFD, 0xFF, 0xFF, 0x5B, 0x59, 0xE9, 0x53, 0xFF, 0xFF, 0xFF, 0x80, 0xFB, 0x03, 0x0F, 0x84, 0x4A, 0xFF, 0xFF, 0xFF, 0x51, 0x53, 0xB0, 0x08, 0xE8, 0x7A, 0xFC, 0xFF, 0xFF, 0xB0, 0x20, 0xE8, 0x73, 0xFC, 0xFF, 0xFF, 0xB0, 0x08, 0xE8, 0x6C, 0xFC, 0xFF, 0xFF, 0x5B, 0x59, 0x80, 0xFB, 0x00, 0x75, 0x07, 0xB3, 0x03, 0xE9, 0x25, 0xFF, 0xFF, 0xFF, 0x80, 0xFB, 0x02, 0x75, 0x0F, 0x81, 0xF9, 0x00, 0x00, 0x00, 0x00, 0x75, 0x07, 0xB3, 0x03, 0xE9, 0x11, 0xFF, 0xFF, 0xFF, 0x89, 0xC8, 0xB9, 0x0A, 0x00, 0x00, 0x00, 0xBA, 0x00, 0x00, 0x00, 0x00, 0x3D, 0x00, 0x00, 0x00, 0x00, 0x7D, 0x08, 0xF7, 0xD8, 0xF7, 0xF9, 0xF7, 0xD8, 0xEB, 0x02, 0xF7, 0xF9, 0x89, 0xC1, 0x81, 0xF9, 0x00, 0x00, 0x00, 0x00, 0x0F, 0x85, 0xE6, 0xFE, 0xFF, 0xFF, 0x80, 0xFB, 0x02, 0x0F, 0x84, 0xDD, 0xFE, 0xFF, 0xFF, 0xB3, 0x03, 0xE9, 0xD6, 0xFE, 0xFF, 0xFF, 0x80, 0xFB, 0x03, 0x0F, 0x85, 0xCD, 0xFE, 0xFF, 0xFF, 0xB0, 0x2D, 0x51, 0x53, 0xE8, 0xFD, 0xFB, 0xFF, 0xFF, 0x5B, 0x59, 0xB3, 0x02, 0xE9, 0xBB, 0xFE, 0xFF, 0xFF, 0x80, 0xFB, 0x03, 0x0F, 0x84, 0xB2, 0xFE, 0xFF, 0xFF, 0x80, 0xFB, 0x02, 0x75, 0x0C, 0x81, 0xF9, 0x00, 0x00, 0x00, 0x00, 0x0F, 0x84, 0xA1, 0xFE, 0xFF, 0xFF, 0x51, 0xE8, 0x14, 0xFD, 0xFF, 0xFF, 0x59, 0x89, 0xC8, 0xC3};
        int m = 0;
        for (int n : encabezado) {
            memoria.add((byte) n);
            m++;
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    //        OPERACIONES MATEMATICAS: (Expresion)
    //
    //////////////////////////////////////////////////////////////////////
    public void generar_suma() {
        cargarPopEAX(); //POP EAX
        cargarPopEBX_5B(); //POP EBX
        cargarByte(0x01); //ADD EAX, EBX (primer mitad)
        cargarByte(0xD8); //ADD EAX, EBX (segunda mitad)
        cargarPushEAX_50();//PUSH EAX
    }

    public void incrementar1(int valor) {

        generarFactorVar(valor);  //carga la variable a incrementar en la pila
        cargarMovEAX_num_B8(0x1);
        cargarPushEAX_50();   //carga un 1 en la pila
        generar_suma();             //los generar_suma
        cargarAsignacion(valor); //asigna el resultado a la variable a incrementar

    }
    
     public void decrementar1(int valor) {

        generarFactorVar(valor);  //carga la variable a incrementar en la pila
        cargarMovEAX_num_B8(0x1);
        cargarPushEAX_50();   //carga un 1 en la pila
        generar_resta();             //los generar_suma
        cargarAsignacion(valor); //asigna el resultado a la variable a incrementar

    }

    public void generar_resta() {
        // System.out.println("cargando la generar_resta");
        cargarPopEAX(); //POP EAX
        cargarPopEBX_5B(); //POP EBX
        cargarXchgEAX_EBX_93();
        cargarByte(0x29); //SUB EAX, EBX
        cargarByte(0xD8); //SUB EAX, EBX
        cargarPushEAX_50();//PUSH EAX
    }

    public void cargar_menosUnario() {
        cargarPopEAX(); //POP EAX
        cargarByte(0xF7); //NEG EAX (primer byte)
        cargarByte(0xD8); //NEG EAX (segundo byte)
        cargarPushEAX_50();//PUSH EAX
    }

    public void cargar_multiplicacion() {
        cargarPopEAX(); //POP EAX
        cargarPopEBX_5B(); //POP EBX
        cargarByte(0xF7); //IMUL EBX (1er byte)
        cargarByte(0xEB); //IMUL EBX (2d0 byte)
        cargarPushEAX_50();//PUSH EAX
    }

    public void cargar_division() {
        cargarPopEAX(); //POP EAX
        cargarPopEBX_5B(); //POP EBX
        cargarXchgEAX_EBX_93();
        cargarByte(0x99); //CDQ
        cargarByte(0xF7); //IDIV EBX (1er byte)
        cargarByte(0xFB); //IDIV EBX (2do byte)
        cargarPushEAX_50(); //PUSH EAX
    }

/////////////////////////////////////////////////////////////////////
//    
//                     FACTOR
//
///////////////////////////////////////////////////////////////////
    public void generarFactorVar(int valor) {
        cargarMovEAX_var_8B_87(valor);
        cargarPushEAX_50();//PUSH EAX
    }

    public void generarFactorConst(int valor) {
        cargarMovEAX_num_B8(valor);
        cargarPushEAX_50();//PUSH EAX
    }

    public void generarFactorNum(int valor) {
        cargarMovEAX_num_B8(valor);
        cargarPushEAX_50();//PUSH EAX
    }

/////////////////////////////////////////////////////////////////
//
//                      Condicion
//
//////////////////////////////////////////////////////////////////////
    public void cargarCondicion(String operador) {
        cargarPopEAX(); //POP EAX
        cargarPopEBX_5B();//POP EBX
        cargarByte(0x39);
        cargarByte(0xC3);

        switch (operador) {

            case ">":
                cargarByte(0x7F);
                break;
            case ">=":
                cargarByte(0x7D);
                break;
            case "<":
                cargarByte(0x7C);
                break;
            case "<=":
                cargarByte(0x7E);
                break;
            case "=":
                cargarByte(0x74);
                break;
            case "<>":
                cargarByte(0x75);
                break;
        }
        cargarByte(0x05);
        generar_JMP(0);
    }

    public void cargarODD() {

        cargarPopEAX(); //POP EAX
        cargarByte(0xA8); //TEST AL, ab
        cargarByte(0x01); //TEST AL, ab
        cargarByte(0x7B); //JPO dir
        cargarByte(0x05);
        generar_JMP(0);

    }

////////////////////////////////////////////////////////////////////
//
//                      PROPOSICION
//
//////////////////////////////////////////////////////////////////////
    public void cargarAsignacion(int valor) {
        cargarPopEAX(); //POP EAX --> TRAE DESDE LA PILA
        cargarMovVar_EAX_89_87(valor);
    }

    public void cargar_Call(int valor) {
        //TRAIGO LA DIRECCION EN LA MEMORIA
        int distancia = valor - (memoria.size() + 5);
        generar_call(distancia);
    }

    public void cargarJmpFor(int dirVar, String paso) {

        generarFactorVar(dirVar); //copio el valor de la variable en EAX y pusheo

        if (paso.equals("DOWNTO")) {
            cargarCondicion("<=");
        } else if (paso.equals("TO")) {
            cargarCondicion(">=");
        }
    }

    public void incrementarContVueltasFor(int dirVar, String paso) {
        cargarMovEAX_var_8B_87(dirVar);
        cargarPushEAX_50();
        cargarMovEAX_num_B8(0x1);
        cargarPushEAX_50();

        if (paso.equals("DOWNTO")) {
            generar_resta();
        } else if (paso.equals("TO")) {
            generar_suma();
        }
    }
// 
//repeat
//

    public void cargarEvaluacionRepeat() {
        cargarMovEAX_num_B8(0x1);
        cargarPushEAX_50();
        generar_resta();//le generar_resta uno a la exprecion de control que quedo en la pila (NO ME GUSTA; MISMO PROBLEMA QUE EL FOR ANIDADO)

        cargarMovEAX_num_B8(0x0);

        cargarXchgEAX_EBX_93(); //lo intercambia con EBX
        cargarPopEAX(); //POPea EAX (ahora tiene la generar_resta entre el valor y uno)
        cargarPushEAX_50(); //y vuelve a ponerlo en la pila, para la siguiente vuelta
        cargarByte(0x39);
        cargarByte(0xC3);
        cargarByte(0x7E);  //copara y salta se se ha completado el num de vueltas
        cargarByte(0x05);
        generar_JMP(0);

    }

/////////////////////////////////////////////////////////////////
//
//                    READLN--WRITE/WRITELN
//
//////////////////////////////////////////////////////////////////////     
    public void generarReadln(int valor) {

        int distancia =   POSICION_MEMORIA_READLN - (memoria.size() + 5);
        generar_call(distancia);
        cargarMovVar_EAX_89_87(valor);
    }

    public void generarWriteString(String cad) {

        int strPos = memoria.size()
                +    DESPLAZAMIENTO_STRING//15
                + traerIntdeMemoria(  POSICION_CODIGO_BASE) //0x1000
                + traerIntdeMemoria(  POSICION_IMAGEN_BASE) //0x400000
                - traerIntdeMemoria(  POSICION_TAMANO_HEADER); //0x200

        cargarMovEAX_num_B8(strPos);

        int distancia =   MUESTRA_CADENA - (memoria.size() + 5);
        generar_call(distancia);

        generar_JMP(cad.length() + 1);

        //Genera los byte de la cadena
        for (int i = 0; i < cad.length(); i++) {
            memoria.add((byte) cad.charAt(i));
        }
        cargarByte(0x00); //seguido de un cero
    }

    public void cargarWriteResultado() {
        cargarPopEAX();
        int distancia =   MUESTRA_INT_EAX - (memoria.size() + 5);
        generar_call(distancia);

    }

    public void genSaltoLinea() {
        int posicion = memoria.size();
        int distancia =   SALTO_DE_LINEA - (posicion + 5);

        generar_call(distancia);

    }

      
    public void cargarRET() {
        cargarByte(0XC3); //al salir de bloque en PROCEDURE debe generarse una instruccion RET (codigo C3) 
    }

    /////////////////////////////////////////////////////////////////
    //
    //                     finalizado el programa
    //
    //////////////////////////////////////////////////////////////////////   
   public void cargarEDI() {
        reservarEDI();
        this.EDI = getPosActual() - 4; // - 4 para quedar en BF x<- _ _ _ 
    }

    private void reservarEDI() {

        cargarByte(0xBF);
        cargarByte(0x00);
        cargarByte(0x00);
        cargarByte(0x00);
        cargarByte(0x00);
    }

    public void fixupEDI() {

        int posVar = getPosActual()
                + traerIntdeMemoria(  POSICION_CODIGO_BASE)//0x1000
                + traerIntdeMemoria(  POSICION_IMAGEN_BASE)//0x400000
                - traerIntdeMemoria(  POSICION_TAMANO_HEADER);//

        reemplazarInt(  POSICION_MOV_EDI + 1, posVar);
    }

    public void reemplazarTamanoVirtual() {
        //descomponer distancia en 4
        int tamanoTexto = memoria.size()
                - traerIntdeMemoria(  POSICION_TAMANO_HEADER);//0x200 -> En esta posicion estara la cadena

        reemplazarInt(  POSICION_TAMANO_VIRTUAL, tamanoTexto);
    }

    public void llenarCeros() {
        int aa = traerIntdeMemoria(  POSICION_ALINEAMIENTO_ARCHIVO);
        while (memoria.size() % aa != 0) {//mientras no sea divisible del alineamiento archivo
            cargarByte(0x00); //cargo un cero
        }
    }

    public void iniciarVars(int contador) {
        for (int i = 0; i < contador; i++) {
            cargarByte(0x00); //cargo 8 ceros/4 bytes por cada variable declarada
            cargarByte(0x00);
            cargarByte(0x00);
            cargarByte(0x00);
        }
    }

    public void reemplazarSizeOfCodSection() {
        int tamanoTexto = memoria.size()
                - traerIntdeMemoria(  POSICION_TAMANO_HEADER);//0x200 -> En esta posicion estara la cadena

        reemplazarInt(  POSICION_SECCION_CODIGO, tamanoTexto);
    }

    public void reemplazarSizeOfRawData() {
        int tamanoTexto = memoria.size()
                - traerIntdeMemoria(  POSICION_TAMANO_HEADER);//0x200 -> En esta posicion estara la cadena

        reemplazarInt(  POSICION_DATA_PURA, tamanoTexto);

    }

    public void reemplazarSizeOfImgyBaseOfData() {
        int tamanoCodSecc = traerIntdeMemoria(  POSICION_SECCION_CODIGO);
        int tamanoDataPura = traerIntdeMemoria(  POSICION_DATA_PURA);
        int alinSecc = traerIntdeMemoria(  POSICION_ALINEAMIENTO_SECCION);

        int nuevaVar = (2 + tamanoCodSecc / alinSecc) * alinSecc;
        reemplazarInt(  POSICION_TAMANO_IMAGEN, nuevaVar);

        int nuevaVar2 = (2 + tamanoDataPura / alinSecc) * alinSecc;
        reemplazarInt(  POSICION_BASE_DATOS, nuevaVar2);
    }

    public int traerIntdeMemoria(int p) {
        int resultado = memoria.get(p)
                + memoria.get(p + 1) * 0x100
                + memoria.get(p + 2) * 0x10000
                + memoria.get(p + 3) * (256 * 256 * 256);
        return resultado;
    }

    public void reemplazarInt(int pos, int a) {
        int revInt = revertirInteger(a);
        Byte[] b = intAByteArray(revInt);
        for (int i = 0; i < 4; i++) {
            memoria.set(pos + i, b[i]);
        }
    }

    public static int revertirInteger(int valor) {
        int b1 = (valor >> 0) & 0xff;
        int b2 = (valor >> 8) & 0xff;
        int b3 = (valor >> 16) & 0xff;
        int b4 = (valor >> 24) & 0xff;

        return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
    }

    public static Byte[] intAByteArray(int valor) {

        Byte[] val = new Byte[4];

        Integer v1 = (valor >> 24) & 0xff;
        Integer v2 = (valor >> 16) & 0xff;
        Integer v3 = (valor >> 8) & 0xff;
        Integer v4 = (valor >> 0) & 0xff;

        val[0] = v1.byteValue();
        val[1] = v2.byteValue();
        val[2] = v3.byteValue();
        val[3] = v4.byteValue();

        return val;
    }

}
