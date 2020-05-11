package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    /**
     * customer account balance
     **/
    private Double balance = 0.0;

    /**
     * Adds money to the customer account
     *
     * @param addedAmount - the money to add (should be positive, otherwise it is a withdrawal)
     */
    public void add(Double addedAmount) throws IllegalOperationException {
        /** check that addedAmount is positive **/
        if (addedAmount >= 0.0) {
            this.balance += addedAmount;
        } else {
            throw new IllegalOperationException(IllegalOperationException.POSITIVE_AMOUNT_OF_MONEY);
        }
    }

    /**
     * Returns balance of the customer account
     *
     * @return customer account balance
     */
    public Double getBalance() {
        return this.balance;
    }

    /**
     * Withdraw money from account respecting the rules defined
     *
     * @param withdrawnAmount - the money to withdraw
     * @param rule            - the AccountRule that defines which balance is allowed for this account
     * @return the new account balance
     * @throws IllegalBalanceException   if resulting balance is illegal
     * @throws IllegalOperationException if withdrawnAmount or rule is null
     */
    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule)
            throws IllegalBalanceException, IllegalOperationException {
        try {
            Double newAccountBalance = this.balance - withdrawnAmount;
            boolean withdrawPermitted = rule.withdrawPermitted(newAccountBalance);
            if (withdrawPermitted) {
                this.balance -= withdrawnAmount;
                return this.balance;
            } else {
                throw new IllegalBalanceException(newAccountBalance);
            }
        } catch (NullPointerException npe) {
            throw new IllegalOperationException(npe.getMessage());
        }
    }

}
