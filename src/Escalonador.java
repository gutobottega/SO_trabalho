import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Escalonador extends Thread{

    Semaphore semaSch;
    Semaphore semaPro;
    Semaphore semaBlo;
    Queue<ProcessControlBlock> prontos;
    private Queue<ProcessControlBlock> bloqs;
    CPU cpu;

    Escalonador(){
        semaSch = new Semaphore(0);
        semaPro = new Semaphore(1);
        semaBlo = new Semaphore(1);
        prontos = new ConcurrentLinkedQueue<>();
        bloqs = new ConcurrentLinkedQueue<>();
        start();
    }

    public void run(){
        ProcessControlBlock next;
        while(true){
            try {
                semaSch.acquire();
                semaPro.acquire();
            } catch (InterruptedException e) {}
            if(prontos.size() > 0){
                next = prontos.poll();
                cpu.setContext(next);
                cpu.semaCPU.release();
            }
            semaPro.release();
        }
    }

    void liberaBloqueado(){
        try {
            semaPro.acquire();
            semaBlo.acquire();
        } catch (InterruptedException e) {}
        prontos.add(bloqs.poll());
        semaBlo.release();
        semaPro.release();
        if(prontos.size() == 1){
            semaSch.release();
        }
    }

    void addBloqs(ProcessControlBlock pcb){
        try {
            semaBlo.acquire();
        } catch (InterruptedException e) {}
        bloqs.add(pcb);
        semaBlo.release();
        if (!prontos.isEmpty()){
            semaSch.release();
        }
    }

    void addProntos(ProcessControlBlock pcb){
        try {
            semaPro.acquire();
        } catch (InterruptedException e) {}
        prontos.add(pcb);
        semaPro.release();
    }

    void setCpu(CPU cpu1){
        cpu = cpu1;
    }
}
