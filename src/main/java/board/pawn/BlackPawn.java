package board.pawn;

import javafx.scene.paint.Color;

/**
 * Creating an object extending after Pawn, which is used as black pawn in game
 */
public class BlackPawn extends Pawn {
    /**
     * @param color Black is set as color of BlackPawn group
     */
    private static final Color color = Color.BLACK;

    public BlackPawn() {
        super();
        setFill(color);
    }
}
