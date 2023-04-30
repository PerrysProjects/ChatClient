import messages.Emoji;
import messages.Swear;
import settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server {
    private static BufferedReader in;
    private static PrintWriter out;
    private static Socket socket;

    private static Exception error;

    public static boolean connect(String host, int port, String username) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);
            return true;
        } catch(Exception e) {
            error = e;
            return false;
        }
    }

    public static void sendMessage(String message) {
        if(!message.isEmpty()) {
            out.println(message);
        }
    }

    public static String getMessage() {
        try {
            String message = in.readLine();
            if(Settings.isSafeMode()) {
                message = Swear.replaceSwears(message);
            }
            return Emoji.replaceEmojis(message);
        } catch(IOException e) {
            return new RuntimeException(e).toString();
        }
    }

    public static Exception getError() {
        return error;
    }
}
