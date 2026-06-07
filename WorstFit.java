import java.util.LinkedList;

public class WorstFit implements EstrategiaAlocacao {
    @Override
    public int buscarBloco(LinkedList<Bloco> memoria, int tamanhoSolicitado) {
        int indiceEscolhido = -1;
        int maiorTamanho = -1;

        for (int i = 0; i < memoria.size(); i++) {
            Bloco b = memoria.get(i);
            if (!b.ocupado && b.tamanho >= tamanhoSolicitado) {
                if (b.tamanho > maiorTamanho) {
                    maiorTamanho = b.tamanho;
                    indiceEscolhido = i;
                }
            }
        }
        return indiceEscolhido;
    }
}