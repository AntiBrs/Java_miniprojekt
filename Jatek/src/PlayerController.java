import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import static java.lang.Math.abs;

public class PlayerController extends KeyAdapter {
    PlayerModel player;
    private PlayerView playerView;
    Timer timer;
    int k=0;
    boolean explodes=false;
    Thread thread2;
    Thread thread;

    public PlayerController(PlayerModel player,PlayerView playerView) {
        this.player = player;
        this.playerView = playerView;
        playerView.value=0;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player.life) {
                    System.out.println("VEGE");
                    String content = "Time: " + playerView.value + "\nScore: " + player.point + "\n";
                    try {
                        File myObj = new File("ki.txt");
                        if (myObj.createNewFile()) {
                            System.out.println("Letrehozva: " + myObj.getName());
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException ae) {
                        System.out.println("An error occurred.");
                        ae.printStackTrace();
                    }
                    FileWriter myWriter = null;
                    try {
                        myWriter = new FileWriter("ki.txt");
                        myWriter.write(content);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        if (myWriter != null) {
                            try {
                                myWriter.close();
                            } catch (IOException ee) {
                                ee.printStackTrace();
                            }
                        }
                    }
                timer.stop();
                }
                else{
                playerView.value+=0.1;
                playerView.repaint();
                //System.out.println(playerView.value);
                if (player.point>0 && playerView.value%5<0.5 && !explodes){
                    explodes=true;
                    player.bombCount=2+k;
                    k++;
                    player.randSetBomb();
                    thread = new Thread(() -> tick());
                    thread.start();
                }
                }
            }
        });
        timer.start();

    }
    void isWave(){
        player.bombCount=2;
        player.randSetBomb();
        playerView.isWave = true;
       // if (playerView.isWave==true){explodes=true;tick();}
    }
    public boolean isMoving() {
        return playerView.isMoving;
    }

    void tick() {
        playerView.tick = true;
        long start = new Date().getTime();
        while (new Date().getTime() - start < 3500L) {
            playerView.repaint();
        }
        thread2 = new Thread(this::tick2);
        thread2.start();
        playerView.tick = false;
    }
    void tick2() {
        playerView.tick2 = true;
        try {
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("SOUND/explode.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException re) {
            System.out.println("IO error");
        }
        long start2 = new Date().getTime();
        while (new Date().getTime() - start2 < 2500L && player.life) {
            for(int i=0;i< player.bombCount;i++) {
                if (abs(player.getX() - player.bombX[i]) < 200 && abs(player.getY() - player.bombY[i]) < 200) {
                    player.life = false;
                    //timer.stop();
                }
            }
            playerView.repaint();
        }
        explodes=false;
        playerView.tick2 = false;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerView.isMoving = true;
        playerView.right=false;
        int key = e.getKeyCode();
        int speed=30;
        if(abs(player.getY()-player.randy)<30  &&  abs(player.getX()-player.randx)<30){
            try {
                AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("SOUND/beep.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException re) {
                System.out.println("IO error");
            }
            player.randSet();
        }
        switch (key) {
            case KeyEvent.VK_W:
                if(player.getY() - speed>10){player.setY(player.getY() - speed);}
                step();
               // System.out.println("Y= "+player.getY());
                break;
            case KeyEvent.VK_S:
                if(player.getY() - speed<655){player.setY(player.getY() + speed);}
              //  System.out.println("Y= "+player.getY());
                step();
                break;
            case KeyEvent.VK_A:
                if(player.getX() + speed>-5){player.setX(player.getX() - speed);}
              //  System.out.println("X= "+player.getX());
                step();
                break;
            case KeyEvent.VK_D:
                playerView.right=true;
                if(player.getX() + speed<1450){player.setX(player.getX() + speed);}
                step();
                //   System.out.println("X= "+player.getX());
                break;
        }

        playerView.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        playerView.isMoving = false;
        playerView.right=false;
        playerView.repaint();
    }
    void step(){
        try {
            //File URL relative to project folder
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("SOUND/step.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException re) {
            System.out.println("IO error");
        }
    }
}