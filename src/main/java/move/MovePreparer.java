package move;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


public interface MovePreparer {
    void prepareMove(ObservableList<Node> listOfFields, Circle c) throws ClassNotFoundException;
    public Class getPawnClass();
    void setPawnClass(String arg) throws ClassNotFoundException;
}
