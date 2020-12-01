//Augusto Bottega

public class VM {

	public static void main(String args[]) {
		VM vm = new VM(1024);
		vm.shell.start();
	}

	public Shell shell;
	public GerenciadorMemoria gm;
    public CPU cpu;
    public GerenciadorProcesso gp;
    int tamMem;
    Escalonador escalonador;
    Console console;

    public VM(int tamMemoria){
		tamMem = tamMemoria;
		escalonador = new Escalonador();
		gm = new GerenciadorMemoria(tamMemoria);
		gp = new GerenciadorProcesso(gm, escalonador);
		console = new Console(gm);
		cpu = new CPU(gm, gp, escalonador, console);
		shell = new Shell(gp, console);
		console.setShell(shell);
		console.setCPU(cpu);
		escalonador.setCpu(cpu);
	}
}
