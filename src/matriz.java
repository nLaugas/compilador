import java.io.*;
import java.util.*;

public class matriz {
    private Hashtable<Integer, Hashtable<Integer, AcSemantica>> dinamiContainer;

    public matriz(String archivo) throws FileNotFoundException, IOException {
        dinamiContainer = new Hashtable<Integer, Hashtable<Integer, AcSemantica>>();
        int fil = 0;
        int col = 0;
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            dinamiContainer.put(fil,new Hashtable<Integer, AcSemantica>());
            String[] tokens = cadena.split(";");
            for (String t : tokens) {
                AcSemantica in = null;
                switch (t){
                    case "-1" :
                        in = new AcError();
                        break;
                    case "1" :
                        in = new AcNext(1);
                        break;
                    case "2" :
                        in = new AcNext(2);
                        break;
                    case "3" :
                        in = new AcNext(3);
                        break;
                    case "4" :
                        in = new AcNext(4);
                        break;
                    case "f" :
                        in = new AcFinal(0); //Estado final constante = 100
                        break;
                    default:
                        in = new AcNoSignificativo(777);

                }
                dinamiContainer.get(fil).put(col, in);
               // dinamiContainer.get(fil).put(col, t); // En vez de ingresar un String utilizar alguna instancia de la clase AcSemantica
                col++;
            }
            fil++;
            col=0;
        }
        b.close();
        this.imprimir();
    }

    public AcSemantica getAccionSemantica(int fil, int col){
        return dinamiContainer.get(fil).get(col);
    }

    public void imprimir(){

        ArrayList<Integer> e = new ArrayList<Integer>(dinamiContainer.keySet());
        Collections.sort(e);
        for (Integer f : e){
            ArrayList<Integer> e2 = new ArrayList<Integer>(dinamiContainer.get(f).keySet());
            Collections.sort(e2);
            for (Integer c :e2){
                System.out.print(dinamiContainer.get(f).get(c)+"  ");
            }
                System.out.println("");
        }

    }
}
