public class Vaga {
    private String codigoDaVaga;

    public Vaga(String codigoDaVaga) {
        this.codigoDaVaga = codigoDaVaga;
    }

    public String getCodigoDaVaga() {
        return codigoDaVaga;
    }

    public void setCodigoDaVaga(String codigoDaVaga) {
        this.codigoDaVaga = codigoDaVaga;
    }

    //preço sem desconto
    public double aplicarDescontoOuAcrescimo(double precoBase) {
        return precoBase;
    }


    public static class VagaVip extends Vaga {

        public VagaVip(String codigoDaVaga) {
            super(codigoDaVaga);
        }

        @Override
        public double aplicarDescontoOuAcrescimo(double precoBase) {
            return precoBase * 1.20; // Acréscimo de 20% para vagas VIP
        }
    }

    // subclasse VagaIdoso
    public static class VagaIdoso extends Vaga {

        public VagaIdoso(String codigoDaVaga) {
            super(codigoDaVaga);
        }

        @Override
        public double aplicarDescontoOuAcrescimo(double precoBase) {
            return precoBase * 0.85; // Desconto de 15% para vagas Idoso
        }
    }

    // subclasse VagaPCD
    public static class VagaPCD extends Vaga {

        public VagaPCD(String codigoDaVaga) {
            super(codigoDaVaga);
        }

        @Override
        public double aplicarDescontoOuAcrescimo(double precoBase) {
            return precoBase * 0.87; // Desconto de 13% para vagas PCD
        }
    }
}
