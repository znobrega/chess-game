import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Write a description of class Pawn here.
 *
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Pawn extends Peca{
    private Casa initCasa;
    private int direction;
    private Casa enPassant;
    private boolean passant;
    public boolean passanted;
    private int turnPassant;

    public Pawn(Casa casa, int tipo, String cor)
    {
        super(casa, tipo, cor);
        initCasa = casa;
        passant = false;
        passanted = false;
        turnPassant = 0;
        if(cor == "Black"){
            direction = -1;
        } else {
            direction = 1;
        }
    }

    public Casa getInitCasa(){
        return initCasa;
    }

    @Override
    public void mover(Casa newCasa) {
        int newX = newCasa.getX();
        int newY = newCasa.getY();
        int nowX = casa.getX();
        int nowY = casa.getY();
        Tabuleiro board = casa.getTabuleiroCasa();
        Peca peca = board.getCasa(nowX, nowY).getPeca();

        if(casa == initCasa){
            if(((nowY == newY && (((newX-(2*direction)) == nowX) && board.getCasa(nowX+direction,nowY).estaVazia())
                    && newCasa.estaVazia()))) {
                casa.removerPeca();
                newCasa.colocarPeca(this);                                
                casa = newCasa;    

                if((board.getCasa(newX, newY+1) != null && !board.getCasa(newX, newY+1).estaVazia() && 
                    ((board.getCasa(newX, newY+1).getPeca().getTipo() == 14 && super.tipo == 15) || board.getCasa(newX, newY+1).getPeca().getTipo() == 15 && super.tipo == 14))                    
                ||
                (board.getCasa(newX, newY-1) != null && !board.getCasa(newX, newY-1).estaVazia() && 
                    ((board.getCasa(newX, newY-1).getPeca().getTipo() == 14 && super.tipo == 15) 
                        || board.getCasa(newX, newY-1).getPeca().getTipo() == 15 && super.tipo == 14))) {
                    
                    passant = true;
                    turnPassant = board.getJogo().getCountTurn();
                }

            } else if(nowY == newY && newX-direction == nowX && newCasa.estaVazia()) {
                casa.removerPeca();
                newCasa.colocarPeca(this);
                casa = newCasa;    
            }
        } else {
            if((nowY == newY && newX-direction == nowX && newCasa.estaVazia())) {
                casa.removerPeca();
                newCasa.colocarPeca(this);
                casa = newCasa;
            }
        }
        eatDirection(direction, newX, newY, newCasa, board);
        finalCasa(newCasa);        
    }

    public void eatDirection(int direction, int newX, int newY, Casa newCasa, Tabuleiro board) {          
        if( (newX-direction) == casa.getX() && (newY-1 == casa.getY() || newY+1 == casa.getY()) 
        &&  (!newCasa.estaVazia()) && (newCasa.getPeca().getCor() != cor)) {
            casa.removerPeca();
            newCasa.colocarPeca(this);
            casa = newCasa; 
        } else if(cor == "White" && casa.getX() == 4) {
            Pawn[] blacksPawn = board.getJogo().getBlackPawn();
            //Casa direita
            Casa rightCasa = board.getCasa(4, casa.getY()+1);
            //Casa esquerda
            Casa leftCasa = board.getCasa(4, casa.getY()-1);
            //Casa final direita
            Casa finalCasaRight = board.getCasa(5, casa.getY()+1);
            //Casa final direita
            Casa finalCasaLeft = board.getCasa(5, casa.getY()-1);

            if(rightCasa != null && !rightCasa.estaVazia() && finalCasaRight.estaVazia()
            && newY == finalCasaRight.getY() && newX == finalCasaRight.getX() && rightCasa.getPeca().getTipo() == 14
            && blacksPawn[finalCasaRight.getY()].getPassant() && blacksPawn[finalCasaRight.getY()].turnPassant == board.getJogo().getCountTurn()-1) {
                casa.removerPeca();
                rightCasa.removerPeca();
                finalCasaRight.colocarPeca(this);
                casa = finalCasaRight;
                passanted = true;
            } else if(leftCasa != null && !leftCasa.estaVazia() && finalCasaLeft.estaVazia() 
            && newY == finalCasaLeft.getY() && newX == finalCasaLeft.getX() && leftCasa.getPeca().getTipo() == 14
            && blacksPawn[finalCasaLeft.getY()].getPassant() && blacksPawn[finalCasaLeft.getY()].turnPassant == board.getJogo().getCountTurn()-1) {
                casa.removerPeca();
                leftCasa.removerPeca();
                finalCasaLeft.colocarPeca(this);
                casa = finalCasaLeft;
                passanted = true;
            } 
            else {
                //System.out.println("Nem entrou no negocio chefia");
            }
        }
        else if(cor == "Black" && casa.getX() == 3 ) {
            Pawn[] whitesPawn = board.getJogo().getWhitePawn();            
            //Casa direita
            Casa rightCasa = board.getCasa(3, casa.getY()+1);
            //Casa esquerda
            Casa leftCasa = board.getCasa(3, casa.getY()-1);
            //Casa final direita
            Casa finalCasaRight = board.getCasa(2, casa.getY()+1);
            //Casa final direita
            Casa finalCasaLeft = board.getCasa(2, casa.getY()-1);

            if(rightCasa != null && !rightCasa.estaVazia() && finalCasaRight.estaVazia()
            && newY == finalCasaRight.getY() && newX == finalCasaRight.getX() && rightCasa.getPeca().getTipo() == 15
            && whitesPawn[finalCasaRight.getY()].getPassant() && whitesPawn[finalCasaRight.getY()].turnPassant == board.getJogo().getCountTurn()-1) {
                casa.removerPeca();
                rightCasa.removerPeca();
                finalCasaRight.colocarPeca(this);
                casa = finalCasaRight;
                passanted = true;
            } else if(leftCasa != null && !leftCasa.estaVazia() && finalCasaLeft.estaVazia() 
            && newY == finalCasaLeft.getY()  && newX == finalCasaLeft.getX() && leftCasa.getPeca().getTipo() == 15
            && whitesPawn[finalCasaLeft.getY()].getPassant() && whitesPawn[finalCasaLeft.getY()].turnPassant == board.getJogo().getCountTurn()-1) {
                casa.removerPeca();
                leftCasa.removerPeca();
                finalCasaLeft.colocarPeca(this);
                casa = finalCasaLeft;
                passanted = true;
            } 
            else {
                //System.out.println("Nem entrou no negocio chefia");
            }
        }
    }

    public void finalCasa(Casa newCasa){
        if((newCasa.getX() == 7 || newCasa.getX() == 0) && newCasa.getPeca() == this) {
            String promotionPawn = JOptionPane.showInputDialog("Escolha uma das peças: Queen, Rook, Bishop or Knight");
            while(promotionPawn == null) {
                promotionPawn = JOptionPane.showInputDialog("Escolha uma das peças: Queen, Rook, Bishop or Knight");
            }
            String changePeca = promotionPawn.trim().toLowerCase();
            //System.out.println(changePeca);

            if(changePeca.equals("queen") && cor == "White") {
                newCasa.removerPeca();
                Queen newQueen = new Queen(newCasa, Peca.QUEEN_WHITE, "White");      
            } 
            else if(changePeca.equals("queen") && cor == "Black") {
                newCasa.removerPeca();
                Queen newQueen = new Queen(newCasa, Peca.QUEEN_BLACK, "Black");   
            } 
            else if(changePeca.equals("rook") && cor == "White") {
                newCasa.removerPeca();
                Rook newRook = new Rook(newCasa, Peca.TOWER_WHITE, "White");   
            }
            else if(changePeca.equals("rook") && cor == "Black") {
                newCasa.removerPeca();
                Rook newRook = new Rook(newCasa, Peca.TOWER_BLACK, "Black");   
            } 
            else if (changePeca.equals("bishop") && cor == "White") {
                newCasa.removerPeca();
                Bishop newBishop = new Bishop(newCasa, Peca.BISHOP_BLACK, "White");  
            } 
            else if(changePeca.equals("bishop") && cor == "Black") {
                newCasa.removerPeca();
                Bishop newBishop = new Bishop(newCasa, Peca.BISHOP_BLACK, "Black");   
            }
            else if(changePeca.equals("knight") && cor == "White") {
                newCasa.removerPeca();
                Knight newKnight = new Knight(newCasa, Peca.KNIGHT_WHITE, "White");   
            } 
            else if (changePeca.equals("knight") && cor == "Black") {
                newCasa.removerPeca();
                Knight newKnight = new Knight(newCasa, Peca.KNIGHT_BLACK, "Black");  
            }
            else {
                finalCasa(newCasa);
            }
        }
    }

    public void checkEatPawnWhite(Jogo jogo) {

        if(casa.getPeca() == this){
            Tabuleiro board = jogo.getTabuleiro();

            Casa casaRight = board.getCasa(casa.getX()+1, casa.getY()+1);
            Casa casaLeft = board.getCasa(casa.getX()+1, casa.getY()-1);

            if(casaRight != null && !jogo.casasCheckWhite.contains(casaRight) ) {
                jogo.casasCheckWhite.add(casaRight);
            }

            if(casaLeft != null && !jogo.casasCheckWhite.contains(casaLeft)) {
                jogo.casasCheckWhite.add(casaLeft);
            }
        }
    }

    public void checkEatPawnBlack(Jogo jogo) {
        if(casa.getPeca() == this){
            Tabuleiro board = jogo.getTabuleiro();

            Casa casaRight = board.getCasa(casa.getX()-1, casa.getY()+1);
            Casa casaLeft = board.getCasa(casa.getX()-1, casa.getY()-1);

            if(casaRight != null && !jogo.casasCheckBlack.contains(casaRight)) {
                jogo.casasCheckBlack.add(casaRight);
            }

            if(casaLeft != null && !jogo.casasCheckBlack.contains(casaLeft)) {
                jogo.casasCheckBlack.add(casaLeft);
            }

        }
    }

    public boolean getPassant() {
        return passant; 
    }

    public int getDirection(){
        return direction;
    }
    
    public int getTurnPassant() {
        return turnPassant;
    }
}
