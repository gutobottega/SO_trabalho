

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

public class Console extends Thread{

    public Semaphore semCons;
    private Semaphore semPed;
    private ConcurrentLinkedQueue<Pedido> pedidos;
    private GerenciadorMemoria gm;
    private CPU cpu;
    private Shell shell;


    Console(GerenciadorMemoria gm){
        this.gm = gm;
        semCons = new Semaphore(0);
        semPed = new Semaphore(1);
        pedidos = new ConcurrentLinkedQueue<>();
        start();
    }

    public void run(){
        while (true){
            try {
                semCons.acquire();
                semPed.acquire();
            } catch (InterruptedException e) {}
            Pedido aux = pedidos.poll();
            if (aux.read){
                Scanner scanner = new Scanner(System. in);
                System.out.println("Digite um valor inteiro para o processo " + aux.id + " executar:");
                int num = scanner.nextInt();
                aux.pos.opc = CPU.Opcode.DADO;
                aux.pos.p = num;
            } else{
                System.out.println("Processo " + aux.id + " retornou valor: ");
                int ret = aux.pos.p;
                System.out.println(ret);
            }
            cpu.interruptCPU(CPU.Interrupts.intIO);
            semPed.release();
            shell.bloquear.release();
        }
    }

    public void add(boolean read, PosMemoria pos, int id){
        try {
            semPed.acquire();
        } catch (InterruptedException e) {}
        Pedido pedido = new Pedido(read, pos, id);
        pedidos.add(pedido);
        semPed.release();
    }

    public boolean temPedido(){ return pedidos.size() > 0;}
    public int nmrPedido(){return pedidos.size();}

    public void setShell(Shell shell){ this.shell = shell;}

    public void setCPU(CPU cpu){ this.cpu = cpu;}
}
class Pedido {
    boolean read;
    PosMemoria pos;
    int id;

    Pedido(boolean read, PosMemoria pos, int id){
        this.id = id;
        this.pos = pos;
        this.read = read;
    }
}