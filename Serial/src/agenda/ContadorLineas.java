package agenda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContadorLineas {

    private long lNumeroLineas;
    private String sCadena;
    private int conteo;
    
    public void contarLineas() {
            lNumeroLineas = 0;
        try {

            FileReader fr = new FileReader("Directorio.dat");
            BufferedReader bf = new BufferedReader(fr);

            while ((sCadena = bf.readLine()) != null) {
                lNumeroLineas++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println(lNumeroLineas);
        conteo=(int) lNumeroLineas;
    }

    public int getConteo() {
        return conteo;
    }

}
