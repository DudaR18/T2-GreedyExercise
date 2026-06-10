# T2-GreedyExercise

## 1. O Problema

Considere uma trilha de rally modelada por um segmento de comprimento $L$. O carro pode percorrer no máximo $d$ quilômetros por dia, e existem $n$ pontos de parada candidatos em posições $x_1, x_2, \dots, x_n$ medidas a partir do início da trilha.

<<<<<<< HEAD
Para tornar a questão mais precisa vamos assumir as seguintes premissas:

• Modelaremos a trilha do rally como um longo segmento de linha de comprimento 𝐿.

• Vocês conseguem viajar no máximo 𝑑 quilômetros por dia antes de anoitecer.

• Assumiremos que os pontos de parada estão localizados a distâncias 𝑥1, 𝑥2, … , 𝑥𝑛 do ponto de
partida.

• Assumiremos também que os seus amigos sempre estão corretos quando estimam se conseguem
ou não chegar ao próximo ponto de parada antes do anoitecer.

• Vamos considerar um conjunto de pontos de parada como válidos se a distância entre cada par
adjacente é no máximo 𝑑, e o primeiro ponto de parada está a no máximo uma distância 𝑑 do início e o último ponto de parada está a uma distância no máximo 𝑑 do final da corrida. Portanto, um conjunto de pontos de parada é valido se vocês conseguirem acampar nestes pontos e ainda completar toda a trilha.

• Assumimos que o conjunto 𝑛 com todos os pontos de parada é valido.
=======
O objetivo é escolher o menor subconjunto de pontos de parada que permita completar toda a rota, respeitando a regra de que a distância entre dois pontos consecutivos escolhidos, assim como entre o início e o primeiro ponto, e entre o último ponto e o final da trilha, seja no máximo $d$.
>>>>>>> d0cfe05 (.)

## 2. O Algoritmo

A estratégia proposta é gulosa:

1. Comece na posição inicial.
2. Entre todos os pontos alcançáveis no dia atual, avance até o ponto mais distante possível.
3. Repita o processo a partir desse novo ponto.
4. Pare quando a distância restante até o final for no máximo $d$.

Em outras palavras, a cada etapa escolhe-se o ponto de parada mais avançado que ainda pode ser alcançado antes do anoitecer.

## 3. Análise do Algoritmo

O algoritmo é ótimo. A intuição é que, ao escolher sempre o ponto mais distante possível dentro do alcance atual, ele maximiza o progresso diário e nunca usa uma parada desnecessária.

Uma forma simples de justificar isso é por argumento de troca. Suponha que exista uma solução ótima que, em algum passo, escolha um ponto $p$ que não seja o mais distante alcançável. Se trocarmos $p$ pelo ponto mais distante alcançável nesse mesmo passo, o número de paradas não aumenta e a solução continua válida, porque esse novo ponto está pelo menos tão à frente quanto o original. Repetindo esse raciocínio para cada escolha, obtemos uma solução ótima com a mesma decisão do algoritmo guloso.

Logo, o algoritmo retorna um conjunto válido com o menor número possível de paradas.

## 4. Implementação e Tempo de Execução

A implementação em [RallyGuloso.java](RallyGuloso.java) percorre a lista de pontos uma única vez. Em cada iteração, ela avança até o ponto mais distante ainda alcançável dentro do limite $d$.

Se os pontos já estiverem ordenados em ordem crescente, o tempo de execução é $O(n)$, pois cada ponto é visitado no máximo uma vez. O espaço auxiliar usado é $O(k)$ para armazenar as paradas escolhidas, onde $k$ é o número de pontos selecionados.

No exemplo do programa, com $L = 25$, $d = 10$ e pontos $\{5, 9, 13, 18, 24\}$, a saída é $[9, 18]$, que corresponde a uma solução válida com duas paradas.
