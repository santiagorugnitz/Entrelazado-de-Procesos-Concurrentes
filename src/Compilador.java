import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Compilador {
    private String nombre;
    private File archivo;
    private Programa prog;
    private boolean hayErrores;


    public Compilador(File f, String n) {
        this.nombre = n;
        this.archivo = f;
        this.prog = null;
        this.hayErrores = false;
    }

    public Programa getPrograma() {
        return prog;
    }


    public boolean compilar() throws FileNotFoundException {
        Scanner in = new Scanner(archivo);
        int pos = 1;
        while (in.hasNext()) {
            Sentencia s = procesarLinea(in.nextLine());
            if (s.t == Sentencia.Tipo.ERROR) {
                System.out.println("Error en " + nombre + " en linea " + pos);
                return false;
            } else prog.agregarSentencia(s);

        }

        return true;
    }

    private Sentencia procesarLinea(String linea) {
        linea = linea.trim();
        linea = linea.toLowerCase();
        Sentencia ret = null;
        if (linea.startsWith("if")) {

        } else if (linea.startsWith("while")) {

        } else if (linea.startsWith("endif")) {

        } else if (linea.startsWith("endwhile")) {

        }
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
                    ret.valor2 = Integer.parseInt(operacion);
                } else {
                    ret = new Sentencia(Sentencia.Tipo.ERROR);
                }
            }
            //es un ASIGOP
            else {
                String[] operadores = operacion.split(String.valueOf(op));
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
                        v2 = Integer.parseInt(operadores[0]);
                    } else o2 = operadores[1].charAt(0);
                    ret = new Sentencia(Sentencia.Tipo.ASIGOP, linea.charAt(0), o1, op, o2);
                    ret.valor2 = v1;
                    ret.valor3 = v2;

                } else ret = new Sentencia(Sentencia.Tipo.ERROR);
            }
            if (linea.length() == 5 && esOperador(linea.charAt(3)) && esUnaVar(linea.charAt(2)) && esUnaVar(linea.charAt(4))) {
                ret = new Sentencia(Sentencia.Tipo.ASIGOP, linea.charAt(0), linea.charAt(2), linea.charAt(3), linea.charAt(4));
            } else if (linea.length() == 3 && esUnaVar(linea.charAt(2))) {
                ret = new Sentencia(Sentencia.Tipo.ASIGVAR, linea.charAt(0), linea.charAt(2));
            } else ret = new Sentencia(Sentencia.Tipo.ERROR);
        } else if (linea.startsWith("co")) {
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
