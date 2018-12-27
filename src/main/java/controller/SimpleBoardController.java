package controller;

import client.request.RequestDirector;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import move.MovePreparerImpl;


public class SimpleBoardController {
    @FXML
    private GridPane boardPane;

    private ControllerState state;

    @FXML
    public void initialize() {
        RequestDirector.getInstance().setBoardController(this);
    }

    /**
     * Handling of clicking on circles - pawns or emptyfields of game gridPane
     * @param mouseEvent clicking on circles
     */
    @FXML
    public void circleClicked(MouseEvent mouseEvent) {
        if (state.equals(ControllerState.ACTIVE)) {
            MovePreparerImpl.getInstance().prepareMove(boardPane.getChildren(), (Circle) mouseEvent.getSource());
        }
    }

    public GridPane getBoardPane() {
        return boardPane;
    }

    /**
     * Getting an info about his color for player
     */
    public void informPlayerAboutHisColor() {
        String[] parameters = (MovePreparerImpl.getInstance().getPawnClass().getName()).split("\\.");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You are " + parameters[2], ButtonType.OK);
        alert.showAndWait();
    }

    public void setState(ControllerState state) {
        this.state = state;
    }

    /**
     * Showing alert with information from server
     * @param arg Body of server information
     */
    public void showAlertWithResults (String arg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, arg, ButtonType.OK);
        alert.showAndWait();
    }
}
