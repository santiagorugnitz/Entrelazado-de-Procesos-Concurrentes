import java.util.ArrayList;

public class Tabla {
    class Estado{
        int[] variables;
        Estado anterior;

    }

    Programa p1;
    Programa p2;
    Estado[][] estados;
    boolean[] vUsadas= new boolean[6];
    int actual1=0;
    int actual2=0;


    public Tabla(Programa p1, Programa p2,Programa main) {
        estados= new Estado[p1.largo][p2.largo];
        for (int i = 0; i <vUsadas.length ; i++) {
            vUsadas[i]=p1.variables[i]||p2.variables[i];
        }
        
    }
}
