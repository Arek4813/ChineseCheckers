package bot;

/**
 * Implementation of this interface depends on state of bot which may be active or inactive
 */
public interface BotState {
    /**
     * Getting the view
     * @param view current view of board
     */
    void reactOnViewMsg(String view);

    /**
     * Getting the color for bot
     * @param receivedMessage message containing color for bot
     */
    void reactOnColorMsg(String receivedMessage);

    /**
     * Reacting on valid moves of other players
     * @param msg message from executed move
     */
    void reactOnValidMsg(String msg);

    /**
     * Reacting on invalid moves of other players
     * @param msg message from (no)executed move
     * @throws ClassNotFoundException
     */
    void reactOnInvalidMsg(String msg) throws ClassNotFoundException;

    /**
     * React on being active
     * @throws ClassNotFoundException
     */
    void reactOnActivateMsg() throws ClassNotFoundException;

    /**
     * React on being inactive
     */
    void reactOnDeactivateMsg();

    /**
     * React on end game (someone wins)
     */
    void reactOnFinishMsg();

}
