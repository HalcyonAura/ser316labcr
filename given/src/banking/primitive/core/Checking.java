/*
  File:	Checking.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/2017

  Description: File for the checking class
*/
package banking.primitive.core;

/**
  Class:	Checking

  Description: Holds information about checking type Account and ways to change information in it
*/
public class Checking extends Account {
	
	/**
	  Method: Checking
	  Inputs: String name
	  Returns:

	  Description: Constructor creates new Checking account when called
	*/
	public Checking(String name) {
		super(name);
	}

	/**
	  Method: Checking
	  Inputs: String name, float balance
	  Returns:

	  Description: Returns instantiated Checking account when called
	*/
	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	  Method: deposit
	  Inputs: float amount
	  Returns: boolean

	  Description: Deposits money into non-closed Checking account, boolean return if it is successful or not
	*/
	public boolean deposit(float amount) {
		if (getState() != STATE.CLOSED && amount > _minBalance) {
			_balance = _balance + amount;
			if (_balance >= _minBalance) {
				setState(STATE.OPEN);
			}
			return true;
		}
		return false;
	}
	/**
	  Method: getType
	  Inputs:
	  Returns: String

	  Description: Returns account type Checking
	*/
	public String getType() { 
		return "Checking"; 
	}

	/**
	  Method: toString
	  Inputs:
	  Returns: String

	  Description: Returns a string representation of the Checking account with the name and balance
	*/
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}

	/**
	  Method: withdraw
	  Inputs: float amount
	  Returns: boolean

	  Description: Withdraws money from Checking account, boolean return if it is successful or not
	  More than 10 withdrawals incurs a 2$ fee per transaction
	  Withdrawals occur until the balance is below -$100
	*/
	public boolean withdraw(float amount) {
		if (amount > _minBalance) {
			// KG: incorrect, last balance check should be >=
			if (getState() == STATE.OPEN || (getState() == STATE.OVERDRAWN && _balance >= _minOverdraw)) {
				_balance = _balance - amount;
				_numWithdraws++;
				if (_numWithdraws > _withdrawLimit){
					_balance = _balance - _withdrawFee;
				}
				if (_balance < _minOverdraw) {
					setState(STATE.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}
	
	private int _numWithdraws = 0;
	private float _minBalance = 0.0f;
	private float _minOverdraw = -100.0f;
	private float _withdrawFee = 2.0f;
	private float _withdrawLimit = 10;
	private static final long serialVersionUID = 11L;
}
