package banking.primitive.core;

import java.io.IOException;
import java.util.List;


/**
  Class:	AccountServer
  
  Description: This is an interface file that is used by the ServerSolution class. 
*/
public interface AccountServer {

	/**
	  Method: newAccount
	  Inputs: String type, String name, float balance
	  Returns: boolean or IllegalArgumentException

	  Description: Create a new account object in the server. if an account already exists with the given name
	 *  then a new account is not created and stored.
	*/
	public boolean	newAccount(String type, String name, float balance) throws IllegalArgumentException;

	/**
	  Method: closeAccount
	  Inputs: String name
	  Returns: boolean

	  Description: Close an account
	*/
	public boolean	closeAccount(String name);

	/**
	  Method: getAccount
	  Inputs: String name
	  Returns: Account

	  Description: Returns specific account
	*/
	public Account	getAccount(String name);

	/**
	  Method: getAllAccounts
	  Inputs: 
	  Returns: List<Account>

	  Description: Returns a list of all Accounts inside the server
	*/
	public List<Account> getAllAccounts();

	/**
	  Method: getActiveAccounts
	  Inputs: 
	  Returns: List<Account>

	  Description: Returns a list of all active Accounts inside the server
	*/
	public List<Account> getActiveAccounts();

		/**
	  Method: saveAccounts
	  Inputs: 
	  Returns: IOException

	  Description: Saves the state of the server
	*/
	public void	saveAccounts() throws IOException;
}
