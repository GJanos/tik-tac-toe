package game.base;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * fo ozstaly, olyan mint egy mastermind mindenkit csak iranyit mindenkinek megmondja
 * ki merre mit csinaljon, megkapott eredmenyeket logikailag kiiertekeli es feldolgozza
 * ezekbol szuri le a kovetkezo akcionak a lepeset
 */
public class TikTakTo implements Coloring{
    protected ArrayList<Window> allWindows;
    protected int numberOfWindows;
    protected MainWindow mainW;
    protected OptionWindow optW;
    protected GameWindow gameW;
    protected PopUpWindows aboutGameW;
    protected PopUpWindows howToWinW;
    protected PopUpWindows savedGamesW;
    protected Model model;
    protected int playerNum; /// todo ezzel dolgokat kezdeni
    protected int clickCounter;
    protected int gridSize;
    protected boolean xTurn;
    protected boolean saveEnabled;
    protected int coverage;

    /**
     * konstruktor, ablakokat megnyitja listenereket hozzaadja ablakokhoz
     */
    public TikTakTo() {

        // inicializalni az osszes ablakot & valtozot
        clickCounter = 0;
        gridSize = 3;
        xTurn = true;
        saveEnabled = true;
        mainW = new MainWindow();
        optW = new OptionWindow();
        gameW = new GameWindow();
        aboutGameW = new PopUpWindows(0);
        howToWinW = new PopUpWindows(1);
        savedGamesW = new PopUpWindows(2);
        allWindows = new ArrayList<>();
        allWindows.add(mainW);
        allWindows.add(optW);
        allWindows.add(gameW);
        allWindows.add(aboutGameW);
        allWindows.add(howToWinW);
        allWindows.add(savedGamesW);
        numberOfWindows = 6;


    }

    void start(){
        model = new Model(this); /// ?????
        // szukseges es allando listenerek hozzaadasa
        mainW.addMainWindowListener(new MainWindowListener());
        optW.addOptionsWindowListener(new OptionWindowListener());
        savedGamesW.addSavedGamesListener(new SavedGamesListener());

        setWindowVisible(0);
    }


    /// LISTENEREK ///
    /**
     * mainwindowhoz rendelodnek hozza nem tortenik semmi kulonleges csak
     *     a megfelelo ablakok jelenitodnek meg /tunnek el
     */
    class MainWindowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            saveEnabled = true;
            if (event.getSource() == mainW.getNewGameButton()) {
                setWindowVisible(1);
            }
            if (event.getSource() == mainW.getAboutGame()) {
                setWindowVisible(3);
            }
            if (event.getSource() == mainW.getHowToWin()) {
                setWindowVisible(4);
            }
            if (event.getSource() == mainW.getLoadButton()) {
                setWindowVisible(5);
            }
        }
    }

    /**
     * tarolt jatkokat megjelenito listanak a listenerje, a kijelolt elemnek
     *     megfelelo nevu file-bol adott jatekallast betoltve elinditja azt a jatkot amit
     *     lehet folytatni
     */
    class SavedGamesListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (!event.getValueIsAdjusting()) {
                int gameIndex = savedGamesW.getSavedGames().getSelectedIndex();
                initNewPlayField(gameIndex);
                setWindowVisible(2);
                // pici elougro ablak eltunjon
                savedGamesW.setVisible(false);
                // menobb selection effect xd
                savedGamesW.getSavedGames().setSelectionBackground(null);
            }
        }
    }
    /**
     *     opciokat beallito ablakhoz definial listenereket
     */
    class OptionWindowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == optW.getOnePlayerButton()) {
                playerNum = 1;
                initNewPlayField();
                gameW.setTitleText("Versus AI!");
                /// TODO somehow do the play against the ai
                /// TODO import data like fieldsize ect.
                setWindowVisible(2);
            }
            if (event.getSource() == optW.getTwoPlayerButton()) {
                playerNum = 2;
                initNewPlayField();
                gameW.setTitleText("X-turn to move!");
                /// TODO import data like fieldsize ect.
                setWindowVisible(2);
            }
            // 2-8 ig allitottam be a hatarokat mert ez nez ki meg jol, de minden algoritmus mukodik nagyobb
            // n*n-es meretben is
            if (event.getSource() == optW.getIncrementButton()) {
                if (gridSize + 1 < 9)
                    optW.getSizeLable().setText(String.valueOf(++gridSize));
            }
            if (event.getSource() == optW.getDecrementButton()) {
                if (gridSize - 1 > 2)
                    optW.getSizeLable().setText(String.valueOf(--gridSize));
            }
        }
    }



    /**
     * save & exit buttonoknak a listenerje mivel mindketto egy ablakon van es
     *     mindketto egyszeru
     */
    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
