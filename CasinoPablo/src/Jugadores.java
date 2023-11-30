import java.util.Random;

public class Jugadores extends Thread {
    private int id;
    private int dinero;
    private Sincro sincro;
    private Ruleta ruleta;
    private Banca banca;




    public Jugadores(int id, int dinero, Sincro sincro, Banca banca) {
        this.id = id;
        this.dinero = dinero;
        this.sincro = sincro;
        ruleta = new Ruleta();
        this.banca = banca;
        this.start();
    }


    @Override
    public void run() {
        esperarTresSegundos();
        int perdidos = 1;
        boolean fin = false;
        while (!fin && banca.getDineroBanca() > 0) {
            if (this.id < 4) {
                if (this.dinero < 10) {
                    System.out.println("El jugador con id " + this.id + " no tiene dinero suficiente");
                    System.out.println("La banca tiene: " + banca.getDineroBanca() + " dinero");
                    fin = true;
                } else {
                    this.dinero -= 10;
                    banca.setDineroBanca(banca.getDineroBanca() + 10);

                    if (numeroRandomTresSeis() == ruleta.numeroRandomTresSeis()) {
                        if (banca.getDineroBanca() < 360) {
                            this.dinero += banca.getDineroBanca();
                            banca.setDineroBanca(0);
                            System.out.println("La banca se ha quedado sin dinero.");
                            System.out.println("El jugador con id " + this.id + " tiene " + this.dinero + " dinero");
                            fin = true;
                        } else {
                            this.dinero += 360;
                            banca.setDineroBanca(banca.getDineroBanca() - 360);
                        }
                    }
                }
            } else if (this.id < 8) {
                if (this.dinero < 10) {
                    System.out.println("El jugador con id " + this.id + " no tiene dinero suficiente");
                    System.out.println("La banca tiene: " + banca.getDineroBanca() + " dinero");
                    fin = true;
                } else {
                    this.dinero -= 10;
                    banca.setDineroBanca(banca.getDineroBanca() + 10);
                    if (numeroRandomParImpar() == ruleta.numeroRandomParImpar()) {
                        if(banca.getDineroBanca() < 20) {
                            this.dinero += banca.getDineroBanca();
                            banca.setDineroBanca(0);
                            System.out.println("La banca se ha quedado sin dinero.");
                            System.out.println("El jugador con id " + this.id + " tiene " + this.dinero + " dinero");
                            fin = true;
                        } else {
                            this.dinero += 20;
                            banca.setDineroBanca(banca.getDineroBanca() - 20);
                        }
                    }
                }

            } else if (this.id < 12) {
                if (this.dinero < 10 * perdidos) {
                    System.out.println("El jugador con id " + this.id + " no tiene dinero suficiente");
                    System.out.println("La banca tiene: " + banca.getDineroBanca() + " dinero");
                    fin = true;
                } else {
                    this.dinero -= 10 * perdidos;
                    banca.setDineroBanca(banca.getDineroBanca() + 10 * perdidos);
                    if (numeroRandomTresSeis() == ruleta.numeroRandomTresSeis()) {
                        if(banca.getDineroBanca() < (10 * perdidos)) {
                            this.dinero += banca.getDineroBanca();
                            banca.setDineroBanca(0);
                            System.out.println("La banca se ha quedado sin dinero.");
                            System.out.println("El jugador con id " + this.id + " tiene " + this.dinero + " dinero");
                            fin = true;
                        } else {
                            this.dinero += 360 * perdidos;
                            banca.setDineroBanca(banca.getDineroBanca() - 360);
                            perdidos = 1;
                        }
                    } else {
                        perdidos++;
                    }
                }
            }
        }
    }

    private void esperarTresSegundos() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private int numeroRandomTresSeis(){
        Random random = new Random();
        return random.nextInt(35)+1;
    }

    private int numeroRandomParImpar() {
        Random random = new Random();
        return random.nextInt(2)+1;
    }
}
