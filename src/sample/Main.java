package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{
    /**
     * 棋盘的行数
     */
    private static int lineAmount = 21;

    /**
     * 棋盘的宽度
     */
    private static double width = 950;

    /**
     * 每两条线间的宽度
     * 即格子的宽度
     */
    private static double gap = width / (lineAmount - 1);

    public static double getWidth() { return width; }

    public static double getGap() { return gap; }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        AnchorPane root = new AnchorPane();
        primaryStage.setScene(new Scene(root, Main.getWidth(), Main.getWidth()));
        Board board = new Board(Main.lineAmount);
        board.paint(root, primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}




