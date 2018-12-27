package client.request;


import client.Client;
import client.connector.ServerConnectorImpl;
import controller.ControllerState;
import controller.GamePanelController;
import controller.SimpleBoardController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import move.MoveImpl;
import move.MovePreparerImpl;
import utils.LineDivider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/** Used for sending messages from client to server in dependency of actual situation/case
 *
 */

public class RequestDirector {
    private static RequestDirector INSTANCE = null;
    private final BufferedReader readerFromServer;
    private final PrintWriter writerToServer;
    private SimpleBoardController boardController;
    private GamePanelController gamePanelController;

    public static RequestDirector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequestDirector();
        }
        return INSTANCE;
    }

    private RequestDirector() {
        readerFromServer = ServerConnectorImpl.getInstance().getReader();
        writerToServer = ServerConnectorImpl.getInstance().getWriter();
    }

    /**
     * Starting a thread from RequestReceiver class
     */
    public void runHandler() {
        Thread receiver = new Thread(new RequestReceiver());
        receiver.start();
    }

    /**
     * Sending an message(request) from client to server
     * @param message Information from client in which case is he and what he wants from server
     */
    public void sendRequest(String message) {
        writerToServer.println(message);
    }

    private String getReceivedResponse() throws IOException {
        return readerFromServer.readLine();
    }

    /**
     * Setting appropriate view on precised response(given as arg)
     * @param response String variable - response as result of request from server
     * @throws IOException
     */
    private void setViewOnResponse(String response) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(response));
        Scene scene = new Scene((Parent) loader.load());
        Client.setScene(scene);
    }

    /**
     * Getting a node from given col and row coordinates from given list of nodes
     * @param col Column coordinate
     * @param row Row coordinate
     * @param list List of nodes where we search in
     * @return node which column equals (declared) col and row equals (declared) row
     */
    private Node getNodeFor(Integer col, Integer row, List<Node> list) {
        for (Node node : list) {
            if (GridPane.getColumnIndex(node).equals(col) && GridPane.getRowIndex(node).equals(row)) {
                return node;
            }
        }
        return null;
    }

    public void setBoardController(SimpleBoardController simpleBoardController) {
        this.boardController = simpleBoardController;

    }

    public void setGamePanelController(GamePanelController gamePanelController) {
        this.gamePanelController = gamePanelController;
    }

    /**
     * Inner class which determinates behaviour of thread in dependency of receivedresponse
     */
    class RequestReceiver implements Runnable {
        @Override
        public synchronized void run() {
            while (true) {
                String line = null;
                try {
                    line = getReceivedResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line.endsWith("fxml")) {
                    String finalLine = line;
                    Platform.runLater(() -> {
                        try {
                            setViewOnResponse(finalLine);
                            boardController.informPlayerAboutHisColor();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                else if (line.startsWith("VALID")) {
                    List<Integer> locations = LineDivider.getInstance().divideLine(line, 6);
                    Node from = getNodeFor(locations.get(0), locations.get(1), boardController.getBoardPane().getChildren());
                    Node to = getNodeFor(locations.get(2), locations.get(3), boardController.getBoardPane().getChildren());
                    new MoveImpl().swap(from, to);
                }
                else if (line.startsWith("YOURCOLOR")) {
                    String[] args = line.split(" ");
                    System.out.println(args[1]);
                    try {
                        MovePreparerImpl.getInstance().setPawnClass(args[1]);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if (line.startsWith("ACTIVATE")) {
                    Platform.runLater(() -> {
                        boardController.setState(ControllerState.ACTIVE);
                        gamePanelController.setState(ControllerState.ACTIVE);
                        gamePanelController.enableEndMoveButton();
                    });
                    System.out.println(line);
                }
                else if (line.startsWith("DEACTIVATE")) {
                    Platform.runLater(() -> {
                        boardController.setState(ControllerState.INACTIVE);
                        gamePanelController.setState(ControllerState.INACTIVE);
                        gamePanelController.disableEndMoveButton();
                    });
                    System.out.println(line);
                }
                else if (line.startsWith("FINISH")) {
                    String finalLine1 = line;
                    Platform.runLater(() -> {
                        boardController.showAlertWithResults(finalLine1);
                    });
                }
            }
        }
    }
}
