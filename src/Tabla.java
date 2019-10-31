import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Tabla {


    class Estado {
        int[] variables;

        Estado() {
            this.variables = new int[6];
        }

        Estado(int[] a) {
            this.variables = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                this.variables[i] = a[i];
            }
        }

        @Override
        public boolean equals(Object o) {
            Estado e = (Estado) o;
            for (int i = 0; i < e.variables.length; i++) {
                if (e.variables[i] != this.variables[i]) return false;
            }
            return true;
        }

        public void mostrar() {
            if (vUsadas[0]) System.out.println("a = " + variables[0]);
            if (vUsadas[1]) System.out.println("b = " + variables[1]);
            if (vUsadas[2]) System.out.println("c = " + variables[2]);
            if (vUsadas[3]) System.out.println("d = " + variables[3]);
            if (vUsadas[4]) {
                if (variables[4] == 1) System.out.println("e = true");
                else System.out.println("e = false");
            }
            if (vUsadas[5]) {
                if (variables[5] == 1) System.out.println("f = true");
                else System.out.println("f = false");
            }

        }

        public void mostrarReducido() {
            for (int i = 0; i < 4; i++) {
                if (vUsadas[i]) System.out.print(variables[i]);
            }

            for (int i = 4; i < 6; i++) {
                if (vUsadas[i]) {
                    if (variables[i] == 1) System.out.print("T");
                    else System.out.print("F");
                }
            }
            System.out.println();
        }
    }

    Programa p1;
    Programa p2;
    boolean[] ignorarp1;
    boolean[] ignorarp2;
    ArrayList<Estado>[][] estados;
    boolean[] vUsadas = new boolean[6];
    boolean whileAfuera;

    public Tabla(Programa p1, Programa p2, int valoresIni[],boolean whi) {
        this.p1 = p1;
        this.p2 = p2;
        estados = new ArrayList[p2.largo + 1][p1.largo + 1];
        ignorarp1 = new boolean[p1.largo + 1];
        ignorarp2 = new boolean[p2.largo + 1];
        whileAfuera=whi;
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < estados[0].length; j++) {
                estados[i][j] = new ArrayList<>();
                if (j < p1.sentencias.size() && esUnEnd(p1.sentencias.get(j))) ignorarp1[j] = true;
            }
            if (i < p2.sentencias.size() && esUnEnd(p2.sentencias.get(i))) ignorarp2[i] = true;
        }
        for (int i = 0; i < vUsadas.length; i++) {
            vUsadas[i] = p1.variables[i] || p2.variables[i];
        }
        estados[0][0].add(new Estado(valoresIni));
        ampliarCelda(0,0);
        if(whileAfuera){
            for (int i = 0; i < estados.length; i++) {
                ArrayList<Estado> desde=estados[i][estados[0].length-1];
                ArrayList<Estado> hasta=estados[i][0];
                if(agregarTodos(desde,hasta))ampliarCelda(i,0);
            }
            for (int j = 0; j < estados[0].length; j++) {
                ArrayList<Estado> desde=estados[estados.length-1][j];
                ArrayList<Estado> hasta=estados[0][j];
                if(agregarTodos(desde,hasta))ampliarCelda(0,j);
            }
        }
    }
    boolean agregarTodos(ArrayList<Estado> desde,ArrayList<Estado> hasta){
        boolean ret=false;
        for (int i = 0; i < desde.size(); i++) {
            if(!hasta.contains(desde.get(i))){
                ret=true;
                hasta.add(desde.get(i));
            }
        }
        return ret;
    }
    boolean esUnEnd(Sentencia s) {
        return (s.t == Sentencia.Tipo.ENDWHILE) || (s.t == Sentencia.Tipo.ENDIF);
    }

    void ampliarCelda(int i, int j) {
        ArrayList<Estado> c = estados[i][j];
        for (int k = 0; k < c.size(); k++) {
            if (j < estados[0].length - 1) ejecutar(i, j, c.get(k), true);
            if (i < estados.length - 1) ejecutar(i, j, c.get(k), false);
        }
    }

    void ejecutar(int i, int j, Estado e, boolean derecha) {
        //Ejecuto p1 j
        if (derecha) {
            int pos = j + 1;
            Estado nuevo = new Estado(e.variables);
            Sentencia s = p1.sentencias.get(j);
            switch (s.t) {
                case ASIGOP:
                case ASIGVAR:
                case ASIGBOOL:
                    nuevo = calcular(s, e);
                    break;
                case IF:
                case WHILE:
                case ENDWHILE:
                    pos = ir(s, pos, nuevo.variables);
                    break;
                default:
                    break;
            }
            if (!estados[i][pos].contains(nuevo)) {
                estados[i][pos].add(nuevo);
                ampliarCelda(i, pos);
            }
        }
        //Ejecuto p2 i
        else {

            int pos = i + 1;
            Estado nuevo = new Estado(e.variables);
            Sentencia s = p2.sentencias.get(i);
            switch (s.t) {
                case ASIGOP:
                case ASIGVAR:
                case ASIGBOOL:
                    nuevo = calcular(s, e);
                    break;
                case IF:
                case WHILE:
                case ENDWHILE:
                    pos = ir(s, pos, nuevo.variables);
                    break;
                default:
                    break;

            }
            if (!estados[pos][j].contains(nuevo)) {
                estados[pos][j].add(nuevo);
                ampliarCelda(pos, j);
            }
        }
    }

    Estado calcular(Sentencia s, Estado anterior) {
        Estado ret = new Estado(anterior.variables);
        int pos = key(s.variables[1]);
        int x = key(s.variables[2]);
        int y = key(s.variables[3]);
        int op1 = s.valores[2];
        int op2 = s.valores[3];
        if (x != -1) op1 = ret.variables[x];
        if (y != -1) op2 = ret.variables[y];
        if (s.t == Sentencia.Tipo.ASIGVAR) {
            ret.variables[pos] = op1;
        } else if (s.t == Sentencia.Tipo.ASIGBOOL) {
            if (s.b.evaluar(anterior.variables)) ret.variables[pos] = 1;
            else ret.variables[pos] = 0;
        } else {
            switch (s.operador) {
                case '+':
                    ret.variables[pos] = op1 + op2;
                    break;

                case '-':
                    ret.variables[pos] = op1 - op2;
                    break;

                case '*':
                    ret.variables[pos] = op1 * op2;
                    break;

                case '/':
                    ret.variables[pos] = op1 / op2;
                    break;
            }
        }
        if (pos > 3 && ret.variables[pos] != 0) ret.variables[pos] = 1;
        return ret;
    }

    int ir(Sentencia s, int anterior, int[] valores) {
        switch (s.t) {
            case IF:
            case WHILE:
                if (s.b.evaluar(valores)) return anterior;
                return s.valores[0];
            case ENDWHILE:
                return s.valores[0];
            default:
                return anterior;
        }
    }

    public int key(char c) {
        if (c == 'z' || c == 0) return -1;
        else return c - 'a';
    }

    public void mostrar() {
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < estados[0].length; j++) {
                System.out.println("Celda:" + i + " " + j);
                for (int k = 0; k < estados[i][j].size(); k++) {
                    estados[i][j].get(k).mostrarReducido();
                    //System.out.println("---------");
                }
            }
        }
    }

    public void mostrarReducido() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < estados.length; i++) {
            if (!ignorarp2[i]) {
                for (int j = 0; j < estados[0].length; j++) {
                    if (!ignorarp1[j]) {

                        if (estados[i][j].size() > 0) System.out.println("Celda: " + x + " " + y);
                        for (int k = 0; k < estados[i][j].size(); k++) {
                            estados[i][j].get(k).mostrarReducido();
                        }
                        y++;
                    }
                }
                x++;
                y = 0;
                System.out.println("---------------------------------------------------------------------------------");
            }
        }
    }

}
