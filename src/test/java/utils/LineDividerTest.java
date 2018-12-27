package utils;

import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LineDividerTest {
    private LineDivider lineDivider;


    @Before
    public void initialize() {
        lineDivider = LineDivider.getInstance();
    }

    @Test
    public void dividesLineCorrectly() {
        String testString ="TEST 1,2 3,4";
        List<Integer> dividedString = lineDivider.divideLine(testString, 5);
        assertEquals(new Integer(1), dividedString.get(0));
        assertEquals(new Integer(2), dividedString.get(1));
        assertEquals(new Integer(3), dividedString.get(2));
        assertEquals(new Integer(4), dividedString.get(3));
    }

    @Test (expected = StringIndexOutOfBoundsException.class)
    public void dividesEmptyLine() {
        String testString = "";
        List<Integer> dividedString = lineDivider.divideLine(testString, 2);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void dividesTooShortLine() {
        String testString ="HELLO";
        List<Integer> dividedString = lineDivider.divideLine(testString, 5);
    }

    @Test (expected = StringIndexOutOfBoundsException.class)
    public void dividesLineWithWrongIndex() {
        String testString = "TEST 1,2 3,4";
        List<Integer> dividedString = lineDivider.divideLine(testString, -1);
    }

    @Test (expected = NumberFormatException.class)
    public void dividesLineWithWrongIndexLocation() {
        String testString = "TEST 1,2 3,4";
        List<Integer> dividedString = lineDivider.divideLine(testString, 2);
    }

}
