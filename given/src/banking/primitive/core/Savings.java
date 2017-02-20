/*
  File:	Savings.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/17

  Description: This file holds the savings account class.
*/
package banking.primitive.core;
/**
  Class:	Savings

  Description: Class to organize the values pertaining to savings accounts
*/
public class Savings extends Account {
/**
	  Method: deposit
	  Inputs: float amount, float balance
	  Returns: boolean

	  Description: Deposits money into a non closed savings account, boolean if it is successful or not.
		All deposits have a depositFee taken from them.
	*/
	public boolean deposit(float amount) {

		if (getState() != STATE.CLOSED && amount > 0.0f) {
			_balance = _balance + amount - _depositFee;
			if (_balance >= 0.0f) {
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
		Description: Returns the type of account this is
	*/
	public String getType() {
		return "Savings";
	}
	/**
		Method: toString
		Inputs:
		Returns: String
		Description: Returns information about the account
	*/
	/**
		Method: Savings
	Inputs: String name
		Returns:
	Description: Constructor for savings
	*/
	public Savings(String name) {
		super(name);
	}
	/**
			Method: Savings
		Inputs: String name, float balance
			Returns:
	
		Description: Constructor for savings
	*/
	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
	/**
	  Method: withdraw
	  Inputs: float amount
	  Returns: boolean

	  Description: Withdraws money from Savings account, boolean if it is successful or not
		More than the withdrawal limit causes a withdrawal fee to incur. If the balance reaches
		below 0, the account enters an overdrawn state.
	*/
	public boolean withdraw(float amount) {

		if (getState() == STATE.OPEN && amount > 0.0f) {
			_balance = _balance - amount;


			_numWithdraws++;
			if (_numWithdraws > _withdrawLimit)
				_balance = _balance - _withdrawFee;
			// KG BVA: should be < 0

			if (_balance < 0.0f) {
				setState(STATE.OVERDRAWN);

			}
			return true;
		}
		return false;
	}
	
	private static final long serialVersionUID = 111L;

	private int _numWithdraws = 0;
	private float _depositFee = 0.50F;
	private float _withdrawFee = 1.0F;
	private int _withdrawLimit = 3;
}
