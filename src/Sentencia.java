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
    char  v1;
    char v2;
    char operador;
    char v3;
    int valor2;
    int valor3;

    public Sentencia(Tipo t, char vPrincipal, char vSecundaria, char operador, char vTerciaria) {
        this.t = t;
        this.v1 = vPrincipal;
        this.v2 = vSecundaria;
        this.operador = operador;
        this.v3= vTerciaria;
    }
    public Sentencia(Tipo t){
        this.t=t;
    }
    public Sentencia(Tipo t,char v1,char v2){
        this.t=t;
        this.v1=v1;
        this.v2=v2;
    }


}
