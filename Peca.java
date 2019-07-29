
/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public abstract class Peca {


    public static final int KING_BLACK = 4;
    public static final int KING_WHITE = 5;
    public static final int QUEEN_BLACK = 6;
    public static final int QUEEN_WHITE = 7;
    public static final int TOWER_BLACK = 8;
    public static final int TOWER_WHITE = 9;
    public static final int BISHOP_BLACK = 10;
    public static final int BISHOP_WHITE = 11;
    public static final int KNIGHT_BLACK = 12;
    public static final int KNIGHT_WHITE = 13;
    public static final int PAWN_BLACK = 14;
    public static final int PAWN_WHITE = 15;

    public Casa casa;
    public int tipo;
    public String cor;

    public Peca(Casa casa, int tipo, String cor) {
        this.casa = casa;
        this.tipo = tipo;
        this.cor = cor;
        casa.colocarPeca(this);
    }

    public abstract void mover(Casa newCasa);

    /**
     * Movimenta a pe�a para uma nova casa.
     * @param destino nova casa que ira conter esta pe�a.
     */
    //public abstract void mover(Casa newCasa);
    //public abstract boolean checkMover();
    //public abstract void eat();
    //public abstract boolean checkEat();

    public int[] checkLines() {
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

            if(cor == "White" && casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(b,y))) {
                    jogo.casasCheckWhite.add(board.getCasa(b,y));
                }
            }
            else if (cor == "Black" && casa.getPeca() == this) {
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
            if(cor == "White" && casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(c,y))) {
                    jogo.casasCheckWhite.add(board.getCasa(c,y));
                } 
            }
            else if (cor == "Black" && casa.getPeca() == this) {
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
            if(cor == "White" && casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(x,d))) {
                    jogo.casasCheckWhite.add(board.getCasa(x,d));
                }
            }
            else if (cor == "Black" && casa.getPeca() == this) {
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
                if(cor == "White" && casa.getPeca() == this) {
                    if(board.getCasa(x,e) != null && !jogo.casasCheckWhite.contains(board.getCasa(x,e))) {
                        jogo.casasCheckWhite.add(board.getCasa(x,e));
                    }  
                } 
                else if (cor == "Black" && casa.getPeca() == this) {
                    if(board.getCasa(x,e) != null &&!jogo.casasCheckBlack.contains(board.getCasa(x,e))) {
                        jogo.casasCheckBlack.add(board.getCasa(x,e));
                    }
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
        
        return position;
    }

    public void checkMoveDiagonals(Jogo jogo) {
        Tabuleiro board = casa.getTabuleiroCasa();
        int xOrigem = casa.getX();
        int yOrigem = casa.getY();

        int baixoEsquerdaX = 0;
        int baixoEsquerdaY = 0;

        int baixoDireitaX  = 0;
        int baixoDireitaY  = 7;

        int cimaEsquerdaX =  7;
        int cimaEsquerdaY =  0;

        int cimaDireitaX = 7;
        int cimaDireitaY = 7;

        //DIAGONAL BAIXO ESQUERDA
        int bey = yOrigem-1;
        for(int bex = xOrigem-1; bex >= 0; bex--) {
            if(bey < 0) {
                break;
            }

            if(cor == "White" && casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(bex,bey))) {
                    jogo.casasCheckWhite.add(board.getCasa(bex,bey));
                }              
            } else if (cor == "Black" && casa.getPeca() == this) {
                if(!jogo.casasCheckBlack.contains(board.getCasa(bex,bey))) {
                    jogo.casasCheckBlack.add(board.getCasa(bex,bey));
                }
            }

            if(!board.getCasa(bex,bey).estaVazia()){ 
                if(board.getCasa(bex,bey).getPeca().getTipo() != tipo){
                    baixoEsquerdaX = bex;
                    baixoEsquerdaY = bey;
                    break;                
                }  
                else {
                    baixoEsquerdaX = bex+1;
                    baixoEsquerdaY = bey+1;
                }
                break;
            } 
            else {
                baixoEsquerdaX = bex;
                baixoEsquerdaY = bey;                    
            }
            bey--; 

        } 

        //DIAGONAL BAIXO DIREITA
        int bdy = yOrigem+1;
        for(int bdx = xOrigem-1; bdx >= 0; bdx--) {
            if(bdy > 7) {
                break;
            }

            if(cor == "White" && casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(bdx, bdy))) {
                    jogo.casasCheckWhite.add(board.getCasa(bdx, bdy));
                } 
            } else if (cor == "Black" && casa.getPeca() == this) {
                if(!jogo.casasCheckBlack.contains(board.getCasa(bdx, bdy))) {
                    jogo.casasCheckBlack.add(board.getCasa(bdx, bdy));
                } 
            }

            if(!board.getCasa(bdx,bdy).estaVazia()){ 
                if(board.getCasa(bdx,bdy).getPeca().getTipo() != tipo){
                    baixoDireitaX  = bdx;
                    baixoDireitaY  = bdy;
                    break;
                }                  
                else {
                    baixoDireitaX  = bdx+1;
                    baixoDireitaY  = bdy-1;
                }
                break;
            }
            else {
                baixoDireitaX  = bdx;
                baixoDireitaY  = bdy;                    
            }

            bdy++;
        }  

        //DIAGONAL CIMA ESQUERDA
        int cey = yOrigem-1;
        for(int cex = xOrigem+1; cex <= 7; cex++) {
            if(cey < 0) {
                break;
            }

            if(cor == "White"&& casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(cex, cey))) {
                    jogo.casasCheckWhite.add(board.getCasa(cex, cey));
                } 
            } else if(cor == "Black" && casa.getPeca() == this) {
                if(!jogo.casasCheckBlack.contains(board.getCasa(cex, cey))) {
                    jogo.casasCheckBlack.add(board.getCasa(cex, cey));
                }             
            }

            if(!board.getCasa(cex,cey).estaVazia()){ 
                if(board.getCasa(cex,cey).getPeca().getTipo() != tipo){
                    cimaEsquerdaX =  cex;
                    cimaEsquerdaY =  cey;
                    break;                    
                } 
                else {
                    cimaEsquerdaX =  cex-1;
                    cimaEsquerdaY =  cey+1;
                }
                break;
            }
            else {
                cimaEsquerdaX =  cex;
                cimaEsquerdaY =  cey;                    
            }
            //ALTERACAO
            //break
            cey--;
        }    

        //DIAGONAL CIMA DIREITA
        int cdy = yOrigem+1;
        for(int cdx = xOrigem+1; cdx <= 7; cdx++) {
            if(cdy > 7) {
                break;
            }

            if(cor == "White" && casa.getPeca() == this) {
                if(!jogo.casasCheckWhite.contains(board.getCasa(cdx, cdy))) {
                    jogo.casasCheckWhite.add(board.getCasa(cdx, cdy));
                } 
            }  else if (cor == "Black" && casa.getPeca() == this) {
                if(!jogo.casasCheckBlack.contains(board.getCasa(cdx, cdy))) {
                    jogo.casasCheckBlack.add(board.getCasa(cdx, cdy));
                }
            }

            if(!board.getCasa(cdx, cdy).estaVazia()){ 
                if(board.getCasa(cdx, cdy).getPeca().getTipo() != tipo){
                    cimaDireitaX = cdx;
                    cimaDireitaY = cdy;
                    break;         
                } 
                else {
                    cimaDireitaX = cdx-1;
                    cimaDireitaY = cdy-1;
                    //break;
                }
                break;
                //casa.getTabuleiro().getJogo().getJanela().destacar();
            }
            else {
                cimaDireitaX = cdx;
                cimaDireitaY = cdy;                  
            } 
            cdy++;
        }
    }

    /**
     * Valor    Tipo
     *   0   Branca (Pedra)
     *   1   Branca (Dama)
     *   2   Vermelha (Pedra)
     *   3   Vermelha (Dama)
     * @return o tipo da peca.
     */
    public int getTipo() {
        return tipo;
    }

    public String getCor(){
        return cor;
    }    

    public Casa getCasaFromPeca(){
        return casa;
    }
}
