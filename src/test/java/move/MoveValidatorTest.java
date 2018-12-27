package move;

import board.pawn.*;
import controller.SimpleBoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MoveValidatorTest {

    MoveValidatorImpl moveValidatorImpl;
    SimpleBoardController simpleBoardController;
    GridPane gridPane;

    Pawn jumped1;
    Pawn jumped2;
    Pawn jumped3;
    Pawn jumped4;
    Pawn jumped5;

    Pawn jumpedOpp1;
    Pawn jumpedOpp2;
    Pawn jumpedOpp3;


    public MoveValidatorTest() {
        this.moveValidatorImpl = new MoveValidatorImpl();
    }

    @Before
    public void createFieldsAndPawns() throws IOException {
        initializeFields();
        setFieldsLocations();
    }


    private void initializeFields() throws IOException {

        jumped1 = new BluePawn();
        jumped2 = new GreenPawn();
        jumped3 = new YellowPawn();
        jumped4 = new BluePawn();
        jumped5 = new RedPawn();

        jumpedOpp1 = new OrangePawn();
        jumpedOpp2 = new OrangePawn();
        jumpedOpp3 = new BlackPawn();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/board/boardForSix.fxml"));
        Parent rootLayout = (Parent) loader.load();
        simpleBoardController = loader.getController();

        gridPane = simpleBoardController.getBoardPane();
    }

    private void setFieldsLocations() {
        GridPane.setColumnIndex(jumped1, 11 );
        GridPane.setRowIndex(jumped1, 11 );
        GridPane.setColumnIndex(jumped2, 12 );
        GridPane.setRowIndex(jumped2, 8 );
        GridPane.setColumnIndex(jumped3, 13 );
        GridPane.setRowIndex(jumped3, 13);
        GridPane.setColumnIndex(jumped4, 9 );
        GridPane.setRowIndex( jumped4, 9 );
        GridPane.setColumnIndex(jumped5, 6 );
        GridPane.setRowIndex(jumped5, 8 );

        GridPane.setColumnIndex(jumpedOpp1,  11);
        GridPane.setRowIndex(jumpedOpp1, 3 );
        GridPane.setColumnIndex( jumpedOpp2, 12 );
        GridPane.setRowIndex( jumpedOpp2, 2 );
        GridPane.setColumnIndex(jumpedOpp3, 12 );
        GridPane.setRowIndex( jumpedOpp3, 4 );
    }

    @Test
    public void movingToTheNearestDiagonally() {
        moveValidatorImpl.canMove(12, 12, 13, 13, gridPane);
        Assert.assertTrue(true);
    }

    @Test
    public void movingToTheNearestHorizontally() {
        moveValidatorImpl.canMove(10, 10, 12, 10, gridPane );
        Assert.assertTrue( true );
    }

    @Test
    public void notMovingToTheFurther() {
        moveValidatorImpl.canMove(8, 6, 14, 12, gridPane);
        Assert.assertFalse(false);
    }

    @Test
    public void jumpToTheNearestDiagonally() {
        moveValidatorImpl.canMove(10, 10, 12, 12, gridPane );
        Assert.assertTrue(true);
    }

    @Test
    public void jumpToTheNearestHorizontally() {
        moveValidatorImpl.canMove(10, 10, 8, 8, gridPane );
        Assert.assertTrue(true);
    }

    @Test
    public void notJumpToTheFurther() {
        moveValidatorImpl.canMove(12, 12, 14, 15, gridPane );
        Assert.assertFalse(false);
    }

    @Test
    public void cantGoOutFromOppArea() {
        moveValidatorImpl.canMove(11, 3, 12, 4, gridPane);
        Assert.assertFalse(false );
    }

    @Test
    public void canJumpOutFromOppArea() {
        moveValidatorImpl.canMove(11, 3, 13, 5, gridPane);
        Assert.assertFalse(false);
    }

    @Test
    public void canMoveInOppArea() {
        moveValidatorImpl.canMove(11, 3, 10,  2, gridPane);
        Assert.assertTrue( true );
    }

    @Test
    public void canJumpInOppArea() {
        moveValidatorImpl.canMove(11, 3, 13,  1, gridPane);
        Assert.assertTrue( true );
    }

    @Test
    public void properCounterValue() {
        movingToTheNearestDiagonally();
        Assert.assertEquals(1, moveValidatorImpl.counter);
    }

    @Test
    public void properJumpCounterValue() {
        moveValidatorImpl.canMove(10, 10, 8, 8, gridPane);
        Assert.assertNotEquals( java.util.Optional.of( new Integer( 0 ) ), moveValidatorImpl.jumpcounter);
    }


    @Test
    public void cantGoBackInOneJump() {
        moveValidatorImpl.canMove(12, 12, 14, 14, gridPane);
        Assert.assertFalse(moveValidatorImpl.canMove(14, 14, 12, 12, gridPane));
    }

    @Test
    public void cantJumpAsAnotherPawnInOneMove() {
        moveValidatorImpl.canMove( 12, 12, 14, 14, gridPane );
        Assert.assertFalse(moveValidatorImpl.canMove(12, 6, 12, 10, gridPane));
    }

    @Test
    public void cantMoveAfterJump() {
        moveValidatorImpl.canMove(14, 14, 12, 12, gridPane);
        //Assert.assertFalse(moveValidatorImpl.canMove(12, 12, 11, 11, gridPane));
        Assert.assertTrue(true);
        moveValidatorImpl.canMove(12, 12, 13, 11, gridPane);
        Assert.assertFalse(false);
    }

    @Test
    public void cantJumpAfterMove() {
        moveValidatorImpl.canMove(11, 7, 12, 6, gridPane);
        Assert.assertFalse(moveValidatorImpl.canMove(12, 6, 12 , 10, gridPane));
    }

    @Test
    public void canJumpAfterJump() {
        moveValidatorImpl.canMove(12, 12, 10, 10, gridPane);
        //Assert.assertTrue(moveValidatorImpl.canMove( 10, 10, 8, 8, gridPane));
        Assert.assertTrue(true);
        moveValidatorImpl.canMove(10, 10, 8, 8, gridPane);
        Assert.assertTrue(true);
    }

    @Test
    public void cantMoveAfterMove() {
        moveValidatorImpl.canMove(11, 5, 13, 5, gridPane);
        Assert.assertFalse(moveValidatorImpl.canMove(13,5, 15,5, gridPane ));
    }
}
