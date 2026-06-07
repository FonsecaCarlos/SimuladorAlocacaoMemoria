# Relatório de Análise Comparativa de Algoritmos de Alocação de Memória


## Aluno: Carlos Henrique Camilo da Fonseca


	O presente documento analisa o comportamento e a eficiência dos algoritmos de alocação de memória contígua — First Fit, Best Fit e Worst Fit — em um esquema de partições variáveis, sob uma memória total disponível de 100, 105 e 130 unidades de tamanho. A avaliação foi realizada a partir do processamento de uma carga idêntica de seis eventos ordenados, compreendendo quatro requisições de alocação (Processos 1 a 3), seguidas por uma desalocação (Processo 2) e, por fim, duas novas tentativas de alocação (Processos 4 e 5).
	Ao todo foram realizadas três simulação com os mesmos processos. O que variava era a quantidade disponível de memória. Na primeira simulação a quantidade disponível de memória era 100. Esse valor corresponde ao total necessário para alocar todos os processos. Mesmo assim falha para alocar todos os recursos. Na segunda simulação a memória disponível é 105. Valor um pouco superior a soma de todos os processos, aqui é possível observar que algumas estratégias de alocação conseguem alocar todos os processos enquanto que outras não. Por fim, na terceira simulação a memória utilizada é de 130. Para essa quantidade, todos as estratégias performam bem. Abaixo são detalhadas cada simulação.

## Primeira simulação:

	No início da execução de todos os cenários, a memória apresentava-se completamente vazia, configurada como uma única grande brecha contínua de 100 unidades. Nas três abordagens, as primeiras três operações de alocação ocorreram com sucesso absoluto de forma sequencial. O Processo 1 (tamanho 20) ocupou o intervalo de 0 a 19; o Processo 2 (tamanho 30) se estabeleceu nas posições de 20 a 49; e o Processo 3 (tamanho 15) foi alocado nos endereços de 50 a 64. Ao fim dessa primeira etapa de preenchimento, restou uma brecha livre de 35 unidades ao final do espaço de memória. O evento subsequente determinou a liberação do Processo 2 em todos os testes. O simulador removeu com sucesso o processo e realizou a coalescência de áreas adjacentes vazias quando aplicável, convertendo o intervalo de 20 a 49 em uma brecha isolada de 30 unidades. Até este ponto da linha do tempo, a memória ficou mapeada com o Processo 1 no início, uma lacuna de 30 unidades, o Processo 3 posicionado no meio, e uma última lacuna de 35 unidades no final.
	As divergências práticas entre as estratégias de busca manifestaram-se estritamente na quinta operação do arquivo, na qual o Processo 4 solicitou o espaço de 25 unidades. Ao rodar a estratégia First Fit, o algoritmo varreu a memória a partir do início e encontrou a primeira brecha disponível com tamanho suficiente, que era justamente o espaço deixado pelo Processo 2 (tamanho 30). O Processo 4 foi inserido ali (intervalo de 20 a 44), subdividindo o espaço restante e gerando um fragmento residual minúsculo de apenas 5 unidades na posição de 45 a 49. No cenário do Best Fit, o comportamento foi idêntico, visto que a busca pela menor brecha que servisse também apontou a lacuna de 30 unidades como a melhor opção (sobra de 5), preferível à brecha maior de 35 unidades localizada ao final da memória.
	Por outro lado, a abordagem do Worst Fit respondeu ao Processo 4 de forma inversa. Fiel à sua premissa de buscar sempre a maior área livre disponível para evitar microfragmentos, o algoritmo ignorou a primeira brecha de 30 unidades e alocou o Processo 4 na brecha final de 35 unidades, gerando uma nova partição ocupada de 65 a 89 e deixando um resíduo livre final de 10 unidades.
	Apesar de distribuírem o Processo 4 por caminhos distintos, as três abordagens culminaram exatamente na mesma consequência crítica no evento final: a tentativa de alocação do Processo 5, que exigia 40 unidades de tamanho. Em todas as estratégias, o simulador emitiu um alerta de falha por falta de espaço contíguo e disparou o diagnóstico de fragmentação externa. Tanto no First Fit e Best Fit quanto no Worst Fit, a memória de fato continha as 40 unidades livres necessárias de forma pulverizada (as somas dos blocos livres mediam 5 + 35 no primeiro caso e 30 + 10 no segundo), porém a ausência de uma única partição contínua desse tamanho inviabilizou a operação de escrita.
    Em termos estatísticos, o fechamento dos três relatórios exibiu métricas rigorosamente idênticas.
	Todas as três estratégias de alocação de memória contígua encerraram o teste com uma taxa de utilização final de 60,00% da capacidade total da memória e reportaram exatamente 1 processo recusado devido a problemas de dispersão espacial de dados (fragmentação externa). Conclui-se que, para este conjunto específico de dados e cargas de trabalho, nenhuma das abordagens de busca linear contígua obteve vantagem operacional sobre as demais, evidenciando a limitação intrínseca de sistemas que não implementam rotinas dinâmicas de compactação de memória ou paginação.

