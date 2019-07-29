
/**
 * Write a description of class Rook here.
 *
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Rook extends Peca{

    public boolean initCasaRook; 
    /**
     * Constructor for objects of class Rook
     */
    public Rook(Casa casa, int tipo, String cor)
    {
        super(casa, tipo, cor);
        initCasaRook = true;
    }

    @Override
    public void mover(Casa newCasa) {
        int[] rookFinal = checkLines();

        Casa nowCasa = casa;
        int newX = newCasa.getX();
        int newY = newCasa.getY();
        int nowX = casa.getX();
        int nowY = casa.getY();

        if((newY>nowY && nowX == newX && newY<=rookFinal[2])
        || (newY<nowY && nowX == newX && newY>=rookFinal[3])) {
            nowCasa.removerPeca();
            newCasa.colocarPeca(this);
            casa = newCasa;
            initCasaRook = false;
        }
        else if(newX<nowX && nowY == newY && newX>=rookFinal[0] 
        ||     (newX>nowX && nowY == newY && newX<=rookFinal[1]) ) {
            nowCasa.removerPeca();
            newCasa.colocarPeca(this);
            casa = newCasa;  
            initCasaRook = false;
        } 
        else {
            //System.out.println("you cant move rook");
            //moveBishop();
        }
    }

    public int[] checkMoveRook() {
        Tabuleiro board = casa.getTabuleiroCasa();
        Jogo jogo = board.getJogo();
        int x = casa.getX();
        int y = casa.getY();
        int[] position = new int[4];
        position[0] = 0; //BAIXO
        position[1] = 7; //CIMA
        position[2] = 7; //DIREITA
        position[3] = 0; //ESQUERDA
        int p = 0;
        //BAIXO
        for (int b = x-1; b >= 0; b--) {

            if(cor == "White") {
                if(!jogo.casasCheckWhite.contains(board.getCasa(b,y))) {
                    jogo.casasCheckWhite.add(board.getCasa(b,y));
                }
            }
            else if (cor == "Black") {
                if(!jogo.casasCheckBlack.contains(board.getCasa(b,y))) {
                    jogo.casasCheckBlack.add(board.getCasa(b,y));
                }
            }

            if(!board.getCasa(b,y).estaVazia()){ 
                if(board.getCasa(b,y).getPeca().getCor() != cor){
                    position[p] = b;
                } else {
                    b++;
                    position[p] = b;
                }
                break;
            } 
        }  
        p++;

        //CIMA
        for (int c = x+1; c <= 7 ; c++) {
            if(cor == "White") {
                if(!jogo.casasCheckWhite.contains(board.getCasa(c,y))) {
                    jogo.casasCheckWhite.add(board.getCasa(c,y));
                } 
            }
            else if (cor == "Black") {
                if(!jogo.casasCheckBlack.contains(board.getCasa(c,y))) {
                    jogo.casasCheckBlack.add(board.getCasa(c,y));
                } 
            }

            if(!board.getCasa(c,y).estaVazia()){
                if(board.getCasa(c,y).getPeca().getCor() != cor) {
                    position[p] = c;
                } else {
                    c--;
                    position[p] = c;
                }
                break;
            }
        }
        p++;
        //DIREITA
        for (int d = y+1; d <= 7; d++) {
            if(cor == "White") {
                if(!jogo.casasCheckWhite.contains(board.getCasa(x,d))) {
                    jogo.casasCheckWhite.add(board.getCasa(x,d));
                }   
                else if (cor == "Black") {
                    if(!jogo.casasCheckBlack.contains(board.getCasa(x,d))) {
                        jogo.casasCheckBlack.add(board.getCasa(x,d));
                    } 
                }

                if(!board.getCasa(x,d).estaVazia()){ 
                    if( board.getCasa(x,d).getPeca().getCor() != cor){
                        position[p] = d;
                        // p++;

                    } else {
                        d--;
                        position[p] = d;
                    }
                    break;
                }
            }
            p++;
            //ESQUERDA 
            for (int e = y-1; e >= 0; e--) {

                if(!jogo.casasCheckWhite.contains(board.getCasa(x,e))) {
                    jogo.casasCheckWhite.add(board.getCasa(x,e));
                }            

                if(!board.getCasa(x,e).estaVazia()){ 
                    if( board.getCasa(x,e).getPeca().getCor() != cor){
                        position[p] = e;
                        // p++;

                    } else {
                        e++;
                        position[p] = e;
                    }
                    break;
                }

            }

        }
        return position;
    }

    public boolean getInitCasaRook() {
        return initCasaRook;
    }

    public Rook getRook() {
        return this;
    }

    public Casa getCasaRook() {
        return super.casa;
    }

    public void setCasaRook(Casa newCasa) {
        super.casa = newCasa;
    }
}
