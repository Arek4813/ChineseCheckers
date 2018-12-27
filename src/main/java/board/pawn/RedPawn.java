package board.pawn;

import javafx.scene.paint.Color;

/**
 * Creating an object extending after Pawn, which is used as red pawn in game
 */
public class RedPawn extends Pawn {
    /**
     * @param color Red is set as color of RedPawn group
     */
    private static final Color color = Color.RED;

    public RedPawn() {
        super();
        setFill(color);
    }
}
