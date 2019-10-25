public class Sentencia {
    enum Tipo {
        IF,
        ERROR,
        NADA, // estilo p_p_a
        ASIGOP,
        ASIGVAR,
        WHILE,
        ENDWHILE,
        ENDIF,

    };
    Tipo t;
    char[] variables= new char[4];
    int[] valores= new int[4];
    char operador;

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


}
