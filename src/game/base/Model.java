package game.base;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * belso mukodest szimulalja, ez az osztaly vegzi el a mentes/betoltes
 * belso tablaval valo valtozttasok bevitelet,tabla frissiteset
 */
public class Model extends TikTakTo {

    //Shape currentTable[];
    //ArrayList<GridCellModel> currentTable;
    TikTakTo control;
    protected GridCellModel[][] currentTable;
    ArrayList<String> workingFiles;
    private Savable save;
    int numberOfFiles;
    int indexOfCurrentWorkingFile;
    int sizeG;

    /**
     * Model osztály konstruktora
     * @param control
     */
    public Model(TikTakTo control) {
        this.control = control;
        indexOfCurrentWorkingFile = 0;
        numberOfFiles = 5;
        workingFiles = new ArrayList<>();
        for (int i = 0; i < numberOfFiles; i++) {
            workingFiles.add("Game" + i + ".txt");
        }
    }

    /**
     * belso tablat kesziti el az adott mereture es iniciallizalja
     * @param sizeG
     */
    public void initInnerTable(int sizeG){
        this.sizeG = sizeG;
        currentTable = new GridCellModel[sizeG][sizeG];
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                currentTable[i][j] = new GridCellModel();
            }
        }
    }

    /**
     * belso tablat allitja vissza a kezdoallapotba az uj jatekhoz
     */
    public void resetTable() {
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                currentTable[i][j].setOrder(0);
                currentTable[i][j].setShape(Shape.DEFAULT);
            }
        }
    }

    /**
     * robot lepeseit szimulalja es annak gondolkodasimodjat, elsonek megnezi
     * tud e nyerni, majd azt meg tudja e akadalyozni, hogy jatekos nyerjen
     * ha egyik sem igaz random rak le, visszater a robot altal valasztott
     * sor es oszlop koordinakkal
     *
     * @param clickCounter
     * @param row
     * @param col
     * @return
     */
    public int[] robotPlaces(int clickCounter,int row,int col){
        int xRowCounter = 0;
        int xColCounter = 0;
        int oRowCounter = 0;
        int oColCounter = 0;
        int shapeXRowCounter = 0;
        int shapeXColCounter = 0;
        int shapeRowCounter = 0;
        int shapeColCounter = 0;
        int[][] robotPlaces = new int[3][2];
        // todo ha kurvara sok idot lesz csinald meg hogy atlosan is ellenorizzen... ff

        // attacking
        for (int i = 0; i < sizeG; i++) {
            xColCounter = 0;
            shapeXColCounter = 0;
            xRowCounter = 0;
            shapeXRowCounter = 0;
            for (int j = 0; j < sizeG; j++) {
                if(currentTable[i][j].getShape() != Shape.DEFAULT){
                    if(currentTable[i][j].getShape() == Shape.O){
                        xRowCounter++;
                    }
                    shapeXRowCounter++;
                }else{
                    robotPlaces[2][0] = i;
                    robotPlaces[2][1] = j;
                }

                if(currentTable[j][i].getShape() != Shape.DEFAULT){
                    if(currentTable[j][i].getShape() == Shape.O){
                        xColCounter++;
                    }
                    shapeXColCounter++;
                }else{
                    robotPlaces[2][0] = j;
                    robotPlaces[2][1] = i;
                }
            }
            if(xRowCounter==sizeG-1 && shapeXRowCounter == sizeG-1) {
                setTableOrder(robotPlaces[2][0], robotPlaces[2][1], clickCounter);
                setTableShape(robotPlaces[2][0], robotPlaces[2][1], Shape.O);
                return robotPlaces[2];
            }
            if(xColCounter==sizeG-1 && shapeXColCounter == sizeG-1) {
                setTableOrder(robotPlaces[2][0], robotPlaces[2][1], clickCounter);
                setTableShape(robotPlaces[2][0], robotPlaces[2][1], Shape.O);
                return robotPlaces[2];
            }
        }

        /// defending 
        for (int i = 0; i < sizeG; i++) {
//            oRowCounter = 0;
//            shapeRowCounter = 0;
//            oColCounter = 0;
//            shapeColCounter = 0;

            if(currentTable[row][i].getShape() != Shape.DEFAULT){
                if(currentTable[row][i].getShape() == Shape.X){
                    oRowCounter++;
                }
                shapeRowCounter++;
            }else{
                robotPlaces[0][0] = row;
                robotPlaces[0][1] = i;
            }

            /// todo columnnál asszem a vedekezesnel valami gebasz van
            if(currentTable[i][col].getShape() != Shape.DEFAULT){
                if(currentTable[i][col].getShape() == Shape.X){
                    oColCounter++;
                }
                shapeColCounter++;
            }else{
                robotPlaces[1][0] = i;
                robotPlaces[1][1] = col;
            }
        }

        //System.out.println(robotPlaces[0] + " " + robotPlaces[1] + " " + oCounter + " " + shapeCounter);

        if(oRowCounter == sizeG-1 && shapeRowCounter == sizeG-1){
            setTableOrder(robotPlaces[0][0],robotPlaces[0][1],clickCounter);
            setTableShape(robotPlaces[0][0],robotPlaces[0][1],Shape.O);
            return robotPlaces[0];
        }else if(oColCounter == sizeG-1 && shapeColCounter == sizeG-1){
            setTableOrder(robotPlaces[1][0],robotPlaces[1][1],clickCounter);
            setTableShape(robotPlaces[1][0],robotPlaces[1][1],Shape.O);
            return robotPlaces[1];
        }else{
            Random randI = new Random();
            Random randJ = new Random();
            int i = 0,j = 0;
            do{
                i = randI.nextInt(sizeG);
                j = randJ.nextInt(sizeG);
            }while (currentTable[i][j].getShape() != Shape.DEFAULT);
            robotPlaces[0][0] = i;
            robotPlaces[0][1] = j;
            setTableOrder(robotPlaces[0][0],robotPlaces[0][1],clickCounter);
            setTableShape(robotPlaces[0][0],robotPlaces[0][1],Shape.O);
            return robotPlaces[0];
        }

    }

    /**
     * lecsekkolja, hogy oszlopban,sorban,atloban valaki megnyerte e a meccset
     * @param winners
     * @return
     */
    public boolean checkForWinner(int[][] winners) {
        GridCellModel[][] mirroredTable = mirrorTable(currentTable);
        if(check(currentTable,winners,false)) return true;
        else if(check(mirroredTable,winners,true)) return true;
        else return checkDiagonal(currentTable, winners);
    }

    /**
     * transzponalja a tablat, ezaltal a sotos csekkolas is jo lesz az oszloposra
     * @param currentTable
     * @return
     */
    protected GridCellModel[][] mirrorTable(GridCellModel[][] currentTable) {
        GridCellModel[][] temp = new GridCellModel[sizeG][sizeG];
        for (int i = 0; i < sizeG; i++) {
            for (int j = 0; j < sizeG; j++) {
                temp[i][j] = new GridCellModel();
                temp[i][j].setShape(currentTable[j][i].getShape());
            }
        }
        return temp;
    }

    /**
     * megnezi osszehaslitasokkal hogy az adott sorban van e N db ugyan
     * olyan elem ha igen igazzal tér vissza
     * @param currentTable
     * @param winners
     * @param transposed
     * @return
     */
    protected boolean check(GridCellModel[][] currentTable,int[][] winners,boolean transposed){
        for (int i = 0; i < sizeG; i++) {
            boolean similar = true;
            for (int j = 0; (j < sizeG-1)&&(similar); j++) {
                if(currentTable[i][j].getShape() == currentTable[i][j+1].getShape()
                        && currentTable[i][j].getShape() != Shape.DEFAULT) {
                    winners[j][0] = i;      winners[j][1] = j;
                    winners[j+1][0] = i;    winners[j+1][1] = j+1;
                }else similar = false;
            }
            if(similar){
                if(transposed){
                    for (int k = 0; k < sizeG; k++) {
                        int temp = winners[k][0];
                        winners[k][0] = winners[k][1];
                        winners[k][1] = temp;
                    }
                }
                return true;
            }

        }
        return false;
    }

    /**
     * megnezi osszehaslitasokkal hogy az adott atlokban van e N db ugyan
     * olyan elem ha igen igazzal tér vissza
     * @param currentTable
     * @param winners
     * @return
     */
    protected boolean checkDiagonal(GridCellModel[][] currentTable,int[][] winners){
        boolean similar = true;
        for (int i = 0; i < sizeG-1; i++) {
            if(currentTable[i][i].getShape() == currentTable[i+1][i+1].getShape()
                    && currentTable[i][i].getShape() != Shape.DEFAULT){
                winners[i][0] = i;      winners[i][1] = i;
                winners[i+1][0] = i+1;      winners[i+1][1] = i+1;
            }else similar = false;
        }
        if(similar) return true;
        similar = true;
        for (int i = 0; i < sizeG-1; i++) {
            if(currentTable[i][sizeG-1-i].getShape() == currentTable[i+1][sizeG-2-i].getShape()
                    && currentTable[i][sizeG-1-i].getShape() != Shape.DEFAULT){
                winners[i][0] = i;      winners[i][1] = sizeG-1-i;
                winners[i+1][0] = i+1;      winners[i+1][1] = sizeG-2-i;
            }else similar = false;
        }
        if(similar) return true;
        return false;
    }


    /**
     *     adott indexhez tartozo nevu file-bol serializava betolti az adatokat
     *     amelyek save-ben talalhatoak, sajat ertekeit kiolvasott objektumbol
     *     kimasolja, amit szukseges atad a kontrolnak
     *     boolean visszateresi ertekkel rendelkezik ami akkor igaz ha olyan
     *     filet nyitunk meg vele ami nem ures, maskulonben hamissal ter  vissza
     * @param gameIndex
     * @return
     */

    public boolean loadInnerTable(int gameIndex) {
        this.indexOfCurrentWorkingFile = gameIndex;
        try {
            File file = new File(workingFiles.get(indexOfCurrentWorkingFile));
            if(file.length() != 0){
                FileInputStream f = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(f);
                save = (Savable) in.readObject();
                sizeG = save.gridSize;
                currentTable = save.savableTable;
                control.setMetaData(save);
                in.close();
                return true;
            }
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("Hiba a beolvasasnal!" + ex);
        }
        return false;
    }

    /**
     *     savable object segitsegevel kimenti az adott allas metaadatait
     *     es tablajat serializalassal
     */

    public void saveInnerTable() {
        try {
            save = new Savable(currentTable);
            control.getMetaData(save);
            FileOutputStream fs = new FileOutputStream(workingFiles.get(indexOfCurrentWorkingFile));
            ObjectOutputStream out = new ObjectOutputStream(fs);
            out.writeObject(save);
            out.close();
        } catch(IOException ex) {
            System.out.println("Hiba a beolvasasnal!" + ex);
        }
    }

    /**
     * megadott sor,oszlopu elemet adott shapre allitja
     * @param i
     * @param j
     * @param turn
     */
    public void setTableShape(int i,int j, Shape turn) {
        if (turn == Shape.X)
            currentTable[i][j].setShape(Shape.X);
        else if (turn == Shape.O)
            currentTable[i][j].setShape(Shape.O);
        else
            currentTable[i][j].setShape(Shape.DEFAULT);
    }

    /**
     * megadott sor,oszlopu elemnek sorszamat beallitja
     * @param i
     * @param j
     * @param order
     */
    public void setTableOrder(int i,int j, int order) {
        currentTable[i][j].setOrder(order);
    }

    /**
     * megadott sor,oszlopu elemnek lekeri a shapejet
     * @param i
     * @param j
     * @return
     */
    public Shape getTableShape(int i,int j) {
        return currentTable[i][j].getShape();
    }


    /**
     * megadott sor,oszlopu elemnek lekeri  a sorszamat
     * @param i
     * @param j
     * @return
     */
    public int getTableOrder(int i,int j) {
        return currentTable[i][j].getOrder();
    }


}
