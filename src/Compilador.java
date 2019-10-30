import com.sun.org.apache.xpath.internal.operations.Bool;

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
        //ASIGNACION
        else if (linea.length() >= 3 && esUnaVar(linea.charAt(0)) && linea.charAt(1) == '=') {
            String operacion = linea.substring(2);
            Booleano b = volverBooleano(operacion);
            if(b!=null){
                return new Sentencia(Sentencia.Tipo.ASIGBOOL,linea.charAt(0),b);
            }
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

            }
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

    boolean esUnaVarB(char c) {
        return 'e' <= c && c <= 'f';
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

    //da null si no es posible
     Booleano volverBooleano(String s){
        s=s.toLowerCase();
        s=s.trim();
        if(s.length()==1&&esUnaVarB(s.charAt(0))){
            return new Booleano(Booleano.tipo.VAR,key(s.charAt(0)));
        }
        s=s.replaceAll("true","1");
        s=s.replaceAll("false","0");
        if(s.length()==1){
            if(s.charAt(0)=='1')return new Booleano(Booleano.tipo.TRUE);
            if(s.charAt(0)=='0')return new Booleano(Booleano.tipo.FALSE);
        }
        String[] operadores={"==","<=",">=","<",">","!="};
        for (int i = 0; i < operadores.length; i++) {
            if(s.contains(operadores[i])){
                String[] operandos = s.split(operadores[i]);
                if(operandos.length!=2)return null;
                Booleano ret;
                switch (i){
                    case 0:
                        ret= new Booleano(Booleano.tipo.IGUAL);
                        break;
                    case 1:
                        ret= new Booleano(Booleano.tipo.MENORIGUAL);
                        break;
                    case 2:
                        ret= new Booleano(Booleano.tipo.MAYORIGUAL);
                        break;
                    case 3:
                        ret= new Booleano(Booleano.tipo.MENOR);
                        break;
                    case 4:
                        ret= new Booleano(Booleano.tipo.MAYOR);
                        break;
                    case 5:
                        ret= new Booleano(Booleano.tipo.DIFERENTE);
                        break;
                    default:
                        //nunca entra aca pero si no lo pongo llora intellij
                        ret= new Booleano(Booleano.tipo.TRUE);

                }
                for (int j = 0; j < 2; j++) {
                    String op = operandos[j];
                    if(esNumero(op)){
                        ret.pos[j]=-1;
                        ret.constantes[j]=Integer.parseInt(op);
                    }
                    else if(op.length()==1&&esUnaVar(op.charAt(0))){
                        ret.pos[j]=key(op.charAt(0));
                    }
                }



            }
        }
        return null;
    }

    public int key(char c){
        if (c=='z'||c==0)return -1;
        else return c-'a';
    }
}
