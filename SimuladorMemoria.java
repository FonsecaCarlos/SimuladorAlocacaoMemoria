import java.io.*;
import java.util.*;

public class SimuladorMemoria {
    private static int tamanhoTotalMemoria;
    private static List<String> eventos = new ArrayList<>();
    private static LinkedList<Bloco> memoria = new LinkedList<>();
    private static int processosNaoAlocados = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        carregarArquivo("memoria.txt");

        while (true) {
            System.out.println("\n--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---");
            System.out.println("1. First Fit (Primeiro-Apto)");
            System.out.println("2. Best Fit (Melhor-Apto)");
            System.out.println("3. Worst Fit (Pior-Apto)");
            System.out.println("0. Sair");
            System.out.print("Escolha a estratégia: ");
            int opcao = scanner.nextInt();

            if (opcao == 0) break;

            EstrategiaAlocacao estrategia = null;
            String nomeEstrategia = "";

            switch (opcao) {
                case 1 -> { estrategia = new FirstFit(); nomeEstrategia = "First Fit"; }
                case 2 -> { estrategia = new BestFit(); nomeEstrategia = "Best Fit"; }
                case 3 -> { estrategia = new WorstFit(); nomeEstrategia = "Worst Fit"; }
                default -> { System.out.println("Opção inválida."); continue; }
            }

            executarSimulacao(estrategia, nomeEstrategia);
        }
        scanner.close();
    }

    private static void carregarArquivo(String nomeArq) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArq))) {
            String linha = br.readLine();
            if (linha != null) {
                tamanhoTotalMemoria = Integer.parseInt(linha.trim());
            }
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    eventos.add(linha.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo 'memoria.txt': " + e.getMessage());
            System.exit(1);
        }
    }

    private static void inicializarMemoria() {
        memoria.clear();
        memoria.add(new Bloco(0, tamanhoTotalMemoria, false, -1));
        processosNaoAlocados = 0;
    }

    private static void executarSimulacao(EstrategiaAlocacao estrategia, String nomeEstrategia) {
        inicializarMemoria();
        System.out.println("\n=== INÍCIO DA SIMULAÇÃO: " + nomeEstrategia + " (Total: " + tamanhoTotalMemoria + ") ===");
        exibirEstadoMemoria();

        for (String evento : eventos) {
            String[] tokens = evento.split(";");
            String tipo = tokens[0];

            if (tipo.equalsIgnoreCase("A")) {
                int id = Integer.parseInt(tokens[1]);
                int tamanho = Integer.parseInt(tokens[2]);
                System.out.printf("\n>> Operação: Alocar Processo %d (Tamanho: %d)\n", id, tamanho);
                processarAlocacao(id, tamanho, estrategia);
            } else if (tipo.equalsIgnoreCase("L")) {
                int id = Integer.parseInt(tokens[1]);
                System.out.printf("\n>> Operação: Liberar Processo %d\n", id);
                processarLiberacao(id);
            }
            exibirEstadoMemoria();
        }
        exibirRelatorioFinal();
    }

    private static void processarAlocacao(int id, int tamanho, EstrategiaAlocacao estrategia) {
        int indice = estrategia.buscarBloco(memoria, tamanho);

        if (indice != -1) {
            Bloco blocoAlvo = memoria.get(indice);
            int tamanhoRestante = blocoAlvo.tamanho - tamanho;

            blocoAlvo.ocupado = true;
            blocoAlvo.idProcesso = id;
            int inicioOriginal = blocoAlvo.inicio;
            blocoAlvo.tamanho = tamanho;

            if (tamanhoRestante > 0) {
                Bloco novaBrecha = new Bloco(inicioOriginal + tamanho, tamanhoRestante, false, -1);
                memoria.add(indice + 1, novaBrecha);
            }
            System.out.println("Resultado: Sucesso.");
        } else {
            System.out.println("Resultado: Falha por falta de espaço contíguo.");
            checarFragmentacaoExterna(tamanho);
            processosNaoAlocados++;
        }
    }

    private static void processarLiberacao(int id) {
        boolean encontrado = false;
        for (Bloco b : memoria) {
            if (b.ocupado && b.idProcesso == id) {
                b.ocupado = false;
                b.idProcesso = -1;
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            System.out.println("Resultado: Sucesso. Mesclando espaços livres adjacentes...");
            coalescerMemoria();
        } else {
            System.out.println("Resultado: Erro. Processo " + id + " não está na memória.");
        }
    }

    private static void coalescerMemoria() {
        for (int i = 0; i < memoria.size() - 1; i++) {
            Bloco atual = memoria.get(i);
            Bloco proximo = memoria.get(i + 1);

            if (!atual.ocupado && !proximo.ocupado) {
                atual.tamanho += proximo.tamanho;
                memoria.remove(i + 1);
                i--; 
            }
        }
    }

    private static void checarFragmentacaoExterna(int tamanhoSolicitado) {
        int somaBrechas = 0;
        for (Bloco b : memoria) {
            if (!b.ocupado) {
                somaBrechas += b.tamanho;
            }
        }

        if (somaBrechas >= tamanhoSolicitado) {
            System.out.println("[ALERTA] FRAGMENTAÇÃO EXTERNA DETECTADA!");
            System.out.printf("         Espaço livre fragmentado total: %d | Solicitado: %d\n", 
                    somaBrechas, tamanhoSolicitado);
        }
    }

    private static void exibirEstadoMemoria() {
        System.out.println("Estado Atual da Memória:");
        for (Bloco b : memoria) {
            System.out.println("   " + b);
        }
    }

    private static void exibirRelatorioFinal() {
        int memoriaOcupada = 0;
        for (Bloco b : memoria) {
            if (b.ocupado) {
                memoriaOcupada += b.tamanho;
            }
        }

        double percentualUtilizacao = ((double) memoriaOcupada / tamanhoTotalMemoria) * 100;

        System.out.println("\n================ RELATÓRIO ESTATÍSTICO ================");
        System.out.printf("Utilização final da memória: %.2f%%\n", percentualUtilizacao);
        System.out.println("Qtd de processos não alocados (falha contígua): " + processosNaoAlocados);
        System.out.println("=======================================================");
    }
}