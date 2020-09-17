import java.util.Arrays;
import java.util.LinkedList;

public class GerenciadorMemoria {
    private PosMemoria[] m;
    public boolean[] liberado;

    GerenciadorMemoria(int tamMem){
        m = new PosMemoria[tamMem];
        liberado = new boolean[tamMem/16];
        Arrays.fill(liberado, Boolean.TRUE);
        for (int i=0; i<tamMem; i++) {
            m[i] = new PosMemoria(CPU.Opcode.___,-1,-1,-1);
        };
    }

    public LinkedList<Integer> aloca(PosMemoria[] p, int tamanho) {
        int tamPag = tamanho/16;
        int offset = tamanho%16;
        int tamaux = p.length/16;
        int offsetaux = p.length%16;
        if(offset > 0){
            tamPag++;
        }
        LinkedList<Integer> ret = new LinkedList();

        int count = 0;

        //calcula a qntidade necessária de paginas e percorre todas
        for (int i = 0; i < tamPag; i++) {
            //procura frames liberados
            for (int j = 0; j < liberado.length; j++) {
                //se frame estiver liberado, adiciona ao indice de paginas
                if (liberado[j]){
                    int pos = j*16;
                    liberado[j] = false;
                    ret.add(j);
                    //se for a ultima pagina e tiver offset, usa o offset como referencia para transferir os dados para memoria
                    //se não usa como referencia 16 posições para lançar os dados para memoria.
                    if(i <= tamaux){
                        if( i == tamaux && offset != 0) {
                            for (int k = 0; k < offsetaux; k++) {
                                m[pos + k] = p[count];
                                count++;
                            }
                        }else {
                            for (int k = 0; k < 16; k++) {
                                m[pos+k] = p[count];
                                count++;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return ret;
    }

    public void desaloca(LinkedList<Integer> list){
        list.forEach(item -> {
            liberado[item] = true;
            int pos = item * 16;
            for (int k = 0; k < 16; k++) {
                m[pos+k] = new PosMemoria(CPU.Opcode.___,-1,-1,-1);
            }
        });
    }

    public void dumpProg(LinkedList<Integer> lst){
        //Dump do programa recebe a lista de paginas e acessa os frames da memoria
        for (int pag: lst) {
            int pos = pag*16;
            for (int k = 0; k < 16; k++) {
                System.out.print(pos+k); System.out.print(":  ");
                dumpMem(m[pos+k]);
            }
        }
    }

    public void dumpMem(int ini, int fim) {
        for (int i = ini; i < fim; i++) {
            System.out.print(i); System.out.print(":  ");
            dumpMem(m[i]);
        }
    }

    public void dumpMem(PosMemoria w) {
        System.out.print("[ ");
        System.out.print(w.opc); System.out.print(", ");
        System.out.print(w.r1);  System.out.print(", ");
        System.out.print(w.r2);  System.out.print(", ");
        System.out.print(w.p);   System.out.println("  ] ");
    }

    public void setMem(PosMemoria mem, int pos) {
        m[pos] = mem;
    }

    public PosMemoria getMem(int pos) {
        return m[pos];
    }
}
