import javax.swing.*;
import java.awt.*;

public class Break extends JFrame {
    public Break () {
        init();
    }

    private void init() {
        add(new Tablero());
        setTitle("Breakout");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

    public static void main (String[] args) {
        EventQueue.invokeLater(()->{
            var game = new Break();
            game.setVisible(true);
        });
    }
}
