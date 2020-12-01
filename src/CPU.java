import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class CPU extends Thread{

    public enum Opcode {
        DADO, ___, TRAP,
        JMP, JMPI, JMPIG,
        JMPIL, JMPIE, ADDI,
        SUBI, ANDI, ORI, LDI,
        LDD, STD, ADD, SUB,
        MULT, LDX, STX, SWAP, STOP;
    }

    public enum Interrupts {
        noInterrupt, intEnderecoInvalido, intInstrucaoInvalida, intSTOP, timeout, intIO, intTRAP
    }

    public Semaphore semaCPU;
    private int pc;
    private int[] reg;
    private int regIO1;
    private int regIO2;
    private Map<Interrupts, Boolean> flags;
   private int base;
    private int limite;
    private LinkedList<Integer> pagsProg;
    private ProcessControlBlock processoRodando;
    private Escalonador escalonador;
    private Console console;
    private int timer;
    private GerenciadorMemoria gm;
    private GerenciadorProcesso gp;
    private boolean rodando;

    public CPU(GerenciadorMemoria gm, GerenciadorProcesso gp, Escalonador escalonador, Console console) {
        semaCPU = new Semaphore(0 );
        flags = new ConcurrentHashMap<>();
        this.gp = gp;
        this.escalonador = escalonador;
        this.console = console;
        this.base = 0;
        this.limite = gm.tamMemoria() - 1;
        this.gm = gm;
        this.reg = new int[8];
        timer = 5;
        buildFlags();
        start();
    }

    private void buildFlags(){
        flags.put(CPU.Interrupts.intEnderecoInvalido, false);
        flags.put(CPU.Interrupts.intInstrucaoInvalida, false);
        flags.put(CPU.Interrupts.intIO, false);
        flags.put(CPU.Interrupts.intSTOP, false);
        flags.put(CPU.Interrupts.intTRAP, false);
        flags.put(CPU.Interrupts.timeout, false);
        flags.put(CPU.Interrupts.noInterrupt, true);
    }

    public void setContext(ProcessControlBlock pcb) {
        pc = pcb.pc;
        reg = pcb.reg;
        pagsProg = pcb.paginas;
        processoRodando = pcb;
    }

    private boolean legal(int e) {
        if ((e < base) || (e > limite)) {
            flags.replace(Interrupts.intEnderecoInvalido, true);
            flags.replace(Interrupts.noInterrupt, false);
            return false;
        };
        return true;
    }

    private PosMemoria getPosMem(int pos){
        if(legal(pos)) {
            int pag = pos / 16;
            int offset = pos % 16;
            pos = pagsProg.get(pag);
            return gm.getMem((pos * 16) + offset);
        }else return null;
    }

    private void setPosMem(PosMemoria memo, int pos){
        if(legal(pos)){
            int pag = pos / 16;
            int offset = pos % 16;
            pos = pagsProg.get(pag);
            gm.setMem(memo , (pos * 16) + offset);
        }
    }

    private void saveContext(){
        processoRodando.pc = pc;
        processoRodando.reg = reg;
        processoRodando.paginas = pagsProg;
    }

    public void interruptCPU(Interrupts interrupt){
        flags.replace(Interrupts.noInterrupt, false);
        flags.replace(interrupt, true);
        if (!rodando) checkInterruption();
    }

    public void run() {

        while (true) {
            try {
                semaCPU.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rodando = true;
            while (true) {
                // FETCH
                if (legal(pc) && timer > 0) {
                    timer--;
                    PosMemoria ir = getPosMem(pc);
                    PosMemoria aux;
                    switch (ir.opc) {
                        case LDI: // Rd ← k
                            reg[ir.r1] = ir.p;
                            pc++;
                            break;

                        case STD: // [A] ← Rs
                            aux = getPosMem(ir.p);
                            if(aux != null) {
                                aux.opc = Opcode.DADO;
                                aux.p = reg[ir.r1];
                                pc++;
                                setPosMem(aux, ir.p);
                            }
                            break;

                        case ADD: // Rd ← Rd + Rs
                            reg[ir.r1] = reg[ir.r1] + reg[ir.r2];
                            pc++;
                            break;

                        case ADDI: // Rd ← Rd + k
                            reg[ir.r1] = reg[ir.r1] + ir.p;
                            pc++;
                            break;

                        case STX: // [Rd] ←Rs
                            aux = getPosMem(reg[ir.r1]);
                            if(aux != null) {
                                aux.opc = Opcode.DADO;
                                aux.p = reg[ir.r2];
                                pc++;
                                setPosMem(aux, reg[ir.r1]);
                            }
                            break;

                        case SUB: // Rd ← Rd - Rs
                            reg[ir.r1] = reg[ir.r1] - reg[ir.r2];
                            pc++;
                            break;

                        case JMPIG: // If Rc > 0 Then PC ← Rs // Else PC ← PC +1
                            if (reg[ir.r2] > 0) pc = reg[ir.r1];
                            else pc++;
                            break;

                        case JMP: //PC ← k
                            pc = ir.p;
                            break;

                        case JMPI: //PC ← Rs
                            pc = reg[ir.r2];
                            break;

                        case JMPIE: //if Rc = 0 then PC ← Rs // Else PC ← PC +1
                            if (reg[ir.r2] == 0) pc = reg[ir.r1];
                            else pc++;
                            break;

                        case JMPIL: //if Rc < 0 then PC ← Rs  //Else PC ← PC +1
                            if (reg[ir.r2] < 0) pc = reg[ir.r1];
                            else pc++;
                            break;

                        case ANDI: //Rd ←Rd AND k
                            reg[ir.r1] = reg[ir.r1] & ir.p;
                            pc++;
                            break;

                        case ORI: //Rd ←Rd OR k
                            reg[ir.r1] = reg[ir.r1] | ir.p;
                            pc++;
                            break;

                        case LDD: //Rd ← [A]
                            aux = getPosMem(ir.p);
                            if(aux != null) {
                                reg[ir.r1] = aux.p;
                                pc++;
                            }
                            break;

                        case MULT: //Rd ← Rd * Rs
                            reg[ir.r1] = reg[ir.r1] * reg[ir.r2];
                            pc++;
                            break;

                        case LDX: //Rd ← [Rs]
                            aux = getPosMem(ir.r2);
                            if(aux != null) {
                                reg[ir.r1] = aux.p;
                                pc++;
                            }
                            break;

                        case SWAP: //Rd7←Rd3, Rd6←Rd2, Rd5←Rd1, Rd4←Rd0
                            reg[7] = reg[3];
                            reg[6] = reg[2];
                            reg[5] = reg[1];
                            reg[4] = reg[0];
                            break;

                        case STOP: //  para a execucao
                            flags.replace(Interrupts.intSTOP, true);
                            flags.replace(Interrupts.noInterrupt, false);
                            break;

                        case TRAP:
                            regIO1 = ir.r1;
                            regIO2 = reg[ir.r2];
                            flags.replace(Interrupts.intTRAP, true);
                            flags.replace(Interrupts.noInterrupt, false);
                            pc++;
                            break;

                        case DADO:
                            break;

                        default:
                            break;
                    }
                }

                if (timer == 0) {
                    flags.replace(Interrupts.timeout, true);
                    flags.replace(Interrupts.noInterrupt, false);
                }

                if (checkInterruption()){
                    rodando = false;
                    break;
                }
            }
        }
    }

    private boolean checkInterruption(){
        boolean ret = false;
        if (!flags.get(Interrupts.noInterrupt)) {
            timer = 5;
            if (flags.get(Interrupts.timeout)) {
                flags.replace(Interrupts.timeout, false);
                saveContext();
                escalonador.addProntos(processoRodando);
                escalonador.semaSch.release();
                ret = true;
            } else if (flags.get(Interrupts.intTRAP)) {
                flags.replace(Interrupts.intTRAP, false);
                saveContext();
                escalonador.addBloqs(processoRodando);
                boolean read = regIO1 == 1 ? true : false;
                PosMemoria pos = getPosMem(regIO2);
                console.add(read, pos, processoRodando.id);
                escalonador.semaSch.release();
                ret = true;
            } else if (flags.get(Interrupts.intIO)) {
                flags.replace(Interrupts.intIO, false);
                escalonador.liberaBloqueado();
            } else if (flags.get(Interrupts.intEnderecoInvalido)) {
                flags.replace(Interrupts.intEnderecoInvalido, false);
                gp.desalocaProcesso(processoRodando);
                escalonador.semaSch.release();
                ret = true;
            } else if (flags.get(Interrupts.intInstrucaoInvalida)) {
                flags.replace(Interrupts.intInstrucaoInvalida, false);
                gp.desalocaProcesso(processoRodando);
                escalonador.semaSch.release();
                ret = true;
            } else if (flags.get(Interrupts.intSTOP)) {
                flags.replace(Interrupts.intSTOP, false);
                gp.desalocaProcesso(processoRodando);
                escalonador.semaSch.release();
                ret = true;
            }
        }
        if(!flags.get(Interrupts.timeout)){
            if(!flags.get(Interrupts.intTRAP)){
                if(!flags.get(Interrupts.intSTOP)){
                    if(!flags.get(Interrupts.intIO)){
                        if(!flags.get(Interrupts.intInstrucaoInvalida)){
                            if(!flags.get(Interrupts.intEnderecoInvalido)){
                                flags.replace(Interrupts.noInterrupt, true);
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
}