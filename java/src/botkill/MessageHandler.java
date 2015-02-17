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

    public static final String GAME_OVER = "gameover";
    public static final String JOIN_REQUEST = "joinrequest";
    public static final String GAME_STATE = "gamestate";
    public static final String EXPERIENCE = "experience";
    public static final String ROUND_END = "roundend";

    public String handle(String msg) {
        String msgType = "NO MESSAGE TYPE";
        JSONObject msgObject = null;
        try {
            JSONObject msgJson = new JSONObject(msg);
            msgType = (String) msgJson.keySet().toArray()[0];
            msgObject = msgJson.getJSONObject(msgType);
        } catch (Exception e) {
            System.out.println("Failed to parse msgtype. Exception: " + e.getMessage());
        }

        String response = null;

        switch (msgType) {
            case JOIN_REQUEST:
                // Game data received. Create our own game object.
                game = new Game(msgObject);
                // Create the player and return createplayer message
                return game.createPlayer();
            case GAME_STATE:
                // Update game state and return possible action message
                response = game.update(msgObject);
                break;
            case EXPERIENCE:
                // Assign exp points in player/weapon skills and return levelup message
                response = game.getMyPlayer().levelUp(msgObject);
                break;
            case GAME_OVER:
                game = null;
                response = GAME_OVER;
            case ROUND_END:
                assert game != null;
                game.reset();
            default:
                System.out.println("Unknown message type received: " + msgType);
        }

        return response;
    }
}
