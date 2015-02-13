package botkill;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 23.11.2014
 * Time: 1:55
 */
public class MessageListener extends Thread {

    TCPClient client;
    MessageHandler handler;
    MessageBuffer messageBuffer;
    String aiId;

    public MessageListener(TCPClient client, MessageBuffer buffer, String aiId) {
        this.client = client;
        handler = new MessageHandler();
        messageBuffer = buffer;
        this.aiId = aiId;
    }

    public void run() {
        System.out.println("Starting game loop...");

        while (!isInterrupted()) {

            String msg = messageBuffer.read(aiId);

            if (msg != null) {
                String response = handler.handle(msg);
                if (response != null) {

                    // Check if our game ended. Not just one round but the whole game.
                    if (response == MessageHandler.GAME_OVER) {
                        // Interrupt this game loop but continue listening new join requests in the AI thread
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

}
