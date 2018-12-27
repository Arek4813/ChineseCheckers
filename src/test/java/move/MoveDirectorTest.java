package move;

import board.Board;
import controller.SimpleBoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MoveDirectorTest {

    MoveDirector moveDirector;
    private MoveImpl moveImpl;
    SimpleBoardController simpleBoardController;
    GridPane gridPane;


    public MoveDirectorTest() {
        this.moveDirector=new MoveDirector();
        moveImpl = new MoveImpl();
    }

    @Before
    public void createFieldsAndPawns() throws IOException {
        initializeFields();
    }

    @Before
    public void test() throws IOException {
        Board.getInstance().setBoardFilename("/board/boardForSix.fxml");
    }

    public void initializeFields() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/board/boardForSix.fxml"));
        Parent rootLayout = (Parent) loader.load();
        simpleBoardController = loader.getController();

        gridPane = simpleBoardController.getBoardPane();
    }

    @Test
    public void returnCorrectStringInvalid() throws IOException {
        String resultOfMovePawn = moveDirector.movePawn(4, 10, 7, 11, gridPane);
        String myExpectation = "INVALID 4,10 7,11";
        Assert.assertEquals(resultOfMovePawn, myExpectation);
    }

    @Test
    public void returnCorrectStringValid() throws IOException {
        String resultOfMovePawn = moveDirector.movePawn(8, 10, 7, 11, gridPane);
        String myExpectation = "VALID 8,10 7,11";
        Assert.assertEquals(resultOfMovePawn, myExpectation);
    }
}
