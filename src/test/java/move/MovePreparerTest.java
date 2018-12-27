/*package move;

import board.field.EmptyField;
import board.pawn.BluePawn;
import controller.SimpleBoardController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;


public class MovePreparerTest {

    MovePreparerImpl movePreparer;
    BluePawn testBluePawn;
    EmptyField testEmptyField;
    GridPane gridPane;
    SimpleBoardController simpleBoardController;


    private MoveValidatorImpl moveValidatorImpl;

    public MovePreparerTest() {
        movePreparer=new MovePreparerImpl();
        moveValidatorImpl=MoveValidatorImpl.getInstance();

    }

    @Before
    public void createFieldsAndPawns() throws IOException {
        initializeFields();
        setFieldsLocations();
    }

    public void initializeFields() throws IOException {
        testBluePawn=new BluePawn();
        testEmptyField=new EmptyField();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/board/boardForSix.fxml"));
        Parent rootLayout = (Parent) loader.load();
        simpleBoardController = loader.getController();

        gridPane = simpleBoardController.getBoardPane();
    }

    public void setFieldsLocations() {
        GridPane.setColumnIndex(testBluePawn, 6);
        GridPane.setRowIndex(testBluePawn, 12 );

    }

    @Test
    public void preparingPawn() {
        movePreparer.prepareMove(gridPane.getChildren(), testBluePawn);
        Assert.assertEquals(testBluePawn, movePreparer.from);
    }

    @Test
    public void preparingEmptyField() {
        movePreparer.prepareMove(gridPane.getChildren(), testEmptyField);
        Assert.assertEquals(testEmptyField, movePreparer.to);
    }


    @Test
    public void resetingFromAndTo() {
        movePreparer.prepareMove(gridPane.getChildren(), testBluePawn);
        movePreparer.prepareMove(gridPane.getChildren(), testEmptyField);
        Assert.assertEquals(null, movePreparer.from);
        Assert.assertEquals(null, movePreparer.to);
    }
}*/