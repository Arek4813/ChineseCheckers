package move;

import board.field.EmptyField;
import client.request.RequestDirector;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

/**
 * Preparing for move possibilities - classification circles(pawns) in dependency of class instance
 */
public class MovePreparerImpl implements MovePreparer {
    /**
     * @param from Variable determinating from which node the move is started
     * @param to Variable determinating to which node the move is
     */
    private static MovePreparerImpl INSTANCE;
    private Class pawnClass;
    public Circle from;
    public Circle to;

    public MovePreparerImpl(){}

    public static MovePreparerImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovePreparerImpl();
        }
        return INSTANCE;
    }

    /**
     * Matching from and to node in dependency of class instance - empty field must be destination and instance of
     * Pawn classes must be source of move (from Node)
     * Checking if gave parameter Circle c is not null, making from and to null after preparing of move
     * @param listOfFields current list of nodes used in game
     * @param c Given Circle object - Pawn class object or EmptyField (eventually null)
     */
    @Override
    public void prepareMove(ObservableList<Node> listOfFields, Circle c) {
        if (pawnClass.isInstance(c)) {
            from = c;
        }
        else if (c instanceof EmptyField && from != null) {
            to = c;
        }
        if (from !=null && to != null) {
            sendMoveRequest();
            makeFieldsNull();
        }
    }

    @Override
    public void setPawnClass(String arg) throws ClassNotFoundException {
        this.pawnClass = Class.forName(arg);
    }

    public Class getPawnClass() {
        return pawnClass;
    }

    /**
     * Reseting the values of from and to after correct matching in prepareMove method
     */
    private void makeFieldsNull() {
        from = null;
        to = null;
    }

    /**
     * Sending MOVE request to server and giving location From and To (as Strings)
     */
    private void sendMoveRequest() {
        String locationFrom = GridPane.getColumnIndex(from) + "," + GridPane.getRowIndex(from);
        String locationTo = GridPane.getColumnIndex(to) + "," + GridPane.getRowIndex(to);
        RequestDirector.getInstance().sendRequest("MOVE " + locationFrom + " " + locationTo);
    }
}
