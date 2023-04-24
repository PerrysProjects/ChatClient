import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    private static String host = "localhost";
    private static int port = 8000;

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(host, port);
        System.out.println("Connected to server on " + host + ":" + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter username
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        out.println(username);

        // Start a thread to read messages from the server
        Thread thread = new Thread(() -> {
            try {
                while(true) {
                    String input = in.readLine();
                    if(input == null) {
                        break;
                    }
                    System.out.println(input);
                }
            } catch(IOException e) {
                System.out.println("Error reading from server: " + e);
            }
        });
        thread.start();

        // Prompt user to enter messages and send them to the server
        while(true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            out.println(message);
        }

        // Close the socket and exit
        socket.close();
        System.exit(0);
    }
}