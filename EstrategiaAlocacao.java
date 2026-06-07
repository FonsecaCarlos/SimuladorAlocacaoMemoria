import java.util.LinkedList;

public interface EstrategiaAlocacao {
    // Retorna o índice do bloco escolhido na lista, ou -1 se nenhum servir.
    int buscarBloco(LinkedList<Bloco> memoria, int tamanhoSolicitado);
}