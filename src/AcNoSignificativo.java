public class AcNoSignificativo extends AcSemantica {
    public AcNoSignificativo(int codigo) {
        super(codigo);
    }

    @Override
    public void ejecutar() {
        System.out.println("caracter perteneciente al resto de universo"+" codigo: "+this.getCodigo());
    }
}
