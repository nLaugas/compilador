public class AcNext extends AcSemantica {
    public AcNext(int codigo) {
        super(codigo);
    }

    @Override
    public void ejecutar() {
        System.out.println("abance en el buffer " +" codigo: "+this.getCodigo());
    }
}
