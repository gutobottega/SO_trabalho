public class VM {

	public static void main(String args[]) {
		VM vm = new VM(1024);
		vm.prog3();
	}

	public GerenciadorMemoria gm;
    public CPU cpu;
    int tamMem;
    Programas progs;

    public VM(int tamMemoria){
		tamMem = tamMemoria;
		gm = new GerenciadorMemoria(tamMemoria);
		cpu = new CPU(gm);
		progs = new Programas();
	}

	//teste programa 3
	public void prog3() {
		progs.paginas.put(3, gm.carga(progs.p3));
		cpu.setContext(0, tamMem - 1, 0);
		System.out.println("---------------------------------- programa carregado ");
		gm.dump(0, 18);
		System.out.println("---------------------------------- após execucao ");
		cpu.run();
		gm.dump(0, 18);
	}
}
