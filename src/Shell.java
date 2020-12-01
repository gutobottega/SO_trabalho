import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Shell extends Thread{

    private GerenciadorProcesso gp;
    private Scanner scanner;
    private Console console;
    Semaphore bloquear;

    Shell(GerenciadorProcesso gp, Console console){
        this.gp = gp;
        this.console = console;
        scanner = new Scanner(System. in);
        bloquear = new Semaphore(0);
    }

    public void run(){
        boolean ativo = true;
        int aux;
        System.out.println("Bem vindo ao meu sistema operacional!");
        while (ativo){
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Lista de programas.");
            System.out.println("2 - Verificar pedidos de escrita e leitura em espera("+console.nmrPedido()+").");
            System.out.println("0 - Finalizar execução");
            aux = scanner.nextInt();
            switch (aux){
                case 1:
                    this.listarProgramas();
                    break;
                case 2:
                    if(console.temPedido()){
                        console.semCons.release();
                        try {
                            bloquear.acquire();
                        } catch (InterruptedException e) {}
                    }else {
                        System.out.println("Sem nenhum pedido de escrita em espera.");
                    }
                     break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Digite um valor válido!");
                    break;
            }
        }
    }

    private void listarProgramas(){
        System.out.println("1 - Os 10 primeiros elementos de fibonacci.");
        System.out.println("2 - Indicar quantidade de elementos para executar o fibonacci ");
        int aux = scanner.nextInt();
        boolean validador = true;
        while (validador){
            switch (aux){
                case 1:
                    gp.alocaProcesso(1);
                    validador = false;
                    break;
                case 2:
                    gp.alocaProcesso(2);
                    validador = false;
                    break;
                default:
                    System.out.println("Digite um valor válido!");
                    validador = true;
                    break;
            }
        }
    }

}
