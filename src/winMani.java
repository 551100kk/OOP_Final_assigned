import javax.swing.JFrame;

public class winMani {
	protected JFrame frame;

	/**
	 * Show the frame.
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Set title.
	 * @param title title
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}
}
