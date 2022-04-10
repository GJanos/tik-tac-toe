package game.base;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TikTakToTest {

    TikTakTo control;
    Savable save;


    @Before
    public void init(){
        // private-ek neki az ablakai
        control = new TikTakTo();
        control.start();
        save = new Savable(null);
        control.model.initInnerTable(3);
        control.gameW.initGameTable(3);

    }

    @Test
    public void winTest(){

        int[][] koordinates ={{0,0},{0,1},{0,2}};
        control.gameW.win(koordinates);
        int cnt=0;
        Color winColor = new Color(128,0,0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //System.out.println(gameW.getGridButtons()[i][j].getBackground());
                if(control.gameW.getGridButtons()[i][j].getBackground().equals(winColor))
                    cnt++;
            }
        }
        assertEquals(3,cnt);
    }
    @Test
    public void settingInfoAndTitleTest(){
        save.gridSize = 3;
        save.clickCounter = 2;
        save.playerNum = 1;
        save.xTurn = true;

        control.setMetaData(save);
        control.setTitle();

        assertEquals("Versus AI!",control.gameW.getTitleText());

        save.playerNum = 2;
        save.xTurn = true;

        control.setMetaData(save);
        control.setTitle();

        assertEquals("X-turn to move!",control.gameW.getTitleText());

    }

    @Test
    public void windowTest(){
        control.setWindowVisible(0);
        assertTrue(control.mainW.isVisible());

        control.setWindowVisible(2);
        assertTrue(control.gameW.isVisible());
    }

    @Test
    public void shapeTest(){

        control.model.setTableShape(1,1,Shape.X);
        control.model.setTableOrder(1,1,10);
        assertEquals(Shape.X,control.model.getTableShape(1,1));
        assertEquals(10,control.model.getTableOrder(1,1));
    }

    @Test
    public void innerTableWinTest(){
        control.model.setTableShape(0,0,Shape.X);
        control.model.setTableShape(0,1,Shape.X);
        control.model.setTableShape(0,2,Shape.X);
        control.model.setTableShape(1,0,Shape.X);
        control.model.setTableShape(2,0,Shape.X);
        control.model.setTableShape(1,1,Shape.X);
        control.model.setTableShape(2,2,Shape.X);

        int cnt = 0;
        int[][] winners = new int[3][2];
        control.model.check(control.model.currentTable,winners,false);
        int[][] temp = {{0,0},{0,1},{0,2}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++)
                if(temp[i][j] == winners[i][j])
                    cnt++;
        }
        assertEquals(6,cnt);

        temp = new int[][]{{0, 0}, {1, 0}, {2, 0}};
        cnt = 0;
        control.model.check(control.model.currentTable,winners,true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++)
                if(temp[i][j] == winners[i][j])
                    cnt++;
        }
        assertEquals(6,cnt);

        temp = new int[][]{{0, 0}, {1, 1}, {2, 2}};
        cnt = 0;
        control.model.checkDiagonal(control.model.currentTable,winners);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++)
                if(temp[i][j] == winners[i][j])
                    cnt++;
        }
        assertEquals(6,cnt);
    }



    @Test
    public void mirrorTableTest(){
        control.model.setTableShape(0,0,Shape.X);
        control.model.setTableShape(0,1,Shape.X);
        control.model.setTableShape(0,2,Shape.X);

        GridCellModel[][] mirroredTable = control.model.mirrorTable(control.model.currentTable);
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(control.model.currentTable[i][j].getShape() == mirroredTable[i][j].getShape()){
                    cnt++;
                }
            }
        }
        assertEquals(5,cnt);
    }

    @Test
    public void robotPlacesTest(){
        control.model.setTableShape(0,0,Shape.X);
        control.model.setTableShape(0,1,Shape.X);
        int cnt =0;
        int[] robot = control.model.robotPlaces(10,0,1);
        int[] temp = {0,2};
        if(robot[0] == temp[0] && robot[1] == temp[1])
            cnt++;
        assertEquals(1,cnt);
    }
}
