import java.util.Random;

public class Ruleta {

    public int numeroRandomTresSeis(){
        Random random = new Random();
        return random.nextInt(36);
    }

    public int numeroRandomParImpar() {
        Random random = new Random();
        return random.nextInt(2)+1;
    }
}
