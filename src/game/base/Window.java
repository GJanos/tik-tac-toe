package game.base;

import javax.swing.*;
import java.awt.*;

/**
 * ablakokat osszefoglalo ososztaly, alapbeallitasokkal rendelkezik
 */
public class Window extends JFrame implements Coloring{

    int textSize;

    /**
     * window ososztaly konstruktora,alapbeallitasokat vegez
     */
    public Window(){

        textSize = 60;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setResizable(false);

    }
}
