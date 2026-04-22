import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Client implements ActionListener {

    // ── Configuration constants ──────────────────────────────────────────────
    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 6001;

    // ── UI components ────────────────────────────────────────────────────────
    private JTextField  text;
    private static JPanel    a1;
    private static Box       vertical = Box.createVerticalBox();
    private static JFrame    f        = new JFrame();

    // ── Network components ───────────────────────────────────────────────────
    private static DataOutputStream dout;
    private static volatile boolean connected = false;

    // ────────────────────────────────────────────────────────────────────────
    // Constructor — builds the GUI
    // ────────────────────────────────────────────────────────────────────────
    Client() {
        f.setLayout(null);

        // Header panel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(85, 107, 4));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // Close icon
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image     i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        JLabel    j1 = new JLabel(new ImageIcon(i2));
        j1.setBounds(5, 20, 25, 25);
        j1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) { System.exit(0); }
        });
        p1.add(j1);

        // Profile picture
        ImageIcon pp1  = new ImageIcon(ClassLoader.getSystemResource("icons/pp2.jpeg"));
        Image     pp1a = pp1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        JLabel    j2   = new JLabel(new ImageIcon(pp1a));
        j2.setBounds(40, 10, 50, 50);
        p1.add(j2);

        // User name
        JLabel name1 = new JLabel("User 2");
        name1.setBounds(110, 15, 100, 40);
        name1.setForeground(Color.WHITE);
        name1.setFont(new Font("", Font.BOLD, 18));
        p1.add(name1);

        // Status label
        JLabel status1 = new JLabel("Active Now");
        status1.setBounds(110, 15, 120, 70);
        status1.setForeground(Color.WHITE);
        status1.setFont(new Font("", Font.BOLD, 12));
        p1.add(status1);

        // Chat area
        a1 = new JPanel();
        a1.setBounds(10, 75, 430, 550);
        f.add(a1);

        // Message input
        text = new JTextField();
        text.setBounds(5, 640, 310, 50);
        f.add(text);

        // Send button
        JButton send = new JButton("Send");
        send.setBounds(320, 640, 123, 50);
        send.addActionListener(this);
        send.setFont(new Font("", Font.BOLD, 14));
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(750, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(new Color(245, 245, 220));
        f.setVisible(true);
    }

    // ────────────────────────────────────────────────────────────────────────
    // Send button handler
    // ────────────────────────────────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent ae) {
        String out = text.getText().trim();
        if (out.isEmpty()) return; // ignore empty sends

        if (!connected || dout == null) {
            System.err.println("[Client] Cannot send — not connected to server.");
            return;
        }

        try {
            // Render own message on the right
            JPanel p2    = ChatUtils.formatLabel(out);
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));

            a1.setLayout(new BorderLayout());
            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);
            text.setText("");

            f.repaint();
            f.revalidate();

        } catch (IOException e) {
            System.err.println("[Client] Failed to send message: " + e.getMessage());
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // Main — connects to the server, then listens on a background thread
    // so the Swing UI stays responsive
    // ────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        new Client();
        System.out.println("[Client] Connecting to " + HOST + ":" + PORT + "...");

        try {
            Socket           s   = new Socket(HOST, PORT);
            DataInputStream  din = new DataInputStream(s.getInputStream());
            dout                 = new DataOutputStream(s.getOutputStream());
            connected            = true;
            System.out.println("[Client] Connected to server.");

            // ── Background thread — reads incoming messages ──────────────
            Thread listenerThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg = din.readUTF(); // blocks until message arrives

                        // Update UI on the Event Dispatch Thread (Swing thread-safety)
                        SwingUtilities.invokeLater(() -> {
                            a1.setLayout(new BorderLayout());
                            JPanel panel = ChatUtils.formatLabel(msg);
                            JPanel left  = new JPanel(new BorderLayout());
                            left.add(panel, BorderLayout.LINE_START);
                            vertical.add(left);
                            vertical.add(Box.createVerticalStrut(10));
                            a1.add(vertical, BorderLayout.PAGE_START);
                            f.revalidate();
                        });
                    }
                } catch (EOFException e) {
                    // Server disconnected cleanly
                    System.out.println("[Client] Server disconnected.");
                } catch (IOException e) {
                    System.err.println("[Client] Connection error: " + e.getMessage());
                } finally {
                    connected = false;
                    try { s.close(); } catch (IOException ignored) {}
                }
            });

            listenerThread.setDaemon(true); // dies when main process exits
            listenerThread.start();

        } catch (ConnectException e) {
            System.err.println("[Client] Could not connect — is the server running on port " + PORT + "?");
        } catch (IOException e) {
            System.err.println("[Client] Network error: " + e.getMessage());
        }
    }
}
