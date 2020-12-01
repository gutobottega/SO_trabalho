 import java.util.HashMap;
import java.util.Map;

//Contem todos programas
public class Programas {

    public Map<Integer, PosMemoria[]> processos;

    Programas(){
        processos = new HashMap<>();
        processos.put(1,new PosMemoria[] {
                new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
                new PosMemoria(CPU.Opcode.STD, 0, -1, 50),
                new PosMemoria(CPU.Opcode.LDI, 1, -1, 1),
                new PosMemoria(CPU.Opcode.STD, 1, -1, 51),
                new PosMemoria(CPU.Opcode.LDI, 7, -1, 52),
                new PosMemoria(CPU.Opcode.LDI, 5, -1, 6),
                new PosMemoria(CPU.Opcode.LDI, 6, -1, 61),
                new PosMemoria(CPU.Opcode.LDI, 2, -1, 0),
                new PosMemoria(CPU.Opcode.ADD, 2, 0, -1),
                new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
                new PosMemoria(CPU.Opcode.ADD, 0, 1, -1),
                new PosMemoria(CPU.Opcode.ADD, 1, 2, -1),
                new PosMemoria(CPU.Opcode.STX, 7, 1, -1),
                new PosMemoria(CPU.Opcode.TRAP, 2, 7, -1),
                new PosMemoria(CPU.Opcode.ADDI, 7, -1, 1),
                new PosMemoria(CPU.Opcode.SUB, 6, 7, -1),
                new PosMemoria(CPU.Opcode.JMPIG, 5, 6, -1),
                new PosMemoria(CPU.Opcode.STOP, -1, -1, -1),
                });
        processos.put(2, new PosMemoria[] {
                new PosMemoria(CPU.Opcode.LDI, 0, -1, 49),
                new PosMemoria(CPU.Opcode.TRAP, 1, 0, -1),
                new PosMemoria(CPU.Opcode.LDD, 0, -1, 49),
                new PosMemoria(CPU.Opcode.LDI, 1, -1, 22),
                new PosMemoria(CPU.Opcode.JMPIL, 1, 0, -1),
                new PosMemoria(CPU.Opcode.ADDI, 0, 0, 51),
                new PosMemoria(CPU.Opcode.STD, 0, -1, 49),
                new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
                new PosMemoria(CPU.Opcode.STD, 0, -1, 50),
                new PosMemoria(CPU.Opcode.LDI, 1, -1, 1),
                new PosMemoria(CPU.Opcode.STD, 1, -1, 51),
                new PosMemoria(CPU.Opcode.LDI, 7, -1, 52),
                new PosMemoria(CPU.Opcode.LDI, 5, -1, 13),
                new PosMemoria(CPU.Opcode.LDD, 6, -1, 49),
                new PosMemoria(CPU.Opcode.LDI, 2, -1, 0),
                new PosMemoria(CPU.Opcode.ADD, 2, 0, -1),
                new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
                new PosMemoria(CPU.Opcode.ADD, 0, 1, -1),
                new PosMemoria(CPU.Opcode.ADD, 1, 2, -1),
                new PosMemoria(CPU.Opcode.STX, 7, 1, -1),
                new PosMemoria(CPU.Opcode.TRAP, 2, 7, -1),
                new PosMemoria(CPU.Opcode.ADDI, 7, -1, 1),
                new PosMemoria(CPU.Opcode.SUB, 6, 7, -1),
                new PosMemoria(CPU.Opcode.JMPIG, 5, 6, -1),
                new PosMemoria(CPU.Opcode.STOP, -1, -1, -1),
                new PosMemoria(CPU.Opcode.LDI, 1, -1, -1),//pular aqui
                new PosMemoria(CPU.Opcode.STD, 1, 0, 49),
                new PosMemoria(CPU.Opcode.STOP, -1, -1, -1)
        });
    }
}