/*
  File:	Main.java
  Author:	Paul Horton and Cecilia LaPlace
  Date:	2/20/17
  
  Description: This file holds the main function used to run the program.
*/
package banking.gui;

import javax.swing.JFrame;

/**
  Class:	Main
  
  Description: Runs program.
*/
final class Main {
	/**
	 * Private constructor to address STYLE issue.
	 */
	private Main() {
	}
	
	/**
	 * All methods should have a Javadoc according to STYLE.
	 * @param args command-line arguments
	 * @throws Exception as per typical main specifications
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("Usage: java FormMain <property file>");
			System.exit(1);
		}

		String propertyFile = args[0];
		JFrame frame = new MainFrame(propertyFile);
		frame.setVisible(true);

	}
}
