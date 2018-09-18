public abstract class AcSemantica {
    protected int codigo;

    public AcSemantica(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String toString(){
        return Integer.toString(codigo);
    }

    public abstract void ejecutar();
}
