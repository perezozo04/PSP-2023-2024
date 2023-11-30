public class Casino
{
    private static final int MAX_JUGADORES = 12;
    private static int saldo = 1000;

    public static Banca banca = new Banca(50000);

    public static void main(String[] args) {



        Jugadores [] jugador = new Jugadores[MAX_JUGADORES];

        Sincro sincro = new Sincro(MAX_JUGADORES);

        for (int i = 0; i < MAX_JUGADORES; i++) {
            jugador[i] = new Jugadores(i, saldo, sincro, banca);

        }






    }
}
