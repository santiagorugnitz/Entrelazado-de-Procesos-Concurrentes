import java.util.ArrayList;

public class Programa {
    int largo;
    ArrayList<Sentencia> sentencias;
    Variable[] variables;
    

    public void agregarSentencia(Sentencia s) {
        sentencias.add(s);
        largo++;
    }

}