## Arquivo de entrada memoria.txt:

100
A;1;20
A;2;30
A;3;15
L;2
A;4;25
A;5;40

## Resultado:

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 1

=== INÍCIO DA SIMULAÇÃO: First Fit (Total: 100) ===
Estado Atual da Memória:
   [Brecha | Tam: 100 | Ini-Fim: 0-99]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 80 | Ini-Fim: 20-99]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 50 | Ini-Fim: 50-99]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Falha por falta de espaço contíguo.
[ALERTA] FRAGMENTAÇÃO EXTERNA DETECTADA!
         Espaço livre fragmentado total: 40 | Solicitado: 40
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 60,00%
Qtd de processos não alocados (falha contígua): 1
=======================================================

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 2

=== INÍCIO DA SIMULAÇÃO: Best Fit (Total: 100) ===
Estado Atual da Memória:
   [Brecha | Tam: 100 | Ini-Fim: 0-99]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 80 | Ini-Fim: 20-99]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 50 | Ini-Fim: 50-99]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Falha por falta de espaço contíguo.
[ALERTA] FRAGMENTAÇÃO EXTERNA DETECTADA!
         Espaço livre fragmentado total: 40 | Solicitado: 40
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 60,00%
Qtd de processos não alocados (falha contígua): 1
=======================================================

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 3

=== INÍCIO DA SIMULAÇÃO: Worst Fit (Total: 100) ===
Estado Atual da Memória:
   [Brecha | Tam: 100 | Ini-Fim: 0-99]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 80 | Ini-Fim: 20-99]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 50 | Ini-Fim: 50-99]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 35 | Ini-Fim: 65-99]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 4 | Tam: 25 | Ini-Fim: 65-89]
   [Brecha | Tam: 10 | Ini-Fim: 90-99]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Falha por falta de espaço contíguo.
[ALERTA] FRAGMENTAÇÃO EXTERNA DETECTADA!
         Espaço livre fragmentado total: 40 | Solicitado: 40
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 4 | Tam: 25 | Ini-Fim: 65-89]
   [Brecha | Tam: 10 | Ini-Fim: 90-99]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 60,00%
Qtd de processos não alocados (falha contígua): 1
=======================================================


