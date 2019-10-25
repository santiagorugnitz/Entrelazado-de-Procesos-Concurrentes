import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File f1 = new File("p1.txt");
        File f2 = new File("p2.txt");
        File main= new File("main.txt");
        System.out.println(f1.getAbsolutePath());
        Compilador c1 = new Compilador(f1,"Programa 1");
        Compilador c2 = new Compilador(f2,"Programa 2");
        Compilador cMain = new Compilador(main,"Main");
        if(c1.compilar()&&c2.compilar()&&cMain.compilar()){
            Tabla t= new Tabla(c1.getPrograma(),c2.getPrograma());
            //t.mostrar????
        }
    }
    }
