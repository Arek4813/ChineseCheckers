package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Management of game - functionality connected with: changing active players, appropriate queue, making list of
 * players that have finished a game
 */
public class Game {
    private static final String[] colorsClassesPool = {"board.pawn.BluePawn", "board.pawn.RedPawn", "board.pawn.BlackPawn", "board.pawn.OrangePawn", "board.pawn.YellowPawn", "board.pawn.GreenPawn"};
    private List<Server.Player> listOfActivePlayers;
    private List<Server.Player> resultList;
    private Server.Player activePlayer;
    private Queue<Server.Player> playersQueue;

    public Game(List<Server.Player> listOfActivePlayers) {
        this.listOfActivePlayers = listOfActivePlayers;
        initializeQueue();
        resultList = new ArrayList<>();
        System.out.println("Game has been created");
    }

    /**
     * Randomly setting an starting player and printing an proper info
     */
    public void setStartingPlayer() {
        int indexOfStartingPlayer = (int)(Math.random()* listOfActivePlayers.size());
        activePlayer = listOfActivePlayers.get(indexOfStartingPlayer);
        while (playersQueue.peek() != activePlayer) {
            Server.Player player = playersQueue.poll();
            playersQueue.offer(player);
        }
        System.out.println("Active player has been set");
    }

    /**
     * Changing an active player with correct players queue
     */
    public void changeActivePlayer() {
        Server.Player player = playersQueue.poll();
        playersQueue.offer(player);
        activePlayer = playersQueue.peek();
    }

    /**
     * Putting active players to list of players that have already finished a game
     */
    public void insertActivePlayerWhoFinishedInResultList() {
        playersQueue.poll();
        listOfActivePlayers.remove(activePlayer);
        resultList.add(activePlayer);
        System.out.println("Player " + activePlayer + " has been moved to result list.");
        activePlayer = playersQueue.peek();
    }

    /**
     * Starting an queue - used in mechanism of changing of active players
     */
    private void initializeQueue() {
        playersQueue = new ConcurrentLinkedQueue<>();
        for (String colorClass: colorsClassesPool) {
            for (Server.Player player : listOfActivePlayers) {
                System.out.println(player.getColorClass() + " " + colorClass);
                if (colorClass.equals(player.getColorClass())) {
                    playersQueue.add(player);
                }
            }
        }
        System.out.println("Queue has been initialized");
    }

    public List<Server.Player> getListOfActivePlayers() {
        return listOfActivePlayers;
    }

    public List<Server.Player> getResultList() {
        return resultList;
    }

    public Server.Player getActivePlayer() {
        return activePlayer;
    }

    public Queue<Server.Player> getPlayersQueue() {
        return playersQueue;
    }
}
