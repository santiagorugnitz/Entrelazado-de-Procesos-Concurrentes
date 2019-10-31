public class Stack<T> {
    class nodoLista{
        T dato;
        nodoLista sig;
        int pos;

        public nodoLista(T d,nodoLista s,int p){
            this.dato=d;
            this.sig=s;
            this.pos=p;
        }
    }
    private nodoLista primero;
    private int cant;
    public Stack(){
        this.cant=0;
        this.primero=null;
    }

    public T getTop(){
        return primero.dato;
    }

    public int getPos(){
        return primero.pos;
    }
    public void sacar(){
        if(cant>0){
            primero=primero.sig;
            cant--;
        }
    }
    public void agregar(T dato,int p){
            primero=new nodoLista(dato,primero,p);
            cant++;
    }
    public int largo(){
        return cant;
    }
}
