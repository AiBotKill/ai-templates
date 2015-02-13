package botkill;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jhell on 13.2.2015.
 *
 * Thread safe message buffer for message queuing.
 * Each game loop will have it's own key that will have a queue of messages.
 */
public class MessageBuffer {

    private Map<String, Queue<String>> buf;

    public MessageBuffer() {
        buf = new ConcurrentHashMap<String, Queue<String>>();
    }

    /**
     * Add message to the tail of given key's queue
     * @param key
     * @param msg
     */
    public void add(String key, String msg) {
        if (buf.containsKey(key)) {
            buf.get(key).add(msg);
        } else {
            Queue<String> msgList = new ConcurrentLinkedQueue<String>();
            buf.put(key, msgList);
        }
    }

    /**
     * Retrieves the head of give key's queue. Return null if no messages in queue.
     * @param key
     * @return
     */
    public String read(String key) {
        if (buf.containsKey(key) && buf.get(key).size() > 0) {
            return buf.get(key).poll();
        }

        return null;
    }
}
