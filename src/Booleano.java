public class Booleano {
    enum tipo {TRUE,FALSE,MENOR,MAYOR,MENORIGUAL,MAYORIGUAL,IGUAL,DIFERENTE}
    tipo t;
    int pos1;
    int pos2;

    Booleano(tipo t,char p1, char p2){
        this.t=t;
        this.pos1=p1;
        this.pos2=p2;
    }

    Booleano(tipo t){
        this.t=t;
    }

    boolean evaluar(int[] valores){
        switch (this.t){
            case TRUE:
                return true;
            case FALSE:
                return false;
            case IGUAL:
                return valores[pos1]==valores[pos2];
            case MAYOR:
                return valores[pos1]>valores[pos2];
            case MENOR:
                return valores[pos1]<valores[pos2];
            case DIFERENTE:
                return valores[pos1]!=valores[pos2];
            case MAYORIGUAL:
                return valores[pos1]>=valores[pos2];
            case MENORIGUAL:
                return valores[pos1]<=valores[pos2];
        }
        return true;
    }

    static boolean esBooleano(String s){
        s=s.toLowerCase();
        s=s.trim();
        s=s.replaceAll("true","1");
        s=s.replaceAll("false","0");
        String[] operadores={"==","<=",">=","<",">","!="};
        for (int i = 0; i < operadores.length; i++) {

        }

    }
}
