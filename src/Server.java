import java.io.BufferedReader;
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

    public static BufferedReader getIn() {
        return in;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static Exception getError() {
        return error;
    }
}
