import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private static String host = "localhost";
    private static int port = 8000;
    private String username;

    private JTextArea messageArea;
    private JTextField messageField;

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public Main() {
        super("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        JPanel mainPanel = new JPanel(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(messageArea);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);

        // Prompt user to enter username
        while(username == null || username.isEmpty()) {
            username = JOptionPane.showInputDialog("Enter your username:");
        }

        // Connect to server
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);
            messageArea.append("Connected to server on " + host + ":" + port + "\n");
        } catch(Exception e) {
            messageArea.append("Error connecting to server: " + e + "\n");
        }

        // Start a thread to read messages from the server
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String input = in.readLine();
                        if(input == null) {
                            break;
                        }
                        messageArea.append(input + "\n");
                    }
                } catch(Exception e) {
                    messageArea.append("Error reading from server: " + e + "\n");
                }
            }
        });
        thread.start();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if(!message.isEmpty()) {
            out.println(message);
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