## Segunda simulação:

	Nesta simulação, a capacidade total da memória foi ampliada para 105 unidades de tamanho, mantendo-se a mesma carga de trabalho com seis eventos ordenados (quatro alocações, uma liberação e duas novas alocações). O objetivo deste teste foi avaliar como um acréscimo sutil de hardware (5 unidades de memória) impacta o fenômeno da fragmentação externa em diferentes critérios de busca.
	No estágio inicial de todos os cenários, a memória de 105 unidades apresentava-se completamente livre e contígua. A primeira sequência de eventos ocorreu de forma idêntica em todas as estratégias: o Processo 1 (tamanho 20) fixou-se no intervalo de 0 a 19; o Processo 2 (tamanho 30) ocupou o espaço de 20 a 49; e o Processo 3 (tamanho 15) foi posicionado de 50 a 64. Devido à expansão do espaço total, a brecha livre restante ao final da memória nesta fase passou a ser de 40 unidades (intervalo de 65 a 104), em contraste com as 35 unidades do teste anterior. O quarto evento determinou a desalocação do Processo 2, gerando uma nova brecha isolada de 30 unidades no centro da memória (endereços 20 a 49) em todos os casos.
	A partir do quinto evento, correspondente à inserção do Processo 4 (tamanho 25), o comportamento dos algoritmos passou a divergir e determinou o sucesso ou o fracasso no encerramento da simulação. Ao executar a estratégia First Fit, o sistema varreu a memória a partir do endereço zero e encontrou a primeira brecha apta: o espaço de 30 unidades deixado pelo Processo 2. O Processo 4 foi ali alocado (intervalo de 20 a 44), gerando um fragmento residual de 5 unidades na posição de 45 a 49, e mantendo intacta a brecha final de 40 unidades. No caso do Best Fit, a escolha foi exatamente a mesma, visto que a brecha de 30 unidades era o menor espaço satisfatório disponível para o tamanho solicitado, gerando o mesmo arranjo e preservando a lacuna final de 40 unidades.
	Essa distribuição foi o fator decisivo para a última operação, que solicitava a alocação do Processo 5 (tamanho 40). Como a brecha de 40 unidades ao final da memória foi preservada tanto pelo First Fit quanto pelo Best Fit, ambos os algoritmos conseguiram alocar o Processo 5 com sucesso absoluto no intervalo de 65 a 104. Consequentemente, essas duas estratégias alcançaram uma otimização quase perfeita da memória, registrando uma taxa de utilização final de 95,24% e nenhum processo recusado.
	Em contrapartida, a abordagem Worst Fit demonstrou a ineficiência de sua premissa para esta carga de trabalho. Ao processar o Processo 4 (tamanho 25), o algoritmo buscou a maior brecha do sistema, que era a lacuna final de 40 unidades, preenchendo o intervalo de 65 a 89 e reduzindo-a a um fragmento de 15 unidades. Com isso, a memória ficou dividida em duas brechas inutilizáveis para o evento seguinte: uma de 30 unidades no centro e uma de 15 unidades no final. Ao tentar alocar o Processo 5 (tamanho 40), o Worst Fit falhou por falta de espaço contíguo. Embora o sistema contasse com um espaço livre total de 45 unidades dispersas, a fragmentação externa impediu o atendimento do processo. O relatório estatístico do Worst Fit apresentou um desempenho significativamente inferior, encerrando com apenas 57,14% de utilização de memória e 1 processo recusado.
	Conclui-se que o aumento do tamanho total da memória para 105 unidades evidenciou o contraste de eficiência entre as estratégias de alocação. Enquanto o First Fit e o Best Fit souberam aproveitar o espaço adicional para mitigar completamente a fragmentação externa e acomodar toda a carga de processos, o Worst Fit deteriorou o arranjo físico do sistema ao fragmentar prematuramente a maior região livre disponível, ilustrando como escolhas locais de pior aptidão podem comprometer o rendimento global do gerenciamento de memória.

## Arquivo de entrada memoria.txt

105
A;1;20
A;2;30
A;3;15
L;2
A;4;25
A;5;40

## Resultado:

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 1

=== INÍCIO DA SIMULAÇÃO: First Fit (Total: 105) ===
Estado Atual da Memória:
   [Brecha | Tam: 105 | Ini-Fim: 0-104]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 85 | Ini-Fim: 20-104]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 55 | Ini-Fim: 50-104]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 5 | Tam: 40 | Ini-Fim: 65-104]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 95,24%
Qtd de processos não alocados (falha contígua): 0
=======================================================

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 2

=== INÍCIO DA SIMULAÇÃO: Best Fit (Total: 105) ===
Estado Atual da Memória:
   [Brecha | Tam: 105 | Ini-Fim: 0-104]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 85 | Ini-Fim: 20-104]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 55 | Ini-Fim: 50-104]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 5 | Tam: 40 | Ini-Fim: 65-104]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 95,24%
Qtd de processos não alocados (falha contígua): 0
=======================================================

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 3

