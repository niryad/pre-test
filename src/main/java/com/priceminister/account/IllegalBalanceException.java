package com.priceminister.account;


public class IllegalBalanceException extends Exception {
    
    private static final long serialVersionUID = -9204191749972551939L;

    public static final String ILLEGAL_ACCOUNT_BALANCE = "Illegal account balance: ";
    
	private Double balance;
    
    public IllegalBalanceException(Double illegalBalance) {
        super(ILLEGAL_ACCOUNT_BALANCE + illegalBalance);
        balance = illegalBalance;
    }
    
    public String toString() {
        return ILLEGAL_ACCOUNT_BALANCE + balance;
    }
}
