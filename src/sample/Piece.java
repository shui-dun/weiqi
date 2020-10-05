package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * 棋子类
 * 为方便绘图，该类继承于Circle类
 *
 * @author 黄小可
 * @see Board
 */
public class Piece extends Circle
{
    public static final int BLACK = 1;

    /**
     * 注意WHITE与BLACK的值互为相反数
     */
    public static final int WHITE = -1;

    public static final int NULL = 0;

    public static final int BORDER = -2;

    /**
     * 棋子的状态
     * BLACK代表黑子
     * WHITE代表白子
     * NULL代表该位置没有棋子
     * BORDER代表此处为边界
     */
    private int status;

    /**
     * 四周的四个棋子
     */
    private Piece[] surround = new Piece[4];

    /**
     * 该子所在的棋盘
     */
    private Board board;

    /**
     * 在一次更新操作中是否被搜索过，如果被搜索过，则不再进行第二次搜索
     */
    private boolean beSearched;

    public Piece(int status, boolean beSearched)
    {
        this.status = status;
        this.beSearched = beSearched;
    }

    public void update()
    {
        setStatus(board.getCurrentStatus());
        updateColour();
        updateAmount();
        for (int position = 0; position < 4; position++)
        {
            if (surround[position].status == -status)
            {
                List<Piece> checkedPieces = new ArrayList<>();
                surround[position].unionPieces(checkedPieces);
                if (!isAlive(checkedPieces))
                {
                    updateAmount(checkedPieces);
                    for (Piece piece : checkedPieces)
                    {
                        piece.status = Piece.NULL;
                        piece.updateColour();
                    }
                }
                for (Piece piece : checkedPieces)
                {
                    piece.beSearched = false;
                }
            }
        }
        board.setCurrentStatus(-board.getCurrentStatus());
    }

    public void unionPieces(List<Piece> checkedPieces)
    {
        beSearched = true;
        checkedPieces.add(this);
        for (Piece surroundPiece : surround)
        {
            if (!surroundPiece.beSearched && surroundPiece.status == status)
            {
                surroundPiece.beSearched = true;
                surroundPiece.unionPieces(checkedPieces);
            }
        }
    }

    public static boolean isAlive(List<Piece> pieces)
    {
        for (Piece piece : pieces)
        {
            for (Piece surroundPiece : piece.surround)
            {
                if (surroundPiece.status == Piece.NULL)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateAmount()
    {
        if (board.getCurrentStatus() == Piece.BLACK)
            board.setBlackAmount(board.getBlackAmount() + 1);
        else
            board.setWhiteAmount(board.getWhiteAmount() + 1);
    }

    public void updateAmount(List<Piece> pieces)
    {
        if (pieces.get(0).getStatus() == Piece.BLACK)
            board.setBlackAmount(board.getBlackAmount() - pieces.size());
        else
            board.setWhiteAmount(board.getWhiteAmount() - pieces.size());
    }

    /**
     * 每当一个点的状态改变，
     * 就调用该方法，
     * 根据新状态更新棋子的颜色
     */
    public void updateColour()
    {
        switch (status)
        {
            case Piece.BLACK:
                setFill(Paint.valueOf("#000000"));
                break;
            case Piece.WHITE:
                setFill(Paint.valueOf("#F5F5F5"));
                break;
            default:
                setFill(Paint.valueOf("#ffffff00"));
                break;
        }
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public void setSurround(Piece[] surround) { this.surround = surround; }

    public void setBoard(Board board) { this.board = board; }
}
