package move;

import javafx.scene.layout.GridPane;

public interface MoveValidator {
    boolean canMove(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow, GridPane gridPane);
}
