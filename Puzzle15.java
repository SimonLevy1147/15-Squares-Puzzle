import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Puzzle15 extends Application{
    public static int[][] gameBoard;
    public static Scanner scan = new Scanner(System.in);
    public static GridPane grid;
    public static boolean hasWon = false;
    public static void main(String[] args) {
        gameBoard = Puzzle15.boardMaker();

        Application.launch(args);


        //TODO: reset button
    }

    public static class CoordinatePair {
        private int x;
        private int y;
        private int value;
        public CoordinatePair(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
        public void setX(int newX) {
            x = newX;
        }
        public void setY(int newY) {
            y = newY;
        }
        public void setValue(int newValue) {
            value = newValue;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getValue() {
            return value;
        }
    }

    public static CoordinatePair finder(int value) {
        CoordinatePair coords = new CoordinatePair(0, 0, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[j][i] == value) {
                    coords.setX(j);
                    coords.setY(i);
                    coords.setValue(value);
                    break;
                }
            }
        }
        return coords;
    }

    public void start(Stage primaryStage) {
        System.out.println();
        grid = new GridPane();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[j][i] == 0)
                    continue;
                Button button = new Button(String.valueOf(gameBoard[j][i]));
                button.setMinSize(100, 100);
                grid.add(button, i, j);
                button.setOnAction(event -> {
                    CoordinatePair coords = finder(Integer.parseInt(button.getText()));
                    if (validSquare(coords.getX(), coords.getY()))
                        grid.getChildren().remove(button);
                    turn(coords);
                });
            }
        }
        final int BORDER_WIDTH = 20;

        grid.setLayoutX(BORDER_WIDTH);
        grid.setLayoutY(BORDER_WIDTH);

        grid.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(grid, 440, 440, Color.BEIGE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static int[][] boardMaker() {
        int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        Random rand = new Random();

        for (int i = 0; i < values.length; i++) {
			int randomIndexToSwap = rand.nextInt(values.length);
			int temp = values[randomIndexToSwap];
			values[randomIndexToSwap] = values[i];
			values[i] = temp;
		}

        int[][] gameBoard = new int[4][4];

        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(count==values.length) 
                    break;

            gameBoard[i][j]=values[count];
            count++;
            }
        }
        return gameBoard;
    }
    
    public static boolean winChecker(int[][] gameBoard) {

        for (int j = 0; j < 3; j++) {
            for (int i = 1; i < 4; i++) {
                if (gameBoard[i][j] != ((gameBoard[i-1][j])+4)) //Vertical checking
                    return false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                if (gameBoard[i][j] != (gameBoard[i][j-1]+1)) //Horizontal checking
                    return false;
            }
        }
        return true;
    }

    public static boolean checkUp(int i, int j) {
        if (i == 0)
            return false;
        
        if (gameBoard[i-1][j] == 0)
            return true;
        
        return false;
    }

    public static boolean checkDown(int i, int j) {
        if (i == 3)
            return false;
        
        if (gameBoard[i+1][j] == 0)
            return true;
        
        return false;
    }

    public static boolean checkRight(int i, int j) {
        if (j == 3)
            return false;
        
        if (gameBoard[i][j+1] == 0)
            return true;
        
        return false;
    }

    public static boolean checkLeft(int i, int j) {
        if (j == 0)
            return false;
        
        if (gameBoard[i][j-1] == 0)
            return true;
        
        return false;
    }

    public static boolean validSquare(int i, int j) {
        return (checkLeft(i, j) || checkRight(i, j) || checkDown(i, j) || checkUp(i, j));
    }
    public static int[][] turn(CoordinatePair coords) {
        int i = coords.getX();
        int j = coords.getY();
        int value = coords.getValue();
        int temp;


        if (checkLeft(i, j)) {
            temp = gameBoard[i][j-1];
            gameBoard[i][j-1] = gameBoard[i][j];
            gameBoard[i][j] = temp;
            Button button = new Button(String.valueOf(value));
            button.setMinSize(100, 100);
            grid.add(button, j-1, i);
            button.setOnAction(event -> {
                CoordinatePair coords1 = finder(Integer.parseInt(button.getText()));
                if (validSquare(coords1.getX(), coords1.getY()))
                    grid.getChildren().remove(button);
                turn(coords1);
            });
        } else if (checkRight(i, j)) {
            temp = gameBoard[i][j+1];
            gameBoard[i][j+1] = gameBoard[i][j];
            gameBoard[i][j] = temp;
            Button button = new Button(String.valueOf(value));
            button.setMinSize(100, 100);
            grid.add(button, j+1, i);
            button.setOnAction(event -> {
                CoordinatePair coords1 = finder(Integer.parseInt(button.getText()));
                if (validSquare(coords1.getX(), coords1.getY()))
                    grid.getChildren().remove(button);
                turn(coords1);
            });
        } else if (checkUp(i, j)) {
            temp = gameBoard[i-1][j];
            gameBoard[i-1][j] = gameBoard[i][j];
            gameBoard[i][j] = temp;
            Button button = new Button(String.valueOf(value));
            button.setMinSize(100, 100);
            grid.add(button, j, i-1);
            button.setOnAction(event -> {
                CoordinatePair coords1 = finder(Integer.parseInt(button.getText()));
                if (validSquare(coords1.getX(), coords1.getY()))
                    grid.getChildren().remove(button);
                turn(coords1);
            });
        } else if (checkDown(i,j)) {
            temp = gameBoard[i+1][j];
            gameBoard[i+1][j] = gameBoard[i][j];
            gameBoard[i][j] = temp;
            Button button = new Button(String.valueOf(value));
            button.setMinSize(100, 100);
            grid.add(button, j, i+1);
            button.setOnAction(event -> {
                CoordinatePair coords1 = finder(Integer.parseInt(button.getText()));
                if (validSquare(coords1.getX(), coords1.getY()))
                    grid.getChildren().remove(button);
                turn(coords1);
                });
        } else {
            System.out.println(validSquare(i, j));
            System.out.println("Box cannot be moved. Try again");
        }
        
        if (winChecker(gameBoard)) {
            System.out.println("Congratulations!!!");
            System.exit(0);
        }
        return gameBoard;
    }

}