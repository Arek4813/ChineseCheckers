package bot;

import client.connector.ServerConnectorImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handler for bot - management of activities in dependency of received messages
 */
public class Bot {
    private PrintWriter writer;
    private BufferedReader reader;
    private BotState state;
    private GridPane board;
    private String colorClass;

    public Bot() {
        reader = ServerConnectorImpl.getInstance().getReader();
        writer = ServerConnectorImpl.getInstance().getWriter();
        state = new BotStateInactive(this);
        new JFXPanel();
    }

    public static void main(String[] args) {
        try {
            ServerConnectorImpl.getInstance().connectWithServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bot bot = new Bot();
        try {
            bot.runListeningLoops();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void joinTheGame() {
        writer.println("JOIN");
        System.out.println("JOIN request has been send");
    }

    private void runListeningLoops() throws IOException {
        joinTheGame();
        String receivedMessage = null;
        while ((receivedMessage = reader.readLine()) != null) {

            if (receivedMessage.endsWith("fxml")) {
                String finalReceivedMessage = receivedMessage;
                state.reactOnViewMsg(finalReceivedMessage);
            }
            else if (receivedMessage.startsWith("YOURCOLOR")) {
                state.reactOnColorMsg(receivedMessage);
            }
            else if (receivedMessage.startsWith("VALID")) {
                state.reactOnValidMsg(receivedMessage);
            }
            else if (receivedMessage.startsWith("INVALID")) {
                try {
                    state.reactOnInvalidMsg(receivedMessage);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (receivedMessage.startsWith("ACTIVATE")) {
                try {
                    state.reactOnActivateMsg();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (receivedMessage.startsWith("DEACTIVATE")) {
                state.reactOnDeactivateMsg();
            }
            else if (receivedMessage.startsWith("FINISH")) {
                state.reactOnFinishMsg();
            }
        }
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BotState getState() {
        return state;
    }

    public void setState(BotState state) {
        this.state = state;
    }

    public GridPane getBoard() {
        return board;
    }

    public void setBoard(GridPane board) {
        this.board = board;
    }

    public String getColorClass() {
        return colorClass;
    }

    public void setColorClass(String colorClass) {
        this.colorClass = colorClass;
    }
}
