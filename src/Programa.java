import java.util.ArrayList;

public class Programa {
    int largo;
    ArrayList<Sentencia> sentencias;
    boolean[] variables;


    public void agregarSentencia(Sentencia s) {
        sentencias.add(s);
        largo++;

    }

    //vuelve el char la pos en el array
    public int key(char c){
        if (c=='z'||c==0)return -1;
        else return c-'a';
    }

}
