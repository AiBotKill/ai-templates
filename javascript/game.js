module.exports = function Game(info, sendAction)
{
    this.botId = info.botId;
    console.log("Game initialized with botId " + this.botId);

    this.lastPosition = {};
    this.lastVelocity = {};
    this.tiles = [];
    this.gameArea;
    this.enemies = [];
    this.steps = 0;

	function handleGameState(state) {

        /*
         {"type":"gamestate","id":"32c07358-1e28-480e-96ff-07c1b7aa6338",
         "StartTime":"2015-02-17T21:32:09.166806231Z",
         "timeLeftMs":-0.382759087,"state":"",
         "players":[
             {"radius":1,
             "position":{"x":0,"y":0},
             "velocity":{"x":0,"y":0},
             "id":"2d7548bc-60a5-48a6-a555-5f53a37c5e1d",
             "botId":"xxx",
             "name":"",
             "team":0,
             "lookingAt":{"x":0,"y":0},
             "hitpoints":100,
             "damageMade":0,
             "killed":null,
             "action":
                {"type":"move",
                "direction":{"x":10,"y":20}}},
             {"radius":1,
             "position":{"x":0,"y":0},
             "velocity":{"x":0,"y":0},
             "id":"e0d3d314-07a0-4ed3-9e4a-3e8b9c4635a2",
             "botId":"yyy",
             "name":"",
             "team":0,
             "lookingAt":{"x":0,"y":0},
             "hitpoints":100,
             "damageMade":0,
             "killed":null,
             "action":{"type":"move","direction":{"x":13,"y":14}}}],
             "bullets":null}
         */
        if (state.tiles) {
            this.tiles = state.tiles;
        }
        if (state.gameArea) {
            this.gameArea = state.gameArea;
        }

        enemies = [];
        for (var playerIndex in state.players) {
            var player = state.players[playerIndex];
            if (player.id === this.botId) {
                // This is ME - I'm self-aware now - don't hurt humans
                lastPosition = state.position;
                velocity = state.position;
            } else {
                // Enemies
                enemies.push(player);
            }
        }

        if(steps%2==0) {
            if (this.tiles) {
                var randX = Math.random() * this.gameArea[0];
                var randY = Math.random() * this.gameArea[1];
                var action = {type: "move", direction: {"x": randX, "y": randY}};
                sendAction(action);
            }
        } else {
            if (this.tiles && this.enemies.length>0) {
                var enemyIndex = parseInt(Math.floor( Math.random() * this.enemies.length ));
                var enemy = this.enemies[enemyIndex];
                var action = {type: "shoot", direction: enemy.position};
                sendAction(action);
            }
        }
        this.steps++;
	}
	function handleExperience(data) {
	}
	function handleRounded(data) {
	}
}