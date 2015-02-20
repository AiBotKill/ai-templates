package botkill.handler;

import botkill.ActionMessage;
import botkill.GameState;
import botkill.enums.Action;
import botkill.util.Vector2d;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 24.11.2014
 * Time: 1:33
 */
public class GameStateHandler implements Handler<GameState> {

    private ActionMessage nextAction;
    private Vector2d shootingDir;

    @Override
    public String getMessage() {
        return nextAction.getMessage();
    }

    @Override
    public void handle(GameState state) {
        // TODO: IMPLEMENT GAME STATE HANDLING
        nextAction = new ActionMessage();
        nextAction.setActionType(Action.SHOOT);
        if (shootingDir == null) {
            Vector2d currentPos = state.getMyPlayer().getPosition();
            shootingDir = new Vector2d(currentPos.getX(), currentPos.getY()-1);
        }

        nextAction.setDirection(shootingDir);
        shootingDir.rotate(5*Math.PI/180);
    }
}
