public class HiloSimple2 extends Thread {
    boolean fin = false;

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        String nombre = t.getName();
        while (!fin)
            System.out.println("Hola soy HiloSimple" + nombre);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminar() {
        fin = true;
    }
}
