import java.util.LinkedList;

public class FirstFit implements EstrategiaAlocacao {
    @Override
    public int buscarBloco(LinkedList<Bloco> memoria, int tamanhoSolicitado) {
        for (int i = 0; i < memoria.size(); i++) {
            Bloco b = memoria.get(i);
            if (!b.ocupado && b.tamanho >= tamanhoSolicitado) {
                return i;
            }
        }
        return -1;
    }
}