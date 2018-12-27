package bot;

import client.connector.ServerConnectorImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utils.LineDivider;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class BotStateInactive implements BotState {
    private PrintWriter writer;
    private Bot bot;
    private BotMove botMove;

    public BotStateInactive (Bot bot) {
        this.bot = bot;
        this.writer = bot.getWriter();
        this.botMove = new BotMoveImpl();
    }

    @Override
    public void reactOnViewMsg(String view) {
        initializeBotsBoard(view);
        System.out.println("Dostalem widok");
    }

    @Override
    public void reactOnColorMsg(String receivedMessage) {
        String colorClass = receivedMessage.split(" ")[1];
        bot.setColorClass(colorClass);
        System.out.println("Dostalem kolor");
        botMove.setColumnAndRowOfTarget(bot.getColorClass());
    }

    @Override
    public void reactOnValidMsg(String msg) {
        List<Integer> locations = LineDivider.getInstance().divideLine(msg, 6);
        Node from = botMove.getNodeByColumnAndRow(bot.getBoard(), locations.get(0), locations.get(1));
        Node to = botMove.getNodeByColumnAndRow(bot.getBoard(), locations.get(2), locations.get(3));
        botMove.moveBot(from, to);
        System.out.println("Aktualizuje teraz moj widok bo ktos sie ruszyl");
    }

    @Override
    public void reactOnInvalidMsg(String msg) {
        System.out.println("Nic nie robie bo ktos sie zle ruszyl");
    }

    @Override
    public void reactOnActivateMsg() throws ClassNotFoundException {
        bot.setState(new BotStateActive(bot));
        System.out.println("Zostalem aktywowany i wysylam MOVE request");
        Node from = botMove.getNodeOfBotsColor(bot.getBoard(), bot.getColorClass());
        Node to = botMove.getNearestEmptyNode(bot.getBoard(), from);
        writer.println("MOVE " + GridPane.getColumnIndex(from) + "," + GridPane.getRowIndex(from) + " " + GridPane.getColumnIndex(to) + "," + GridPane.getRowIndex(to));
    }

    @Override
    public void reactOnDeactivateMsg() {
        System.out.println("Nic nie robie bo jestem juz nieaktywny");
    }

    @Override
    public void reactOnFinishMsg() {
        System.out.println("koniec gry");
        try {
            ServerConnectorImpl.getInstance().closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeBotsBoard(String view) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(view));
        try {
            BorderPane pane = loader.load();
            bot.setBoard((GridPane) pane.getCenter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
