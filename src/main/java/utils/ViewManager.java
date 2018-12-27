package utils;

/**
 * Setting current view in dependency of amount of players(2, 3, 4 or 6) or setting starting view
 */
public class ViewManager{
    private static ViewManager INSTANCE = null;
    private final String STARTING_VIEW = "/clientwindow/startingView.fxml";
    private final String BOARD_VIEW_2 = "/board/boardForTwo.fxml";
    private final String BOARD_VIEW_3 = "/board/boardForThree.fxml";
    private final String BOARD_VIEW_4 = "/board/boardForFour.fxml";
    private final String BOARD_VIEW_6 = "/board/boardForSix.fxml";
    private final String PANEL_FOR_2 = "/board/gamePanelForTwo.fxml";
    private final String PANEL_FOR_3 = "/board/gamePanelForThree.fxml";
    private final String PANEL_FOR_4 = "/board/gamePanelForFour.fxml";
    private final String PANEL_FOR_6 = "/board/gamePanelForSix.fxml";


    private ViewManager() {}

    public static ViewManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewManager();
        }
        return INSTANCE;
    }

    public String getStartingView() {
        return STARTING_VIEW;
    }
    public String getBoardViewForSix() {
        return BOARD_VIEW_6;
    }

    public String getBoardViewForFour() {
        return BOARD_VIEW_4;
    }

    public String getBoardViewForThree() {
        return BOARD_VIEW_3;
    }

    public String getBoardViewForTwo() {
        return BOARD_VIEW_2;
    }

    public String getPanelForTwo() {
        return PANEL_FOR_2;
    }

    public String getPanelForThree() {
        return PANEL_FOR_3;
    }

    public String getPanelForFour() {
        return PANEL_FOR_4;
    }

    public String getPanelForSix() {
        return PANEL_FOR_6;
    }
}
