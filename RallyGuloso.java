import java.util*;

public class RallyGuloso {

    //trilha do rally de comprimento 𝐿
    //em um conjunto de pokntos a distância entre cada par adjacente é no máximo d
    public static List<Integer> rallyguloso (int L, int d, int[] pontos){

        int atual = 0; 
        int indice = 0; 

        List<Integer> paradas = new ArrayList<>(); // pontos de paradas 𝑥1, 𝑥2, … , 𝑥n do ponto de partida


        while (atual + d < L) {

            int ultimo = atual;

            while (indice < pontos.length && pontos[indice] <= atual + d) {

                ultimo = pontos[indice];
                indice++;

            }

            if (ultimo == atual) {
                return null; //n existe solucao
            }

            paradas.add(ultimo);
            atual = ultimo;
        }
    }
}