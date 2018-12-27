package board.pawn;

import javafx.scene.paint.Color;

/**
 * Creating an object extending after Pawn, which is used as orange pawn in game
 */
public class OrangePawn extends Pawn {
    /**
     * @param color Orange is set as color of OrangePawn group
     */
    private static final Color color = Color.ORANGE;

    public OrangePawn() {
        super();
        setFill(color);
    }
}
