package server;

import org.junit.Before;
import org.junit.Test;
import server.Game;
import server.Server;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {
    private Game game;
    private List<Server.Player> listOfPlayers;
    private Server.Player playerBlue = null;
    private Server.Player playerRed = null;
    private Server.Player playerBlack = null;
    private Server.Player playerOrange = null;
    private Server.Player playerYellow = null;
    private Server.Player playerGreen = null;

    @Before
    public void initializeFields() {
        playerBlue = mock(Server.Player.class);
        when(playerBlue.getColorClass()).thenReturn("board.pawn.BluePawn");
        playerRed = mock(Server.Player.class);
        when(playerRed.getColorClass()).thenReturn("board.pawn.RedPawn");
        playerBlack = mock(Server.Player.class);
        when(playerBlack.getColorClass()).thenReturn("board.pawn.BlackPawn");
        playerOrange = mock(Server.Player.class);
        when(playerOrange.getColorClass()).thenReturn("board.pawn.OrangePawn");
        playerYellow = mock(Server.Player.class);
        when(playerYellow.getColorClass()).thenReturn("board.pawn.YellowPawn");
        playerGreen = mock(Server.Player.class);
        when(playerGreen.getColorClass()).thenReturn("board.pawn.GreenPawn");
        listOfPlayers = new ArrayList<>();

        listOfPlayers.add(playerBlue);
        listOfPlayers.add(playerGreen);
        listOfPlayers.add(playerBlack);
        listOfPlayers.add(playerRed);
        listOfPlayers.add(playerYellow);
        listOfPlayers.add(playerOrange);
        game = new Game(listOfPlayers);
    }

    @Test
    public void checkWhetherListOfPlayersContainsStartingPlayer() {
        game.setStartingPlayer();
        assertTrue(game.getListOfActivePlayers().contains(game.getActivePlayer()));
    }

    @Test
    public void startingPlayerIsTheFirstElementOfTheQueue() {
        game.setStartingPlayer();
        assertEquals(game.getActivePlayer(), game.getPlayersQueue().peek());
    }

    @Test
    public void activePlayerIsTheFirstInQueueAfterChangingActivePlayer() {
        game.setStartingPlayer();
        game.changeActivePlayer();
        assertEquals(game.getActivePlayer(), game.getPlayersQueue().peek());
    }

    @Test
    public void previousActivePlayerIsStillInTheQueueAfterChangingActivePlayer() {
        game.setStartingPlayer();
        Server.Player player = game.getActivePlayer();
        game.changeActivePlayer();
        assertTrue(game.getPlayersQueue().contains(player));
    }

    @Test
    public void listOfPlayersRemovedWinningPlayer() {
        game.setStartingPlayer();
        game.insertActivePlayerWhoFinishedInResultList();
        assertEquals(5, game.getListOfActivePlayers().size());
    }

    @Test
    public void resultListAddedWinningPlayer() {
        game.setStartingPlayer();
        game.insertActivePlayerWhoFinishedInResultList();
        assertEquals(1, game.getResultList().size());
    }

    @Test
    public void activePlayerWhoFinishedIsNotLongerInActivePlayersList() {
        game.setStartingPlayer();
        Server.Player player = game. getActivePlayer();
        game.insertActivePlayerWhoFinishedInResultList();
        assertFalse(game.getListOfActivePlayers().contains(player));
    }

    @Test
    public void activePlayerWhoFinishedIsNowInResultList() {
        game.setStartingPlayer();
        Server.Player player = game.getActivePlayer();
        game.insertActivePlayerWhoFinishedInResultList();
        assertTrue(game.getResultList().contains(player));
    }
}
