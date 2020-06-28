import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;

public class JavaSweeper extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel label;

    private final int COLUMNS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new JavaSweeper().setVisible(true);
    }

    private JavaSweeper() {
        game = new Game(COLUMNS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()){
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE,
                            this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (MouseEvent.BUTTON1 == e.getButton())
                    game.pressLeftButton(coord);
                if (MouseEvent.BUTTON2 == e.getButton())
                    game.start();
                if (MouseEvent.BUTTON3 == e.getButton())
                    game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getGameState()) {
            case PLAYED: return "Think twice!";
            case BOMBED: return "YOU LOSE! BIG BA-DA-BOOM!";
            case WINNER: return "CONGRATULATIONS!";
            default: return "";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Saper ЫЫЫЫ");
        setResizable(false);
        setVisible(true);
        pack();
        setIconImage(getImage("icon"));
        setLocationRelativeTo(null);
    }

    private void initLabel() {
        label = new JLabel("Wellcome!");
        add(label, BorderLayout.SOUTH);
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
     }
}
