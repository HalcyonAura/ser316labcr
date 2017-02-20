/*
  File:	MainFrame.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/2017

  Description: The GUI for the banking program
*/
package banking.gui;

import banking.primitive.core.Account;
import banking.primitive.core.AccountServer;
import banking.primitive.core.AccountServerFactory;

import java.io.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
/**
  Class:	MainFrame

  Description: Class to create the GUI for the banking program
*/
@SuppressWarnings("serial")
class MainFrame extends JFrame {
	AccountServer	myServer;
	Properties		props;
	JLabel			typeLabel;
	JLabel			nameLabel;
	JLabel			balanceLabel;
	JComboBox		typeOptions;
	JTextField		nameField;
	JTextField		balanceField;
	JButton 		depositButton;
	JButton 		withdrawButton;
	JButton			newAccountButton;
	JButton			displayAccountsButton;
	JButton			displayODAccountsButton;

	/**
	  Class:	DepositHandler

	  Description: button class to deposit money
	*/
	class DepositHandler implements ActionListener {
		/**
		Method: actionPerformed
		Inputs: ActionEvent e
		Returns:

		Description: Connects button events to in-class actions
		*/
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.deposit(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Deposit successful");
			} 
			else {
				JOptionPane.showMessageDialog(null, "Deposit unsuccessful");
			}
		}
	}
	/**
	  Class:	DisplayHandler

	  Description:Implements button presses to view account
	*/
	class DisplayHandler implements ActionListener {
		/**
		Method: actionPerformed
		Inputs: ActionEvent e
		Returns:

		Description: Connects button events to in-class actions
		*/
		public void actionPerformed(ActionEvent e) {
			List<Account> accounts = null;
			if (e.getSource() == displayAccountsButton) {
				accounts = myServer.getActiveAccounts();
			} 
			else {
				accounts = myServer.getAllAccounts();
			}
			StringBuffer sb = new StringBuffer();
			Account thisAcct = null;
			for (Iterator<Account> li = accounts.iterator(); li.hasNext();) {
				thisAcct = (Account)li.next();
				sb.append(thisAcct.toString()+"\n");
			}

			JOptionPane.showMessageDialog(null, sb.toString());
		}
	}

	//** Complete a handler for the Frame that terminates
		//** (System.exit(1)) on windowClosing event
		static class FrameHandler extends WindowAdapter{
			/**
			Method: windowClosing
			Inputs: windowEvent e
			Returns:

			Description: Connects window closing event to closing window.
			*/
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		}
	/**
	  Class:	NewAccountHandler

	  Description: implements button pressed for new accounts
	*/
	class NewAccountHandler implements ActionListener {
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent e
		  Returns:

		  Description: Connects button events to in-class actions
		*/
		public void actionPerformed(ActionEvent e) {
			String type = typeOptions.getSelectedItem().toString();
			String name = nameField.getText();
			String balance = balanceField.getText();

			if (myServer.newAccount(type, name, Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Account created successfully");
			} 
			else {
				JOptionPane.showMessageDialog(null, "Account not created!");
			}
		}
	}

	/**
	  Class:	SaveAccountsHandler

	  Description: button class to save accounts
	*/
	class SaveAccountsHandler implements ActionListener {
		/**
		Method: actionPerformed
		Inputs: ActionEvent e
		Returns:

		Description: Connects button events to in-class actions
		*/
			public void actionPerformed(ActionEvent e) {
			try {
				myServer.saveAccounts();
				JOptionPane.showMessageDialog(null, "Accounts saved");
			} 
			catch (IOException exc) {
				JOptionPane.showMessageDialog(null, "Error saving accounts");
			}
		}
	}

	/**
	  Class:	WithdrawHandler

	  Description: button class to withdraw money
	*/
	class WithdrawHandler implements ActionListener {
		/**
		Method: actionPerformed
		Inputs: ActionEvent e
		Returns:

		Description: Connects button events to in-class actions
		*/
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.withdraw(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Withdrawal successful");
			} 
			else {
				JOptionPane.showMessageDialog(null, "Withdrawal unsuccessful");
			}
		}
	}

	/**
	  Method: MainFrame
	  Inputs: String propertyFile
	  Returns:
	
	  Description: Constructor for MainFrame which initializes the AccountServerFactory and pulls saved accounts from a file
	*/
	public MainFrame(String propertyFile) throws IOException {

		//** initialize myServer
		myServer = AccountServerFactory.getMe().lookup();

		props = new Properties();

		FileInputStream fis = null;
		try {
			fis =  new FileInputStream(propertyFile);
			props.load(fis);
			fis.close();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
		constructForm();
	}
	/**
	  Method: constructForm
	  Inputs:
	  Returns:
	
	  Description: Creates the components of the JPanels for the UI.
	*/
	private void constructForm() {
		//*** Make these read from properties
		typeLabel		= new JLabel(props.getProperty("TypeLabel"));
		nameLabel		= new JLabel(props.getProperty("NameLabel"));
		balanceLabel	= new JLabel(props.getProperty("BalanceLabel"));

		Object[] accountTypes = {"Savings", "Checking"};
		typeOptions = new JComboBox(accountTypes);
		nameField = new JTextField(20);
		balanceField = new JTextField(20);

		newAccountButton = new JButton("New Account");
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton saveButton = new JButton("Save Accounts");
		displayAccountsButton = new JButton("List Accounts");
		JButton displayAllAccountsButton = new JButton("All Accounts");

		this.addWindowListener(new FrameHandler());
		newAccountButton.addActionListener(new NewAccountHandler());
		displayAccountsButton.addActionListener(new DisplayHandler());
		displayAllAccountsButton.addActionListener(new DisplayHandler());
		depositButton.addActionListener(new DepositHandler());
		withdrawButton.addActionListener(new WithdrawHandler());
		saveButton.addActionListener(new SaveAccountsHandler());

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());

		JPanel panel1 = new JPanel();
		panel1.add(typeLabel);
		panel1.add(typeOptions);

		JPanel panel2 = new JPanel();
		panel2.add(displayAccountsButton);
		panel2.add(displayAllAccountsButton);
		panel2.add(saveButton);

		JPanel panel3 = new JPanel();
		panel3.add(nameLabel);
		panel3.add(nameField);

		JPanel panel4 = new JPanel();
		panel4.add(balanceLabel);
		panel4.add(balanceField);

		JPanel panel5 = new JPanel();
		panel5.add(newAccountButton);
		panel5.add(depositButton);
		panel5.add(withdrawButton);

		pane.add(panel1);
		pane.add(panel2);
		pane.add(panel3);
		pane.add(panel4);
		pane.add(panel5);

		setSize(400, 250);
	}
}