
/**
 * Write a description of class Knight here.
 *
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Knight extends Peca
{
    
    /**
     * Constructor for objects of class Knight
     */
    public Knight(Casa casa, int tipo, String cor)
    {
        super(casa, tipo, cor);
    }

    public void mover(Casa newCasa) {

        int newX = newCasa.getX();
        int newY = newCasa.getY();
        int nowX = casa.getX();
        int nowY = casa.getY();

        if(
        newX-2 == nowX && (newY-1 == nowY || newY+1 == nowY)
        ||  newY-2 == nowY && (newX-1 == nowX || newX+1 == nowX)
        ||  newX+2 == nowX && (newY-1 == nowY || newY+1 == nowY)
        ||  newY+2 == nowY && (newX-1 == nowX || newX+1 == nowX) 
        ) {

            casa.removerPeca();
            newCasa.colocarPeca(this);
            casa = newCasa;

        }

    }

    public void checkEatKnight(Jogo jogo) {
        Tabuleiro board = jogo.getTabuleiro();
        
        if(casa.getPeca() == this){
            Casa[] arrayPoss = {
    
                    //Casa casaUpRight =
                    board.getCasa(casa.getX()+2, casa.getY()+1),
                    //Casa casaUpLeft = 
                    board.getCasa(casa.getX()+2, casa.getY()-1),
    
                    //Casa casaRightUp =
                    board.getCasa(casa.getX()+1, casa.getY()+2),
                    //Casa casaRightDown = 
                    board.getCasa(casa.getX()-1, casa.getY()+2),
    
                    //Casa casaDownRight = 
                    board.getCasa(casa.getX()-2, casa.getY()+1),
                    //Casa casaDownLeft =
                    board.getCasa(casa.getX()-2, casa.getY()-1),    
    
                    //Casa casaLeftUp =
                    board.getCasa(casa.getX()+1, casa.getY()-2),
                    //Casa casaRightDown =
                    board.getCasa(casa.getX()-1, casa.getY()-2)
                };
    
            for(Casa checkCasa: arrayPoss) {
                if(cor == "White") {
                    if(checkCasa != null && !jogo.casasCheckWhite.contains(checkCasa) && casa.getPeca() != null) {
                        jogo.casasCheckWhite.add(checkCasa);
                    }
                } else if(cor == "Black") {                                               
                    if(checkCasa != null && !jogo.casasCheckBlack.contains(checkCasa) && casa.getPeca() != null) {
                        jogo.casasCheckBlack.add(checkCasa);
                    }
                }
            } 
        }
        
    }
}
