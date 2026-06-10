# T2-GreedyExercise

## 1. O Problema

### Contexto

Um rally no deserto requer planejamento cuidadoso. A equipe pode viajar apenas durante o dia e deve acampar em pontos de parada seguros identificados no mapa. O desafio é minimizar o número de paradas necessárias para completar a jornada.

### Formalização

Modelamos a trilha como um segmento de linha de comprimento $L$. O carro consegue percorrer no máximo $d$ quilômetros entre amanhecer e anoitecer. Existem $n$ pontos de parada candidatos em posições $x_1, x_2, \ldots, x_n$ (todas em ordem crescente) medidas desde o ponto de partida.

### Definição de Solução Válida

Um conjunto de paradas $\{s_1, s_2, \ldots, s_k\}$ é **válido** se:

- A primeira parada $s_1$ satisfaz $s_1 \leq d$ (alcançável no primeiro dia)
- Para todo par consecutivo, $s_{i+1} - s_i \leq d$ para $i = 1, 2, \ldots, k-1$ (distância máxima entre paradas)
- A última parada satisfaz $L - s_k \leq d$ (é possível alcançar o final)

### Objetivo

Encontrar um conjunto de paradas válido com o **menor número possível de elementos**. Equivalentemente, minimizar o número de noites acampadas para completar a trilha.

## 2. O Algoritmo

### Estratégia Gulosa (Greedy)

O algoritmo proposto emprega uma estratégia gulosa: **em cada etapa, avançamos para o ponto de parada mais distante ainda alcançável dentro do limite de distância $d$**.

### Pseudocódigo

```
algoritmo RALLYGULOSO(L, d, pontos[])
    posicao_atual ← 0
    paradas ← lista vazia
    
    enquanto posicao_atual + d < L:
        ponto_mais_distante ← posicao_atual
        
        para cada ponto em pontos:
            se ponto ≤ posicao_atual + d:
                ponto_mais_distante ← ponto
            senão:
                quebra
        
        se ponto_mais_distante = posicao_atual:
            retorna IMPOSSÍVEL  // nenhum ponto alcançável
        
        paradas.adiciona(ponto_mais_distante)
        posicao_atual ← ponto_mais_distante
    
    retorna paradas
```

### Intuição

A ideia fundamental é que adiar uma parada nunca piora a solução. Se conseguimos alcançar um ponto $p_1$ e depois um ponto $p_2$ onde $p_1 < p_2 \leq posicao\_atual + d$, é sempre melhor parar em $p_2$, pois maximizamos o progresso e deixamos mais espaço para o próximo dia.

## 3. Análise do Algoritmo

### Teorema

O algoritmo guloso encontra uma solução ótima, ou seja, uma solução com o menor número de paradas possível.

### Demonstração (Argumento de Troca)

**Lema 1:** Se existe uma solução válida, o algoritmo guloso encontra uma.

*Prova:* Se nenhuma parada é alcançável em alguma etapa, isso significa que não há ponto candidato no intervalo $[posicao\_atual, posicao\_atual + d]$. Nesse caso, nenhuma solução é possível (nem mesmo usando todos os $n$ pontos). Caso contrário, há sempre uma parada alcançável e o algoritmo continua até alcançar o fim.

**Lema 2:** A solução do algoritmo guloso é ótima.

*Prova:* Suponha que exista uma solução ótima $S^*$ diferente da solução $S_{greedy}$ retornada pelo algoritmo. Seja $i$ o primeiro índice onde as duas divergem, ou seja, $S^*[1..i-1] = S_{greedy}[1..i-1]$ mas $S^*[i] \neq S_{greedy}[i]$.

Neste ponto, ambas partiram da mesma posição $p = S_{greedy}[i-1]$ (ou do início se $i=1$). O algoritmo guloso escolheu $S_{greedy}[i]$ como o ponto mais distante alcançável, enquanto $S^*$ escolheu $S^*[i]$ onde $S^*[i] < S_{greedy}[i]$.

Como ambas as soluções são válidas, $S^*[i]$ está no intervalo $[p, p+d]$ e $S_{greedy}[i]$ também. Mas como $S_{greedy}[i]$ é o **máximo** neste intervalo, temos $S_{greedy}[i] \geq S^*[i]$.

Substituímos $S^*[i]$ por $S_{greedy}[i]$ em $S^*$. A nova solução permanece válida porque:
- A distância entre $S^*[i-1] = p$ e $S_{greedy}[i]$ é no máximo $d$ ✓
- A distância entre $S_{greedy}[i]$ e $S^*[i+1]$ não diminui (apenas aumenta), logo continua $\leq d$ ✓

Continuando este argumento para todos os índices onde há divergência, transformamos $S^*$ em $S_{greedy}$ mantendo validade e otimalidade. Portanto, $S_{greedy}$ é ótima. ∎

### Corolário

A escolha gulosa (sempre avançar o máximo possível) é localmente ótima e resulta em uma solução globalmente ótima.

## 4. Implementação e Complexidade

### Descrição da Implementação

O arquivo [RallyGuloso.java](RallyGuloso.java) implementa o algoritmo descrito acima. O método `rallyguloso(int L, int d, int[] pontos)` recebe:

- `L`: comprimento total da trilha
- `d`: distância máxima viajável por dia
- `pontos[]`: array de posições de parada (assumido estar em ordem crescente)

### Análise de Complexidade

**Tempo:** $O(n)$
- O array `pontos` é percorrido uma única vez (variável `indice` nunca volta)
- Cada elemento é processado no máximo uma vez
- As operações dentro do loop são $O(1)$

**Espaço:** $O(k)$
- A lista `paradas` armazena as $k$ paradas selecionadas
- Nenhuma estrutura auxiliar proporcional a $n$ é criada

### Exemplo de Execução

Com os parâmetros do programa:
- $L = 25$ (comprimento da trilha)
- $d = 10$ (distância máxima por dia)  
- Pontos = $\{5, 9, 13, 18, 24\}$

**Passo-a-passo:**

| Etapa | Posição | Alcance | Pontos Alcançáveis | Escolha | Motivo |
|-------|---------|---------|-------------------|---------|--------|
| 0 | 0 | [0, 10] | {5, 9} | 9 | Ponto mais distante no alcance |
| 1 | 9 | [9, 19] | {13, 18} | 18 | Ponto mais distante no alcance |
| 2 | 18 | [18, 28] | {24} | — | Distância restante: $25-18=7 \leq 10$ ✓ |

**Resultado:** Paradas = $[9, 18]$ com **2 paradas**.

Esta é uma solução válida pois:
- Primeira parada: $9 \leq 10$ ✓
- Diferença: $18 - 9 = 9 \leq 10$ ✓  
- Final: $25 - 18 = 7 \leq 10$ ✓

### Execução do Programa

```bash
javac RallyGuloso.java
java RallyGuloso
```

**Saída esperada:**
```
Paradas: [9, 18]
Quantidade de paradas: 2
```

### Validação

Para entradas inválidas (sem solução possível), o programa retorna `null` e exibe "Não existe solução."
