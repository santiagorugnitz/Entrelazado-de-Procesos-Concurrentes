import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File f1 = new File("p1.txt");
        File f2 = new File("p2.txt");
        File main= new File("main.txt");
        System.out.println(f1.getAbsolutePath());
        Compilador c1 = new Compilador(f1,"Programa 1");
        Compilador c2 = new Compilador(f2,"Programa 2");
        int[] valoresIniciales = new int[6];
        ArrayList<String> operacionesConocidas = new ArrayList<String>();
        Scanner in = new Scanner(main);
        int i = 0;
        while(in.hasNext() && i < 4){
            String s = in.nextLine();
            s = s.trim();
            valoresIniciales[i] = Integer.parseInt(s.charAt(2)+"");
            i++;
        }

        // 1 es True y 0 es False
        while(in.hasNext() && i < 6){
            String s = in.nextLine();
            s = s.trim();
            if (s.charAt(2) == 't'){
                valoresIniciales[i] = 1;
            }else{
                valoresIniciales[i] = 0;
            }
            i++;
        }

        while(in.hasNext()){
            String[] s = in.nextLine().split(" ");
            operacionesConocidas.add(s[1]);
        }

        if(c1.compilar()&&c2.compilar()){
            Tabla t= new Tabla(c1.getPrograma(),c2.getPrograma(),valoresIniciales);
            t.mostrar();
        }
    }
    }
