import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private BufferedImage background;
    PlayerModel player;
    PlayerView playerView;
    PlayerController controller;
    JFrame frame;

    public MenuPanel() throws IOException {
        startButton = new JButton();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(0, 300, 0, 0);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("SOUND/beep.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException re) {
                    System.out.println("IO error");
                }
                try {
                    AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("SOUND/music.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException re) {
                    System.out.println("IO error");
                }
                frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                player = new PlayerModel();
                playerView = new PlayerView(player);
                controller = new PlayerController(player,playerView);
                frame.addKeyListener(controller);
                frame.add(playerView);
                JMenuBar menuBar = getjMenuBar(controller,player,playerView);
                frame.setJMenuBar(menuBar);
                frame.setBounds(0,0,1980,1024);
                frame.setVisible(true);

            }
        });
        setBounds(0,0,1980,1024);
        background = ImageIO.read(new File("IMG/menubckg.jpg"));
        ImageIcon imageIcon = new ImageIcon("IMG/b.png");
        startButton.setIcon(imageIcon);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        add(startButton, constraints);
        setVisible(true);
    }
    public static JMenuBar getjMenuBar(PlayerController controller, PlayerModel player,PlayerView playerView) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem menuItem = new JMenuItem("Pause");
        JMenuItem menuItem2 = new JMenuItem("Continue");
        JMenuItem menuItem3 = new JMenuItem("Reset");
        JMenuItem menuItem4 = new JMenuItem("Save");


        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.timer.stop();
                player.paused=true;
            }
        });
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.timer.start();
                player.paused=false;

            }
        });
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.life=true;
                playerView.value= 0.0F;
                player.point=0;
                controller.k=0;
                controller.timer.start();

            }
        });
        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (PrintWriter out = new PrintWriter(file)) {
                        out.println("Time: " + playerView.value);
                        out.println("Result: " + player.point);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });

        menu.add(menuItem);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);
        menuBar.add(menu);
        return menuBar;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,1980,1024,null);
    }
}