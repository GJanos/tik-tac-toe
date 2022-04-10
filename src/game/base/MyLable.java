package game.base;

import javax.swing.*;
import java.awt.*;
/**
 * sajat lable osztaly vizualis es kenyelmi okok miatt
 */
public class MyLable extends JLabel {
    /**
     * sajst definialt lable osztaly dolgokat beallitja
     * @param text
     * @param textColor
     * @param textSize
     * @param x
     * @param y
     * @param w
     * @param h
     */
    MyLable(String text,Color textColor,int textSize,int x,int y, int w,int h){
        setText(text);
        setBounds(x,y,w,h);
        setForeground(textColor);
        setFont(new Font("Times Roman",Font.BOLD,textSize));
    }

    /**
     * sajst definialt lable osztaly dolgokat beallitja koordinatak nelkul
     * @param text
     * @param textColor
     * @param textSize
     */
    MyLable(String text,Color textColor,int textSize){
        setText(text);
        setForeground(textColor);
        setFont(new Font("Times Roman",Font.BOLD,textSize));
    }
}
