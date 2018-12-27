package server;

import board.Board;
import board.pawn.*;
import javafx.embed.swing.JFXPanel;
import move.MoveDirector;
import move.MoveValidatorImpl;
import utils.LineDivider;
import utils.ViewManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Whole mechanism of server work - full handler for game
 */
public class Server {
    private static final Integer PORT_NUMBER = 5000;

    private List<PrintWriter> listOfWriters;
    private List<Player> listOfPlayers;
    private List<Player> readyPlayers;
    private Game game;

    public Server() {
        listOfWriters = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        readyPlayers = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            new Server().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starting of server work - return message for connected players
     * @throws IOException
     */
    private void start() throws IOException {
        System.out.println("Server started.");
        ServerSocket socketListener = new ServerSocket(PORT_NUMBER);
        try {
            while (true) {
                Player player = new Player(socketListener.accept());
                System.out.println("Connection accepted: " + socketListener.toString());
                listOfPlayers.add(player);
                Thread handler = new Thread(player);
                handler.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            socketListener.close();
        }
    }

    /**
     * Sending message for each connected player which color is lined with him
     */
    private void sendColorToEachPlayer() {
        int size = readyPlayers.size();
        switch(size) {
            case 2:
                listOfPlayers.get(0).setColorClass(BluePawn.class.getName());
                listOfPlayers.get(1).setColorClass(OrangePawn.class.getName());
                listOfWriters.get(0).println("YOURCOLOR " + BluePawn.class.getName());
                listOfWriters.get(1).println("YOURCOLOR " + OrangePawn.class.getName());
                break;
            case 3:
                listOfPlayers.get(0).setColorClass(BluePawn.class.getName());
                listOfPlayers.get(1).setColorClass(BlackPawn.class.getName());
                listOfPlayers.get(2).setColorClass(YellowPawn.class.getName());
                listOfWriters.get(0).println("YOURCOLOR " + BluePawn.class.getName());
                listOfWriters.get(1).println("YOURCOLOR " + BlackPawn.class.getName());
                listOfWriters.get(2).println("YOURCOLOR " + YellowPawn.class.getName());
                break;
            case 4:
                listOfPlayers.get(0).setColorClass(RedPawn.class.getName());
                listOfPlayers.get(1).setColorClass(BlackPawn.class.getName());
                listOfPlayers.get(2).setColorClass(YellowPawn.class.getName());
                listOfPlayers.get(3).setColorClass(GreenPawn.class.getName());
                listOfWriters.get(0).println("YOURCOLOR " + RedPawn.class.getName());
                listOfWriters.get(1).println("YOURCOLOR " + BlackPawn.class.getName());
                listOfWriters.get(2).println("YOURCOLOR " + YellowPawn.class.getName());
                listOfWriters.get(3).println("YOURCOLOR " + GreenPawn.class.getName());
                break;
            case 6:
                listOfPlayers.get(0).setColorClass(BluePawn.class.getName());
                listOfPlayers.get(1).setColorClass(RedPawn.class.getName());
                listOfPlayers.get(2).setColorClass(BlackPawn.class.getName());
                listOfPlayers.get(3).setColorClass(OrangePawn.class.getName());
                listOfPlayers.get(4).setColorClass(YellowPawn.class.getName());
                listOfPlayers.get(5).setColorClass(GreenPawn.class.getName());
                listOfWriters.get(0).println("YOURCOLOR " + BluePawn.class.getName());
                listOfWriters.get(1).println("YOURCOLOR " + RedPawn.class.getName());
                listOfWriters.get(2).println("YOURCOLOR " + BlackPawn.class.getName());
                listOfWriters.get(3).println("YOURCOLOR " + OrangePawn.class.getName());
                listOfWriters.get(4).println("YOURCOLOR " + YellowPawn.class.getName());
                listOfWriters.get(5).println("YOURCOLOR " + GreenPawn.class.getName());
                break;
        }
    }

    /**
     * Method using lineDivider to divide messagefromClient into String including coordinates of move
     * @param messageFromClient message if move is valid or invalid
     * @return appropriate String including coordinates of executed move
     * @throws IOException
     */
    private String pawnHasBeenMovedOrNot(String messageFromClient) throws IOException {
        List<Integer> locations = LineDivider.getInstance().divideLine(messageFromClient, 5);
        return MoveDirector.getInstance().movePawn(locations.get(0), locations.get(1), locations.get(2),
                locations.get(3), Board.getInstance().getBoardGridPane());
    }

    /**
     * Sending an message to all players
     * @param message Content of message
     */
    private void sendToAll(String message) {
        for (PrintWriter writer : listOfWriters) {
            writer.println(message);
        }
    }

    /**
     * Checking if all connected players are ready
     * @param listOfPlayers current list of players
     * @return true if they are ready, false if not
     */
    private boolean allConnectedPlayersAreReady(List<Player> listOfPlayers) {
        if (readyPlayers.size() != listOfPlayers.size())
            return false;
        for (Player player : listOfPlayers) {
            if (!readyPlayers.contains(player))
                return false;
        }
        return true;
    }

    /**
     * Giving info how many players take part in game
     * @param listOfPlayers current list of players
     * @return amount of allowed players to start a game
     */
    private boolean numberOfPlayersAllowsStartingGame(List<Player> listOfPlayers) {
        int numberOfPlayers = listOfPlayers.size();
        return numberOfPlayers == 2 || numberOfPlayers == 3 || numberOfPlayers == 4 || numberOfPlayers == 6;
    }

    /**
     * Sending info to player when he is made active
     */
    private void sendActivatingMessageToActivePlayer() {
        game.getActivePlayer().getWriter().println("ACTIVATE");
    }

    /**
     * Sending info to all players when they are made inactive
     */
    private void sendDeactivatingMessageToInactivePlayers() {
        for (Player player : game.getListOfActivePlayers()) {
            if (!player.equals(game.getActivePlayer()))
                player.getWriter().println("DEACTIVATE");
        }
    }

    /**
     * Deactivating moves of player which is a winner - allowing the rest to fully end a game
     */
    private void deactivateWinner() {
        game.getActivePlayer().getWriter().println("DEACTIVATE");
    }

    /**
     * Inner class used to handle client
     * Each instance of class represent other client
     */
    public class Player implements Runnable {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String colorClass;

        public Player(Socket socket) {
            System.out.println("Player has been created");
            this.socket = socket;
            try {
                writer = new PrintWriter(this.socket.getOutputStream(), true);
                listOfWriters.add(writer);
                reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            new JFXPanel();

            try {
                String messageFromClient;

                while ((messageFromClient = reader.readLine()) != null) {
                    if (messageFromClient.startsWith("JOIN")) {
                        readyPlayers.add(this);
                        if (allConnectedPlayersAreReady(listOfPlayers) && numberOfPlayersAllowsStartingGame(readyPlayers)) {

                            int size = listOfPlayers.size();
                            String boardFile;
                            String panelFile = null;
                            if (size == 2) {
                                boardFile = ViewManager.getInstance().getBoardViewForTwo();
                                panelFile = ViewManager.getInstance().getPanelForTwo();
                            }
                            else if (size == 3) {
                                boardFile = ViewManager.getInstance().getBoardViewForThree();
                                panelFile = ViewManager.getInstance().getPanelForThree();
                            }
                            else if (size == 4) {
                                boardFile = ViewManager.getInstance().getBoardViewForFour();
                                panelFile = ViewManager.getInstance().getPanelForFour();
                            }
                            else {
                                boardFile = ViewManager.getInstance().getBoardViewForSix();
                                panelFile = ViewManager.getInstance().getPanelForSix();
                            }
                            Board.getInstance().setBoardFilename(boardFile);
                            sendToAll(panelFile);
                            sendColorToEachPlayer();

                            game = new Game(readyPlayers);
                            game.setStartingPlayer();
                            sendActivatingMessageToActivePlayer();
                            sendDeactivatingMessageToInactivePlayers();
                        }
                    }
                    else if (messageFromClient.startsWith("MOVE")) {
                        String response = pawnHasBeenMovedOrNot(messageFromClient);
                        sendToAll(response);
                        if(MoveValidatorImpl.getInstance().getCounter() > 0) {
                            MoveValidatorImpl.getInstance().resetFields();
                            game.changeActivePlayer();
                            sendActivatingMessageToActivePlayer();
                            sendDeactivatingMessageToInactivePlayers();
                        }
                    }
                    else if (messageFromClient.startsWith("ENDMOVE")) {
                        if (Board.getInstance().hasNewWinner()) {
                            deactivateWinner();
                            game.insertActivePlayerWhoFinishedInResultList();
                            System.out.println("Winner!");
                            System.out.println("Result list size: " + game.getResultList().size());
                            System.out.println("Players list size: " + readyPlayers.size());
                            if (readyPlayers.size() > 0) {
                                sendActivatingMessageToActivePlayer();
                                sendDeactivatingMessageToInactivePlayers();
                            }
                            else {
                                sendToAll("FINISH");
                            }
                        }
                        else {
                            game.changeActivePlayer();
                            sendActivatingMessageToActivePlayer();
                            sendDeactivatingMessageToInactivePlayers();
                        }
                        MoveValidatorImpl.getInstance().resetFields();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public PrintWriter getWriter() {
            return writer;
        }

        public String getColorClass() {
            return colorClass;
        }

        public void setColorClass(String colorClass) {
            this.colorClass = colorClass;
        }
    }
}
