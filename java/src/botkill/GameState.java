package botkill;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 24.11.2014
 * Time: 1:31
 */
public class GameState {

    private int timeLeftMs;
    private Player myPlayer;
    private List<Player> players;
    private List<Bullet> bullets;

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getTimeLeftMs() {
        return timeLeftMs;
    }

    public void setTimeLeftMs(int timeLeftMs) {
        this.timeLeftMs = timeLeftMs;
    }
}
