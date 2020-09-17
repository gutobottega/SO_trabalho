public class VM {

	public static void main(String args[]) {
		VM vm = new VM(1024);
		vm.prog1();
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

	public void prog1(){
		cpu.setContext(0, tamMem - 1, 0);
		progs.paginas.put(1, gm.aloca(progs.p1,62));
		progs.paginas.put(2, gm.aloca(progs.p2,62));
		cpu.run(progs.paginas.get(1));
	}

	//aqui criar um metodo chamando cada programa individualmente

	/*/teste programa 1
	public void prog1() {
		progs.paginas.put(1, gm.carga(progs.p1,62));
		cpu.setContext(0, tamMem - 1, 0);
		System.out.println("---------------------------------- programa carregado ");
		gm.dumpMem(0, 18);
		System.out.println("---------------------------------- após execucao ");
		//manda rodar o programa 1;
		cpu.run(progs.paginas.get(1));
		gm.dumpMem(0, 18);
	}*/
}
