package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * 棋盘类
 *
 * @author 黄小可
 * @see Piece
 */

public class Board
{
    private int lineAmount;

    private Piece[][] pieces;

    private int blackAmount;

    private int whiteAmount;

    /**
     * 记录现在应由黑子还是白子下棋
     */
    private int currentStatus;

    /**
     * 初始化棋子的二维数组，
     * 包括初始化棋子的状态（status）以及四周的子，
     * 初始化黑白子的数目为0，
     * 黑子先下
     *
     * @param lineAmount 行数
     */
    public Board(int lineAmount)
    {
        this.lineAmount = lineAmount;
        pieces = new Piece[lineAmount][lineAmount];
        for (int x = 0; x < lineAmount; x++)
        {
            for (int y = 0; y < lineAmount; y++)
            {
                if (x == 0 || y == 0 || x == lineAmount - 1 || y == lineAmount - 1)
                {
                    pieces[x][y] = new Piece(Piece.BORDER, false);
                } else
                {
                    pieces[x][y] = new Piece(Piece.NULL, false);
                }
                pieces[x][y].setBoard(this);
            }
        }
        for (int x = 1; x < lineAmount - 1; x++)
            for (int y = 1; y < lineAmount - 1; y++)
            {
                Piece[] surroundPieces = {
                        pieces[x - 1][y], pieces[x + 1][y],
                        pieces[x][y - 1], pieces[x][y + 1]
                };
                pieces[x][y].setSurround(surroundPieces);
            }
        this.blackAmount = 0;
        this.whiteAmount = 0;
        this.currentStatus = Piece.BLACK;
    }

    /**
     * 初始化UI界面
     * 画出线段和棋子
     * 并对棋子添加点击事件
     *
     * @param pane  布局
     * @param stage 舞台
     */
    public void paint(Pane pane, Stage stage)
    {
        Line[] vLines = new Line[lineAmount];
        Line[] hLines = new Line[lineAmount];
        for (int i = 0; i < lineAmount; i++)
        {
            vLines[i] = new Line(i * Main.getGap(), 0, i * Main.getGap(),
                    Main.getWidth());
            hLines[i] = new Line(0, Main.getGap() * i, Main.getWidth(),
                    Main.getGap() * i);
            pane.getChildren().addAll(vLines[i], hLines[i]);
        }
        for (int x = 0; x < lineAmount; x++)
        {
            for (int y = 0; y < lineAmount; y++)
            {
                Piece piece = pieces[x][y];
                piece.setCenterX(x * Main.getGap());
                piece.setCenterY(y * Main.getGap());
                piece.setFill(Paint.valueOf("#ffffff00"));
                piece.setRadius(15);
                pane.getChildren().add(piece);

                piece.setOnMouseClicked(event ->
                {
                    if (piece.getStatus() == Piece.NULL)
                    {
                        piece.update();
                        stage.setTitle("黑：" + blackAmount + " 白：" + whiteAmount);
                    }
                });
            }
        }
    }

    public int getBlackAmount() { return blackAmount; }

    public void setBlackAmount(int blackAmount) { this.blackAmount = blackAmount; }

    public int getWhiteAmount() { return whiteAmount; }

    public void setWhiteAmount(int whiteAmount) { this.whiteAmount = whiteAmount; }

    public int getCurrentStatus() { return currentStatus; }

    public void setCurrentStatus(int currentStatus)
    {
        this.currentStatus = currentStatus;
    }
}
