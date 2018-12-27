package board.field;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Creating an object extending after Circle, which is used as empty field in game
 */
public class EmptyField extends Circle {
    /**
     * @param color Emptyfield hasn't got any color, so white is set as default color
     */
    private static final Color color = Color.WHITE;

    public EmptyField() {
        super();
        setFill(color);
    }
}
