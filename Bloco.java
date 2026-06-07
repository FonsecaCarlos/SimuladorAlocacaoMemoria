public class Bloco {
    int inicio;
    int tamanho;
    boolean ocupado;
    int idProcesso;

    public Bloco(int inicio, int tamanho, boolean ocupado, int idProcesso) {
        this.inicio = inicio;
        this.tamanho = tamanho;
        this.ocupado = ocupado;
        this.idProcesso = idProcesso;
    }

    @Override
    public String toString() {
        if (ocupado) {
            return String.format("[Proc %d | Tam: %d | Ini-Fim: %d-%d]", 
                    idProcesso, tamanho, inicio, inicio + tamanho - 1);
        } else {
            return String.format("[Brecha | Tam: %d | Ini-Fim: %d-%d]", 
                    tamanho, inicio, inicio + tamanho - 1);
        }
    }
}