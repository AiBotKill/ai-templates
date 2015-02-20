package botkill;

import botkill.handler.CreatePlayerHandler;
import botkill.handler.Handler;
import botkill.handler.LevelUpHandler;
import botkill.util.Vector2d;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 23.11.2014
 * Time: 23:31
 */
public class Player {

    private String id;
    private String botId;
    private String name;

    private Integer currentHp;
    private int hitpoints;
    private int speed;
    private int sight;
    private int hearing;
    private Weapon weapon;

    private Vector2d position;
    private Vector2d velocity;
    private Vector2d lookingAt;
    private Vector2d shootAt;
    private Integer team;
    private Boolean killed;
    private Integer damageMade;
    private ActionMessage action;

    public Player() {}

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getHearing() {
        return hearing;
    }

    public void setHearing(int hearing) {
        this.hearing = hearing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector2d getLookingAt() {
        return lookingAt;
    }

    public void setLookingAt(Vector2d lookingAt) {
        this.lookingAt = lookingAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector2d getShootAt() {
        return shootAt;
    }

    public void setShootAt(Vector2d shootAt) {
        this.shootAt = shootAt;
    }

    public int getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Integer getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setTeam(Integer team) {
        this.team = team;
    }

    public Boolean getKilled() {
        return killed;
    }

    public void setKilled(Boolean killed) {
        this.killed = killed;
    }

    public Integer getDamageMade() {
        return damageMade;
    }

    public void setDamageMade(Integer damageMade) {
        this.damageMade = damageMade;
    }

    public ActionMessage getAction() {
        return action;
    }

    public void setAction(ActionMessage action) {
        this.action = action;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }
}
