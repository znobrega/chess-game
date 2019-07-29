
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Interface Grafica de uma Casa no tabuleiro do jogo.
 *
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class CasaGUI extends JButton {

    // Constantes 
    public static final Color COR_CLARA = new Color(192, 217, 217);
    public static final Color COR_ESCURA = new Color(0,0,156);
    private static final Color COR_DESTAQUE = new Color(0, 1, 0, 0.4f);
    // �cones das pe�as
    private static final Icon PEDRA_BRANCA = new ImageIcon("pecas/damas/dama_branca_70x70.png");
    private static final Icon DAMA_BRANCA = new ImageIcon("pecas/damas/dama_branca_rainha_70x70.png");
    private static final Icon PEDRA_VERMELHA = new ImageIcon("pecas/damas/dama_vermelha_70x70.png");
    private static final Icon DAMA_VERMELHA = new ImageIcon("pecas/damas/dama_vermelha_rainha_70x70.png");
    private static final Icon KING_BLACK = new ImageIcon("pecas/damas/king_black.png");
    private static final Icon KING_WHITE = new ImageIcon("pecas/damas/king_white.png");
    private static final Icon QUEEN_BLACK = new ImageIcon("pecas/damas/queen_black.png");
    private static final Icon QUEEN_WHITE = new ImageIcon("pecas/damas/queen_white.png");
    private static final Icon TOWER_BLACK = new ImageIcon("pecas/damas/tower_black.png");
    private static final Icon TOWER_WHITE = new ImageIcon("pecas/damas/tower_white.png");
    private static final Icon BISHOP_BLACK = new ImageIcon("pecas/damas/bishop_black.png");
    private static final Icon BISHOP_WHITE = new ImageIcon("pecas/damas/bishop_white.png");
    private static final Icon PAWN_BLACK = new ImageIcon("pecas/damas/pawn_black.png");
    private static final Icon PAWN_WHITE = new ImageIcon("pecas/damas/pawn_white.png");
    private static final Icon KNIGHT_BLACK = new ImageIcon("pecas/damas/knight_black.png");
    private static final Icon KNIGHT_WHITE = new ImageIcon("pecas/damas/knight_white.png");
    // Cores das pe�as
    public static final int SEM_PECA = -1;
    public static final int PECA_BRANCA = 0;
    public static final int PECA_VERMELHA = 1;
    public static final int PECA_CHESS = 2;

    private int x;
    private int y;
    private Color cor;

    public CasaGUI(int x, int y, Color cor, TabuleiroGUI tabuleiro) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        setIcon(null);

        // Layout e cor
        setBackground(cor);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(cor, 1));
        setContentAreaFilled(false);

        addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabuleiro.getJanela().reagir((CasaGUI) e.getSource());
                }
            });
    }

    public int getPosicaoX() {
        return x;
    }

    public int getPosicaoY() {
        return y;
    }

    public void colocarPedraBranca() {
        setIcon(PEDRA_BRANCA);
    }

    public void colocarPedraVermelha() {
        setIcon(PEDRA_VERMELHA);
    }

    public void colocarDamaBranca() {
        setIcon(DAMA_BRANCA);
    }

    public void colocarDamaVermelha() {
        setIcon(DAMA_VERMELHA);
    }

    public void colocarKingBlack() {
        setIcon(KING_BLACK);
    }

    public void colocarKingWhite() {
        setIcon(KING_WHITE);
    }

    public void colocarQueenBlack() {
        setIcon(QUEEN_BLACK);
    }

    public void colocarQueenWhite() {
        setIcon(QUEEN_WHITE);
    }

    public void colocarTowerBlack() {
        setIcon(TOWER_BLACK);
    }

    public void colocarTowerWhite() {
        setIcon(TOWER_WHITE);
    }

    public void colocarBishopBlack() {
        setIcon(BISHOP_BLACK);
    }

    public void colocarBishopWhite() {
        setIcon(BISHOP_WHITE);
    }

    public void colocarKnightBlack() {
        setIcon(KNIGHT_BLACK);
    }

    public void colocarKnightWhite() {
        setIcon(KNIGHT_WHITE);
    }

    public void colocarPawnBlack() {
        setIcon(PAWN_BLACK);
    }

    public void colocarPawnWhite() {
        setIcon(PAWN_WHITE);
    }

    public void removerPeca() {
        setIcon(null);
    }

    public boolean possuiPeca() {
        return getIcon() != null;
    }

    public int getCorPeca() {
        Icon icone = getIcon();

        if (icone == PEDRA_BRANCA || icone == DAMA_BRANCA) {
            return PECA_BRANCA;
        }
        else if (icone == PEDRA_VERMELHA || icone == DAMA_VERMELHA) {
            return PECA_VERMELHA;
        }
        else if (icone == KING_BLACK){
            return PECA_CHESS;
        }
        else {
            return SEM_PECA;
        }
    }

    public void destacar() {
        setBackground(COR_DESTAQUE);
    }

    public void atenuar() {
        setBackground(cor);
    }

    /**
     * Pinta o componente com a cor de fundo, aceita valores RGBA
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