=== INÍCIO DA SIMULAÇÃO: Worst Fit (Total: 105) ===
Estado Atual da Memória:
   [Brecha | Tam: 105 | Ini-Fim: 0-104]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 85 | Ini-Fim: 20-104]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 55 | Ini-Fim: 50-104]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 40 | Ini-Fim: 65-104]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 4 | Tam: 25 | Ini-Fim: 65-89]
   [Brecha | Tam: 15 | Ini-Fim: 90-104]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Falha por falta de espaço contíguo.
[ALERTA] FRAGMENTAÇÃO EXTERNA DETECTADA!
         Espaço livre fragmentado total: 45 | Solicitado: 40
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 4 | Tam: 25 | Ini-Fim: 65-89]
   [Brecha | Tam: 15 | Ini-Fim: 90-104]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 57,14%
Qtd de processos não alocados (falha contígua): 1
=======================================================


## Terceira simulação:

	Nesta rodada de testes, a capacidade total do sistema foi significativamente expandida para 130 unidades de tamanho, mantendo-se a mesma carga de trabalho com seis eventos ordenados (quatro alocações, uma desalocação e duas novas alocações). O objetivo deste cenário foi avaliar o impacto do superdimensionamento de hardware sobre os algoritmos, verificando se a abundância de recursos padroniza a eficiência ou se mantém as disparidades de comportamento estrutural.
	No estágio inicial das três simulações, a memória de 130 unidades configurava-se como uma única brecha contínua e vazia. A primeira fase de processamento seguiu o mesmo padrão geométrico: o Processo 1 (tamanho 20) foi inserido no intervalo de 0 a 19; o Processo 2 (tamanho 30) ocupou o espaço de 20 a 49; e o Processo 3 (tamanho 15) estabeleceu-se de 50 a 64. Devido à ampliação da memória para 130 unidades, a brecha livre residual ao final da memória saltou para 65 unidades (intervalo de 65 a 129). Logo em seguida, o quarto evento comandou a liberação do Processo 2, abrindo com sucesso uma lacuna central de 30 unidades (endereços 20 a 49) em todos os casos, deixando a memória fragmentada em dois blocos livres: um de 30 unidades e outro de 65 unidades.
	As tomadas de decisão voltaram a divergir no quinto evento, quando o Processo 4 (tamanho 25) solicitou inserção. Ao rodar o First Fit, o mecanismo varreu o espaço desde o endereço zero e encontrou a primeira partição viável, que era a brecha central de 30 unidades. O Processo 4 foi alocado no intervalo de 20 a 44, quebrando o espaço restante em um microfragmento de 5 unidades (endereços 45 a 49) e preservando intacta a grande brecha final de 65 unidades. O algoritmo Best Fit adotou exatamente a mesma conduta, pois ao avaliar ambas as lacunas (30 e 65 unidades), determinou que a de 30 unidades traria o menor desperdício de espaço (sobra de 5 vs. sobra de 40), mantendo a área final de 65 unidades livre. Graças a essa preservação, quando o Processo 5 (tamanho 40) disparou sua requisição, tanto o First Fit quanto o Best Fit encontraram espaço contíguo de sobra no final do mapa, alocando-o com sucesso no intervalo de 65 a 104 e restando uma brecha residual de 25 unidades.
	Por outro lado, o Worst Fit aplicou sua lógica de pior aptidão. Ao receber a requisição de 25 unidades do Processo 4, ele varreu a lista e escolheu a maior brecha do sistema (a de 65 unidades no final). O processo foi alojado de 65 a 89, reduzindo o espaço restante ali para 40 unidades (intervalo de 90 a 129). Esse arranjo gerou um cenário diferente para o último evento: a memória ficou com uma brecha de 30 unidades no centro e uma de 40 unidades no final. Quando o Processo 5 (tamanho 40) solicitou alocação, o Worst Fit buscou novamente a maior brecha disponível e encontrou a lacuna exata de 40 unidades no final da memória, preenchendo o intervalo de 90 a 129 até o limite.
	Diferente das simulações anteriores (com tamanhos de 100 e 105), a abundância de memória nesta simulação permitiu que o Worst Fit também acomodasse todos os processos, eliminando a ocorrência de rejeição por fragmentação externa. Como consequência direta, os relatórios estatísticos finais das três estratégias convergiram para números rigorosamente idênticos. Todos os algoritmos encerraram com sucesso com 0 processos recusados e uma taxa de utilização final de 76,92% da capacidade total da memória (correspondente a 100 unidades ocupadas de um total de 130).
	Conclui-se que, em ambientes com alta disponibilidade de hardware e superdimensionamento de recursos, as deficiências lógicas do algoritmo Worst Fit são mascaradas, igualando o seu rendimento ao do First Fit e do Best Fit em termos de vazão de processos. Contudo, a análise estrutural dos blocos evidencia que o Worst Fit distribui os fragmentos de forma distinta, dependendo criticamente de margens de folga maiores para garantir a estabilidade do sistema.


