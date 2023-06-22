package clean.code.chess.requirements;

public class ChessBoard {
    public static final int MAX_BOARD_WIDTH = 7;
    public static final int MAX_BOARD_HEIGHT = 7;
    private final Pawn[][] pieces;
    private int nrAvailableWhitePawns;
    private int nrAvailableBlackPawns;

    public ChessBoard() {
        pieces = new Pawn[MAX_BOARD_WIDTH + 1][MAX_BOARD_HEIGHT + 1];
        nrAvailableWhitePawns = MAX_BOARD_WIDTH + 1;
        nrAvailableBlackPawns = MAX_BOARD_WIDTH + 1;
    }

    public Pawn getPawnByPosition(int xCoordinate, int yCoordinate) {
        if (isOnTable(xCoordinate, yCoordinate)) {
            return pieces[xCoordinate][yCoordinate];
        }
        throw new IllegalArgumentException("The provided position is not on the table!");
    }

    /**
     * This method places a pawn of the specified color in the specified position.
     * As long as we have available pawns, they must be placed in a free position on the table.
     *
     * @param pawn        An instance of a pawn
     * @param xCoordinate The column where it will be placed
     * @param yCoordinate The row where it will be placed
     * @param pieceColor  The color of the pawn
     */
    public void place(Pawn pawn, int xCoordinate, int yCoordinate, PieceColor pieceColor) {
        if (!isLegalBoardPosition(xCoordinate, yCoordinate)) {
            pawn.setXCoordinate(-1);
            pawn.setYCoordinate(-1);
            return;
        }
        if (pieceColor == PieceColor.WHITE && nrAvailableWhitePawns > 0) {
            nrAvailableWhitePawns--;
        } else if (pieceColor == PieceColor.BLACK && nrAvailableBlackPawns > 0) {
            nrAvailableBlackPawns--;
        }
        pawn.setXCoordinate(xCoordinate);
        pawn.setYCoordinate(yCoordinate);
        pieces[xCoordinate][yCoordinate] = pawn;
    }

    public boolean isLegalBoardPosition(int xCoordinate, int yCoordinate) {
        return isOnTable(xCoordinate, yCoordinate) && isPositionFree(xCoordinate, yCoordinate);
    }

    public boolean isOnTable(int xCoordinate, int yCoordinate) {
        return xCoordinate >= 0 && xCoordinate < MAX_BOARD_WIDTH && yCoordinate >= 0 && yCoordinate < MAX_BOARD_HEIGHT;
    }

    public boolean isPositionFree(int xCoordinate, int yCoordinate) {
        return pieces[xCoordinate][yCoordinate] == null;
    }
}
