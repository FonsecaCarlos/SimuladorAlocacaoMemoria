import java.util.LinkedList;

public class BestFit implements EstrategiaAlocacao {
    @Override
    public int buscarBloco(LinkedList<Bloco> memoria, int tamanhoSolicitado) {
        int indiceEscolhido = -1;
        int menorTamanho = Integer.MAX_VALUE;

        for (int i = 0; i < memoria.size(); i++) {
            Bloco b = memoria.get(i);
            if (!b.ocupado && b.tamanho >= tamanhoSolicitado) {
                if (b.tamanho < menorTamanho) {
                    menorTamanho = b.tamanho;
                    indiceEscolhido = i;
                }
            }
        }
        return indiceEscolhido;
    }
}