module.exports = function Game(info, sendAction)
{
    var botId = info.botId;
    console.log("Game initialized with botId " + botId);

    var currentGameId = "";
    var lastPosition = {};
    var lastVelocity = {};
    var tiles = [];
    var gameArea;
    var enemies = [];
    var steps = 0;
    var areaWidth = 0;
    var areaHeight = 0;

    var randomMovementSteps = 0;
    var shouldWeMove = 10;

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
        console.log("GameState: " + JSON.stringify(state));
        if(state.state==="end") {
            // Game over - wait for new one
            return;
        }
        if (state.id!=currentGameId) {
            currentGameId = state.id;
            // Game started (or changed to new game)
            // Do your reset stuff here
            if (state.tiles!==undefined) {
                tiles = state.tiles;
            }
            gameArea = state.gameArea;
            areaWidth = state.gameArea[0];
            areaHeight = state.gameArea[1];
        }

        enemies = [];
        for (var playerIndex in state.players) {
            var player = state.players[playerIndex];
            if (player.botId === this.botId) {
                //console.log("Found myself");
                // This is ME - I'm self-aware now - don't hurt humans
                lastPosition = player.position;
                lastVelocity = player.velocity;
            } else {
                // Enemies
                enemies.push(player);
            }
        }
        if(lastPosition!==undefined) {
            console.log("x: " + lastPosition.x + " y: " + lastPosition.y);
        }


        if(randomMovementSteps>shouldWeMove==0) {
            if (tiles!==undefined) {


                var xFactor = 1.0;
                if(lastPosition!=undefined && lastPosition.x>areaWidth/2) {
                    xFactor = 3.0;
                }
                var yFactor = 1.0;
                if(lastPosition!=undefined && lastPosition.y>areaHeight/2) {
                    yFactor = 3.0;
                }


                var randX = Math.random()*4 - xFactor;
                var randY = Math.random()*4 - yFactor;
                var action = {type: "move", direction: {"x": randX, "y": randY}};
                console.log("move action: " + JSON.stringify(action));
                sendAction(action);
            }
            randomMovementSteps = 0;
            shouldWeMove = Math.floor(Math.random()*15+10);
        } else {
            if (enemies.length>0) {
                var enemyIndex = parseInt(Math.floor( Math.random() * enemies.length ));
                var enemy = enemies[enemyIndex];
                if(enemy!==undefined) {
                    var action = {type: "shoot", direction: enemy.position};
                    console.log("shoot action: " + JSON.stringify(action));
                    sendAction(action);
                }
            }
        }
        randomMovementSteps++;
	}
	function handleExperience(data) {
	}
	function handleRounded(data) {
	}
}