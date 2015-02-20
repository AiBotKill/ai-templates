package botkill;

import botkill.enums.GameEnvironment;
import botkill.enums.GameMode;
import botkill.handler.GameStateHandler;
import botkill.handler.Handler;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 23.11.2014
 * Time: 23:30
 */
public class Game {

    private Handler<GameState> gameStateHandler;

    private String id = null;
    private int rounds;
    private int currentRound;
    private int roundTime;
    private GameEnvironment environment;
    private GameMode mode;
    private int playerCount;
    private float[] gameArea;

    private Player myPlayer;

    public Game() {
        gameStateHandler = new GameStateHandler();
    }

    public void setGameData(JSONObject game) {
        this.id = game.getString("id");
        this.roundTime = game.getInt("timeLimit") / 1000000000;
        this.mode = GameMode.valueOf(game.getString("mode"));
        //this.environment = GameEnvironment.valueOf(game.getString("environment"));
        this.playerCount = game.getJSONArray("players").length();
        JSONArray gameAreaArray = game.getJSONArray("gameArea");
        gameArea = new float[] { (float)gameAreaArray.getDouble(0), (float)gameAreaArray.getDouble(1) };
    }

    public void reset() {
        this.currentRound++;
    }

    public String update(JSONObject state) {
        GameState gameState = new Gson().fromJson(state.toString(), GameState.class);
        System.out.println("Game state: " + gameState.getState());
        gameState.setMyPlayer(myPlayer);
        gameStateHandler.handle(gameState);
        return gameStateHandler.getMessage();
    }

    public void createPlayer(String id) {
        myPlayer = new Player();
        myPlayer.setBotId(id);
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public GameEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public float[] getGameArea() {
        return gameArea;
    }

    public void setGameArea(float[] gameArea) {
        this.gameArea = gameArea;
    }
}
