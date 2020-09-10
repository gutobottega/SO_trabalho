import java.util.Arrays;
import java.util.LinkedList;

public class GerenciadorMemoria {
    public PosMemoria[] m;
    public boolean[] liberado;

    GerenciadorMemoria(int tamMem){
        m = new PosMemoria[tamMem];
        liberado = new boolean[tamMem/16];
        Arrays.fill(liberado, Boolean.TRUE);
        for (int i=0; i<tamMem; i++) {
            m[i] = new PosMemoria(CPU.Opcode.___,-1,-1,-1);
        };
    }

    public LinkedList<Integer> carga(PosMemoria[] p) {
        int count = 0;
        int tamPag = p.length/16;
        int offset = p.length%16;
        if(offset != 0) tamPag++;
        LinkedList<Integer> ret = new LinkedList();

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
                    if(i == tamPag - 1 && offset != 0){
                        for (int k = 0; k < offset; k++) {
                            m[pos+k] = p[count];
                            count++;
                        }
                    }else {
                        for (int k = 0; k < 16; k++) {
                            m[pos+k] = p[count];
                            count++;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public void dump(int ini, int fim) {
        for (int i = ini; i < fim; i++) {
            System.out.print(i); System.out.print(":  ");  dump(m[i]);
        }
    }

    public void dump(PosMemoria w) {
        System.out.print("[ ");
        System.out.print(w.opc); System.out.print(", ");
        System.out.print(w.r1);  System.out.print(", ");
        System.out.print(w.r2);  System.out.print(", ");
        System.out.print(w.p);   System.out.println("  ] ");
    }

}
