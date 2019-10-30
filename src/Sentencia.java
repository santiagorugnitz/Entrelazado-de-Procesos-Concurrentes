public class Sentencia {
    enum Tipo {
        ERROR,
        NADA, // estilo p_p_a
        ASIGOP,
        ASIGVAR,
        ASIGBOOL,
        GOTO,
    };
    Tipo t;
    char[] variables= new char[4];
    int[] valores= new int[4];
    char operador;
    Booleano b;

    public Sentencia(Tipo t, char vPrincipal, char vSecundaria, char operador, char vTerciaria) {
        this.t = t;
        this.variables[1] = vPrincipal;
        this.variables[2] = vSecundaria;
        this.operador = operador;
        this.variables[3]= vTerciaria;
    }
    public Sentencia(Tipo t){
        this.t=t;
    }
    public Sentencia(Tipo t,char v1,char v2){
        this.t=t;
        this.variables[1]=v1;
        this.variables[2]=v2;
    }

    public Sentencia(Tipo t, char v, Booleano b) {
        this.t = t;
        this.variables[1] = v;
        this.b = b;
    }

    public Sentencia(Tipo t, Booleano b, int pos) {
        this.t = t;
        this.valores[1] = pos;
        this.b = b;
    }
}
