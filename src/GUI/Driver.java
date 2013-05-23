package GUI;

public class Driver {
	private static MainGUI mainGui;

	public static void main(String[] args) {
		mainGui = new MainGUI();
	}

	public static MainGUI getMainGui() {
		return mainGui;
	}
}
