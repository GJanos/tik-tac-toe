package game.base;

import java.io.Serializable;

/**
 * elmenteni kivant objektum, az uj jatek betoltesehez szokseges
 * adatokat osszefoglalja rgy helyen, hogy ezt serializalva egybe
 * ki lehessen menteni fileba es visszatolteni
 * public tagokkal rendelkezik, mivel csak a model osztalyban van benne
 * mashol nem es ott is privat tagkent ugyhogy felhasznalo nem tudja
 * elerni
 */
public class Savable implements Serializable {
    public int playerNum;
    public int clickCounter;
    public int gridSize;
    public boolean xTurn;
    public GridCellModel[][] savableTable;

    /**
     * adott tablat beallito konstruktor
     * @param table
     */
    public Savable(GridCellModel[][] table){
       savableTable = table;
    }
}
