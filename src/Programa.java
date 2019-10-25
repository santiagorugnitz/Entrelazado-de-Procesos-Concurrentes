import java.util.ArrayList;

public class Programa {
    private int largo;
    private ArrayList<Sentencia> sentencias;
    private ArrayList<Variable> variables;

    public void agregarSentencia(Sentencia s) {
        sentencias.add(s);
        largo++;
        agregarVariables(s);
    }

    private void agregarVariables(Sentencia s) {
        //TODO un if si la variable no esta en la lista la agrega
    }
}
