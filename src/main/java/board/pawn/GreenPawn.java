package board.pawn;

import javafx.scene.paint.Color;

/**
 * Creating an object extending after Pawn, which is used as green pawn in game
 */
public class GreenPawn extends Pawn {
    /**
     * @param color Green is set as color of GreenPawn group
     */
    private static final Color color = Color.LIMEGREEN;

    public GreenPawn() {
        super();
        setFill(color);
    }
}