public class Booleano {
    enum tipo {TRUE,FALSE,MENOR,MAYOR,MENORIGUAL,MAYORIGUAL,IGUAL,DIFERENTE,VAR}
    tipo t;
    int[] pos = new int[2];
    int[] constantes = new int[2];

    public Booleano(tipo t, int pos1, int pos2, int const1, int const2) {
        this.t = t;
        this.pos[0] = pos1;
        this.pos[1] = pos2;
        this.constantes[0] = const1;
        this.constantes[1] = const2;
    }

    Booleano(tipo t, int p1, int p2){
        this.t=t;
        this.pos[0]=p1;
        this.pos[1]=p2;
    }

    Booleano(tipo t,int p1){
        this.t=t;
        this.pos[0]=p1;
    }

    Booleano(tipo t){
        this.t=t;
    }

    boolean evaluar(int[] valores){
        int x=constantes[0];
        int y=constantes[1];
        if(pos[0]!=1)x=valores[pos[0]];
        if(pos[1]!=1)y=valores[pos[1]];
        switch (this.t){
            case TRUE:
                return true;
            case FALSE:
                return false;
            case IGUAL:
                return x==y;
            case MAYOR:
                return x>y;
            case MENOR:
                return x<y;
            case DIFERENTE:
                return x!=y;
            case MAYORIGUAL:
                return x>=y;
            case MENORIGUAL:
                return x<=y;
            case VAR:
                return valores[pos[0]]==1;
        }
        return true;
    }

}
