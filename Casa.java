
/**
 * Representa uma Casa do tabuleiro.
 * Possui uma posi�ao (i,j) e pode conter uma Pe�a.
 * 
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Casa {

    private Tabuleiro tabuleiro;
    private int i;
    private int j;
    private Peca peca;

    public Casa(Tabuleiro tabuleiro , int i, int j) {
        this.tabuleiro = tabuleiro;
        this.i = i;
        this.j = j;
        this.peca = null;
    }

    /**
     * @param peca a Pe�a a ser posicionada nesta Casa.
     */
    public void colocarPeca(Peca peca) {
        this.peca = peca;
    }

    /**
     * Remove a peca posicionada nesta casa, se houver.
     */
    public void removerPeca() {
        peca = null;
    }

    /**
     * @return a Peca posicionada nesta Casa, ou Null se a casa estiver livre.
     */
    public Peca getPeca() {
        return peca;
    }
    
    /**
     * @return true se existe uma pe�a nesta casa, caso contrario false.
     */
    public boolean possuiPeca() {
        return peca != null;
    }

    public Tabuleiro getTabuleiroCasa(){
        return tabuleiro;
    }

    public int getX(){
        return i;
    }

    public int getY(){
        return j;
    }

    public boolean estaVazia() {
        return peca == null;
    }
}
