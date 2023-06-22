package clean.code.chess.requirements;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PawnTest {

    private ChessBoard chessBoard;
    private Pawn pawn;

    @Before
    public void setUp() {
        this.chessBoard = new ChessBoard();
        this.pawn = new Pawn(PieceColor.BLACK);
        pawn.setChessBoard(this.chessBoard);
    }

    @Test
    public void testChessBoard_Place_Sets_XCoordinate() {
        this.chessBoard.place(pawn, 6, 3, PieceColor.BLACK);
        assertEquals(6, pawn.getXCoordinate());
    }

    @Test
    public void testChessBoard_Place_Sets_YCoordinate() {
        this.chessBoard.place(pawn, 6, 3, PieceColor.BLACK);
        assertEquals(3, pawn.getYCoordinate());
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Right_DoesNotMove() {
        chessBoard.place(pawn, 6, 3, PieceColor.BLACK);
        pawn.move(MovementType.MOVE, 7, 3);
        assertEquals(6, pawn.getXCoordinate());
        assertEquals(3, pawn.getYCoordinate());
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Left_DoesNotMove() {
        chessBoard.place(pawn, 6, 3, PieceColor.BLACK);
        pawn.move(MovementType.MOVE, 4, 3);
        assertEquals(6, pawn.getXCoordinate());
        assertEquals(3, pawn.getYCoordinate());
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward_UpdatesCoordinates() {
        chessBoard.place(pawn, 6, 3, PieceColor.BLACK);
        pawn.move(MovementType.MOVE, 6, 2);
        assertEquals(6, pawn.getXCoordinate());
        assertEquals(2, pawn.getYCoordinate());
    }
}
