import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static int fil = 0;
    public static int col = 0;
    public static int estadoIncial = 0;
    public static int estadoFinal = 0;
    public static String buffer;


    public static int convert(Character c){

        int asciiChar = (int )c;

        switch (asciiChar){
            case 95 :
                // _
                return 0;
            case 97 :
                //a
                return 1;
            case 49 :
                //1
                return 2;
            case 105 :
                //i
                return 3;
            default:
                return 4;  //resto del universo
        }
    }

    public static void main(String Args[]) throws IOException {

        matriz m = new matriz("dat");

        String cadena;
        FileReader f = new FileReader("Entrada");
        BufferedReader b = new BufferedReader(f);

        int caract = f.read();

        while(caract != -1) {

            estadoFinal=convert((char)caract);
            System.out.println("Fila : "+estadoIncial+"Columna : "+estadoFinal);
            m.getAccionSemantica(estadoIncial,estadoFinal).ejecutar();
            //System.out.print(convert((char)caract));
            estadoIncial=m.getAccionSemantica(estadoIncial,estadoFinal).getCodigo();
            caract = f.read();

        }
        b.close();
    }
}
