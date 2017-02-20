package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.State;

/**
  Class:	AccountServer

  Description: Backend control of all accounts (saving to a file, reading from a file, returning specific types of accounts, etc.).
*/
class ServerSolution implements AccountServer {

	/**
	  Method: ServerSolution
	  Inputs:
	  Returns:

	  Description: Constructer to create ServerSolution and create existing accounts from a file
	*/
	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					if (acc != null)
						accountMap.put(acc.getName(), acc);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

	/**
	  Method: newAccount
	  Inputs: String type, String name, float balance
	  Returns: boolean

	  Description: Create new account but first make sure it is has valid fields
	*/
	public boolean newAccount(String type, String name, float balance)
		throws IllegalArgumentException {

		if (balance < 0.0f) throw new IllegalArgumentException("New account may not be started with a negative balance");

		return newAccountFactory(type, name, balance);
	}

	/**
	  Method: closeAccount
	  Inputs: String name
	  Returns: boolean

	  Description: Close specific account when called
	*/
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(State.CLOSED);
		return true;
	}

	/**
	  Method: getAccount
	  Inputs: String name
	  Returns: Account

	  Description: Get specific Account by name
	*/
	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	/**
	  Method: getAllAccounts
	  Inputs:
	  Returns: List<Account>

	  Description: Returns list of all accounts
	*/
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	/**
	  Method: getActiveAccounts
	  Inputs:
	  Returns: List<Account>

	  Description: Returns list of all active accounts
	*/
	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc.getState() != State.CLOSED) {
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
			out = new ObjectOutputStream(new FileOutputStream(fileName));

			out.writeObject(Integer.valueOf(accountMap.size()));
			for (String accName : accountMap.keySet()) {
				out.writeObject(accountMap.get(accName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + fileName);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	static String fileName = "accounts.ser";

	Map<String,Account> accountMap = null;
	/**
	  Method: newAccountFactory
	  Inputs: String type, String name, float balance
	  Returns: boolean

	  Description: Create new account if one of the same name does not exist
	*/
	private boolean newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {

		if (accountMap.get(name) != null) return false;

		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}
}
