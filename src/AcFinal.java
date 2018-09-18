public class AcFinal extends AcSemantica {
    public AcFinal(int codigo) {
        super(codigo);
    }

    @Override
    public void ejecutar() {
        System.out.println("llego a estado final"+" codigo: "+this.getCodigo());
    }

}
