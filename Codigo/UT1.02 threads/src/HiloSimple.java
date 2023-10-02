public class HiloSimple implements Runnable {
    boolean fin = false;

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        String nombre = t.getName();
        System.out.println("Hola soy hilo simple" + nombre);
        while (!fin)
            System.out.println("Hola soy HiloSimple");
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
