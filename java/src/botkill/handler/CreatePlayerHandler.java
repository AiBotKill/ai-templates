package botkill.handler;

import botkill.Game;
import botkill.Player;
import botkill.Weapon;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 24.11.2014
 * Time: 1:10
 */
public class CreatePlayerHandler implements Handler<Game> {

    private Player player;
    private String gameId;

    public CreatePlayerHandler(Player player) {
        this.player = player;
    }

    @Override
    public String getMessage() {
        JSONObject join = new JSONObject();
        join.put("player", new JSONObject(new Gson().toJson(player)));
        join.put("gameId", gameId);
        return join.toString();
    }

    @Override
    public void handle(Game game) {
        gameId = game.getId();
        // TODO: IMPLEMENT PLAYER CREATION

        player.setName("Bot");
        player.setHitpoints(50);
        player.setSpeed(50);
        player.setSight(50);
        player.setHearing(50);
        player.setWeapon(new Weapon());
    }
}
