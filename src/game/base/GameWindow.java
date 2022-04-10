package game.base;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;




/**
 * jatek ablaka, itt zajlik maga a jatek, jatekmezo gombokbol all
 * ezeket a gombokat manageli fokent valamint a szovegeket
 */
public class GameWindow extends Window{

    private JPanel upperPanel;
    private JPanel lowerPanel;
    private MyButton saveButton;
    private MyButton exitButton;
    private MyButton[][] buttons;
    private MyLable title;
    private int sizeG;

    public GameWindow(){
        // frame
        int width = 600;
        int height = 700;
        this.getContentPane().setPreferredSize(new Dimension(width,height));
        this.setTitle("Tik-Tak-Toe");
        this.setLayout(new BorderLayout());

        // upper panel
        upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBounds(0,0,width,100);
        upperPanel.setBackground(darkTheme.get("backC"));
        upperPanel.setBorder(new LineBorder(darkTheme.get("textC"),5));

        // lower panel
        lowerPanel = new JPanel();
        lowerPanel.setBackground(darkTheme.get("backC"));
        lowerPanel.setBorder(new LineBorder(darkTheme.get("textC"),5));

        // title
        title = new MyLable("",darkTheme.get("importantC"),40,130,0,400,100);
        title.setHorizontalAlignment(JLabel.CENTER);

        // save & exit buttons
        saveButton = new MyButton("Save",darkTheme.get("highlightC"),darkTheme.get("backC"),40);
        saveButton.setBorder(new LineBorder(darkTheme.get("highlightC"),5));
        exitButton = new MyButton("Exit",darkTheme.get("highlightC"),darkTheme.get("backC"),45);
        exitButton.setBorder(new LineBorder(darkTheme.get("highlightC"),5));

        // adding fixed components
        upperPanel.add(title);
        upperPanel.add(saveButton,BorderLayout.EAST);
        upperPanel.add(exitButton,BorderLayout.WEST);
        this.add(upperPanel,BorderLayout.NORTH);
        this.setLocation(500,50);
        pack();
    }



    /**
     * a gamewindow tabeljet inicializalja, lowerpanel-be rakja az ujonnan letrehozott
     *     gombokat majd a panelt a framehez adja
     * @param sizeG
     */
    public void initGameTable(int sizeG){
        this.sizeG = sizeG;
        lowerPanel.setLayout(new GridLayout(sizeG,sizeG));
        buttons = new MyButton[sizeG][sizeG];
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                buttons[i][j] = new MyButton("",darkTheme.get("textC"),darkTheme.get("backC"),textSize);
                lowerPanel.add(buttons[i][j]);
            }

        }
        this.add(lowerPanel);
        pack();
    }

    /**
     * innerTabelnek megfeleloen megalkotja a jelenlegi allast a gamewindowon
     *     innerTable elemeinek shapjeivel hasonlit ossze
     * @param sizeG
     * @param innerTable
     */
    public void copyTable(int sizeG,GridCellModel[][] innerTable){
        this.sizeG = sizeG; /// copymetadataban mar ezt be kellett volna hogy állítsa
        lowerPanel.setLayout(new GridLayout(sizeG,sizeG));
        buttons = new MyButton[sizeG][sizeG];
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                if(innerTable[i][j].getShape() == Shape.X){
                    buttons[i][j] = new MyButton("X",darkTheme.get("textC"),darkTheme.get("backC"),textSize);
                }
                else if(innerTable[i][j].getShape() == Shape.O){
                    buttons[i][j] = new MyButton("O",darkTheme.get("textC"),darkTheme.get("backC"),textSize);
                }
                else{
                    buttons[i][j] = new MyButton("",darkTheme.get("textC"),darkTheme.get("backC"),textSize);
                }
                lowerPanel.add(buttons[i][j]);
            }
        }
        this.add(lowerPanel);
        pack();
    }

    /**
     * a gamewindowhoz ad hozza actionlistenereket, jatekter gombjaihoz es save&exit gombhoz
     * @param listener
     * @param saveListener
     */
    protected void addGameWindowListener(ActionListener listener, ActionListener saveListener){
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                buttons[i][j].addActionListener(listener);
            }
        }
        saveButton.addActionListener(saveListener);
        exitButton.addActionListener(saveListener);
    }



    /**
     * beallitja hogy a gombok de lehessenek kattinthatoak
     */
    protected void setButtonsNotEnabled(){
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }



    /**
     * palya elemeinek a szinet es korvonalat allitja be, ez akkor fontos
     *     amikor egyes elemek kijelolodnek hogy el fognak tunni
      */
    protected void resetGrid(){
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                buttons[i][j].setForeground(darkTheme.get("textC"));
                buttons[i][j].setBorder(new LineBorder(darkTheme.get("textC"), 3));
            }
        }
    }



    /**
     * lowerpanel gomb komponenseit removeolja hogy majd ujakat lehessen behelyezni
     *     palya meretenek megvaltoztatasakor
     */
    protected void resetAll(){
        Component[] componentList = lowerPanel.getComponents();
        for(Component c : componentList){
            if(c instanceof MyButton){
                lowerPanel.remove(c);
            }
        }
    }



    /**
     *    kijeloli azokat a gombokat melyek benne vannak a nyero kombinacioban
     * @param winners
     */
    public void win(int[][] winners){
        for (int i = 0; i < sizeG; i++) {
            buttons[winners[i][0]][winners[i][1]].setFont(new Font("Times Roman",Font.BOLD,30));
            buttons[winners[i][0]][winners[i][1]].setBackground(darkTheme.get("winC"));
        }
    }

    /**
     * visszaadja az elmentett gombokat
     * @return
     */
    public MyButton getSaveButton() {return saveButton;}

    /**
     * kilepes gombot adja vissza
     * @return
     */
    public MyButton getExitButton() {return exitButton;}

    /**
     * oldal cimet beallitja
     * @param text
     */
    public void setTitleText(String text){title.setText(text);}

    /**
     * teszteleshez kell, visszaadja a title szoveget
     * @return
     */
    public String getTitleText(){return title.getText();}

    /**
     * a bekattintgatos gombokat adja vissza
     * @return
     */
    public MyButton[][] getGridButtons(){return buttons;}
}