//            save gomb megnyomasa eseten a gamewindowon eppen megjeleno jatek
//            automatikusan mentodik az annak megfelelo txt file-ba
//            jatek folytathato kedv szerint de csak save gombnyomasra ment
            if (event.getSource() == gameW.getSaveButton()){
                if (saveEnabled) {
                    model.saveInnerTable();
                }
            }
//            kilep a fomenube ahonnan indulva mindig uj tabla generalodik
//            gamewindowban ezert nem kell foglalkozni itt a resetelessel
            if (event.getSource() == gameW.getExitButton()){
                setWindowVisible(0);
            }
        }
    }



    /**
     *      legnagyobb & leghosszabb fgv, validalja hogy a kattintas ervenyes e, eltuno elemeket beallitja
     *     ha gyoztes van azt kijelzi majd es visszavisz a mainwindowba
     */
    class TwoPlayerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (event.getSource() == gameW.getGridButtons()[i][j]) {
                        if (model.getTableShape(i,j) == Shape.DEFAULT) {
                            palceXO(xTurn,i,j);
                            xTurn = !xTurn;
                        }
                        // mivel elozo fuggvenyhivas noveli egyedul a clickCountert ezert
                        // ennek a fuggvenynek csak akkor van hatasa ha az elozo lefutott
                        if (clickCounter > (coverage-1)) {
                            selectDisappear();
                        }
                        ///  todo section 3
                        if (clickCounter > coverage) {
                            selectDisappearedCell();
                        }
                        /// todo section 4
                        selectWinner();
                    }
                }
            }
        }
    }

    /**
     * tematikaja hasonlit a twoplayerlistenerhez, csak itt az emberi lepes utan a robot
     * kovetkezik es, utana ismet az ember
     */
    class OnePlayerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            xTurn = true;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (event.getSource() == gameW.getGridButtons()[i][j]) {

                        if (model.getTableShape(i,j) == Shape.DEFAULT){
                            palceXO(xTurn,i,j);
                            if (clickCounter > (coverage-1))
                                selectDisappear();
                            if (clickCounter > coverage)
                                selectDisappearedCell();
                            selectWinner();
                            // robot places object
                            robotMoves(++clickCounter,i,j);
                            if (clickCounter > (coverage-1))
                                selectDisappear();
                            if (clickCounter > coverage)
                                selectDisappearedCell();
                            selectWinner();
                        }
                    }
                }
            }
        }
    }

    /**
     * ujraalitja a szinet a palyanak, megkapja a robot altal valasztott
     * koordinatakat, azokat a gamewindow palyajan beallitja
     * @param clickCounter
     * @param row
     * @param col
     */
    private void robotMoves(int clickCounter,int row,int col){
        // todo robot akkor is lep amikor player ervenytelen lepest tett ff
        gameW.resetGrid();
        int[] result = model.robotPlaces(clickCounter,row,col);
        gameW.getGridButtons()[result[0]][result[1]].setText("O");
    }

    /**
     *     jatekos altal kattintott elemet lerakja annak megfeleloen eppen
     *     kinek van a kore
     * @param xTurn
     * @param i
     * @param j
     */
    private void palceXO(boolean xTurn,int i,int j){
        gameW.resetGrid();
        model.setTableOrder(i,j, ++clickCounter);
        model.setTableShape(i,j,xTurn ? Shape.X : Shape.O);
        gameW.getGridButtons()[i][j].setText(xTurn ? "X" : "O");
        gameW.setTitleText(xTurn ? "X" + " - making a move" : "O" + " - making a move");
    }

    /**
     *     kijeloli azt az elemet a tablan amelyik kovetkezo lepeskor el fog tunni
     */
    private void selectDisappear(){
        for (int k = 0; k < gridSize; k++) {
            for (int l = 0; l < gridSize; l++) {
                if (model.getTableOrder(k,l) == clickCounter - (coverage-1)) {
                    /// minisection?? maybe?
                    gameW.getGridButtons()[k][l].setForeground(darkTheme.get("disappearC"));
                    gameW.getGridButtons()[k][l].setBorder(new LineBorder(darkTheme.get("disappearC"), 3));
                }
            }
        }
    }

    /**
     *     a feltételnek megfelelő clickcounterhez viszonyitott erteku mezot reseteli,
     *     ugymond eltunteti
     */
    private void selectDisappearedCell(){
        for (int k = 0; k < gridSize; k++) {
            for (int l = 0; l < gridSize; l++) {
                if (model.getTableOrder(k,l) == clickCounter - coverage) {
                    /// minisection?? maybe?
                    model.setTableOrder(k,l, 0);
                    model.setTableShape(k,l, Shape.DEFAULT);
                    gameW.getGridButtons()[k][l].setText("");
                }
            }
        }
    }

    /**
     *     megnezi vannak e gyoztes allasok, ha igen akkor azokat kijelti
     *     majd 3 mp-et varva ujrainditja a jatekot, tehat visszavisz a fomenube
     */
    private void selectWinner(){
        int[][] winners = new int[gridSize][2];
        if (model.checkForWinner(winners)) {
            // todo xturn alapjan kiirast is leeht valtoztatni xd
            gameW.setTitleText(xTurn ? "O" + " won!" : "X" + " won!");
            gameW.win(winners);
            gameW.setButtonsNotEnabled();
            saveEnabled = false;
            try {
                // updateli az ablakot sleep elott
                gameW.update(gameW.getGraphics());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameW.setVisible(false);
            mainW.setVisible(true);
            xTurn = true;
        }
    }

    /// KARBANTARTO METODUSOK ///
    /**
     *      uj jatek inditasanal hivodik meg attol fuggetelenul hogy
     *     hany jatekos van model beslo & gamewindow tablait frissiti
     */
    private void initNewPlayField(){

        model.initInnerTable(gridSize);
        model.resetTable();
        gameW.resetAll();
        gameW.initGameTable(gridSize);
        clickCounter = 0;
        setTitle();
        addListeners();

        coverage =(int)Math.floor((double)gridSize*gridSize*2/3);
    }

    /**
     *     kivalasztott indexu file-bol elkezdi betolteni a jatekot es meg a
     *     hozza kapcsolodo beallitasokat is elvegzi hogy minden siman menjen
     * @param gameIndex
     */
    private void initNewPlayField(int gameIndex){

        gameW.resetAll();

        if(model.loadInnerTable(gameIndex)){
            // todo currenttable legyen private
            gameW.copyTable(gridSize,model.currentTable);
        }else{
            // sikertelen beolvasas eseten tudjuk ures filet nyitottunk meg ezert
            // model innerTabeljet gamewindow Tabeljet le kell frissitenunk, hogy
            // elozo jaekbol ne maradjanak meg az el  nem mentett reszek
            // todo maybe egy kulon fuggveny ennek a resznek mert ez a reszlet megegyezik elozovel
            playerNum = 2;
            model.initInnerTable(gridSize);
            model.resetTable();
            gameW.initGameTable(gridSize);
            clickCounter = 0;

        }
        setTitle();
        addListeners();
        coverage =(int)Math.floor((double)gridSize*gridSize*2/3);
    }
    /**
     * jatekosszamtol fuggoen
     */
    public void addListeners(){
        if(playerNum == 1){
            gameW.addGameWindowListener(new OnePlayerListener(),new SaveButtonListener());
            gameW.setTitleText("Versus AI!");
        }else{
            gameW.addGameWindowListener(new TwoPlayerListener(),new SaveButtonListener());
            gameW.setTitleText("X-turn to move!");
        }
    }

    /**
     * jatekosszamtol fuggoen
     */
    public void setTitle(){
        if(playerNum == 1)
            gameW.setTitleText("Versus AI!");
        else{
            if (xTurn) {
                gameW.setTitleText("X-turn to move!");
            } else {
                gameW.setTitleText("O-turn to move!");
            }
        }
    }

    /**
     * adott ablakot megjeleniti es lathatova teszi a tobbit eltunteti
     * @param win
     */
    public void setWindowVisible(int win) {

        if (win < 3) {
            for (int i = 0; i < numberOfWindows - 1; i++) {
                allWindows.get(i).setVisible(i == win);
            }
        } else {
            switch (win) {
                case 3 -> {
                    allWindows.get(3).setVisible(true);
                }
                case 4 -> {
                    allWindows.get(4).setVisible(true);
                }
                case 5 -> {
                    allWindows.get(5).setVisible(true);
                }
            }
        }
    }

    /**
     * itt kicsit tobb adatbol all mert ezek osszetartoznak getter
     * @param save
     */
    public void getMetaData(Savable save){

        save.playerNum = playerNum;
        save.clickCounter = clickCounter;
        save.gridSize = gridSize;
        save.xTurn = xTurn;
    }

    /**
     * setter osszefuggo adatokat allit be
     * @param save
     */
    public void setMetaData(Savable save){
        this.playerNum = save.playerNum;
        this.clickCounter = save.clickCounter;
        /// todo lehet egy olyan eset hogy amikor egybol betoltjuk a tablat es 66%+ elem van a palyan akkor
        /// todo az az elem aminek el kellene tűnnie nem fog eltűnni sem kijelölődni. maybe fix this
        this.gridSize = save.gridSize;
        this.xTurn = save.xTurn;
    }

}
