import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
        Scanner input = new Scanner(new File("Entrada"));
        while((input.hasNextLine())) {
            String line = input.nextLine();
            for (int n = 0; n < line.length(); n++) {
                char c = line.charAt(n);
                estadoFinal = convert(c);
                m.getAccionSemantica(estadoIncial, estadoFinal).ejecutar();
                estadoIncial = m.getAccionSemantica(estadoIncial, estadoFinal).getCodigo();
            }
        }
        input.close();
    }
}
