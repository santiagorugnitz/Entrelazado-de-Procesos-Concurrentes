import java.util.ArrayList;

public class Programa {
    int largo;
    ArrayList<Sentencia> sentencias;
    boolean[] variables;

    public Programa() {
        this.largo = 0;
        sentencias=new ArrayList<>();
        variables=new boolean[6];
    }

    public void agregarSentencia(Sentencia s) {
        sentencias.add(s);
        largo++;
        for (int i = 0; i < s.variables.length; i++) {
            int k=key(s.variables[i]);
            if(k!=-1)variables[k]=true;
        }
    }

    //vuelve el char la pos en el array
    public int key(char c){
        if (c=='z'||c==0)return -1;
        else return c-'a';
    }

}
