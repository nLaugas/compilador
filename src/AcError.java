public class AcError extends AcSemantica {

    public AcError(){
        super(-1);
    }
    public void ejecutar(){
        System.out.println("hay error, en linea.. tal"+" codigo: "+this.getCodigo());
    }
}
