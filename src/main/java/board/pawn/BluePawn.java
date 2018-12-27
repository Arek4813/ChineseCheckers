package board.pawn;

import javafx.scene.paint.Color;

/**
 * Creating an object extending after Pawn, which is used as blue pawn in game
 */
public class BluePawn extends Pawn {
    /**
     * @param color Blue is set as color of BluePawn group
     */
    private static final Color color = Color.DODGERBLUE;

    public BluePawn() {
        super();
        setFill(color);
    }
}
