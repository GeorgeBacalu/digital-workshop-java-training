package clean.code.chess.requirements;

public class Pawn {
    private ChessBoard chessBoard;
    private int xCoordinate;
    private int yCoordinate;
    private PieceColor pieceColor;

    public Pawn(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int value) {
        this.xCoordinate = value;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int value) {
        this.yCoordinate = value;
    }

    public PieceColor getPieceColor() {
        return this.pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * This method checks to see if the pawn can be moved forward or can capture another piece.
     *
     * @param movementType The type of movement (go forward or capture a piece)
     * @param newX         The x-coordinate of the new position
     * @param newY         The y-coordinate of the new position
     */
    public void move(MovementType movementType, int newX, int newY) {
        if (movementType == MovementType.MOVE && chessBoard.isLegalBoardPosition(newX, newY)) {
            moveForward(newY);
        }
        if (movementType == MovementType.CAPTURE && chessBoard.isOnTable(newX, newY) && !chessBoard.isPositionFree(newX, newY)) {
            attack(newX, newY);
        }
    }

    /**
     * This method moves the pawn forward.
     * From its initial position the pawn can be moved forward either by 1 or 2 positions at once, otherwise it can only be moved by one position.
     *
     * @param newY The new line where the pawn is placed
     */
    private void moveForward(int newY) {
        if ((pieceColor == PieceColor.WHITE && (yCoordinate == 1 && newY == yCoordinate + 2 || newY == yCoordinate + 1)) ||
            (pieceColor == PieceColor.BLACK && (yCoordinate == 6 && newY == yCoordinate - 2 || newY == yCoordinate - 1))) {
            yCoordinate = newY;
        }
    }

    /**
     * This method moves the pawn diagonally.
     * From its current position the pawn can capture a piece of the opposite color by moving in an adjacent position, diagonally and forward by one position.
     *
     * @param newX The new column where the pawn is placed
     * @param newY The new line where the pawn is placed
     */
    private void attack(int newX, int newY) {
        Pawn pieceToCapture = chessBoard.getPawnByPosition(newX, newY);
        if (pieceToCapture != null && pieceToCapture.getPieceColor() != pieceColor && (newX == xCoordinate + 1 || newX == xCoordinate - 1) &&
           ((pieceColor == PieceColor.WHITE && newY == yCoordinate + 1) || (pieceColor == PieceColor.BLACK && newY == yCoordinate - 1))) {
            xCoordinate = newX;
            yCoordinate = newY;
        }
    }

    @Override
    public String toString() {
        String eol = System.lineSeparator();
        return String.format("Current X: %d %s Current Y: %d %s Piece Color: %s", xCoordinate, eol, yCoordinate, eol, pieceColor);
    }
}
