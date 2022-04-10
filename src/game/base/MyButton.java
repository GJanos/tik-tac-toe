package game.base;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;



/**
 * sajat gomb osztaly vizualis es kenyelmi okok miatt
 */
public class MyButton extends JButton {
    /**
     * sajat definialt gomb, dolgokat beallit
     * @param text
     * @param textColor
     * @param backColor
     * @param textSize
     * @param x
     * @param y
     * @param w
     * @param h
     */
    MyButton(String text,Color textColor,Color backColor,int textSize,
             int x, int y, int w, int h){
        this(text,textColor,backColor,textSize);
        setBounds(x,y,w,h);
    }

    /**
     * sajat definialt gomb, dolgokat beallit koordinata nelkul
     * @param text
     * @param textColor
     * @param backColor
     * @param textSize
     */
    MyButton(String text,Color textColor,Color backColor,int textSize){
        setText(text);
        setForeground(textColor);
        setBackground(backColor);
        setFont(new Font("Times Roman",Font.BOLD,textSize));
        setBorder(new LineBorder(textColor,3));
    }
}
