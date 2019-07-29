
/**
 * Write a description of class Queen here.
 *
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Queen extends Peca
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Queen
     */
    public Queen(Casa casa, int tipo, String cor)
    {
        super(casa, tipo, cor);
    }
    
    public void mover(Casa newCasa){
        
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

        }
        else if(newX<nowX && nowY == newY && newX>=rookFinal[0] 
        ||     (newX>nowX && nowY == newY && newX<=rookFinal[1]) ) {
            nowCasa.removerPeca();
            newCasa.colocarPeca(this);
            casa = newCasa;  

        } 
        else {
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

                if(!board.getCasa(bex,bey).estaVazia()){ 
                    if(board.getCasa(bex,bey).getPeca().getTipo() != tipo){
                        baixoEsquerdaX = bex;
                        baixoEsquerdaY = bey;

                        //ALTERACAO
                        if((newX == bex && newY == bey)
                        && (newX >= baixoEsquerdaX && newY >= baixoEsquerdaY)){
                            casa.removerPeca();
                            newCasa.colocarPeca(this);
                            casa = newCasa;  
                            break;                
                        } 
                    } 
                    else {
                        baixoEsquerdaX = bex+1;
                        baixoEsquerdaY = bey+1;
                        //break;
                    }
                    break;
                } 
                else {
                    baixoEsquerdaX = bex;
                    baixoEsquerdaY = bey;                    
                }

                //ALTERACAO
                if((newX == bex && newY == bey)
                && (newX >= baixoEsquerdaX && newY >= baixoEsquerdaY)){
                    casa.removerPeca();
                    newCasa.colocarPeca(this);
                    casa = newCasa;  
                    break;                
                } 
                bey--;    
            } 

            //DIAGONAL BAIXO DIREITA
            int bdy = yOrigem+1;
            for(int bdx = xOrigem-1; bdx >= 0; bdx--) {
                if(bdy > 7) {
                    break;
                }

                if(!board.getCasa(bdx,bdy).estaVazia()){ 
                    if(board.getCasa(bdx,bdy).getPeca().getTipo() != tipo){
                        baixoDireitaX  = bdx;
                        baixoDireitaY  = bdy;

                        if((newX == bdx && newY == bdy) 
                        && (newX >= baixoDireitaX && newY <= baixoDireitaY)){
                            casa.removerPeca();
                            newCasa.colocarPeca(this);
                            casa = newCasa;
                            break;
                        } 

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

                if((newX == bdx && newY == bdy) 
                && (newX >= baixoDireitaX && newY <= baixoDireitaY)){
                    casa.removerPeca();
                    newCasa.colocarPeca(this);
                    casa = newCasa;
                    break;
                } 
                bdy++;
            }   

            //DIAGONAL CIMA ESQUERDA
            int cey = yOrigem-1;
            for(int cex = xOrigem+1; cex <= 7; cex++) {
                if(cey < 0) {
                    break;
                }

                if(!board.getCasa(cex,cey).estaVazia()){ 
                    if(board.getCasa(cex,cey).getPeca().getTipo() != tipo){
                        cimaEsquerdaX =  cex;
                        cimaEsquerdaY =  cey;

                        //ALTERACAO
                        if((newX == cex && newY == cey)
                        && (newX <= cimaEsquerdaX && newY >= cimaEsquerdaY)){
                            casa.removerPeca();
                            newCasa.colocarPeca(this);
                            casa = newCasa;
                            break;
                        } 
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
                if((newX == cex && newY == cey)
                && (newX <= cimaEsquerdaX && newY >= cimaEsquerdaY)){
                    casa.removerPeca();
                    newCasa.colocarPeca(this);
                    casa = newCasa;
                    break;  } 
                cey--;
            }    

            //DIAGONAL CIMA DIREITA
            int cdy = yOrigem+1;
            for(int cdx = xOrigem+1; cdx <= 7; cdx++) {
                if(cdy > 7) {
                    break;
                }

                if(!board.getCasa(cdx, cdy).estaVazia()){ 
                    if(board.getCasa(cdx, cdy).getPeca().getTipo() != tipo){
                        cimaDireitaX = cdx;
                        cimaDireitaY = cdy;

                        //ALTERACAO
                        if((newX == cdx && newY == cdy) 
                        && (newX <= cimaDireitaX && newY <= cimaDireitaY)){
                            casa.removerPeca();
                            newCasa.colocarPeca(this);
                            casa = newCasa; 
                            break;
                        }             

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
                //ALTERACAO
                if((newX == cdx && newY == cdy) 
                && (newX <= cimaDireitaX && newY <= cimaDireitaY)){
                    casa.removerPeca();
                    newCasa.colocarPeca(this);
                    casa = newCasa; 
                    break;
                }         
                cdy++;
            }

            int[] position = new int[8];
            position[0] = baixoEsquerdaX;
            position[1] = baixoEsquerdaY;

            position[2] = baixoDireitaX;
            position[3] = baixoDireitaY;

            position[4] = cimaEsquerdaX;
            position[5] = cimaEsquerdaY;

            position[6] = cimaDireitaX;
            position[7] = cimaDireitaY;

            //return position;
        }
        
    }
    
}
