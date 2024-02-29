import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class PlayerView extends JPanel {
    PlayerModel player;
    private BufferedImage img,background,injured,bomb;
    private ImageIcon icon,icon2,explosion;
    Random rand;
    public float value;
    boolean isMoving,right,isWave=false,tick=false,tick2=false;

    public PlayerView(PlayerModel player) {
        this.player=player;
        try {
            background = ImageIO.read(new File("IMG/background.jpeg"));
            img = ImageIO.read(new File("IMG/idle.png"));
            injured = ImageIO.read(new File("IMG/injured.png"));
            bomb = ImageIO.read(new File("IMG/bomb.png"));
            icon = new ImageIcon("IMG/med.gif");
            JLabel label = new JLabel(icon);
            icon2 = new ImageIcon("IMG/med_mirrored.gif");
            JLabel label2 = new JLabel(icon2);
            explosion = new ImageIcon("IMG/explosion.gif");
            JLabel label3 = new JLabel(explosion);

            //this.add(label);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,1920,1080,null);
        g.drawImage(injured, player.randx, player.randy, 100, 100, null);
        if(tick) {
            for (int i = 0; i < player.bombCount; i++) {
                g.drawImage(bomb, player.bombX[i], player.bombY[i], 130, 130, null);
                //g.drawImage(explosion.getImage(), player.bombX[i], player.bombY[i], 130, 130, null);
            }
        }
        if(tick2) {
            for (int i = 0; i < player.bombCount; i++) {
                //g.drawImage(bomb, player.bombX[i], player.bombY[i], 130, 130, null);
                g.drawImage(explosion.getImage(), player.bombX[i], player.bombY[i], 260, 260, null);
            }
        }

        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if(player.life){g.drawString("Pontszám: " + player.point, 10, 20);}
        else {g.drawString("Jatek vege... (freeroam mode),eredmeny mentve", 10, 20);}
        g.drawString("Ido: " + value, 10, 40);
        if (isMoving) {
            if (right){
                g.drawImage(icon2.getImage(), player.getX(), player.getY(), 110, 110, null);

            }
            else {
                g.drawImage(icon.getImage(), player.getX(), player.getY(), 110, 110, null);
            }
            }
        else {
            g.drawImage(img, player.getX(), player.getY(), 100, 100, null);
        }
    }

}

