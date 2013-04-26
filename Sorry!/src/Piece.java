/**
 * Container class to represent a pawn in the game, stores a color.
 * 
 * @author TeamSorryDragons
 */
public class Piece {
	/**
	 * Enumeration for the various colors available, including the lack thereof.
	 * 
	 * @author TeamSorryDragons
	 */
	@SuppressWarnings("javadoc")
	public enum COLOR {
		red, blue, yellow, green, colorless
	}

	/**
	 * The color of this piece.
	 */
	protected COLOR col;

	/**
	 * Construct a colorless piece.
	 * 
	 */
	public Piece() {
		this.col = COLOR.colorless;
	}

	/**
	 * 
	 * Construct a piece of the given color.
	 * 
	 * @param color
	 */
	public Piece(COLOR color) {
		this.col = color;
	}
}
