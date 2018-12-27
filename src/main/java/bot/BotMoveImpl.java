package bot;

import board.field.EmptyField;
import board.pawn.BluePawn;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import move.MoveImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of methods from BotMove interface
 */
public class BotMoveImpl implements BotMove {
    private Integer colTarget;
    private Integer rowTarget;

    /**
     * Setting a target for bot in dependency of pawn class
     * @param colorClass
     */
    @Override
    public void setColumnAndRowOfTarget(String colorClass) {
        switch (colorClass) {
            case "board.pawn.BluePawn":
                colTarget = 12;
                rowTarget = 16;
                break;
            case "board.pawn.RedPawn":
                colTarget = 0;
                rowTarget = 12;
                break;
            case "board.pawn.BlackPawn":
                colTarget = 0;
                rowTarget = 4;
                break;
            case "board.pawn.YellowPawn":
                colTarget = 24;
                rowTarget = 4;
                break;
            case "board.pawn.GreenPawn":
                colTarget = 24;
                rowTarget = 12;
                break;
            case "board.pawn.OrangePawn":
                colTarget = 12;
                rowTarget = 0;
                break;
        }
    }


    /**
     * Thanks to using method getNearestEmptyNode - this method get Node for bot with accurate color
     * @param board current gridpane containing all nodes
     * @param colorClass class from the bot is
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Node getNodeOfBotsColor(GridPane board, String colorClass) throws ClassNotFoundException {
        List<Node> listOfFields = board.getChildren();
        Class botsColorClass = Class.forName(colorClass);
        for (Node n: listOfFields) {
            if (botsColorClass.isInstance(n)) {
                if (getNearestEmptyNode(board, n) != null) {
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * mechanism of bot move
     * @param from Node from we execute a move
     * @param to Node to we execute a move
     */
    @Override
    public void moveBot(Node from, Node to) {
        new MoveImpl().swap(from, to);
    }


    /**
     * Getting a node from given col and row coordinates from all objects of current GridPane
     * @param gridPane current gridpane containing all nodes
     * @param column Column coordinate
     * @param row Row coordinate
     * @return node which column equals (declared) col and row equals (declared) row, null where cannot match anything
     */
    @Override
    public Node getNodeByColumnAndRow(GridPane gridPane,Integer column, Integer row) {
        for (Node n : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(n) == column && GridPane.getRowIndex(n) == row) {
                return n;
            }
        }
        return null;
    }

    /**
     * Used to find nearest empty node
     * @param board current gridpane containing all nodes
     * @param from Node from we are eager to execute the move
     * @return
     */
    @Override
    public Node getNearestEmptyNode (GridPane board, Node from) {
        Integer fromCol = GridPane.getColumnIndex(from);
        Integer fromRow = GridPane.getRowIndex(from);
        for (Node n: board.getChildren()) {
            Integer toCol = GridPane.getColumnIndex(n);
            Integer toRow = GridPane.getRowIndex(n);
            boolean condition = ((Math.abs(fromCol - toCol) == 1) && (Math.abs(fromRow - toRow) == 1) && (n instanceof EmptyField));
            if (condition) {
                return n;
            }
        }
        return null;
    }
}
