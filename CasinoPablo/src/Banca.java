public class Banca {

    private int dineroBanca;

    public synchronized int getDineroBanca() {
        return dineroBanca;
    }

    public synchronized void setDineroBanca(int dineroBanca) {
        this.dineroBanca = dineroBanca;
    }

    public Banca(int dineroBanca) {
        this.dineroBanca = dineroBanca;

    }
}
