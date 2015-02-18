module.exports = function Game(info, sendAction)
{
    this.botId = info.botId;
    console.log("Game initialized with botId " + this.botId);

    this.lastPosition;
    this.lastVelocity;
    this.tiles = [];
    this.gameArea;
    this.enemies = [];
    this.steps = 0;

    /*
     {"type":"gameState",
     "timeLeft":10,
     "id":"bc30b02d-ab95-4aa4-aa00-5d612f87bb27",
     "startTime":"0001-01-01T00:00:00Z",
     "timeLimit":10000000000,
     "state":"new",
     "gameArea":[0,0],
     "mode":"",
     "environment":"",
     "tiles":null,
     "players":[{"radius":1,
     "position":{"x":0,"y":0},
     "velocity":{"x":0,"y":0},
     "id":"216a1ad7-888c-411a-bf1a-1379c90b6fa3",
     "botId":"xxx"
     ,"name":"","team":0,
     "lookingAt":{"x":0,"y":0},
     "hitpoints":100,
     "damageMade":0,
     "killed":null,
     "lastFired":"0001-01-01T00:00:00Z",
     "linkdead":false,
     "action":{"type":"","direction":null}},
     {"radius":1,"position":{"x":0,"y":0},"velocity":{"x":0,"y":0},"id":"3bd395b4-e5c2-4929-a6d5-659cb6cec88c","botId":"yyy","name":"","team":0,"lookingAt":{"x":0,"y":0},"hitpoints":100,"damageMade":0,"killed":null,"lastFired":"0001-01-01T00:00:00Z","linkdead":false,"action":{"type":"","direction":null}}],"bullets":null,"collisions":null,"startingPositions":null}
     */
	this.handleGameState = function (state) {
        if(this.steps===0) {
            console.log("Received game state: " + JSON.stringify(state));
        }

        if (state.tiles!==undefined) {
            console.log("Set tiles");
            this.tiles = state.tiles;
        }
        if (state.gameArea!==undefined) {
            console.log("Set gameArea");
            this.gameArea = state.gameArea;
        }

        this.enemies = [];
        for (var playerIndex in state.players) {
            //console.log("Parsing player " + playerIndex);
            var player = state.players[playerIndex];
            //console.log("player.botId=" + player.botId + " my id=" + this.botId);
            if (player.botId === this.botId) {
                //console.log("Found myself");
                // This is ME - I'm self-aware now - don't hurt humans
                this.lastPosition = player.position;
                this.lastVelocity = player.velocity;
            } else {
                // Enemies
                this.enemies.push(player);
            }
        }
        if(this.lastPosition!==undefined) {
            console.log("x: " + this.lastPosition.x + " y: " + this.lastPosition.y);
        }
        if(this.steps%12==0) {
            if (this.tiles!==undefined) {
                var randX = Math.random() * this.gameArea[0];
                var randY = Math.random() * this.gameArea[1];
                var action = {type: "move", direction: {"x": randX, "y": randY}};
                console.log("move action: " + JSON.stringify(action));
                sendAction(action);
            }
        } else {
            if (this.enemies.length>0) {
                var enemyIndex = parseInt(Math.floor( Math.random() * this.enemies.length ));
                var enemy = this.enemies[enemyIndex];
                var action = {type: "shoot", direction: enemy.position};
                console.log("shoot action: " + JSON.stringify(action));
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