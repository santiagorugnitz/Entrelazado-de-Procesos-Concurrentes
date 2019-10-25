import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Compilador {
    private String nombre;
    private File archivo;
    private Programa prog;
    private boolean hayErrores;


    public Compilador(File f,String n) {
        this.nombre=n;
        this.archivo = f;
        this.prog = null;
        this.hayErrores=false;
    }

    public Programa getPrograma() {
        return prog;
    }


    public boolean compilar() throws FileNotFoundException {
        Scanner in = new Scanner(archivo);
        int pos=1;
        while(in.hasNext()){
            Sentencia s = procesarLinea(in.nextLine());
            if(s==null) {
                System.out.println("Error en " + nombre + " en linea " + pos);
                return false;
            }   
            else prog.agregarSentencia(s);
            
        }
        
        return true;
    }

    private Sentencia procesarLinea(String nextLine) {
        System.out.println("xd");
        return null;
    }
}
