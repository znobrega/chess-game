import java.util.LinkedList;
import java.util.*;

/**
 * Armazena o tabuleiro e respons�vel por posicionar as pe�as.
 * 
 * @author José Carlos Alves da Nóbrega Júnior &lt;josenobrega@cc.ci.ufpb.br&gt;
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Jogo implements Cloneable {

    LinkedList<Casa> casasCheckWhite;
    LinkedList<Casa> casasCheckBlack;
    private Tabuleiro tabuleiro;
    //private Peca[] blacks;
    //private Peca[] whites;
    private Pawn[] whitesPawn;
    private Pawn[] blacksPawn;
    private Queen[] queens;
    private King[] kings;
    private Rook[] rooks;
    private Knight[] knights;
    private Bishop[] bishops;
    private boolean moveKingWhite;
    private boolean moveKingBlack;
    private boolean moveWhite;
    private boolean moveBlack;
    private boolean turn;
    private int deletedTipo;
    private int countTurn;
    private int passantTipo;

    public Jogo() {
        tabuleiro = new Tabuleiro(this);
        criarPecas();
        // casasCheck = new HashSet<Casa>();
        casasCheckWhite = new LinkedList<Casa>();
        casasCheckBlack = new LinkedList<Casa>();
        moveKingWhite = true;
        moveKingBlack = true;
        moveWhite = true;
        moveBlack = true;
        deletedTipo = 0;
        turn = true;
        countTurn = 0;
    }

    /**
     * Posiciona pe�as no tabuleiro.
     * Utilizado na inicializa�ao do jogo.
     */
    private void criarPecas() {
        whitesPawn = new Pawn[8];
        blacksPawn = new Pawn[8];
        queens = new Queen[2];
        kings = new King[2];        
        rooks = new Rook[4];
        knights = new Knight[4];
        bishops = new Bishop[4];

        //WHITES
        for(int i = 1; i < 2; i++) {
            for(int j = 0; j < 8; j++) {
                whitesPawn[j] = new Pawn(tabuleiro.getCasa(i,j), Peca.PAWN_WHITE, "White");
            }
        }
        rooks[0] = new Rook(tabuleiro.getCasa(0,0), Peca.TOWER_WHITE, "White");
        knights[0] = new Knight(tabuleiro.getCasa(0,1), Peca.KNIGHT_WHITE, "White");
        bishops[0] = new Bishop(tabuleiro.getCasa(0,2), Peca.BISHOP_WHITE, "White");
        queens[0] = new Queen(tabuleiro.getCasa(0,3), Peca.QUEEN_WHITE, "White");
        kings[0] = new King(tabuleiro.getCasa(0,4), Peca.KING_WHITE, "White");
        bishops[1] = new Bishop(tabuleiro.getCasa(0,5), Peca.BISHOP_WHITE, "White");
        knights[1] = new Knight(tabuleiro.getCasa(0,6), Peca.KNIGHT_WHITE, "White");
        rooks[1] = new Rook(tabuleiro.getCasa(0,7), Peca.TOWER_WHITE, "White"); 

        //BLACKS
        for(int i = 6; i < 7; i++) {
            for(int j = 0; j < 8; j++) {
                blacksPawn[j] = new Pawn(tabuleiro.getCasa(i,j), Peca.PAWN_BLACK, "Black");
            }
        }
        rooks[2] = new Rook(tabuleiro.getCasa(7,0), Peca.TOWER_BLACK, "Black");
        knights[2] = new Knight(tabuleiro.getCasa(7,1), Peca.KNIGHT_BLACK, "Black");
        bishops[2] = new Bishop(tabuleiro.getCasa(7,2), Peca.BISHOP_BLACK, "Black");
        kings[1] = new King(tabuleiro.getCasa(7,4), Peca.KING_BLACK, "Black");
        queens[1] = new Queen(tabuleiro.getCasa(7,3), Peca.QUEEN_BLACK, "Black");
        bishops[3] = new Bishop(tabuleiro.getCasa(7,5), Peca.BISHOP_BLACK, "Black");
        knights[3] = new Knight(tabuleiro.getCasa(7,6), Peca.KNIGHT_BLACK, "Black");
        rooks[3] = new Rook(tabuleiro.getCasa(7,7), Peca.TOWER_BLACK, "Black"); 
    }

    /**
     * Comanda uma Pe�a na posicao (origemX, origemY) fazer um movimento 
     * para (destinoX, destinoY).
     * 
     * @param origemX linha da Casa de origem.
     * @param origemY coluna da Casa de origem.
     * @param destinoX linha da Casa de destino.
     * @param destinoY coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {

        //casasCheckBlack.contains(kings[0].casa)
        checkKingSecurity(origemX, origemY, destinoX, destinoY);

        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        if(destino.possuiPeca() && destino.getPeca().getCor() != origem.getPeca().getCor() 
        && 
        ((moveWhite && origem.getPeca().getCor() == "White" && turn) || (moveBlack && origem.getPeca().getCor() == "Black" && !turn))){
            Peca peca = origem.getPeca();
            peca.mover(destino);
            if(peca.casa.getX() != origemX || peca.casa.getY() != origemY) {
                changeTurn();
            } 
        } 
        else if(destino.possuiPeca() && destino.getPeca().getCor() == origem.getPeca().getCor()){
            //System.out.println("Cant move");
        }
        else if ((moveWhite && origem.getPeca().getCor() == "White"  && turn) || (moveBlack && origem.getPeca().getCor() == "Black" && !turn)){
            Peca peca = origem.getPeca();
            peca.mover(destino);
            if(peca.casa.getX() != origemX || peca.casa.getY() != origemY) {
                changeTurn();
            } 
        }

        casasCheckWhite.clear();
        casasCheckBlack.clear();
        checkWhite();
        checkBlack(this);        
    }

    public Rook[] getRooks() {
        return rooks;
    }

    public Pawn[] getWhitePawn() {
        return whitesPawn;
    }

    public Pawn[] getBlackPawn() {
        return blacksPawn;
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro; 
    }

    public void checkWhite() {        
        //Verifing white pieces
        kings[0].checkEatKing(this);
        knights[0].checkEatKnight(this);
        knights[1].checkEatKnight(this);
        rooks[0].checkLines();       
        rooks[1].checkLines();  
        queens[0].checkLines();
        queens[0].checkMoveDiagonals(this);
        bishops[0].checkMoveDiagonals(this);
        bishops[1].checkMoveDiagonals(this);

        for(int j = 0; j < 8; j++) {
            whitesPawn[j].checkEatPawnWhite(this);
        }
    }

    public void checkBlack(Jogo jogo) {
        //Verifing white pieces
        kings[1].checkEatKing(this);
        knights[2].checkEatKnight(jogo);
        knights[3].checkEatKnight(jogo);
        rooks[2].checkLines();       
        rooks[3].checkLines();  
        queens[1].checkLines();
        queens[1].checkMoveDiagonals(this);
        bishops[2].checkMoveDiagonals(this);
        bishops[3].checkMoveDiagonals(this);

        for(int j = 0; j < 8; j++) {
            blacksPawn[j].checkEatPawnBlack(this);
        }
    }

    // @Override
    // public Jogo clone() {
    // try {
    // // call clone in Object.
    // return (Jogo) super.clone();
    // } catch (CloneNotSupportedException e) {
    // System.out.println (" Cloning not allowed. " );
    // return this;
    // }
    // }

    public Jogo clone(Jogo a){
        Jogo novo = new Jogo();
        novo.setTabuleiro(a.getTabuleiro());
        //E assim por diante para todas as propriedades.
        return novo;
    }

    public void relocatePeca(Casa destinyCasa) {
        switch(deletedTipo) {
            case 0: 
            break;
            case 6: 
            Queen changeQueenBlack = new Queen(destinyCasa, deletedTipo, "Black");
            break;
            case 7: 
            Queen changeQueenWhite = new Queen(destinyCasa, deletedTipo, "White");
            break;
            case 8: 
            Rook changeRookBlack = new Rook(destinyCasa, deletedTipo, "Black");
            break;
            case 9: 
            Rook changeRookWhite = new Rook(destinyCasa, deletedTipo, "White");
            break;
            case 10: 
            Bishop changeBishopBlack = new Bishop(destinyCasa, deletedTipo, "Black");
            break;
            case 11: 
            Bishop changeBishopWhite = new Bishop(destinyCasa, deletedTipo, "White");
            break;
            case 12: 
            Knight changeKnightBlack = new Knight(destinyCasa, deletedTipo, "Black");
            break;
            case 13: 
            Knight changeKnightWhite = new Knight(destinyCasa, deletedTipo, "White");
            break; 
            case 14: 
            Pawn changePawnBlack = new Pawn(destinyCasa, deletedTipo, "Black");
            break; 
            case 15: 
            Pawn changePawnWhite = new Pawn(destinyCasa, deletedTipo, "White");
            break; 

        }
    }

    public Peca relocatedPecaNew(Casa destinyCasa) {
        if(deletedTipo == 6) {
            Queen changeQueenBlack = new Queen(destinyCasa, deletedTipo, "Black");
            return changeQueenBlack;
        } 
        else if(deletedTipo == 7) {
            Queen changeQueenWhite = new Queen(destinyCasa, deletedTipo, "White");
            return changeQueenWhite;
        }
        else if(deletedTipo == 8) {
            Rook changeRookBlack = new Rook(destinyCasa, deletedTipo, "Black"); 
            return changeRookBlack;
        }
        else if(deletedTipo == 9) {
            Rook changeRookWhite = new Rook(destinyCasa, deletedTipo, "White");
            return changeRookWhite;
        }
        else if(deletedTipo == 10) {
            Bishop changeBishopBlack = new Bishop(destinyCasa, deletedTipo, "Black");
            return changeBishopBlack;
        }
        else if(deletedTipo == 11) {
            Bishop changeBishopWhite = new Bishop(destinyCasa, deletedTipo, "White");
            return changeBishopWhite;
        }
        else if(deletedTipo == 12) {
            Knight changeKnightBlack = new Knight(destinyCasa, deletedTipo, "Black");
            return changeKnightBlack;
        }
        else if(deletedTipo == 13) {
            Knight changeKnightWhite = new Knight(destinyCasa, deletedTipo, "White");
            return changeKnightWhite;
        }
        else if(deletedTipo == 14) {
            Pawn changePawnBlack = new Pawn(destinyCasa, deletedTipo, "Black");
            return changePawnBlack;
        }
        else if(deletedTipo == 15) {
            Pawn changePawnWhite = new Pawn(destinyCasa, deletedTipo, "White");
            return changePawnWhite;
        }
        return null;
    }

    public void checkKingSecurity(int origemX, int origemY, int destinoX, int destinoY) {
        Jogo jogoCopy = this;
        jogoCopy.casasCheckWhite.clear();
        jogoCopy.casasCheckBlack.clear();
        jogoCopy.checkWhite();
        jogoCopy.checkBlack(jogoCopy);

        // Jogo jogoCopy = clone(this);
        //Jogo jogoCopy = new Jogo();

        //didn't work

        boolean moved = false;
        Casa origemCopy = jogoCopy.tabuleiro.getCasa(origemX, origemY);
        Casa destinoCopy = jogoCopy.tabuleiro.getCasa(destinoX, destinoY);

        Peca peca = origemCopy.getPeca();

        if(destinoCopy.possuiPeca() && destinoCopy.getPeca().getCor() != origemCopy.getPeca().getCor()
        && ((origemCopy.getPeca().getCor() == "White" && turn) || (origemCopy.getPeca().getCor() == "Black" && !turn))) {
            //Peca peca = origemCopy.getPeca();
            Peca problem = destinoCopy.getPeca();
            //copia peca destino
            if(destinoCopy.possuiPeca()) {
                Peca deletedPeca = destinoCopy.getPeca();
                deletedTipo = deletedPeca.getTipo();
            }
            else {
                deletedTipo = 0;
            }

            peca.mover(destinoCopy);
            if(origemCopy.estaVazia()) {
                moved = true;
                this.tabuleiro.getCasa(destinoX, destinoY).removerPeca();
                this.tabuleiro.getCasa(destinoX, destinoY).colocarPeca(peca);
                jogoCopy.tabuleiro.getCasa(destinoX, destinoY).removerPeca();
                jogoCopy.tabuleiro.getCasa(destinoX, destinoY).colocarPeca(peca);

                //problem.casa = null;
            } else {
                moved = false;
            }
        } 
        else if(destinoCopy.possuiPeca() && destinoCopy.getPeca().getCor() == origemCopy.getPeca().getCor()){
           // System.out.println("Cant move");
        }
        else if((origemCopy.getPeca().getCor() == "White" && turn) || (origemCopy.getPeca().getCor() == "Black" && !turn)){
            //Peca peca = origemCopy.getPeca();

            if(destinoCopy.possuiPeca()) {
                Peca deletedPeca = destinoCopy.getPeca();
                deletedTipo = deletedPeca.getTipo();
            }

            else if(peca.getTipo() == 15 && origemX == 4) {    
                Casa rightCasa = tabuleiro.getCasa(4, origemY+1);
                //Casa esquerda
                Casa leftCasa = tabuleiro.getCasa(4, origemY-1);
                //Casa final direita
                Casa finalCasaRight = tabuleiro.getCasa(5, origemY+1);
                //Casa final direita
                Casa finalCasaLeft = tabuleiro.getCasa(5, origemY-1);

                if(rightCasa != null && !rightCasa.estaVazia() && finalCasaRight.estaVazia()
                && destinoY == finalCasaRight.getY()  && destinoX == finalCasaRight.getX() && rightCasa.getPeca().getTipo() == 14
                && blacksPawn[finalCasaRight.getY()].getPassant() && blacksPawn[finalCasaRight.getY()].getTurnPassant() == tabuleiro.getJogo().getCountTurn()-1) {

                    Peca deletedPeca = rightCasa.getPeca();
                    deletedTipo = deletedPeca.getTipo();
                } 
                else if(leftCasa != null && !leftCasa.estaVazia() && finalCasaLeft.estaVazia() 
                && destinoY == finalCasaLeft.getY() && destinoX == finalCasaLeft.getX() && leftCasa.getPeca().getTipo() == 14
                && blacksPawn[finalCasaLeft.getY()].getPassant() && blacksPawn[finalCasaLeft.getY()].getTurnPassant() == tabuleiro.getJogo().getCountTurn()-1){
                    Peca deletedPeca = leftCasa.getPeca();
                    deletedTipo = deletedPeca.getTipo();
                }
            }
            else if(peca.getTipo() == 14 && origemX == 3) {
                Casa rightCasa = tabuleiro.getCasa(3, origemY+1);
                //Casa esquerda
                Casa leftCasa = tabuleiro.getCasa(3, origemY-1);
                //Casa final direita
                Casa finalCasaRight = tabuleiro.getCasa(2, origemY+1);
                //Casa final direita
                Casa finalCasaLeft = tabuleiro.getCasa(2, origemY-1);

                if(rightCasa != null && !rightCasa.estaVazia() && finalCasaRight.estaVazia()
                && destinoY == finalCasaRight.getY()  && destinoX == finalCasaRight.getX() && rightCasa.getPeca().getTipo() == 15
                && whitesPawn[finalCasaRight.getY()].getPassant() && whitesPawn[finalCasaRight.getY()].getTurnPassant() == tabuleiro.getJogo().getCountTurn()-1) {
                    Peca deletedPeca = rightCasa.getPeca();
                    deletedTipo = deletedPeca.getTipo();
                } else if(leftCasa != null && !leftCasa.estaVazia() && finalCasaLeft.estaVazia() 
                && destinoY == finalCasaLeft.getY()  && destinoX == finalCasaLeft.getX() && leftCasa.getPeca().getTipo() == 15
                && whitesPawn[finalCasaLeft.getY()].getPassant() && whitesPawn[finalCasaLeft.getY()].getTurnPassant() == tabuleiro.getJogo().getCountTurn()-1) {
                    Peca deletedPeca = leftCasa.getPeca();
                    deletedTipo = deletedPeca.getTipo();
                } 
            } 
            else {
                deletedTipo = 0;
            }

            peca.mover(destinoCopy);

            if(origemCopy.estaVazia()) {
                moved = true;
            } else {
                moved = false;
            }
        } 

        jogoCopy.casasCheckWhite.clear();
        jogoCopy.casasCheckBlack.clear();
        jogoCopy.checkWhite();
        jogoCopy.checkBlack(jogoCopy);
        if(turn){

            if(jogoCopy.casasCheckBlack.contains(kings[0].casa)) {
                moveWhite = false;
                if(moved) {
                    origemCopy.colocarPeca(destinoCopy.getPeca());
                    destinoCopy.removerPeca(); 
                    origemCopy.getPeca().casa = origemCopy;
                    destinoCopy.colocarPeca(relocatedPecaNew(destinoCopy));

                }                            
            } else {
                moveWhite = true;

                if(moved && kings[0].rooked) {
                    // origemCopy.colocarPeca(destinoCopy.getPeca());
                    // destinoCopy.removerPeca();
                    // kings[0].casaRookedReturn.colocarPeca(kings[0].casaRooked.getPeca());
                    // kings[0].casaRooked.removerPeca();
                    if(destinoCopy.getY() > origemCopy.getY()) {
                        //Casa finalKing = this.tabuleiro.getCasa(origemX, destinoY+2);
                        Casa finalRook = this.tabuleiro.getCasa(origemX, destinoY-1);
                        Casa initRook = this.tabuleiro.getCasa(origemX, origemY+3);

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        initRook.colocarPeca(finalRook.getPeca());
                        finalRook.removerPeca();
                        initRook.getPeca().casa = initRook; 
                        kings[0].rooked = false; 
                        kings[0].setInitCasa(true);
                    } else if(destinoCopy.getY() < origemCopy.getY()) {
                        Casa finalRook = this.tabuleiro.getCasa(origemX, destinoY+1);
                        Casa initRook = this.tabuleiro.getCasa(origemX, origemY-4);

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        initRook.colocarPeca(finalRook.getPeca());
                        finalRook.removerPeca();
                        initRook.getPeca().casa = initRook; 
                        kings[0].rooked = false; 
                        kings[0].setInitCasa(true);

                    }                
                }
                else if(moved && whitesPawn[origemCopy.getY()].passanted && peca.getTipo() == 15) {
                    if(destinoCopy.getY() > origemCopy.getY()) {
                        //Casa finalKing = this.tabuleiro.getCasa(origemX, destinoY+2);
                        Casa rightCasa = this.tabuleiro.getCasa(origemX, destinoY);
                        //Casa afterRightCasa = this.tabuleiro.getCasa(origemX+1, destinoY+1)

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        rightCasa.colocarPeca(relocatedPecaNew(rightCasa));                     
                        rightCasa.getPeca().casa = rightCasa; 
                        whitesPawn[origemCopy.getY()].passanted = false; 
                        //kings[1].setInitCasa(true);
                    } else if(destinoCopy.getY() < origemCopy.getY()) {
                        Casa leftCasa = this.tabuleiro.getCasa(origemX, destinoY);
                        //Casa afterRightCasa = this.tabuleiro.getCasa(origemX+1, destinoY+1)

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        leftCasa.colocarPeca(relocatedPecaNew(leftCasa));                     
                        leftCasa.getPeca().casa = leftCasa; 
                        whitesPawn[origemCopy.getY()].passanted = false; 

                    }
                }
                
                else if(moved) {
                    origemCopy.colocarPeca(destinoCopy.getPeca());
                    destinoCopy.removerPeca(); 
                    origemCopy.getPeca().casa = origemCopy;
                    destinoCopy.colocarPeca(relocatedPecaNew(destinoCopy));
                    //tem que passar a peça para a casa
                }
            }
        }

        if(!turn){
            if(jogoCopy.casasCheckWhite.contains(kings[1].casa)) {
                moveBlack = false;
                if(moved) {
                    origemCopy.colocarPeca(destinoCopy.getPeca());
                    destinoCopy.removerPeca(); 
                    origemCopy.getPeca().casa = origemCopy;
                    relocatePeca(destinoCopy);
                }                            
            } else {
                moveBlack = true;
                if(moved && kings[1].rooked ){
                    if(destinoCopy.getY() > origemCopy.getY()) {
                        //Casa finalKing = this.tabuleiro.getCasa(origemX, destinoY+2);
                        Casa finalRook = this.tabuleiro.getCasa(origemX, destinoY-1);
                        Casa initRook = this.tabuleiro.getCasa(origemX, origemY+3);

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        initRook.colocarPeca(finalRook.getPeca());
                        finalRook.removerPeca();
                        initRook.getPeca().casa = initRook; 
                        kings[1].rooked = false; 
                        kings[1].setInitCasa(true);
                    } else if(destinoCopy.getY() < origemCopy.getY()) {
                        Casa finalRook = this.tabuleiro.getCasa(origemX, destinoY+1);
                        Casa initRook = this.tabuleiro.getCasa(origemX, origemY-4);

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        initRook.colocarPeca(finalRook.getPeca());
                        finalRook.removerPeca();
                        initRook.getPeca().casa = initRook; 
                        kings[1].rooked = false; 
                        kings[1].setInitCasa(true);

                    }
                }

                else if(moved && blacksPawn[origemCopy.getY()].passanted  && peca.getTipo() == 14) {
                    if(destinoCopy.getY() > origemCopy.getY()) {
                        //Casa finalKing = this.tabuleiro.getCasa(origemX, destinoY+2);
                        Casa rightCasa = this.tabuleiro.getCasa(origemX, destinoY);
                        //Casa afterRightCasa = this.tabuleiro.getCasa(origemX+1, destinoY+1)

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        rightCasa.colocarPeca(relocatedPecaNew(rightCasa));                     
                        rightCasa.getPeca().casa = rightCasa; 
                        blacksPawn[origemCopy.getY()].passanted = false; 
                        //kings[1].setInitCasa(true);
                    } else if(destinoCopy.getY() < origemCopy.getY()) {
                        Casa leftCasa = this.tabuleiro.getCasa(origemX, destinoY);
                        //Casa afterRightCasa = this.tabuleiro.getCasa(origemX+1, destinoY+1)

                        origemCopy.colocarPeca(destinoCopy.getPeca());
                        destinoCopy.removerPeca();
                        origemCopy.getPeca().casa = origemCopy;
                        leftCasa.colocarPeca(relocatedPecaNew(leftCasa));                     
                        leftCasa.getPeca().casa = leftCasa; 
                        blacksPawn[origemCopy.getY()].passanted = false; 

                    }
                }
                else if(moved) {
                    origemCopy.colocarPeca(destinoCopy.getPeca());
                    destinoCopy.removerPeca(); 
                    origemCopy.getPeca().casa = origemCopy;
                    relocatePeca(destinoCopy);
                    //tem que passar a peça para a casa
                }   
            }
        }
    }

    public void changeTurn() {
        countTurn++;
        if(turn) {
            turn = false;
            // System.out.println("Brancas moveram");
            // System.out.println(countTurn);
        } 
        else {
            turn = true;
            // System.out.println("Pretas moveram");
            // System.out.println(countTurn);
        }
    }

    public int getCountTurn() {
        return countTurn;
    }
}



