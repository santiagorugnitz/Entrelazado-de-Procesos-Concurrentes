import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Compilador {
    private String nombre;
    private File archivo;
    private Programa prog;
    private boolean hayErrores;


    public Compilador(File f, String n) {
        this.nombre = n;
        this.archivo = f;
        this.prog = new Programa();
        this.hayErrores = false;
    }

    public Programa getPrograma() {
        return prog;
    }


    public boolean compilar() throws FileNotFoundException {
        ArrayList<String> listaLineas = new ArrayList<String>();
        Scanner in = new Scanner(archivo);
        int pos = 1;
        while(in.hasNext()){
            listaLineas.add(in.nextLine());
        }

        for (int i = 0; i < listaLineas.size(); i++) {
            String l = listaLineas.get(i);
            l = l.trim();
            l = l.toLowerCase();
            Sentencia s = null;
            if (l.startsWith("if")){
                int cantif = 1;
                int j =0;
                for (j = i; j < listaLineas.size() && cantif !=0; j++) {
                    if (l.startsWith("if")) cantif++;
                    if (l.startsWith("endif")) cantif --;
                }
                s = procesarLinea(listaLineas.get(i));
                //En la posici[on, se guarda la sentencia a la que se tiene que ir si no se cumple
                s.valores[2] = j;

            }else if (l.startsWith("while")){
                int cantwhile = 1;
                int j = 0;
                for (j = i; j < listaLineas.size() && cantwhile !=0; j++) {
                    if (l.startsWith("while")) cantwhile++;
                    if (l.startsWith("endwhile")) cantwhile --;
                }
                s = procesarLinea(listaLineas.get(i));
                //En la posici[on, se guarda la sentencia a la que se tiene que ir si no se cumple
                s.valores[2] = j;
            }else if (l.startsWith("endif")){

            }else if (l.startsWith("endwhile")){

            }else{
                s = procesarLinea(l);
            }

            if (s.t == Sentencia.Tipo.ERROR) {
                System.out.println("Error en " + nombre + " en linea " + pos);
                return false;
            } else prog.agregarSentencia(s);

        }

        return true;
    }

    private Sentencia procesarLinea(String linea) {
        Sentencia ret = null;
        if (linea.startsWith("endif")) {
            //Se procesa en el endif
        } else if (linea.startsWith("endwhile")) {
            //Se procesa en el endwhile
        } else if (linea.startsWith("if")) {

        } else if (linea.startsWith("while")){

        }
        //TODO asignaciones con boolean seguramente sea un casteo 0-False 1-True
        //ASIGNACION
        else if (linea.length() >= 3 && esUnaVar(linea.charAt(0)) && linea.charAt(1) == '=') {
            String operacion = linea.substring(2);
            char op = 'x';
            for (int i = 0; i < operacion.length(); i++) {
                if (esOperador(operacion.charAt(i))) op = operacion.charAt(i);
            }
            //es un ASIGVAR
            if (op == 'x') {
                if (operacion.length() == 1 && esUnaVar(operacion.charAt(0))) {
                    ret = new Sentencia(Sentencia.Tipo.ASIGVAR, linea.charAt(0), operacion.charAt(0));
                } else if (esNumero(operacion)) {
                    ret = new Sentencia(Sentencia.Tipo.ASIGVAR, linea.charAt(0), 'z');
                    ret.valores[2] = Integer.parseInt(operacion);
                } else {
                    ret = new Sentencia(Sentencia.Tipo.ERROR);
                }
            }
            //es un ASIGOP
            else {
                String sep = ""+op;
                String[] operadores = operacion.split(Pattern.quote(sep));
                if (operadores.length == 2 &&
                        (esNumero(operadores[0]) || (operadores[0].length() == 1 && esUnaVar(operadores[0].charAt(0)))) &&
                        (esNumero(operadores[1]) || (operadores[1].length() == 1 && esUnaVar(operadores[1].charAt(0))))) {
                    char o1;
                    char o2;
                    int v1 = 0;
                    int v2 = 0;
                    if (esNumero(operadores[0])) {
                        o1 = 'z';
                        v1 = Integer.parseInt(operadores[0]);
                    } else o1 = operadores[0].charAt(0);
                    if (esNumero(operadores[1])) {
                        o2 = 'z';
                        v2 = Integer.parseInt(operadores[1]);
                    } else o2 = operadores[1].charAt(0);
                    ret = new Sentencia(Sentencia.Tipo.ASIGOP, linea.charAt(0), o1, op, o2);
                    ret.valores[2] = v1;
                    ret.valores[3] = v2;

                } else ret = new Sentencia(Sentencia.Tipo.ERROR);
            }} else if (linea.startsWith("co")) {
            ret = new Sentencia(Sentencia.Tipo.NADA);
        }
        //ERROR
        else {
            ret = new Sentencia(Sentencia.Tipo.ERROR);
        }
        return ret;
    }

    boolean esUnaVar(char c) {
        return 'a' <= c && c <= 'f';
    }

    boolean esNumero(char c) {
        return '0' <= c && c <= '9';
    }

    boolean esNumero(String s) {
        try {
            int n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
