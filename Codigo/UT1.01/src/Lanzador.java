public class Lanzador {
    public static void main(String[] args) {
        LanzadorFicheros l = new LanzadorFicheros();
        l.lanzadorSumador(01,10, "r1.txt");
        l.lanzadorSumador(11,20, "r2.txt");
        System.out.println("Sumadores lanzados");
    }
}
