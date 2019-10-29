import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Tabla {


    class Estado{
        int[] variables;

        Estado(){
            this.variables=new int[6];
        }
        Estado(int[] a){
            this.variables=new int[a.length];
            for (int i = 0; i < a.length; i++) {
                this.variables[i]=a[i];
            }
        }

        @Override
        public boolean equals(Object o) {
            Estado e = (Estado) o;
            for (int i = 0; i < e.variables.length; i++) {
                if(e.variables[i]!=this.variables[i])return false;
            }
            return true;
        }

        public void mostrar() {
            if(vUsadas[0])System.out.println("a = "+variables[0]);
            if(vUsadas[1])System.out.println("b = "+variables[1]);
            if(vUsadas[2])System.out.println("c = "+variables[2]);
            if(vUsadas[3])System.out.println("d = "+variables[3]);
            if(vUsadas[4])System.out.println("e = "+variables[4]);
            if(vUsadas[5])System.out.println("f = "+variables[5]);

        }
    }

    Programa p1;
    Programa p2;
    ArrayList<Estado>[][] estados;
    boolean[] vUsadas= new boolean[6];

    public Tabla(Programa p1, Programa p2,int valoresIni[]) {
        this.p1=p1;
        this.p2=p2;
        estados= new ArrayList[p2.largo+1][p1.largo+1];
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < estados[0].length; j++) {
                estados[i][j]=new ArrayList<>();
            }
        }
        for (int i = 0; i <vUsadas.length ; i++) {
            vUsadas[i]=p1.variables[i]||p2.variables[i];
        }
        estados[0][0].add(new Estado(valoresIni));
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < estados[0].length; j++) {
                ampliarCelda(i,j);
            }
        }
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < estados[0].length; j++) {
                ampliarCelda(i,j);
            }
        }

    }

    void ampliarCelda(int i,int j){
        ArrayList<Estado> c = estados[i][j];
        for (int k = 0; k < c.size(); k++) {
            if(j<estados[0].length-1)ejecutar(i,j,c.get(k),true);
            if(i<estados.length-1)ejecutar(i,j,c.get(k),false);
        }
    }
    void ejecutar(int i,int j,Estado e,boolean derecha){
        //Ejecuto p1 j
        if(derecha){
            int pos=j+1;
            Estado nuevo = new Estado(e.variables);
            Sentencia s = p1.sentencias.get(j);
            switch (s.t){
                case ASIGOP:
                case ASIGVAR:
                    nuevo=calcular(s,e);
                    break;
                case IF:
                case WHILE:
                case ENDWHILE:
                    pos=ir(s);
                    break;

            }
            if(!estados[i][pos].contains(nuevo))
                estados[i][pos].add(nuevo);
        }
        //Ejecuto p2 i
        else{

            int pos=i+1;
            Estado nuevo = new Estado(e.variables);
            Sentencia s = p2.sentencias.get(i);
            switch (s.t){
                case ASIGOP:
                case ASIGVAR:
                    nuevo=calcular(s,e);
                    break;
                case IF:
                case WHILE:
                case ENDWHILE:
                    pos=ir(s);
                    break;

            }
            if(!estados[pos][j].contains(nuevo))
                estados[pos][j].add(nuevo);
        }
    }

    Estado calcular(Sentencia s, Estado anterior){
        Estado ret = new Estado(anterior.variables);
        int pos =key(s.variables[1]);
        int x=key(s.variables[2]);
        int y=key(s.variables[3]);
        int op1=s.valores[2];
        int op2=s.valores[3];
        if(x!=-1)op1=ret.variables[x];
        if(y!=-1)op2=ret.variables[y];
        if(s.t== Sentencia.Tipo.ASIGVAR){
            ret.variables[pos]=op1;
        }
        else{
            switch (s.operador){
                case '+':
                    ret.variables[pos]=op1+op2;
                    break;

                case '-':
                    ret.variables[pos]=op1-op2;
                    break;

                case '*':
                    ret.variables[pos]=op1*op2;
                    break;

                case '/':
                    ret.variables[pos]=op1/op2;
                    break;
            }
        }
        return ret;
    }

    int ir(Sentencia s){
        return 0;
    }

    public int key(char c){
        if (c=='z'||c==0)return -1;
        else return c-'a';
    }

    public void mostrar() {
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < estados[0].length; j++) {
                System.out.println("Celda:"+i+" "+j);
                for (int k = 0; k < estados[i][j].size(); k++) {
                    estados[i][j].get(k).mostrar();
                    System.out.println("---------");
                }
            }
        }
    }
}
