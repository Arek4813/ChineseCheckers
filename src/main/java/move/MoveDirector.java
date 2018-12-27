package move;

import board.Board;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Deciding if move is valid or invalid
 */
public class MoveDirector {
    private static volatile MoveDirector INSTANCE;
    private MoveValidator moveValidator;
    private MoveImpl move;

    public MoveDirector() {
        moveValidator = MoveValidatorImpl.getInstance();
        move = new MoveImpl();
    }

    public static MoveDirector getInstance() {
        if (INSTANCE == null) {
            synchronized (MoveDirector.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoveDirector();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Deciding if planned move is possible to make (invalid or valid) and send this String message
     * @param fromCol Column coordinate of FromNode
     * @param fromRow Row coordinate of FromNode
     * @param toCol Column coordinate of ToNode
     * @param toRow Row coordinate of ToNode
     * @param gridPane Currently used gridPane
     * @return String variable determinating validation of move (containing coordinates of moved nodes)
     * @throws IOException
     */
    public String movePawn(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow, GridPane gridPane) throws IOException {
        if (isMoveDisabled(fromCol, fromRow, toCol, toRow, gridPane))
            return "INVALID " + fromCol + "," + fromRow + " " + toCol + "," + toRow;
        Board.getInstance().swap(move, fromCol, fromRow, toCol, toRow);
        return "VALID " + fromCol + "," + fromRow + " " + toCol + "," + toRow;
    }

    /**
     * Deciding if move is able to execute or node
     * @param fromCol Column coordinate of FromNode
     * @param fromRow Row coordinate of FromNode
     * @param toCol Column coordinate of ToNode
     * @param toRow Row coordinate of ToNode
     * @param gridPane Currently used gridPane
     * @return true if move is really disabled, false if move is possible
     */
    private boolean isMoveDisabled(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow, GridPane gridPane) {
        return !(moveValidator.canMove(fromCol, fromRow, toCol, toRow, gridPane));
    }
}
