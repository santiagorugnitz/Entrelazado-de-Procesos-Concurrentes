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
        int[] valoresIniciales = new int[6];
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
        i=0;
        String[] alias=new String[6];
        while(in.hasNext()){
            String s=in.nextLine();
            if(s.contains("=")){
            String[] linea= s.split("=");
            alias[i]=linea[1];
            i++;
        }}

        Compilador c1 = new Compilador(f1,"Programa 1",alias);
        Compilador c2 = new Compilador(f2,"Programa 2",alias);
        if(c1.compilar()&&c2.compilar()){
            System.out.println("While de afuera? [Y]es");
            Scanner s = new Scanner(System.in);
            Tabla t= new Tabla(c1.getPrograma(),c2.getPrograma(),valoresIniciales,s.nextLine().toUpperCase().equals("Y"));
            t.mostrarReducido();
        }
    }
    }
