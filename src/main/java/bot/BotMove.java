package bot;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public interface BotMove {
    void setColumnAndRowOfTarget(String colorClass);

    Node getNodeOfBotsColor(GridPane board, String colorClass) throws ClassNotFoundException;
    void moveBot(Node from, Node to);

    Node getNearestEmptyNode(GridPane board, Node from);

    Node getNodeByColumnAndRow(GridPane gridPane, Integer column, Integer row);
}
