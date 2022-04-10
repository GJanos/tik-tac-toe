package game.base;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;



/**
 * kezdoablak, fo navigacios szempont, itt donti el a felhasznalo
 * uj jatekot kezdene vagy betoltene ugymond egy kiindulo allomas
 */
public class MainWindow extends Window{

    private JPanel mainPanel;
    private MyLable title;
    private MyButton newGame;
    private MyButton load;
    private JMenuBar menu;
    private JMenu guide;
    private JMenuItem aboutGame;
    private JMenuItem howToWin;

    /**
     * konstruktor, dolgokat megjeleniti setuppolja
     */
    public MainWindow(){
        // frame
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

        // title
        title = new MyLable("TIC-TAC-TOE",darkTheme.get("importantC"),26,115,100,170,50);

        // new game button
        newGame  = new MyButton("NEW GAME",darkTheme.get("textC"),darkTheme.get("backC"),15,
                120,150,160,50);

        // load button
        load = new MyButton("LOAD GAME",darkTheme.get("textC"),darkTheme.get("backC"),15,
                120,210,160,50);

        // decoration
        decoration(100,mainPanel);


        // adding elements
        mainPanel.add(title);
        mainPanel.add(newGame);
        mainPanel.add(load);

        // making the menu
        menu = new JMenuBar();
        guide = new JMenu("Guide");
        aboutGame = new JMenuItem("About game");
        howToWin= new JMenuItem("How to win");

        menu.setPreferredSize(new Dimension(80,20));
        guide.add(aboutGame);
        guide.add(howToWin);
        menu.add(guide);

        // adding the components
        this.add(mainPanel);
        this.setJMenuBar(menu);

        // to make everything perfect
        pack();
        this.setLocation(600,200);

    }

    /**
     * actionlistenereket ad hozza a menut abrazolo gombokhoz
     * @param listener
     */
    public void addMainWindowListener(ActionListener listener){
        newGame.addActionListener(listener);
        load.addActionListener(listener);
        aboutGame.addActionListener(listener);
        howToWin.addActionListener(listener);
    }
    /**
     * dekoracio a vizualis elemek feldobasaert
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
                if(x<90 || x > 300)
                    again = false;

                if(y<80 || y > 250)
                    again = false;

                if(again){
                    x = randX.nextInt(360)+20;
                    y = randY.nextInt(360+20);
                }

            }
            String s = (i % 2 == 0) ? "X" : "O";
            panel.add(new MyLable(s,darkTheme.get("textC"),25,x,y,30,30));
        }
    }

    /**
     * uj jatek gombot visszaadja
     * @return
     */
    protected MyButton getNewGameButton(){
        return newGame;
    }

    /**
     * load game gombot visszaadja
     * @return
     */
    protected MyButton getLoadButton(){
        return load;
    }

    /**
     * about game menueleemet visszaadja
     * @return
     */
    protected JMenuItem getAboutGame(){
        return aboutGame;
    }

    /**
     * how to win menueleemet visszaadja
     * @return
     */
    protected JMenuItem getHowToWin(){
        return howToWin;
    }
}
