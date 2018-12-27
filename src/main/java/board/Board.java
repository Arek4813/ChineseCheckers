package board;

import board.pawn.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import move.Move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loading a game view for client, setting a winner and swaping two nodes
 */
public class Board {
    /**
     * @param winners list used to contain game winners
     */
    private static volatile Board INSTANCE;
    private String boardFilename;
    private GridPane boardGridPane;
    private FXMLLoader loader;
    private List<String> winners;

    private Board() {
        winners = new ArrayList<>();
    }

    public static Board getInstance() throws IOException {
        if (INSTANCE == null) {
            synchronized(Board.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Board();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Setting a filename in dependency of amount of players
     * @param filename String variable which gives info about chosen boardGridPane(for two, three, four or six players)
     * @throws IOException
     */
    public void setBoardFilename(String filename) throws IOException {
        boardFilename = filename;
        loader = new FXMLLoader(this.getClass().getResource(boardFilename));
        boardGridPane = loader.load();
    }

    /**
     * Making a swap between two nodes - first node gets parameters of second, and inversely
     * @param fromCol Column coordinate from FromNode
     * @param fromRow Row coordinate from FromNode
     * @param toCol Column coordinate from ToNode
     * @param toRow Row coordinate from ToNode
     */
    public void swap(Move move, Integer fromCol, Integer fromRow, Integer toCol, Integer toRow) {
        Node from = getNodeFor(fromCol, fromRow);
        Node to = getNodeFor(toCol, toRow);
        System.out.println(from.getClass().getName());
        System.out.println(to.getClass().getName());
        move.swap(from, to);
    }

    /**
     * Checking if any of players has won
     * @return true if someone won, false in case of inconclusive game
     */
    public boolean hasNewWinner() {
        return blueWon() || redWon() || blackWon() || orangeWon() || yellowWon() || greenWon();
    }

    /**
     * Getting a node from given col and row coordinates from all objects of current GridPane
     * @param col Column coordinate
     * @param row Row coordinate
     * @return node which column equals (declared) col and row equals (declared) row, null where cannot match anything
     */
    private Node getNodeFor(Integer col, Integer row) {
        for (Node n : boardGridPane.getChildren()) {
            if (GridPane.getColumnIndex(n) == col && GridPane.getRowIndex(n) == row) {
                return n;
            }
        }
        return null;
    }

    /**
     * Checking if player using black pawns is a winner
     * @return true if all black pawns are in opposite area, false in other cases
     */
    private boolean blackWon() {
        if ( getNodeFor(5, 5) instanceof BlackPawn &&
                getNodeFor(4, 6) instanceof BlackPawn &&
                getNodeFor(3, 7) instanceof BlackPawn &&
                getNodeFor(3, 5) instanceof BlackPawn &&
                getNodeFor(2, 6) instanceof BlackPawn &&
                getNodeFor(1, 5) instanceof BlackPawn &&
                getNodeFor(0, 4) instanceof BlackPawn &&
                getNodeFor(2, 4) instanceof BlackPawn &&
                getNodeFor(4, 4) instanceof BlackPawn &&
                getNodeFor(6, 4) instanceof BlackPawn &&
                !winners.contains(BlackPawn.class.getName())) {
            winners.add(BlackPawn.class.getName());
            System.out.println("Black is true");
            return true;
        }
        return false;
    }

    /**
     * Checking if player using red pawns is a winner
     * @return true if all red pawns are in opposite area, false in other cases
     */
    private boolean redWon() {
        if ( getNodeFor(4, 10) instanceof RedPawn &&
                getNodeFor(8, 12) instanceof RedPawn &&
                getNodeFor(1, 11) instanceof RedPawn &&
                getNodeFor(2, 10) instanceof RedPawn &&
                getNodeFor(3, 9) instanceof RedPawn &&
                getNodeFor(6, 12) instanceof RedPawn &&
                getNodeFor(2, 12) instanceof RedPawn &&
                getNodeFor(4, 12) instanceof RedPawn &&
                getNodeFor(5, 11) instanceof RedPawn &&
                getNodeFor(3, 11) instanceof RedPawn &&
                !winners.contains(RedPawn.class.getName())) {
            winners.add(RedPawn.class.getName());
            System.out.println("Red is true");
            return true;
        }
        return false;
    }

    /**
     * Checking if player using orange pawns is a winner
     * @return true if all orange pawns are in opposite area, false in other cases
     */
    private boolean orangeWon() {
        if ( getNodeFor(12, 0) instanceof OrangePawn &&
                getNodeFor(11, 1) instanceof OrangePawn &&
                getNodeFor(15, 3) instanceof OrangePawn &&
                getNodeFor(13, 3) instanceof OrangePawn &&
                getNodeFor(11, 3) instanceof OrangePawn &&
                getNodeFor(9, 3) instanceof OrangePawn &&
                getNodeFor(10, 2) instanceof OrangePawn &&
                getNodeFor(12, 2) instanceof OrangePawn &&
                getNodeFor(14, 2) instanceof OrangePawn &&
                getNodeFor(13, 1) instanceof OrangePawn &&
                !winners.contains(OrangePawn.class.getName())) {
            winners.add(OrangePawn.class.getName());
            System.out.println("Orange is true");
            return true;
        }
        return false;
    }


    /**
     * Checking if player using yellow pawns is a winner
     * @return true if all yellow pawns are in opposite area, false in other cases
     */
    private boolean yellowWon() {
        if (getNodeFor(18, 4) instanceof YellowPawn &&
                getNodeFor(20, 4) instanceof YellowPawn &&
                getNodeFor(22, 4) instanceof YellowPawn &&
                getNodeFor(24, 4) instanceof YellowPawn &&
                getNodeFor(21, 7) instanceof YellowPawn &&
                getNodeFor(20, 6) instanceof YellowPawn &&
                getNodeFor(22, 6) instanceof YellowPawn &&
                getNodeFor(19, 5) instanceof YellowPawn &&
                getNodeFor(21, 5) instanceof YellowPawn &&
                getNodeFor(23, 5) instanceof YellowPawn &&
                !winners.contains(YellowPawn.class.getName())) {
            winners.add(YellowPawn.class.getName());
            System.out.println("Yellow is true");
            return true;
        }
        return false;
    }

    /**
     * Checking if player using green pawns is a winner
     * @return true if all green pawns are in opposite area, false in other cases
     */
    private boolean greenWon() {
        if ( getNodeFor(19, 11) instanceof GreenPawn &&
                getNodeFor(21, 11) instanceof GreenPawn &&
                getNodeFor(22, 12) instanceof GreenPawn &&
                getNodeFor(20, 12) instanceof GreenPawn &&
                getNodeFor(18, 12) instanceof GreenPawn &&
                getNodeFor(23, 11) instanceof GreenPawn &&
                getNodeFor(22, 10) instanceof GreenPawn &&
                getNodeFor(21, 9) instanceof GreenPawn &&
                getNodeFor(20, 10) instanceof GreenPawn &&
                getNodeFor(24, 12) instanceof GreenPawn &&
                !winners.contains(GreenPawn.class.getName())) {
            winners.add(GreenPawn.class.getName());
            System.out.println("Green is true");
            return true;
        }
        return false;
    }



    /**
     * Checking if player using blue pawns is a winner
     * @return true if all blue pawns are in opposite area, false in other cases
     */
    private boolean blueWon() {
        if ( getNodeFor(15, 13) instanceof BluePawn &&
               getNodeFor(13, 13) instanceof BluePawn &&
               getNodeFor(11, 13) instanceof BluePawn &&
               getNodeFor(9, 13) instanceof BluePawn &&
               getNodeFor(12, 16) instanceof BluePawn &&
               getNodeFor(13, 15) instanceof BluePawn &&
               getNodeFor(11, 15) instanceof BluePawn &&
               getNodeFor(14, 14) instanceof BluePawn &&
               getNodeFor(12, 14) instanceof BluePawn &&
               getNodeFor(10, 14) instanceof BluePawn &&
               !winners.contains(BluePawn.class.getName())) {
            winners.add(BluePawn.class.getName());
            System.out.println("Blue is true");
            return true;
        }
        return false;
    }

    public GridPane getBoardGridPane() {
        return boardGridPane;
    }
}
