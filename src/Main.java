import settings.Settings;
import settings.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class Main extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private JTextArea messageArea;
    private JTextField messageField;

    public Main() {
        super("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        JMenuBar menuBar = new JMenuBar();

        // Server menu content
        JMenu serverMenu = new JMenu("Server");

        JMenuItem changeServerItem = new JMenuItem("Change Server");
        changeServerItem.addActionListener(e -> {
            String newHost = JOptionPane.showInputDialog("Enter server host:");
            if(newHost != null && !newHost.isEmpty()) {
                Settings.setHost(newHost);
            }
            String newPort = JOptionPane.showInputDialog("Enter server port:");
            if(newPort != null && !newPort.isEmpty()) {
                Settings.setPort(Integer.parseInt(newPort));
            }
            if(Server.connect(Settings.getHost(), Settings.getPort(), Settings.getUsername())) {
                messageArea.setText("");
                messageArea.append("Connected to server on " + Settings.getHost() + ":" + Settings.getPort() + "\n");
            } else {
                messageArea.append("Error connecting to server: " + Server.getError() + "\n");
            }
        });

        serverMenu.add(changeServerItem);
        menuBar.add(serverMenu);

        // Settings menu content
        JMenu settingsMenu = new JMenu("Settings");

        JMenu themeMenu = new JMenu("Theme");

        JMenuItem darkThemeItem = new JMenuItem("Dark");
        darkThemeItem.addActionListener(e -> {
            Settings.setTheme(Theme.DARK);
            messageArea.setBackground(Color.BLACK);
            messageArea.setForeground(Color.WHITE);
            messageField.setBackground(Color.BLACK);
            messageField.setForeground(Color.WHITE);
            menuBar.setBackground(Color.BLACK);
            menuBar.setForeground(Color.WHITE);
            serverMenu.setBackground(Color.BLACK);
            serverMenu.setForeground(Color.WHITE);
            settingsMenu.setBackground(Color.BLACK);
            settingsMenu.setForeground(Color.WHITE);
            themeMenu.setBackground(Color.BLACK);
            themeMenu.setForeground(Color.WHITE);
        });
        themeMenu.add(darkThemeItem);

        JMenuItem whiteThemeItem = new JMenuItem("White");
        whiteThemeItem.addActionListener(e -> {
            Settings.setTheme(Theme.WHITE);
            messageArea.setBackground(Color.WHITE);
            messageArea.setForeground(Color.BLACK);
            messageField.setBackground(Color.WHITE);
            messageField.setForeground(Color.BLACK);
            menuBar.setBackground(null);
            menuBar.setForeground(Color.BLACK);
            serverMenu.setBackground(Color.WHITE);
            serverMenu.setForeground(Color.BLACK);
            settingsMenu.setBackground(Color.WHITE);
            settingsMenu.setForeground(Color.BLACK);
            themeMenu.setBackground(Color.WHITE);
            themeMenu.setForeground(Color.BLACK);
        });
        themeMenu.add(whiteThemeItem);

        settingsMenu.add(themeMenu);

        JMenuItem safeModeItem = new JMenuItem("Safe Mode");
        safeModeItem.addActionListener(e -> {
            if(Settings.isSafeMode()) {
                Settings.setSafeMode(false);
                messageArea.append("SafeMode deactivated!");
            } else {
                Settings.setSafeMode(true);
                messageArea.append("SafeMode activated!");
            }
        });
        settingsMenu.add(safeModeItem);

        menuBar.add(settingsMenu);
        setJMenuBar(menuBar);

        // Setting up the text areas
        JPanel mainPanel = new JPanel(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(messageArea);

        messageField = new JTextField();
        messageField.addActionListener(e -> {
            Server.sendMessage(messageField.getText().trim());
            messageField.setText("");
        });

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            Server.sendMessage(messageField.getText().trim());
            messageField.setText("");
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);

        // Prompt user to enter host
        while(Settings.getHost() == null || Settings.getHost().isEmpty()) {
            Settings.setHost(JOptionPane.showInputDialog("Enter server host:"));
        }
        // Prompt user to enter port
        while(Settings.getPort() <= -1) {
            Settings.setPort(Integer.parseInt(JOptionPane.showInputDialog("Enter server port:")));
        }
        // Prompt user to enter username
        while(Settings.getUsername() == null || Settings.getUsername().isEmpty()) {
            Settings.setUsername(JOptionPane.showInputDialog("Enter your username:"));
        }
        /*while(password == null || password.isEmpty()) {
            JLabel label = new JLabel("Enter yor password:");
            JPasswordField passwordField = new JPasswordField(10);
            passwordField.setEchoChar('*');
            JComponent[] jComponent = new JComponent[]{label,passwordField};
            JOptionPane.showMessageDialog(
                    null,
                    jComponent,
                    "Enter password",
                    JOptionPane.OK_OPTION
            );
            passwordField.requestFocus();
            password = String.valueOf(passwordField.getPassword());
        }*/

        // Connect to server
        if(Server.connect(Settings.getHost(), Settings.getPort(), Settings.getUsername())) {
            messageArea.append("Connected to server on " + Settings.getHost() + ":" + Settings.getPort() + "\n");
        } else {
            messageArea.append("Error connecting to server: " + Server.getError() + "\n");
        }

        // Start a thread to read messages from the server
        Thread thread = new Thread(() -> {
            try {
                while(true) {
                    String input = Server.getMessage();
                    if(input == null) {
                        break;
                    }
                    messageArea.append(input + "\n");
                }
            } catch(Exception e) {
                messageArea.append("Error reading from server: " + e + "\n");
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
