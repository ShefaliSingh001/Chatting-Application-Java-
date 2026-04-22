import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;

/**
 * ChatUtils — shared utility methods used by both Server and Client.
 *
 * Centralising formatLabel() here eliminates duplication and ensures
 * consistent message rendering across both sides of the chat.
 */
public class ChatUtils {

    // Prevent instantiation — this is a utility class
    private ChatUtils() {}

    /**
     * Builds a styled message bubble panel with the message text and a timestamp.
     *
     * @param message the text content of the chat message
     * @return a JPanel containing the formatted message label and timestamp
     */
    public static JPanel formatLabel(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Message bubble
        JLabel output = new JLabel(message);
        output.setFont(new Font("SansSerif", Font.PLAIN, 18));
        output.setBackground(new Color(85, 107, 4)); // olive green
        output.setForeground(Color.WHITE);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        // Timestamp
        String timestamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        JLabel time = new JLabel(timestamp);
        time.setFont(new Font("SansSerif", Font.PLAIN, 11));
        time.setForeground(Color.GRAY);
        panel.add(time);

        return panel;
    }
}
