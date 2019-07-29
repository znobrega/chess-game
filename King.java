
/**
 * Write a description of class King here.
 *
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class King extends Peca {
    // instance variables - replace the example below with your own
    private boolean initCasa;
    private boolean kingCheck;
    public boolean rooked;
    //torre posiçao final
    public Casa casaRookedPoint;
    //torre posiçao inicial
    public Casa casaRookedMid;

    /**
     * Constructor for objects of class King
     */
    public King(Casa casa, int tipo, String cor)
    {
        super(casa, tipo, cor);
        initCasa = true;
        rooked = false;
        casaRookedPoint = null;
        casaRookedMid = null;
    }
    
    @Override
    public void mover(Casa newCasa) {
        Tabuleiro board = casa.getTabuleiroCasa();
        Rook[] rooks = board.getJogo().getRooks();
        Jogo jogo = board.getJogo();
        //0 = white left
        //1 = white right
        //2 = black left
        //3 = black right
        int xOrigem = casa.getX();
        int yOrigem = casa.getY();

        int newX = newCasa.getX();
        int newY = newCasa.getY();

        if((jogo.casasCheckWhite.contains(newCasa) && tipo == 4) || (jogo.casasCheckBlack.contains(newCasa) && tipo == 5)) {
            kingCheck = true;
        } 
        else {
            kingCheck = false;
        }

        if(!kingCheck &&
        (newX-1 == xOrigem && (newY-1 == yOrigem || newY+1 == yOrigem)
            ||  newX+1 == xOrigem && (newY-1 == yOrigem || newY+1 == yOrigem)
            ||  newX == xOrigem && (newY-1 == yOrigem || newY+1 == yOrigem)
            ||  newY == yOrigem && (newX-1 == xOrigem || newX+1 == xOrigem))
        ) {
            if((newCasa.estaVazia() || (newCasa.getPeca().getTipo() != tipo)) && !kingCheck){
                casa.removerPeca();
                newCasa.colocarPeca(this);
                casa = newCasa;  
                initCasa = false;
            } 
            else {
                //System.out.println("cant move");
            }
        } 
        else if(!kingCheck && (newX ==  xOrigem && newY-2 == yOrigem) 
        && board.getCasa(newX, yOrigem+1).estaVazia() && board.getCasa(newX, yOrigem+2).estaVazia() && initCasa 
        && ((rooks[1].getInitCasaRook() && xOrigem == 0 ) || (rooks[3].getInitCasaRook() && xOrigem == 7)) ) {
            casa.removerPeca();
            board.getCasa(newX, yOrigem+2).colocarPeca(this);
            board.getCasa(newX, yOrigem+1).colocarPeca(board.getCasa(newX, yOrigem+3).getPeca());
            board.getCasa(newX, yOrigem+3).removerPeca(); 
            casa = board.getCasa(newX, yOrigem+2);

            if(xOrigem == 0){
                rooks[1].setCasaRook(board.getCasa(newX, yOrigem+1));
            } else if(xOrigem == 7) {
                rooks[3].setCasaRook(board.getCasa(newX, yOrigem+1));
            }
            initCasa = false;
            rooked = true;
        } 
        else if(!kingCheck && (newX == xOrigem && newY+2 == yOrigem)
        && board.getCasa(newX, yOrigem-1).estaVazia() && board.getCasa(newX, yOrigem-2).estaVazia() && board.getCasa(newX, yOrigem-3).estaVazia() && initCasa
        && ((rooks[0].getInitCasaRook() && xOrigem == 0) || (rooks[2].getInitCasaRook() && xOrigem == 7)) ){
            casa.removerPeca();
            board.getCasa(newX, yOrigem-1).colocarPeca(board.getCasa(newX, yOrigem-4).getPeca());
            board.getCasa(newX, yOrigem-2).colocarPeca(this);
            board.getCasa(newX, yOrigem-4).removerPeca();
            casa = board.getCasa(newX, yOrigem-2);

            if(xOrigem == 0){
                rooks[0].setCasaRook(board.getCasa(newX, yOrigem-1));
            } else if(xOrigem == 7) {
                rooks[2].setCasaRook(board.getCasa(newX, yOrigem-1));
            }
            initCasa = false;
            rooked = true;
        }
        else {
           // System.out.println("something wrogn");
        }      

    }

    public void checkEatKing(Jogo jogo) {
        Tabuleiro board = jogo.getTabuleiro();

        Casa[] arrayPoss = {
                //Casa casaDiagonalRightUp = 
                board.getCasa(casa.getX()+1, casa.getY()+1),
                //Casa casaDiagonalLeftUp = 
                board.getCasa(casa.getX()+1, casa.getY()-1),

                //Casa casaDiagonalRightDown = 
                board.getCasa(casa.getX()-1, casa.getY()+1),
                //Casa casaDiagonalLeftDown =
                board.getCasa(casa.getX()-1, casa.getY()-1),

                //Casa casaUp = 
                board.getCasa(casa.getX()+1, casa.getY()),
                //Casa casaDown = 
                board.getCasa(casa.getX()-1, casa.getY()),
                //Casa casaLeft =
                board.getCasa(casa.getX(), casa.getY()-1),
                //Casa casaRight = 
                board.getCasa(casa.getX(), casa.getY()+1)       
            };

        for(Casa checkCasa: arrayPoss) {
            if(cor == "White") {
                if(checkCasa != null && !jogo.casasCheckWhite.contains(checkCasa)) {
                    jogo.casasCheckWhite.add(checkCasa);
                }
            } else if(cor == "Black") {
                if(checkCasa != null && !jogo.casasCheckBlack.contains(checkCasa)) {
                    jogo.casasCheckBlack.add(checkCasa);
                }
            }
        }

    }
    
    public void setInitCasa(boolean initCasa) {
        this.initCasa = initCasa;
    }
}
