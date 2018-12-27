package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Divider of String line - to distribute coordinates
 */
public class LineDivider {
    private static LineDivider INSTANCE;

    private LineDivider() {}

    public static LineDivider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LineDivider();
        }
        return INSTANCE;
    }

    /**
     * Dividing String to get coordinates
     * @param line String from it is divided
     * @return list of divided locations
     */
    public List<Integer> divideLine(String line, Integer indexOfFirstDivide) {
        String[] locations = line.substring(indexOfFirstDivide).split(" ");
        String[] fromLocation = locations[0].split(",");
        String[] toLocation = locations[1].split(",");
        List<Integer> returnedLocation = new ArrayList<>();
        returnedLocation.add(Integer.parseInt(fromLocation[0]));
        returnedLocation.add(Integer.parseInt(fromLocation[1]));
        returnedLocation.add(Integer.parseInt(toLocation[0]));
        returnedLocation.add(Integer.parseInt(toLocation[1]));
        return returnedLocation;
    }
}
