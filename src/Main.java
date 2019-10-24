import java.io.File;
import java.io.FilenameFilter;

public class Main {

    public static void main(String[] args) {
        File f1 = new File("C:\\Users\\Santiago\\Google Drive\\Java\\Entrelazado-de-Procesos-Concurrentes\\p1.txt");
        File f2 = new File("C:\\Users\\Santiago\\Google Drive\\Java\\Entrelazado-de-Procesos-Concurrentes\\p2.txt");
        //En PC usar el de abajo, en laptop el de arriba
        //File f1 = new File("D:\\Admin\\Google Drive\\Java\\Entrelazado-de-Procesos-Concurrentes\\p1.txt");
        //File f2 = new File("D:\\Admin\\Google Drive\\Java\\Entrelazado-de-Procesos-Concurrentes\\p2.txt");
        Compilador c1 = new Compilador(f1);
        Compilador c2 = new Compilador(f2);
        if(c1.compilar()&&c2.compilar()){
            Tabla t= new Tabla(c1.getPrograma(),c2.getPrograma());
            //t.mostrar????
        }
    }
    }
