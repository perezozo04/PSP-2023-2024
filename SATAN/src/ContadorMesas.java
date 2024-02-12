//Esta clase debe ser dotada de un control de concurrencia

public class ContadorMesas {
    //Número máximo de mesas asignadas
    int maxMesas;
    //Contador que almacena el número del alumno que está siendo atendido
    int contadorAlumno;
    //Contador que almacena el número de solicitudes de atención generadas
    int peticionAlumno;
    //Array que indica la asignación de un terminal de funcionario a una mes de atención
    int[] asignaMesaTerminal;

    public ContadorMesas(int maxMesas) {
        this.maxMesas = maxMesas;
        this.contadorAlumno = 0;
        this.peticionAlumno = 0;
        this.asignaMesaTerminal = new int[maxMesas];
//inicilizamos todas las asignaciones
        for (int i = 0; i < maxMesas; i++) {
            this.asignaMesaTerminal[i] = 0;
        }
    }//end-constructor

    //Método para asignar un puesto a una mesa
//Si la asignación es posible retorna true
//Se supone que un puesto es el terminal del funcionario
//y la mesa en el número de la ventanilla por la que atiende
//un funcionario puede cambiarse de ventanilla (mesa) pero su terminal (puesto)
    //puede ser el mismo
    public boolean asignaMesa(int mesaAsignada, int puesto) {
        boolean result = false;
        if (asignaMesaTerminal[mesaAsignada] == 0) {
            asignaMesaTerminal[mesaAsignada] = puesto;
            result = true;
        }
        return result;
    }

    //Método que retorna el número del siguiente alumno que debe ser atendido
    public int nextContador() {
        int nextNumero = 0;
//si tenemos alumnos pendientes que atender
        if (contadorAlumno < peticionAlumno) {
            contadorAlumno++;
            nextNumero = contadorAlumno;
        }
        return nextNumero;
    }

    //Método que solicita un número de orden para ser atendido
    public int nextAlumno() {
        int nextPeticion = 0;
//pedimos ser atendidos
        peticionAlumno++;
        nextPeticion = peticionAlumno;
        return nextPeticion;
    }

    public String getAsignacion(int i) {
        String mensa = "Mesa " + i + " asignada al terminal " + asignaMesaTerminal[i];
        return mensa;
    }

    //borra la asignación de una ventanilla (mesa) a un terminal de funcionario
    public void clearAsignacion(int i) {
        asignaMesaTerminal[i] = 0;
    }
}//end-class