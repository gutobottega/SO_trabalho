import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

//Contem todos programas e mais um map que liga o numero do programa ao array de paginas dele

public class Programas {

    Programas(){
        paginas = new LinkedHashMap<>();
    }

    public Map<Integer, LinkedList<Integer>> paginas;

    public PosMemoria[] p1 = new PosMemoria[] {
            new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
            new PosMemoria(CPU.Opcode.STD, 0, -1, 50),
            new PosMemoria(CPU.Opcode.LDI, 1, -1, 1),
            new PosMemoria(CPU.Opcode.STD, 1, -1, 51),
            new PosMemoria(CPU.Opcode.LDI, 7, -1, 52),
            new PosMemoria(CPU.Opcode.LDI, 5, -1, 6),
            new PosMemoria(CPU.Opcode.LDI, 6, -1, 61),
            new PosMemoria(CPU.Opcode.LDI, 2, -1, 1),
            new PosMemoria(CPU.Opcode.ADD, 2, 0, -1),
            new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
            new PosMemoria(CPU.Opcode.ADD, 0, 1, -1),
            new PosMemoria(CPU.Opcode.LDI, 1, 2, -1),
            new PosMemoria(CPU.Opcode.STX, 7, 1, -1),
            new PosMemoria(CPU.Opcode.ADD, 7, -1, 1),
            new PosMemoria(CPU.Opcode.SUB, 6, 7, -1),
            new PosMemoria(CPU.Opcode.JMPIG, 5, 7, -1),
            new PosMemoria(CPU.Opcode.STOP, -1, -1, -1),
    };

    //faz o load do valor da posição 49 da memoria
    public PosMemoria[] p2 = new PosMemoria[] {
            new PosMemoria(CPU.Opcode.LDD, 0, -1, 49),
            new PosMemoria(CPU.Opcode.LDI, 1, -1, 22),//posiçãoi para fim 1
            new PosMemoria(CPU.Opcode.JMPIL, 1, 0, -1),//comparação
            new PosMemoria(CPU.Opcode.ADDI, 0, 0, 51),//adiciona o indice de memoria no nmr de elementos para saber o fim da memoria
            new PosMemoria(CPU.Opcode.STD, 1, -1, 49),
            new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
            new PosMemoria(CPU.Opcode.STD, 0, -1, 50),
            new PosMemoria(CPU.Opcode.LDI, 1, -1, 1),
            new PosMemoria(CPU.Opcode.STD, 1, -1, 51),
            new PosMemoria(CPU.Opcode.LDI, 7, -1, 52),
            new PosMemoria(CPU.Opcode.LDI, 5, -1, 11),
            new PosMemoria(CPU.Opcode.LDD, 6, -1, 49),//pegar da posição 49 da memoria o indice do fim do array
            new PosMemoria(CPU.Opcode.LDI, 2, -1, 1),
            new PosMemoria(CPU.Opcode.ADD, 2, 0, -1),
            new PosMemoria(CPU.Opcode.LDI, 0, -1, 0),
            new PosMemoria(CPU.Opcode.ADD, 0, 1, -1),
            new PosMemoria(CPU.Opcode.LDI, 1, 2, -1),
            new PosMemoria(CPU.Opcode.STX, 7, 1, -1),
            new PosMemoria(CPU.Opcode.ADD, 7, -1, 1),
            new PosMemoria(CPU.Opcode.SUB, 6, 7, -1),
            new PosMemoria(CPU.Opcode.JMPIG, 5, 7, -1),
            new PosMemoria(CPU.Opcode.STOP, -1, -1, -1),
            new PosMemoria(CPU.Opcode.LDI, 1, -1, -1),//pular aqui
            new PosMemoria(CPU.Opcode.STD, 1, 0, 49),
            new PosMemoria(CPU.Opcode.STOP, -1, -1, -1)
    };
    public PosMemoria[] p3 = new PosMemoria[] {
            new PosMemoria(CPU.Opcode.LDI, 0, -1, 5), //LDI R1, 5
            new PosMemoria(CPU.Opcode.LDI, 1, -1, 13), //LDI R2, 13
            new PosMemoria(CPU.Opcode.LDI, 3, -1, 7), //LDI R4, 7
            new PosMemoria(CPU.Opcode.LDI, 4, -1, 11), //LDI R5, 11
            new PosMemoria(CPU.Opcode.JMPIL, 1, 0, 999), //JMPIL R2, R0
            new PosMemoria(CPU.Opcode.STD, 0, -1, 16), //STD R1, 51
            new PosMemoria(CPU.Opcode.LDD, 2, -1, 16), //LDD R3, 51
            new PosMemoria(CPU.Opcode.SUBI, 2, -1, 1), //SUBI R3, 1
            new PosMemoria(CPU.Opcode.JMPIE, 4, 2, 999), //JMPIE R5, R3
            new PosMemoria(CPU.Opcode.MULT, 0, 2, 999), //MULT R1, R3
            new PosMemoria(CPU.Opcode.JMPIG, 3, 2, 999), //JMPIG R4, R3
            new PosMemoria(CPU.Opcode.STD, 0, -1, 16), //STD R1, 51
            new PosMemoria(CPU.Opcode.STOP, -1, -1, -1), //STOP
            new PosMemoria(CPU.Opcode.LDI, 4, -1, -1), //LDI R5, -1
            new PosMemoria(CPU.Opcode.STD, 4, -1, 17), //LDI R5, 52
            new PosMemoria(CPU.Opcode.STOP, -1, -1, -1) //STOP
    };
}