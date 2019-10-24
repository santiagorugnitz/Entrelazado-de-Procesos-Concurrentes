import java.io.File;

public class Compilador {
    private File archivo;
    private Programa prog;


    public Compilador(File f) {
        this.archivo=f;
        this.prog=null;
    }

    public boolean compilar(){
        //TODO
        return true;
    }

    public Programa getPrograma() {
        return prog;
    }
}
