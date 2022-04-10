package game.base;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
/**
 * felugro ablakokat megvalosito osztaly tobb ablakot is megvalosit
 */
public class PopUpWindows extends Window{

    JTextArea textArea;
    JList savedGames;
    String saves[] = {"Game 1","Game 2","Game 3","Game 4","Game 5"};

    /**
     * konstruktor megfelelo popupwindowt nyissa meg es aszerint mukodik
     * @param whichWindow
     */
    public PopUpWindows(int whichWindow){
        // frame
        int width = 200;
        int height = 300;
        getContentPane().setPreferredSize(new Dimension(width,height));
        setTitle("About");
        setLayout(new BorderLayout());

        if(whichWindow != 2){
            // about game ablak
            if(whichWindow == 0)
                textArea = new JTextArea("Tic-Tac-Toe! (with extras)\n" +
                        "This is a modified version of the oldschool tic-tac-toe game.\n" +
                        "There are 2 players, X & O.˛\n" +
                        "They place down their symbols in the map.\n" +
                        "Some adjustments have to be made before playing.\n" +
                        "User has the ability to choose to start a new game or load one.\n" +
                        "User can choose from 5 different saved files.\n" +
                        "After selecting it, that game will load and it can be continued.\n" +
                        "In case of a new game a new window pops up where,\n" +
                        "User can select 1 & 2 player mode and the size of the playfield.\n" +
                        "After setting up the configurations the game window pops up.\n" +
                        "Player can play the game!");
            if(whichWindow == 1)
            // how to win ablak
                textArea = new JTextArea("The goal of the game is to win obviously.\n" +
                        "In order to do that the winning player has to have,\n" +
                        "their pieces in one row/column/diagonal besides each other.\n" +
                        "The pieces they must have equals to the size of the playfield.\n" +
                        "After a certain amount of pieces has been placed,\n" +
                        "some of them start to disappear!");

            // basic settings
            textArea.setBackground(darkTheme.get("backC"));
            textArea.setForeground(darkTheme.get("textC"));
            textArea.setFont(new Font("Times Roman",Font.BOLD,15));
            textArea.setEditable(false);
            add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
                    ,BorderLayout.CENTER);
            setLocation(350,250);
        }

        // mentett jatekokat tarolo tablazat, erre kattintva lehet majd azokat betolteni
        else{
            getContentPane().setPreferredSize(new Dimension(75,80));
            setTitle("Saves");
            savedGames = new JList(saves);
            savedGames.setVisibleRowCount(3);
            savedGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            savedGames.setBackground(darkTheme.get("backC"));
            savedGames.setForeground(darkTheme.get("textC"));
            savedGames.setFont(new Font("Times Roman",Font.BOLD,20));
            add(new JScrollPane(savedGames),BorderLayout.CENTER);
            setLocation(740,515);
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }
    /**
     * listselctionlistenert ad a menunek
     * @param listener
     */
    public void addSavedGamesListener(ListSelectionListener listener){
        savedGames.addListSelectionListener(listener);
    }

    /**
     * visszaadja az elmentett jatekok listajat
     * @return
     */
    public JList getSavedGames() {
        return savedGames;
    }
}
