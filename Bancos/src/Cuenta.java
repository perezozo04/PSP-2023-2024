public class Cuenta {
    private int saldo;
    public Cuenta(int cantidad) {
        this.saldo = cantidad;
    }

    public synchronized int getSaldo() {
        return saldo;
    }

    public synchronized void ingresar(int cantidad) {
        this.saldo += cantidad;
    }

    public synchronized void reintegro(int cantidad) {
        if (saldo >= cantidad) {
            this.saldo -= cantidad;
        }
    }
}
