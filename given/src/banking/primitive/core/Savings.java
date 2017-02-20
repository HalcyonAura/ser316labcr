/*
  File:	Savings.java
  Author:	Paul Horton and Cecilia La Place
  Date:	2/20/17

  Description: This file holds the savings account class.
*/
package banking.primitive.core;

public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	private int numWithdraws = 0;
	private float depositFee = 0.50F;
	private float withdrawFee = 1.0F;
	private int withdrawLimit = 3;
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
/**
	  Method: deposit
	  Inputs: float amount, float balance
	  Returns: boolean

	  Description: Deposits money into a non closed savings account, boolean if it is successful or not.
		All deposits have a depositFee taken from them.
	*/
	public boolean deposit(float amount) {
		if (getState() != STATE.CLOSED && amount > 0.0f) {
			balance = balance + amount - depositFee;
			if (balance >= 0.0f) {
				setState(STATE.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	  Method: withdraw
	  Inputs: float amount
	  Returns: boolean

	  Description: Withdraws money from Savingsa ccount, boolean if it is successful or not
		More than the withdrawl limit causes a withdrawl fee to incur. If the balance reaches
		below 0, the account enters an overdrawn state.
	*/
	public boolean withdraw(float amount) {
		if (getState() == STATE.OPEN && amount > 0.0f) {
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > withdrawLimit)
				balance = balance - withdrawFee;
			// KG BVA: should be < 0
			if (balance < 0.0f) {
				setState(STATE.OVERDRAWN);
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
	public String getType() { return "Savings"; }
	/**
		Method: toString
		Inputs:
		Returns: String

		Description: Returns information about the account
	*/
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
