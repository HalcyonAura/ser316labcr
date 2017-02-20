/*
  File:	ServerSolution.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/2017

  Description: File for the ServerSolution class
*/
package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import banking.primitive.core.Account.STATE;

import java.util.HashMap;
import java.io.*;


/**
  Class:	AccountServer

  Description: Backend control of all accounts (saving to a file, reading from a file, returning specific types of accounts, etc.).
*/
class ServerSolution implements AccountServer {

	/**
	  Method: ServerSolution
	  Inputs:
	  Returns:

	  Description: Constructor to create ServerSolution and create existing accounts from a file
	*/
	public ServerSolution() {
		_accountMap = new HashMap<String,Account>();
		File file = new File(_fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + _fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					if (acc != null)
						_accountMap.put(acc.getName(), acc);
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	/**
	  Method: _newAccountFactory
	  Inputs: String type, String name, float balance
	  Returns: boolean

	  Description: Create new account if one of the same name does not exist
	*/
	private boolean _newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {

		if (_accountMap.get(name) != null) return false;

		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			_accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}

	/**
	  Method: newAccount
	  Inputs: String type, String name, float balance
	  Returns: boolean

	  Description: Create new account but first make sure it is has valid fields
	*/
	public boolean newAccount(String type, String name, float balance)
		throws IllegalArgumentException {
		if (balance < 0.0f) {
			throw new IllegalArgumentException("New account may not be started with a negative balance");
		}
		return newAccountFactory(type, name, balance);
	}

	/**
	  Method: closeAccount
	  Inputs: String name
	  Returns: boolean

	  Description: Close specific account when called
	*/
	public boolean closeAccount(String name) {
		Account acc = _accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(STATE.CLOSED);
		return true;
	}

	/**
	  Method: getAccount
	  Inputs: String name
	  Returns: Account

	  Description: Get specific Account by name
	*/
	public Account getAccount(String name) {
		return _accountMap.get(name);
	}

	/**
	  Method: getAllAccounts
	  Inputs:
	  Returns: List<Account>

	  Description: Returns list of all accounts
	*/
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(_accountMap.values());
	}

	/**
	  Method: getActiveAccounts
	  Inputs:
	  Returns: List<Account>

	  Description: Returns list of all active accounts
	*/
	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();
		for (Account acc : _accountMap.values()) {
			if (acc.getState() != STATE.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}

	/**
	  Method: saveAccounts
	  Inputs:
	  Returns:

	  Description: Saves all accounts to a file
	*/
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(_fileName));

			out.writeObject(Integer.valueOf(_accountMap.size()));
			for (String accName : _accountMap.keySet()) {
				out.writeObject(_accountMap.get(accName));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + _fileName);
		}
		finally {
			if (out != null) {
				try {
					out.close();
				}
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

	/**
	  Method: newAccountFactory
	  Inputs: String type, String name, float balance
	  Returns: boolean

	  Description: Create new account if one of the same name does not exist
	*/
	private boolean newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {

		if (_accountMap.get(name) != null) return false;

		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			_accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}
	
	private static String _fileName = "accounts.ser";
	private Map<String,Account> _accountMap = null;
}
