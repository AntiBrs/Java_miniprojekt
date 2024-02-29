import javax.swing.*;
import java.util.Random;

public class PlayerModel extends JPanel {
    int X,Y;
    boolean life=true,paused=false;
    int randx,randy;
    int bombX[]=new int[50],bombY[]=new int[50];
    int bombCount;
    int point;
    public PlayerModel(){
        X=100;
        Y=100;
        Random rand=new Random();
        randx=rand.nextInt(1455)-5;
        randy=rand.nextInt(645)+10;
        //System.out.println("Randx :"+randx+" Randy:"+randy);
    }
    void randSet()
    {
        if(!paused){point++;}
        Random rand=new Random();
        randx=rand.nextInt(1455)-5;
        randy=rand.nextInt(645)+10;
    }


    void randSetBomb()
    {
        Random rand=new Random();
        for (int i=0;i<bombCount;i++) {
            bombX[i] = rand.nextInt(1455) - 5;
            bombY[i] = rand.nextInt(645) + 10;
        }
    }

    @Override
    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    @Override
    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getRandx() {
        return randx;
    }

    public int getRandy() {
        return randy;
    }
}
