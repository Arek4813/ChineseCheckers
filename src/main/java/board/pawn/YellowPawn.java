package board.pawn;


import javafx.scene.paint.Color;

/**
 * Creating an object extending after Pawn, which is used as yellow pawn in game
 */
public class YellowPawn extends Pawn {
    /**
     * @param color Yellow is set as color of YellowPawn group
     */
    private static final Color color = Color.YELLOW;

    public YellowPawn() {
        super();
        setFill(color);
    }
}
