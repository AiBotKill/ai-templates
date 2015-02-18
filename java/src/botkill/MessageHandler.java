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

    public static final String GAME_OVER = "gameover";
    public static final String JOIN_REQUEST = "joinrequest";
    public static final String GAME_STATE = "gamestate";
    public static final String EXPERIENCE = "experience";
    public static final String ROUND_END = "roundend";
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
                    System.out.println("Bot registered OK with id: " + msgJson.getString("id").split("-")[0]);
                }
                break;
            case JOIN_REQUEST:
                // NOT SUPPORTED
                // Game data received. Create our own game object.
                //game = new Game(msgJson);
                // Create the player and return createplayer message
                //return game.createPlayer();
            case GAME_STATE:
                if (game == null) {
                    game = new Game(msgJson);
                }
                // Update game state and return possible action message
                response = game.update(msgJson);
                break;
            case EXPERIENCE:
                // NOT SUPPORTED
                // Assign exp points in player/weapon skills and return levelup message
                //response = game.getMyPlayer().levelUp(msgJson);
                break;
            case GAME_OVER:
                game = null;
                response = GAME_OVER;
            case ROUND_END:
                // NOT SUPPORTED
                //assert game != null;
                //game.reset();
            default:
                System.out.println("Unknown message type received: " + msgType + " in message: " + msg);
        }
        return response;
    }
}
