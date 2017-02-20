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
	private static final long serialVersionUID = 111L;
	private int _numWithdraws = 0;
	private float _depositFee = 0.50F;
	private float _withdrawFee = 1.0F;
	private int _withdrawLimit = 3;

	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != STATE.CLOSED && amount > 0.0f) {
			balance = balance + amount - _depositFee;
			if (balance >= 0.0f) {
				setState(STATE.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == STATE.OPEN && amount > 0.0f) {
			balance = balance - amount;
			_numWithdraws++;
			if (_numWithdraws > _withdrawLimit)
				balance = balance - _withdrawFee;
			// KG BVA: should be < 0
			if (balance < 0.0f) {
				setState(STATE.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	public String getType() {
		return "Savings";
	}

	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
