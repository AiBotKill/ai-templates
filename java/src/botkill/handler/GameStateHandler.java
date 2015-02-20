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

    @Override
    public String getMessage() {
        return nextAction.getMessage();
    }

    @Override
    public void handle(GameState state) {
        // TODO: IMPLEMENT GAME STATE HANDLING
        nextAction = new ActionMessage();
        nextAction.setActionType(Action.SHOOT);
        Vector2d currentPos = state.getMyPlayer().getPosition();
        nextAction.setDirection(new Vector2d(currentPos.getX()+1.0f, currentPos.getY()+1.0f));
    }
}
