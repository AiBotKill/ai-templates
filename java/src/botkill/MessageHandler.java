package botkill;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 23.11.2014
 * Time: 2:04
 */
public class MessageHandler {

    private Game game;
    private boolean registered = false;

    public static final String GAME_OVER = "gameEnd";
    public static final String GAME_STATE = "gameState";
    public static final String REPLY = "reply";

    public String handle(String msg) {
        String msgType = "NO MESSAGE TYPE";
        JSONObject msgJson = null;
        try {
            msgJson = new JSONObject(msg);
            msgType = msgJson.getString("type");
        } catch (Exception e) {
            System.out.println("Failed to parse msgtype from message " + msg + ". Exception: " + e.getMessage());
            System.exit(1); // TODO: return here instead of giving up
        }

        System.out.println("Received message of type " + msgType);

        String response = null;

        switch (msgType) {
            case REPLY:
                if (msgJson.getString("status").equals("error")) {
                    System.out.println("Received an error from game server: " + msgJson.getString("error"));
                    System.exit(1);
                } else if (!registered) {
                    registered = true;
                    String myBotId = msgJson.getString("id");
                    game = new Game();
                    game.createPlayer(myBotId);
                    System.out.println("Bot registered OK with id: " + myBotId.split("-")[0]);
                }
                break;
            case GAME_STATE:
                if (game.getId() == null) {
                    game.setGameData(msgJson);
                }
                // Update game state and return possible action message
                response = game.update(msgJson);
                break;
            case GAME_OVER:
                game = null;
                response = GAME_OVER;
            default:
                System.out.println("Unknown message type received: " + msgType + " in message: " + msg);
        }
        return response;
    }
}
