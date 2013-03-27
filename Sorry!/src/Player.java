/**
 * Container class for Player's in the game.
 * 
 * @author sturgedl. Created Mar 27, 2013.
 */
public class Player {
	private Piece.COLOR col;
	private String name;
	private boolean isActive;

	/**
	 * Constructs a basic player.
	 * 
	 * @param myCol
	 * @param myName
	 */
	public Player(Piece.COLOR myCol, String myName) {
		this.col = myCol;
		this.name = myName;
		this.isActive = false;
	}

	@SuppressWarnings("javadoc")
	public Piece.COLOR getColor() {
		return this.col;
	}

	@SuppressWarnings("javadoc")
	public String getName() {
		return this.name;
	}

	@SuppressWarnings("javadoc")
	public void setColor(Piece.COLOR newCol) {
		this.col = newCol;
	}

	@SuppressWarnings("javadoc")
	public void setName(String newName) {
		this.name = newName;
	}

	@SuppressWarnings("javadoc")
	public boolean getActive() {
		return this.isActive;
	}

	@SuppressWarnings("javadoc")
	public void setActive(boolean active) {
		this.isActive = active;
	}

}
