package controller;

import client.request.RequestDirector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Initializing and thread from inner class TimerManager
 * Setting an new time in timer after changing an active player
 * Enabling/Disabling button of skipping an move and its handling
 */
public class GamePanelController {

    @FXML
    private Button endButton;
    @FXML
    private Label timerLabel;

    private ControllerState state;
    public int starttime;
    private TimerManager timerManager;

    @FXML
    public void initialize() {
        RequestDirector.getInstance().setGamePanelController(this);
        timerManager = new TimerManager();
        Thread timeThread = new Thread(timerManager, "Timer");
        Platform.runLater( () -> {
            timeThread.start();
        } );
    }

    private int tmpWarrior=0;


    /**
     * Full mechanism of timer - decreasing from given value to 0, and after reaching 0 - sending request to change a
     * player - in the same time returning current timer value in proper label
     */
    public class TimerManager implements Runnable {

        @Override
        public void run() {

            while (starttime > 0) {
                timerLabel.setFont( Font.font( 14 ) );
                Platform.runLater( ()-> {
                    timerLabel.setText( starttime + "s" );
                } );
                try {
                    starttime--;
                    if (starttime == 0) {
                        /*Thread.sleep(1000L );
                        if(state.equals(ControllerState.INACTIVE)) {
                            try {
                                Thread.sleep(500L );
                            }
                            catch (InterruptedException e) {}
                        }
                        if(state.equals(ControllerState.ACTIVE)) {
                            try {
                                Thread.sleep(500L );
                            }
                            catch (InterruptedException e) {}
                        }*/
                        RequestDirector.getInstance().sendRequest("ENDMOVE");
                    }
                    Thread.sleep( 1000L );
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /**
     * Restarting start time in timermanager class
     */
    public void setState(ControllerState state) {
        this.state = state;
        Platform.runLater( ()-> {
            starttime = 16;
        });
        starttime=16;

    }


    /**
     * Sending request of ENDMOVE after clicking on proper button - skipping a move
     * @param actionEvent button clicked
     */
    @FXML
    public void endMove(ActionEvent actionEvent) {
        RequestDirector.getInstance().sendRequest("ENDMOVE");
    }

    /**
     * Making (skipping a move) button enable to click
     */
    public void enableEndMoveButton() {
        endButton.setDisable(false);
    }

    /**
     * Making (skipping a move) button disable to click
     */
    public void disableEndMoveButton() {
        endButton.setDisable(true);
    }
}
