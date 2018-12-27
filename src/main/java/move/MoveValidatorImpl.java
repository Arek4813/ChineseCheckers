package move;

import board.pawn.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Checking the validity of planned moves
 */
public class MoveValidatorImpl implements MoveValidator {

    private volatile static MoveValidatorImpl INSTANCE;
    /**
     * @param counter Increased after moving to the nearest (without jumping) - in due to allows only one move of
     *                this kind in one queue
     * @param jumpcounter Increased after jumping through other pawn - in due to block moving to the nearest after
     *                    jumping through pawn/pawns
     */
    public int counter;
    public int jumpcounter;
    private int previousNodeFromCol;
    private int previousNodeFromRow;
    private int previousNodeToCol;
    private int previousNodeToRow;

    public MoveValidatorImpl() {}

    public static MoveValidatorImpl getInstance() {
        if (INSTANCE == null) {
            synchronized(MoveValidatorImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoveValidatorImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Decide if move is possible from - to declared coordinates on current gridPane conditions
     * @param fromCol Column coordinate of FromNode
     * @param fromRow Row coordinate of FromNode
     * @param toCol Column coordinate of ToNode
     * @param toRow Row coordinate of ToNode
     * @param gridPane Currently used gridPane
     * @return true if move is possible, false if it is not possible
     */
    @Override
    public boolean canMove (Integer fromCol, Integer fromRow, Integer toCol, Integer toRow, GridPane gridPane) {

        Node nodeFrom = getNodeByRowColumnIndex( fromCol, fromRow, gridPane );

        if ((nodeFrom instanceof BluePawn) && (fromRow > 12) && (fromCol > 8) && (fromCol < 16)) {
            if ((toRow > 12) && (toCol > 8) && (toCol < 16)) {
                return isAnythingEnabledInOppositeArea( fromCol, fromRow, toCol, toRow, gridPane );
            }
        } else if ((nodeFrom instanceof GreenPawn) && ((((fromRow == 12) && (fromCol > 17)) || ((fromRow == 11) && (fromCol > 18)) || ((fromRow == 10) && (fromCol > 19)) || ((fromRow == 9) && (fromCol == 21))))) {
            if (((toRow == 12) && (toCol > 17)) || ((toRow == 11) && (toCol > 18)) || ((toRow == 10) && (toCol > 19)) || ((toRow == 9) && (toCol == 21))) {
                return isAnythingEnabledInOppositeArea( fromCol, fromRow, toCol, toRow, gridPane );
            }
        } else if ((nodeFrom instanceof RedPawn) && ((((fromRow == 12) && (fromCol < 7)) || ((fromRow == 11) &&
                (fromCol < 6)) || ((fromRow == 10) && (fromCol < 5)) || ((fromRow == 9) && (fromCol == 3))))) {
            if (((toRow == 12) && (toCol < 7)) || ((toRow == 11) && (toCol < 6)) || ((toRow == 10) && (toCol < 5)) || ((toRow == 9) && (toCol == 3))) {
                return isAnythingEnabledInOppositeArea( fromCol, fromRow, toCol, toRow, gridPane );
            }
        } else if ((nodeFrom instanceof OrangePawn) && (fromRow < 4) && (fromCol > 8) && (fromCol < 16)) {
            if ((toRow < 4) && (toCol > 8) && (toCol < 16)) {
                return isAnythingEnabledInOppositeArea( fromCol, fromRow, toCol, toRow, gridPane );
            }
        } else if ((nodeFrom instanceof YellowPawn) && ((((fromRow == 4) && (fromCol > 17)) || ((fromRow == 5) &&
                (fromCol > 18)) || ((fromRow == 6) && (fromCol > 19)) || ((fromRow == 7) && (fromCol == 21))))) {
            if (((toRow == 4) && (toCol > 17)) || ((toRow == 5) && (toCol > 18)) || ((toRow == 6) && (toCol > 19)) || ((toRow == 7) && (toCol == 21))) {
                return isAnythingEnabledInOppositeArea( fromCol, fromRow, toCol, toRow, gridPane );
            }
        } else if ((nodeFrom instanceof BlackPawn) && ((((fromRow == 4) && (fromCol < 7)) || ((fromRow == 5) &&
                (fromCol < 6)) || ((fromRow == 6) && (fromCol < 5)) || ((fromRow == 7) && (fromCol == 3))))) {
            if (((toRow == 4) && (toCol < 7)) || ((toRow == 5) && (toCol < 6)) || ((toRow == 6) && (toCol < 5)) || ((toRow == 7) && (toCol == 3))) {
                return isAnythingEnabledInOppositeArea( fromCol, fromRow, toCol, toRow, gridPane );
            }
        }

        else if (isMoveEnabled( fromCol, fromRow, toCol, toRow ))
            return true;

        else if (isJumpEnabled( fromCol, fromRow, toCol, toRow, gridPane )) {
            return true;
        }
        return false;
    }

    public int getCounter() {
        return counter;
    }

    /**
     * Reseting variables used on changing active player
     */
    public void resetFields() {
        counter = 0;
        jumpcounter = 0;
        previousNodeToRow = 0;
        previousNodeToCol = 0;
        previousNodeFromRow = 0;
        previousNodeFromCol = 0;
    }

    /**
     * Checking if move to the nearest is possible
     * @param fromCol Column coordinate of FromNode
     * @param fromRow Row coordinate of FromNode
     * @param toCol Column coordinate of ToNode
     * @param toRow Row coordinate of ToNode
     * @return true if move is possible, false in other case
     */
    private boolean isMoveEnabled(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow) {
        Integer colDifference = Math.abs(toCol - fromCol);
        Integer rowDifference = Math.abs(toRow - fromRow);

        if (((colDifference == 1 && rowDifference == 1) || (colDifference == 2 && rowDifference == 0)) && jumpcounter==0 && counter==0) {
            counter++;
            return true;
        }
        return false;
    }

    /**
     * Checking if jump is possible
     * @param fromCol Column coordinate of FromNode
     * @param fromRow Row coordinate of FromNode
     * @param toCol Column coordinate of ToNode
     * @param toRow Row coordinate of ToNode
     * @param gridPane Currently used gridPane
     * @return true if it is possible, false if not
     */
    private boolean isJumpEnabled(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow, GridPane gridPane) {

        Integer colDifference = Math.abs(toCol - fromCol);
        Integer rowDifference = Math.abs(toRow - fromRow);

        boolean canJumpDiagonally = (colDifference == 2 && rowDifference == 2) && counter == 0;
        boolean canJumpHorizontally = (colDifference == 4 && rowDifference == 0)  && counter == 0;

        if (canJumpHorizontally || canJumpDiagonally) {
            Node between=getNodeByRowColumnIndex(((fromCol+toCol)/2),((fromRow+toRow)/2), gridPane);
            if ((between instanceof Pawn) && (((previousNodeFromCol==fromCol) && (previousNodeFromRow==fromRow)) || ((previousNodeFromCol==0) && (previousNodeFromRow==0))) && ((toCol!=previousNodeToCol) || (toRow!=previousNodeToRow))) {
                previousNodeFromCol = toCol;
                previousNodeFromRow = toRow;
                previousNodeToCol = fromCol;
                previousNodeToRow = fromRow;
                jumpcounter++;
                return true;
            }
        }
        return false;
    }


    /**
     * Checking what is possible in opposite area - not allows to get out opp area for pawns
     * @param fromCol Column coordinate of FromNode
     * @param fromRow Row coordinate of FromNode
     * @param toCol Column coordinate of ToNode
     * @param toRow Row coordinate of ToNode
     * @param gridPane Currently used gridPane
     * @return true if declared move is possible in opp area, false in other case
     */
    private boolean isAnythingEnabledInOppositeArea(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow, GridPane gridPane) {

        if((isMoveEnabled(fromCol, fromRow, toCol, toRow)==true) && jumpcounter==0)  {
            return true;
        }
        else if(isJumpEnabled(fromCol, fromRow, toCol, toRow, gridPane)==true) {
            jumpcounter++;
            return true;
        }
        return false;
    }


    /**
     * Getting a node from given col and row coordinates from all objects of current GridPane
     * @param column Column coordinate
     * @param row Row coordinate
     * @return node which column equals (declared) col and row equals (declared) row, null where cannot match anything
     */
    private Node getNodeByRowColumnIndex(Integer column, Integer row, GridPane gridPane) {

        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            int y = GridPane.getColumnIndex(node);
            int x = GridPane.getRowIndex(node);
            if ((y == column) && (x == row)) {
                result = node;
                break;
            }
        }
        return result;
    }
}
