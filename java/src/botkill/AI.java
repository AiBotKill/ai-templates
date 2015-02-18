package botkill;

import java.io.IOException;

public class AI implements Runnable {

    private final TCPClient client;
    private MessageHandler handler;

    public AI(TCPClient client) {
        this.client = client;
        handler = new MessageHandler();
    }

    /**
     * Main thread that will read all the messages from the game server and add those in message queue
     */
    public void run() {


        while (!Thread.currentThread().isInterrupted()) {

            // Read messages from game server
            String msg = client.readLine();

            if (msg != null) {
                String response = handler.handle(msg);
                if (response != null) {

                    // Check if our game ended. Not just one round but the whole game.
                    if (response.equals(MessageHandler.GAME_OVER)) {
                        // Interrupt this AI
                        Thread.currentThread().interrupt();
                    } else {
                        client.send(response);
                    }
                }
            }

            try {
               Thread.sleep(1000); // Work at maximum speed of 60fps
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
//        System.out.println("Thread interrupted. Closing bot...");
//        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClient();
        // TODO: Change the ID
        client.connect("32224957-e7e2-40df-b90b-ca1ac3c40f06");

        Runnable ai = new AI(client);
        new Thread(ai).run();
    }
}
