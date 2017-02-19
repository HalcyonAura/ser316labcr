/*
  File:	AccountServerFactory.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/17
  
  Description: This holds the AccountServerFactory class
*/
  package banking.primitive.core;


public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
