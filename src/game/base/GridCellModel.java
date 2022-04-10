package game.base;

import java.io.Serializable;


/**
 * saját belső táblaelem, tartalmazza az adott mezo alakzatat es sorrendjet
 */
public class GridCellModel implements Serializable {

    private Shape shape;
    private int order;

    /**
     * konstruktor alapallapotot beallit
     */
    public GridCellModel(){
        shape = Shape.DEFAULT;
        order = 0;
    }

    /**
     * visszaadja a shapet
     * @return
     */
    protected Shape getShape(){
        return shape;
    }
    /**
     * visszaadja elem sorszamat
     * @return
     */
    protected int getOrder(){
        return order;
    }
    /**
     * beallitja a shapet
     * @return
     */
    protected void setShape(Shape shape){
        this.shape = shape;
    }
    /**
     * beallitja az adott elem orderjet
     * @return
     */
    protected void setOrder(int order){
        this.order = order;
    }
}
