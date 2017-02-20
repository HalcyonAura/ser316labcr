/*
  File: Account.java
  Author:   Paul Horton and Cecilia LaPlace
  Date: 2/20/17

  Description: This file keep provides generalized methods for the children of this class to use.
*/
package banking.primitive.core;

/**
  Class:	Account

  Description: abstract class for accounts (like savings and checking)
*/
public abstract class Account implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    protected enum STATE {
        OPEN, CLOSED, OVERDRAWN
    };

    protected float balance = 0.0F;
    protected String name;
    private STATE state;
    /**
      Method:Account
      Inputs:String n
      Returns:
      Description:Constructor to create an account with name n
    */
    protected Account(String n) {
        name = n;
        state = STATE.OPEN;
    }

    /**
      Method:Account
      Inputs:String n, float b
      Returns:

      Description:Constructor to create an account with name n and balance b
    */
    protected Account(String n, float b) {
        this(n);
        balance = b;
    }

    /**
      Method: getName1
      Inputs:
      Returns: String

      Description: returns the name of the account
    */
    public final String getName() {
        return name;
    }

    /**
      Method: getBalance
      Inputs:
      Returns: float

      Description: returns the balance of the account
    */
    public final float getBalance() {
        return balance;
    }

    /**
      Method: deposit
      Inputs: float amount
      Returns: boolean

      Description: Abstract deposit method to add money to an account
    */
    public abstract boolean deposit(float amount);

    /**
      Method: withdraw
      Inputs: float amount
      Returns:

      Description: Abstract withdraw method to remove money from an account
    */
    public abstract boolean withdraw(float amount);

    /**
      Method: getType
      Inputs:
      Returns: String

      Description: abstract method to return the type of account
    */
    public abstract String getType();

    /**
      Method: getState
      Inputs:
      Returns: state

      Description: returns the state of the account
    */
    protected final STATE getState() {
        return state;
    }

   /**
      Method: setState
      Inputs: STATE s
      Returns:

      Description: sets the state of the account
    */
    protected final void setState(STATE s) {
        state = s;
    }
    /**
      Method: toString
      Inputs:
      Returns: String

      Description: returns the account in string format.
    */
    public String toString() {
        return "Account " + name + " has $" + balance + "and is " + getState()
                + "\n";
    }
}
