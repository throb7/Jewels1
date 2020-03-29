import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class Jewels1 extends Application {
    private Stage primaryStage;
    private Color[] c;
    private Color[][] colorboard;
    private Button[][] a;
    private int result1;
    private int result2;
    private int result3;
    private Button firstbutton;
    private Button secondbutton;
    private int[] positionarray1;
    private int[] positionarray2;
    private Color originalcolor1;
    private Color originalcolor2;
    private boolean firstalreadyclicked;
    private int topcount;
    private int botcount;
    private int leftcount;
    private int rightcount;
    private int moves;
    private boolean gamewon = true;

    public Jewels1() {
    }

    public void markButtonsVertical(Button[][] buttonsarray, int[] positionarray, int topcount, int botcount) {
        for(int index = positionarray[0] + botcount; index >= positionarray[0] - topcount; --index) {
            buttonsarray[index][positionarray[1]].setText("*");
        }

    }

    public void markButtonsHorizontal(Button[][] buttonsarray, int[] positionarray, int leftcount, int rightcount) {
        for(int index = positionarray[1] - leftcount; index <= positionarray[1] + rightcount; ++index) {
            buttonsarray[positionarray[0]][index].setText("*");
        }

    }

    public void fillButtons(Color[][] colorboard, Button[][] buttonsarray) {
        for(int rowindex = 0; rowindex < colorboard.length; ++rowindex) {
            for(int columnindex = 0; columnindex < colorboard[0].length; ++columnindex) {
                buttonsarray[rowindex][columnindex].setBackground(new Background(new BackgroundFill[]{new BackgroundFill(colorboard[rowindex][columnindex], new CornerRadii(10.0D), new Insets(1.0D, 1.0D, 1.0D, 1.0D))}));
            }
        }

    }

    public Color[][] createColorBoard(Color[] colorarray, int numrows, int numcolumns) {
        this.colorboard = new Color[numrows][numcolumns];

        for(int rowindex = 0; rowindex < this.colorboard.length; ++rowindex) {
            for(int columnindex = 0; columnindex < this.colorboard[0].length; ++columnindex) {
                this.colorboard[rowindex][columnindex] = colorarray[(int)(Math.random() * (double)colorarray.length)];
            }
        }

        return this.colorboard;
    }

    public Color[] createColorArray(int numcolors) {
        Color[] c = new Color[numcolors];

        for(int index = 0; index < numcolors; ++index) {
            c[index] = Color.color(Math.random(), Math.random(), Math.random());
        }

        return c;
    }

    public int checkToTop(Color targetcolor, Color[][] colorboard, int[] positionarray) {
        int count = 0;
        boolean samecolor = true;

        for(int index = positionarray[0] - 1; index >= 0 && samecolor; --index) {
            if (colorboard[index][positionarray[1]].equals(targetcolor)) {
                ++count;
            } else {
                samecolor = false;
            }
        }

        return count;
    }

    public int checkToBottom(Color targetcolor, Color[][] colorboard, int[] positionarray) {
        int count = 0;
        boolean samecolor = true;

        for(int index = positionarray[0] + 1; index <= colorboard.length - 1 && samecolor; ++index) {
            if (colorboard[index][positionarray[1]].equals(targetcolor)) {
                ++count;
            } else {
                samecolor = false;
            }
        }

        return count;
    }

    public int checkToLeft(Color targetcolor, Color[][] colorboard, int[] positionarray) {
        int count = 0;
        boolean samecolor = true;

        for(int index = positionarray[1] - 1; index >= 0 && samecolor; --index) {
            if (colorboard[positionarray[0]][index].equals(targetcolor)) {
                ++count;
            } else {
                samecolor = false;
            }
        }

        return count;
    }

    public int checkToRight(Color targetcolor, Color[][] colorboard, int[] positionarray) {
        int count = 0;
        boolean samecolor = true;

        for(int index = positionarray[1] + 1; index <= colorboard[1].length - 1 && samecolor; ++index) {
            if (colorboard[positionarray[0]][index].equals(targetcolor)) {
                ++count;
            } else {
                samecolor = false;
            }
        }

        return count;
    }

    public void replaceJewels1horizontal(int[] positionarray2, int[] positionarray1, Color[][] colorboard, int leftcount, int rightcount, Color[] colorarray, Color color1, Color color2) {
        if (leftcount + rightcount + 1 >= 3) {
            for(int index = positionarray2[1] - leftcount; index <= positionarray2[1] + rightcount; ++index) {
                for(int shiftindex = positionarray2[0]; shiftindex >= 0; --shiftindex) {
                    if (shiftindex - 1 >= 0) {
                        Color temp = colorboard[shiftindex - 1][index];
                        colorboard[shiftindex][index] = temp;
                    } else {
                        colorboard[shiftindex][index] = colorarray[(int)(Math.random() * (double)colorarray.length)];
                    }
                }
            }
        } else {
            colorboard[positionarray2[0]][positionarray2[1]] = color2;
            colorboard[positionarray1[0]][positionarray1[1]] = color1;
        }

    }

    public void specialreplaceJewels1horizontal(int[] positionarray2, int[] positionarray1, Color[][] colorboard, int leftcount, int rightcount, Color[] colorarray, Color color1, Color color2) {
        if (leftcount + rightcount + 1 >= 3) {
            for(int index = positionarray2[1] - leftcount; index <= positionarray2[1] + rightcount; ++index) {
                for(int shiftindex = positionarray2[0]; shiftindex >= 0; --shiftindex) {
                    if (positionarray2[1] != index) {
                        if (shiftindex - 1 >= 0) {
                            Color temp = colorboard[shiftindex - 1][index];
                            colorboard[shiftindex][index] = temp;
                        } else {
                            colorboard[shiftindex][index] = colorarray[(int)(Math.random() * (double)colorarray.length)];
                        }
                    }
                }
            }
        } else {
            colorboard[positionarray2[0]][positionarray2[1]] = color2;
            colorboard[positionarray1[0]][positionarray1[1]] = color1;
        }

    }

    public boolean checkWin(Button[][] buttonsarray) {
        boolean textmatch = true;

        for(int rowindex = 0; rowindex < buttonsarray.length && textmatch; ++rowindex) {
            for(int columnindex = 0; columnindex < buttonsarray[1].length && textmatch; ++columnindex) {
                if (!buttonsarray[rowindex][columnindex].getText().equals("*")) {
                    textmatch = false;
                    this.gamewon = false;
                }
            }
        }

        if (textmatch) {
            this.gamewon = true;
        }

        return this.gamewon;
    }

    public void replaceJewels1(int[] positionarray2, int[] positionarray1, Color[][] colorboard, int counttop, int countbottom, Color[] colorarray, Color color1, Color color2) {
        if (counttop + countbottom + 1 >= 3) {
            for(int index = positionarray2[0] + countbottom; index >= 0; --index) {
                if (index - (counttop + countbottom + 1) >= 0) {
                    Color tempcolor = colorboard[index - (counttop + countbottom + 1)][positionarray2[1]];
                    colorboard[index][positionarray2[1]] = tempcolor;
                } else {
                    colorboard[index][positionarray2[1]] = colorarray[(int)(Math.random() * (double)colorarray.length)];
                }
            }
        } else {
            colorboard[positionarray2[0]][positionarray2[1]] = color2;
            colorboard[positionarray1[0]][positionarray1[1]] = color1;
        }

    }

    public Button[][] createButtons(int numRows, int numColumns) {
        Button[][] a = new Button[numRows][numColumns];

        for(int i = 0; i < numRows; ++i) {
            for(int j = 0; j < numColumns; ++j) {
                a[i][j] = new Button("  ");
            }
        }

        return a;
    }

    public void initialize() {
        if (this.getParameters().getRaw().size() == 0) {
            this.c = this.createColorArray(4);
            this.colorboard = this.createColorBoard(this.c, 8, 10);
            this.a = this.createButtons(8, 10);
            this.fillButtons(this.colorboard, this.a);
        } else {
            this.result1 = Integer.parseInt((String)this.getParameters().getRaw().get(0));
            this.result2 = Integer.parseInt((String)this.getParameters().getRaw().get(1));
            this.result3 = Integer.parseInt((String)this.getParameters().getRaw().get(2));
            if (this.result1 < 32 && this.result1 >= 3 && this.result2 < 32 && this.result2 >= 3 && this.result3 < 10 && this.result3 > 1) {
                this.c = this.createColorArray(this.result3);
                this.colorboard = this.createColorBoard(this.c, this.result1, this.result2);
                this.a = this.createButtons(this.result1, this.result2);
                this.fillButtons(this.colorboard, this.a);
            } else {
                this.c = this.createColorArray(4);
                this.colorboard = this.createColorBoard(this.c, 8, 10);
                this.a = this.createButtons(8, 10);
                this.fillButtons(this.colorboard, this.a);
            }
        }

    }

    public void registerButtons(Button[][] buttonarray){
        for(int i = 0; i < buttonarray.length; i++){
            for(int j = 0; j < buttonarray[i].length; j++){
                buttonarray[i][j].setOnAction(new ProcessClick());
            }
        }
    }

    public int[] findButtonInArray(Button targetbutton, Button[][] buttonarray) {
        int rowlocation = -1;
        int columnlocation = -1;
        int[] positionarray = new int[2];

        for(int i = 0; i < buttonarray.length; ++i) {
            for(int j = 0; j < buttonarray[i].length; ++j) {
                if (buttonarray[i][j] == targetbutton) {
                    rowlocation = i;
                    columnlocation = j;
                }
            }
        }

        positionarray[0] = rowlocation;
        positionarray[1] = columnlocation;
        return positionarray;
    }

    public void start(Stage primaryStage) {
        GridPane gridpane = new GridPane();
        Scene scene = new Scene(gridpane);
        this.initialize();
        this.registerButtons(this.a);

        for(int i = 0; i < this.a.length; ++i) {
            for(int j = 0; j < this.a[i].length; ++j) {
                gridpane.add(this.a[i][j], j, i);
            }
        }

        this.primaryStage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    private class ProcessClick implements EventHandler<ActionEvent> {
        //override the handle method
        public void handle(ActionEvent e){
            //the case where this is the first time clicking
            if(Jewels1.this.firstalreadyclicked == false){
                //store the location(in memory) of the button
                firstbutton = (Button)e.getSource();
                //use the helper method to store the location in the array for future comparison
                positionarray1 = Jewels1.this.findButtonInArray(firstbutton, a);
                //this coordinate is the same for the color board
                originalcolor1 = colorboard[positionarray1[0]][positionarray1[1]];
                colorboard[positionarray1[0]][positionarray1[1]] = colorboard[positionarray1[0]][positionarray1[1]].darker();
                Jewels1.this.fillButtons(colorboard, a);
                //remember that we have clicked for the first time
                firstalreadyclicked = true;
            }
            //otherwise, this is the second click
            else{
                //we first restore the color of the first clicked button
                colorboard[positionarray1[0]][positionarray1[1]] = originalcolor1;
                Jewels1.this.fillButtons(colorboard, a);
                //store the location(in memory) of the second button
                secondbutton = (Button)e.getSource();
                //store the position of the array
                positionarray2 = Jewels1.this.findButtonInArray(secondbutton, a);
                //store the color of the array
                originalcolor2 = Jewels1.this.colorboard[positionarray2[0]][positionarray2[1]];
                //we check if there are in fact adjacent, and if they are, we swap the color of the two
                if((positionarray2[0] == positionarray1[0] - 1 && positionarray2[1] == positionarray1[1]) ||
                        (positionarray2[0] == positionarray1[0] && positionarray2[1] == positionarray1[1] - 1) ||
                        (positionarray2[0] == positionarray1[0] && positionarray2[1] == positionarray1[1] + 1) ||
                        (positionarray2[0] == positionarray1[0] + 1 && positionarray2[1] == positionarray1[1])){
                    Jewels1.this.colorboard[positionarray1[0]][positionarray1[1]] = originalcolor2;
                    Jewels1.this.colorboard[positionarray2[0]][positionarray2[1]] = originalcolor1;
                    Jewels1.this.fillButtons(colorboard, a);
                    firstalreadyclicked = false;
                    //after the switch, we now proceed to check if there are any 3's in a row or in a column and do the corresponding action
                    topcount = Jewels1.this.checkToTop(originalcolor1, colorboard, positionarray2);
                    botcount = Jewels1.this.checkToBottom(originalcolor1, colorboard, positionarray2);
                    leftcount = Jewels1.this.checkToLeft(originalcolor1, colorboard, positionarray2);
                    rightcount = Jewels1.this.checkToRight(originalcolor1, colorboard , positionarray2);
                    //the case where only vertical removal is needed
                    if((topcount + botcount + 1 >= 3) && (leftcount + rightcount + 1 < 3)){
                        Jewels1.this.replaceJewels1(positionarray2, positionarray1, colorboard, topcount, botcount, c, originalcolor1, originalcolor2);
                        //we update the color to the buttons
                        Jewels1.this.fillButtons(colorboard, a);
                        //we mark the buttons
                        Jewels1.this.markButtonsVertical(a, positionarray2, topcount, botcount);
                        Jewels1.this.moves = Jewels1.this.moves + 1;
                        //check if the game is won
                        if(Jewels1.this.checkWin(a) == true){
                            System.out.println("You Won! " + moves + " moves");
                        }
                    }
                    //the case where only horizontal is needed
                    else if((topcount + botcount + 1 < 3) && (leftcount + rightcount + 1 >= 3)){
                        Jewels1.this.replaceJewels1horizontal(positionarray2, positionarray1, colorboard, leftcount, rightcount, c, originalcolor1, originalcolor2);
                        //we need to update the buttons to the color
                        Jewels1.this.fillButtons(colorboard, a);
                        //we need to mark the buttons
                        Jewels1.this.markButtonsHorizontal(a, positionarray2, leftcount, rightcount);
                        Jewels1.this.moves = Jewels1.this.moves + 1;
                        //check if the game is won
                        if(Jewels1.this.checkWin(a) == true){
                            System.out.println("You Won! " + moves + " moves");
                        }
                    }
                    //the case where neither is needed
                    else if((topcount + botcount + 1 < 3) && (leftcount + rightcount + 1 < 3)){
                        //the method involves the condition and will swap the color back
                        Jewels1.this.replaceJewels1horizontal(positionarray2, positionarray1, colorboard, leftcount, rightcount, c, originalcolor1, originalcolor2);
                        Jewels1.this.fillButtons(colorboard, a);
                    }
                    //the case where both horizontal and vertical are needed
                    else{
                        //we perform the special horizontal shift while leaving the second button to avoid overcounting
                        Jewels1.this.specialreplaceJewels1horizontal(positionarray2, positionarray1, colorboard, leftcount, rightcount, c, originalcolor1, originalcolor2);
                        Jewels1.this.replaceJewels1(positionarray2, positionarray1, colorboard, topcount, botcount, c, originalcolor1, originalcolor2);
                        //we update the board and mark the buttons
                        Jewels1.this.fillButtons(colorboard, a);
                        Jewels1.this.markButtonsHorizontal(a, positionarray2, leftcount, rightcount);
                        Jewels1.this.markButtonsVertical(a, positionarray2, topcount, botcount);
                        Jewels1.this.moves = Jewels1.this.moves + 1;
                        //check if the game is won
                        if(Jewels1.this.checkWin(a) == true){
                            System.out.println("You Won! " + moves + " moves");
                        }
                    }
                }
                //if they are not adjacent, we again wait for the first click
                else
                    firstalreadyclicked = false;
            }

        }
    }
}