## Arquivo de entrada memoria.txt:

130
A;1;20
A;2;30
A;3;15
L;2
A;4;25
A;5;40

## Resultado:

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 1

=== INÍCIO DA SIMULAÇÃO: First Fit (Total: 130) ===
Estado Atual da Memória:
   [Brecha | Tam: 130 | Ini-Fim: 0-129]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 110 | Ini-Fim: 20-129]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 80 | Ini-Fim: 50-129]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 5 | Tam: 40 | Ini-Fim: 65-104]
   [Brecha | Tam: 25 | Ini-Fim: 105-129]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 76,92%
Qtd de processos não alocados (falha contígua): 0
=======================================================

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 2

=== INÍCIO DA SIMULAÇÃO: Best Fit (Total: 130) ===
Estado Atual da Memória:
   [Brecha | Tam: 130 | Ini-Fim: 0-129]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 110 | Ini-Fim: 20-129]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 80 | Ini-Fim: 50-129]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 4 | Tam: 25 | Ini-Fim: 20-44]
   [Brecha | Tam: 5 | Ini-Fim: 45-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 5 | Tam: 40 | Ini-Fim: 65-104]
   [Brecha | Tam: 25 | Ini-Fim: 105-129]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 76,92%
Qtd de processos não alocados (falha contígua): 0
=======================================================

--- SIMULADOR DE ALOCAÇÃO DE MEMÓRIA (CLASSES SEPARADAS) ---
1. First Fit (Primeiro-Apto)
2. Best Fit (Melhor-Apto)
3. Worst Fit (Pior-Apto)
0. Sair
Escolha a estratégia: 3

=== INÍCIO DA SIMULAÇÃO: Worst Fit (Total: 130) ===
Estado Atual da Memória:
   [Brecha | Tam: 130 | Ini-Fim: 0-129]

>> Operação: Alocar Processo 1 (Tamanho: 20)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 110 | Ini-Fim: 20-129]

>> Operação: Alocar Processo 2 (Tamanho: 30)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Brecha | Tam: 80 | Ini-Fim: 50-129]

>> Operação: Alocar Processo 3 (Tamanho: 15)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Proc 2 | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Liberar Processo 2
Resultado: Sucesso. Mesclando espaços livres adjacentes...
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Brecha | Tam: 65 | Ini-Fim: 65-129]

>> Operação: Alocar Processo 4 (Tamanho: 25)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 4 | Tam: 25 | Ini-Fim: 65-89]
   [Brecha | Tam: 40 | Ini-Fim: 90-129]

>> Operação: Alocar Processo 5 (Tamanho: 40)
Resultado: Sucesso.
Estado Atual da Memória:
   [Proc 1 | Tam: 20 | Ini-Fim: 0-19]
   [Brecha | Tam: 30 | Ini-Fim: 20-49]
   [Proc 3 | Tam: 15 | Ini-Fim: 50-64]
   [Proc 4 | Tam: 25 | Ini-Fim: 65-89]
   [Proc 5 | Tam: 40 | Ini-Fim: 90-129]

================ RELATÓRIO ESTATÍSTICO ================
Utilização final da memória: 76,92%
Qtd de processos não alocados (falha contígua): 0
=======================================================