package cs151_assignment4;

/**
 * Interface type used to encapsulate undo-able actions.
 * 
 * @author Zayd
 *
 */
public interface GUICommand {

	/**
	 * Needs to be extended to perform the action.
	 */
	public void execute();
	
	/**
	 * Needs to be extended to undo the action performed by the "execute" method.
	 */
	public void undo();
	
}
