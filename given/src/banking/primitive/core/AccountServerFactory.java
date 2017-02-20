/*
  File:	AccountServerFactory.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/17

  Description: This holds the AccountServerFactory class
*/
  package banking.primitive.core;


public class AccountServerFactory {

	protected static AccountServerFactory _singleton = null;

	protected _AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (_singleton == null) {
			_singleton = new _AccountServerFactory();
		}

		return _singleton;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
