package botkill;

import java.io.IOException;

public class AI extends Thread {

    private TCPClient client;
    private MessageHandler handler;

    public AI(TCPClient client) {
        this.client = client;
        handler = new MessageHandler();
    }

    /**
     * Main thread that will read all the messages from the game server and add those in message queue
     */
    public void run() {
        System.out.println("Waiting for join requests...");

        while (!isInterrupted()) {

            // Read messages from game server
            String msg = client.readLine();

            if (msg != null) {
                String response = handler.handle(msg);
                if (response != null) {

                    // Check if our game ended. Not just one round but the whole game.
                    if (response.equals(MessageHandler.GAME_OVER)) {
                        // Interrupt this AI
                        interrupt();
                    } else {
                        client.send(response);
                    }
                }
            }

            try {
                Thread.sleep(1000/60); // Work at maximum speed of 60fps
            } catch (InterruptedException e) {
                interrupt();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClient();
        // TODO: Change the ID
        client.connect("c357b505-5e00-47ce-aa46-e307c2238a76", "v0.1");

        AI ai = new AI(client);
        ai.start();
    }
}
