package move;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Implementing swap method from Move interface
 */
public class MoveImpl implements Move {
    /**
     * Making a swap between two nodes - first node gets parameters of second, and inversely
     * @param from an object which will be swapped with to object(node)
     * @param to an object which will be swapped with from object(node)
     */
    @Override
    public void swap(Node from, Node to) {
        Integer fromCol = GridPane.getColumnIndex(from);
        Integer fromRow = GridPane.getRowIndex(from);
        Integer toCol = GridPane.getColumnIndex(to);
        Integer toRow = GridPane.getRowIndex(to);
        GridPane.setColumnIndex(from, toCol);
        GridPane.setRowIndex(from, toRow);
        GridPane.setColumnIndex(to, fromCol);
        GridPane.setRowIndex(to, fromRow);
    }
}
