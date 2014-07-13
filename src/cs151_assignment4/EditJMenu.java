package cs151_assignment4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class EditJMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -871826944474861418L;
	public static final int UNDO_DEPTH  = 10;
	public static JMenuItem undoMenuItem;
	public static UndoBuffer<GUICommand> undoBuffer; 
	
	public EditJMenu(String menuName, String undoMenuItemName){
		
		//---- Create the edit menu
		super(menuName);
		
		//----- Add the JMenuItem
		undoMenuItem = new JMenuItem(undoMenuItemName);
		undoMenuItem.setEnabled(false); //---- By default enable is disabled
		undoMenuItem.setActionCommand("editMenu" + undoMenuItemName);
		createUndoActionListener();//---- Creates an ActionListener for when the "Undo" menu item is pressed.
		this.add(undoMenuItem); //---- Add the menu item to the list
		
		//---- Initialize the undo buffer
		undoBuffer = new UndoBuffer<GUICommand>(UNDO_DEPTH);
		
	}
	
	
	/**
	 * Creates an ActionListener for the Undo Menu Item and then adds it to the menu item.
	 */
	private void createUndoActionListener(){
		
		ActionListener undoActionListener = new ActionListener(){
			
									/**
									 * Gets the last action performed and then undoes it.
									 */
									@Override
									public void actionPerformed(ActionEvent e){
										GUICommand lastCommand = undoBuffer.remove();
										lastCommand.undo();
										
										//---- If the buffer is empty, disable the menu item.
										if(undoBuffer.isEmpty()){
											undoMenuItem.setEnabled(false);
										}
									}
										
							};
							
		//--- Add the ActionListener
		undoMenuItem.addActionListener(undoActionListener);
		
	}
	
	
	/**
	 * Adds another item to the undo buffer.
	 *  
	 * @param newCommand  New command to be added to the UndoBuffer
	 */
	public void addCommandToUndo(GUICommand newCommand){
		undoBuffer.add(newCommand);
	}
	
	
	
	/**
	 * Generates and returns an action listener that can be used
	 * @return New action listener that can be used to clear the Undo Buffer.
	 */
	public ActionListener generateClearUndoBufferActionListener(){
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearUndoBuffer();
			}
		};
		
	}
	
	/**
	 * 
	 * @return	New action listener that is specifically intended for JFileChoosers and can clear the UndoBuffer.
	 */
	public ActionListener generateFileChooserClearUndoBufferActionListener() {
		
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//---- Do not do anything on an cancelled command
				if(e.getSource() instanceof JFileChooser && e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) ){
					return;
				}			
				
				clearUndoBuffer();				
			}
		};
	}
	
	/**
	 * 
	 */
	public void clearUndoBuffer(){
		undoBuffer.clear();
		
		//---- Disable the Undo Menu Item.
		undoMenuItem.setEnabled(false); //---- By default enable is disabled
	}
	
	private class UndoBuffer<E>{
		
		ArrayList<E> bufferItems;
		int bufferItemCount;
		int bufferHead;
		int bufferTail;
		
		public UndoBuffer(int size){
			
			//--- Create an array list of the specified size
			bufferItems = new ArrayList<E>(size);
			
			//---- Initialize the buffer as if it was a clear.  Leveraging other source code.
			clear();
		}
		
		/**
		 * Adds an item to the buffer.  If the buffer is full, it overwrites the oldest item in the queue.
		 * 
		 * @param item Adds this item to the buffer
		 */
		public void add(E item){
			bufferItems.set(bufferHead, item);
			
			//---- Update the buffer head
			bufferHead = (++bufferHead) % bufferItems.size();
		
			//---- Decide if queue is full, move the tail. Otherwise increment the item count
			if(bufferItemCount == bufferItems.size()){
				bufferTail = (++bufferTail) % bufferItems.size();
			}
			else{
				bufferItemCount++;
			}
			
			//---- Any time you add an item to the menu, it is guaranteed not be be empty.
			undoMenuItem.setEnabled(true);
		}
		
		public E remove(){
			
			//---- If the buffer is empty, return null.
			if(isEmpty()){
				return null;
			}
			
			//---- Gets the item that will be returned.
			E itemtoReturn = bufferItems.get(bufferHead);
			
			//---- If necessary roll back across the head of the 
			bufferHead = ((--bufferHead) < 0 ) ? bufferHead : bufferItems.size() - 1 ;
			
			//---- Decrement the number of items in the buffer
			bufferItemCount--;
			
			//---- return the value
			return itemtoReturn;
		}
		
//		/**
//		 * Gets the number of items currently in the buffer
//		 * 
//		 * @return Number of items in the buffer
//		 */
//		public int getBufferItemCount(){
//			return bufferItemCount;
//		}
		
		/**
		 * Gets whether the buffer has any elements in it.
		 * 
		 * @return True if buffer is empty, false otherwise
		 */
		public boolean isEmpty(){
			if(bufferItemCount==0) return true;
			
			return false;
		}
		
		
		public void clear(){
			
			bufferItems.clear();
			
			//---- Initialize the locations on the buffer
			bufferHead = -1;
			bufferTail = 0;
			bufferItemCount = 0;
		}
		
		
	}
	
}
