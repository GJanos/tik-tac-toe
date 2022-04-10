package game.base;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * konfiguracios ablak, felhasznalo itt valaszthatja ki gep vagy
 * masik jatgekos ellen szeretne-e jatszani mekkora meretu palyan
 */
public class OptionWindow extends Window{
    JPanel mainPanel;
    JPanel sizePanel;
    MyButton onePlayer;
    MyButton twoPlayer;
    MyButton increment;
    MyButton decrement;
    MyLable sizeLable;
    MyLable currentSize;

    /**
     * konstruktor alapveto dolgokat beallit komponenseket elhelyez
     */
    public OptionWindow(){
        int width = 400;
        int height = 400;
        this.getContentPane().setPreferredSize(new Dimension(width,height));
        this.setTitle("Tik-Tak-Toe");
        // main panel
        mainPanel  = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0,0,width,height);
        mainPanel.setBackground(darkTheme.get("backC"));
        mainPanel.setBorder(new LineBorder(darkTheme.get("textC"),5)); //BorderFactory method

        // 1 & 2 jatekos gombok
        onePlayer  = new MyButton("1 PLAYER",darkTheme.get("textC"),darkTheme.get("backC"),26,
                120,100,160,50);
        twoPlayer  = new MyButton("2 PLAYER",darkTheme.get("textC"),darkTheme.get("backC"),26,
                120,170,160,50);

        sizePanel = new JPanel();
        sizePanel.setBounds(100,230,200,50);

        // meret allito gombok
        sizeLable = new MyLable("SIZE:",darkTheme.get("importantC"),30,100,230,90,50);
        increment = new MyButton("+",darkTheme.get("textC"),darkTheme.get("backC"),16,190,245,25,25);
        currentSize = new MyLable("3",darkTheme.get("importantC"),30,230,230,50,50);
        decrement = new MyButton("-",darkTheme.get("textC"),darkTheme.get("backC"),25,260,245,25,25);

        // komponensek hozzaadasa
        decoration(100,mainPanel);
        mainPanel.add(onePlayer);
        mainPanel.add(twoPlayer);
        mainPanel.add(sizeLable);
        mainPanel.add(increment);
        mainPanel.add(currentSize);
        mainPanel.add(decrement);
        this.add(mainPanel);

        // to make everything perfect
        pack();
        this.setLocation(600,200);
    }
    /**
     * gombokhoz rendel actionlistenereket
     * @param listener
     */
    public void addOptionsWindowListener(ActionListener listener){
        onePlayer.addActionListener(listener);
        twoPlayer.addActionListener(listener);
        increment.addActionListener(listener);
        decrement.addActionListener(listener);
    }
    /**
     * dekoracio vizualitasnak
     * @param numberOfDecoration
     * @param panel
     */
    private void decoration(int numberOfDecoration,JPanel panel){
        for (int i = 0; i < numberOfDecoration; i++) {
            Random randX = new Random();
            Random randY = new Random();

            int x = randX.nextInt(360)+10;
            int y = randY.nextInt(360+10);
            boolean again = true;

            while (again) {
                if(x<75 || x > 310)
                    again = false;

                if(y<70 || y > 260)
                    again = false;

                if(again){
                    x = randX.nextInt(360)+10;
                    y = randY.nextInt(360+10);
                }

            }
            String s = (i % 2 == 0) ? "X" : "O";
            panel.add(new MyLable(s,darkTheme.get("textC"),25,x,y,30,30));
        }
    }

    /**
     * visszaadja oneplayer gombot
     * @return
     */
    public MyButton getOnePlayerButton(){
        return onePlayer;
    }

    /**
     * visszaadja twoplayer gombot
     * @return
     */
    public MyButton getTwoPlayerButton(){
        return twoPlayer;
    }

    /**
     * noveles gombot adja vissza
     * @return
     */
    public MyButton getIncrementButton(){
        return increment;
    }

    /**
     * csokkentes gombot visszaadja
     * @return
     */
    public MyButton getDecrementButton(){
        return decrement;
    }

    /**
     * kiejelzett palyameretet tartalmato lablet adja vissza
     * @return
     */
    public MyLable getSizeLable(){
        return currentSize;
    }
}
