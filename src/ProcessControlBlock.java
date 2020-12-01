import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessControlBlock {
    public int id;
    public LinkedList<Integer> paginas;
    public int pc;	// instruction register,
    public int[] reg;       	// registradores da CPU

    ProcessControlBlock(int id, LinkedList<Integer> paginas){
        this.id = id;
        this.paginas = paginas;
        pc = 0;
        reg = new int[8];
    }


}
