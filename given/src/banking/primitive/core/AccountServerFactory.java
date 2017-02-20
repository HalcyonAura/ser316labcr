/*
  File:	AccountServerFactory.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/17

  Description: This holds the AccountServerFactory class
*/
  package banking.primitive.core;


public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	/**
	  Method:AccountServerFactory
	  Inputs:
	  Returns:

	  Description:constructor for singleton class
	*/
	protected AccountServerFactory() {
	}

	/**
	  Method: getMe
	  Inputs:
	  Returns:AccountServerFactory

	  Description: singleton factory for accountserver
	*/
	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	/**
	  Method: lookup
	  Inputs:
	  Returns: AccountServer

	  Description: creates and returns a new ServerSolution
	*/
	public AccountServer lookup() {
		return new ServerSolution();
	}
}
