package board;

import move.MoveImpl;
import org.junit.Before;
import org.junit.Test;
import utils.ViewManager;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class BoardTest {
    private Board board;

    @Before
    public void initialize() {
        try {
            board = Board.getInstance();
            board.setBoardFilename(ViewManager.getInstance().getBoardViewForTwo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doesNotHaveNewWinnerWhenSomeoneDoesNotFinish() {
        assertFalse(board.hasNewWinner());
    }

    @Test
    public void swapsTwoNodesCorrectly() {
        board.swap(new MoveImpl(), 9,3,10,4);
    }

    @Test (expected = NullPointerException.class)
    public void swapWithWrongIndexes() {
        board.swap(new MoveImpl(), 2,2, -2, 1);
    }

    @Test (expected = NullPointerException.class)
    public void swapNodesFromNonExistingFields() {
        board.swap(new MoveImpl(), 1,1, 1, 2);
    }

    @Test (expected = IllegalStateException.class)
    public void intializeBoardWithNonExistingFilename() throws IOException {
        board.setBoardFilename("NONEXISTING");
    }

}
