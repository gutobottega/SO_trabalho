

//    • Se não á pprocesso executando, o Dispatcher é liberado para escolher um]
//    entre os processos da fila de prontos e
//executar.


public class GerenciadorProcesso {

    int id;
    GerenciadorMemoria gm;
    Escalonador escalonador;
    Programas progs;
    int contador;

    GerenciadorProcesso(GerenciadorMemoria gm, Escalonador escalonador){
        this.escalonador = escalonador;
        id = 0;
        this.gm = gm;
        progs = new Programas();
        contador = 0;
    }

    void alocaProcesso(int pos){
        id++;
        ProcessControlBlock pcb = new ProcessControlBlock(id,gm.aloca(progs.processos.get(pos), 70));
        escalonador.addProntos(pcb);
        if (contador == 0 || escalonador.prontos.size() == 1) {
            escalonador.semaSch.release();
        }
        contador++;
    }

    void desalocaProcesso(ProcessControlBlock pcb){
        gm.desaloca(pcb.paginas);
        contador--;
    }

}
