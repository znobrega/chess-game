
/**
 * O Tabuleiro do jogo. 
 * Responsavel por armazenar as 64 casas.
 * 
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Tabuleiro {
    private Jogo jogo;
    private Casa[][] casas;

    public Tabuleiro(Jogo jogo) {
        this.jogo = jogo;
        casas = new Casa[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casa = new Casa(this, i, j);
                casas[i][j] = casa;
            }
        }
    }

    /**
     * @param i linha
     * @param j coluna
     * @return Casa na posicao (i,j)
     */
    public Casa getCasa(int i, int j) {
        if(i < 0 || i > 7 || j < 0 || j > 7 ) {
            return null;
        }
        return casas[i][j];
    }

    public Jogo getJogo() {
        return jogo;
    }
        
}
