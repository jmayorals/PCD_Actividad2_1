package test;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class AsignadorDeRecursos {
    private final Semaphore semaforo;
    private final int totalRecursos;
    private final Random aleatorio;

    public AsignadorDeRecursos(int cantidadRecursos) {
        semaforo = new Semaphore(cantidadRecursos);
        totalRecursos = cantidadRecursos;
        aleatorio = new Random();
    }

    public void reservar(int r) throws InterruptedException {
        System.out.println("Intentando reservar " + r + " recursos...");
        semaforo.acquire(r);
        System.out.println("Reservados " + r + " recursos. Recursos disponibles: " + semaforo.availablePermits() + " de " + totalRecursos);
    }

    public void liberar(int l) {
        semaforo.release(l);
        System.out.println("Liberados " + l + " recursos. Recursos disponibles: " + semaforo.availablePermits() + " de " + totalRecursos);
    }

    public static void main(String[] args) {
        AsignadorDeRecursos asignador = new AsignadorDeRecursos(10); // Suponiendo 10 recursos disponibles

        while (true) {
            try {
                int recursosDisponibles = asignador.semaforo.availablePermits();
                if (recursosDisponibles > 0) {
                    int recursosAReservar = asignador.aleatorio.nextInt(recursosDisponibles) + 1; // Reserva un número aleatorio de recursos, al menos 1
                    asignador.reservar(recursosAReservar);
                    // Simula el uso de los recursos
                    Thread.sleep(1000); // Espera 1 segundo
                    asignador.liberar(recursosAReservar);
                }
                Thread.sleep(500); // Espera 0.5 segundos antes de intentar de nuevo
            } catch (InterruptedException e) {
                System.out.println("Programa interrumpido.");
                break;
            }
        }
    }
}