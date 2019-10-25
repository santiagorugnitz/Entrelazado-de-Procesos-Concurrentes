import java.util.ArrayList;
import java.util.Arrays;

public class Tabla {
    class Estado{
        int[] variables;

        Estado(){
            this.variables=new int[6];
        }
        Estado(int[] a){
            this.variables=a;
        }

        @Override
        public boolean equals(Object o) {
            Estado e = (Estado) o;
            for (int i = 0; i < e.variables.length; i++) {
                if(e.variables[i]!=this.variables[i])return false;
            }
            return true;
        }
    }

    Programa p1;
    Programa p2;
    ArrayList<Estado>[][] estados;
    boolean[] vUsadas= new boolean[6];
    int actual1=0;
    int actual2=0;

    public Tabla(Programa p1, Programa p2,int valoresIni[]) {
        estados= new ArrayList[p1.largo][p2.largo];
        for (int i = 0; i < p1.largo; i++) {
            for (int j = 0; j < p2.largo; j++) {
                estados[i][j]=new ArrayList<>();
            }
        }
        for (int i = 0; i <vUsadas.length ; i++) {
            vUsadas[i]=p1.variables[i]||p2.variables[i];
        }
        estados[0][0].add(new Estado(valoresIni));
    }

    void ampliarCelda(int i,int j){
        ArrayList<Estado> c = estados[i][j];
        for (int k = 0; k < c.size(); k++) {
            ejecutarAbajo(i,j);
            ejecutarDerecha(i,j);
        }
    }
    void ejecutarAbajo(int i,int j){
        
    }

    void ejecutarDerecha(int i,int j){

    }
}
