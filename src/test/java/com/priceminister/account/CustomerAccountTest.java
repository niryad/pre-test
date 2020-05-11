package com.priceminister.account;


import static org.junit.Assert.*;

import org.junit.*;

import com.priceminister.account.implementation.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.lang.reflect.Field;


/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass.
 * Then focus on the second test, and so on.
 * <p>
 * We want to see how you "think code", and how you organize and structure a simple application.
 * <p>
 * When you are done, please zip the whole project (incl. source-code) and send it to recrutement-dev@priceminister.com
 */
public class CustomerAccountTest {

    /**
     * Customer account class to be tested
     **/
    Account customerAccount;

    /**
     * the account rule to be used to test CustomerAccount class
     **/
    AccountRule rule;

    /**
     * rule for checking throwing exceptions class and properties
     **/
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        /** Instantiate CustomerAccount class **/
        customerAccount = new CustomerAccount();
        /** Create mock for {@link CustomerAccountRule} **/
        rule = Mockito.mock(CustomerAccountRule.class);
    }

    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        /** Check that the empty account has a balance of 0.0, not a null **/
        assertTrue("Account balance is different from 0.0", customerAccount.getBalance() != null && Double.compare(customerAccount.getBalance(), 0.0) == 0);
    }

    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() throws IllegalOperationException {
        /** Money to add to the balance **/
        Double moneyToAdd = 5.0;
        /** Add money to the balance of the empty account **/
        customerAccount.add(moneyToAdd);
        /** Check that money has been successfully added to the balance **/
        assertEquals(moneyToAdd, customerAccount.getBalance(), 0.0);
    }

    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test
    public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalBalanceException, IllegalOperationException {
        /** Amount of money to withdraw from customer account **/
        Double moneyToWithDraw = 5.0;
        /** New expected balance after withdraw **/
        Double newExpectedBalance = customerAccount.getBalance() - moneyToWithDraw;

        /** Stub return values of AccountRule Mock **/
        Mockito.when(rule.withdrawPermitted(newExpectedBalance)).thenReturn(false);
        /** Expected Exception message **/
        String expectedExceptionMessage = IllegalBalanceException.ILLEGAL_ACCOUNT_BALANCE + newExpectedBalance;
        /** Expect {@link IllegalBalanceException} Exception to be thrown **/
        exceptionRule.expect(IllegalBalanceException.class);
        /** Check expected Exception message **/
        exceptionRule.expectMessage(expectedExceptionMessage);
        /** run withdrawAndReportBalance() method to be tested  **/
        customerAccount.withdrawAndReportBalance(moneyToWithDraw, rule);
        Mockito.verify(rule).withdrawPermitted(newExpectedBalance);
    }

    // Also implement missing unit tests for the above functionalities.

    /**
     * Tests that adding negative amount of money to the account throws an exception as expected.
     */
    @Test
    public void testIllegalOperationExceptionIsThrownIfAmountAddedIsNegative() throws IllegalOperationException {
        /** Money to add to the balance **/
        Double moneyToAdd = -5.0;

        /** Expected Exception message **/
        String expectedExceptionMessage = IllegalOperationException.ILLEGAL_OPERATION + IllegalOperationException.POSITIVE_AMOUNT_OF_MONEY;
        /** Expect {@link IllegalBalanceException} Exception to be thrown **/
        exceptionRule.expect(IllegalOperationException.class);
        /** Check expected Exception message **/
        exceptionRule.expectMessage(expectedExceptionMessage);

        /** Add money to the balance of the empty account **/
        customerAccount.add(moneyToAdd);
    }

    /**
     * Tests that withdrawal of Null amount throws the expected exception.
     *
     * @throws IllegalBalanceException, NoSuchFieldException, IllegalAccessException, IllegalOperationException
     */
    @Test
    public void testIllegalOperationExceptionIsThrownIfMoneyToWithdrawIsNull() throws IllegalBalanceException, NoSuchFieldException,
            IllegalAccessException, IllegalOperationException {
        /** Null amount of money to withdraw from customer account **/
        Double moneyToWithDraw = null;

        /** Expected Exception message **/
        String expectedExceptionMessage = IllegalOperationException.ILLEGAL_OPERATION;
        /** Expect {@link IllegalBalanceException} Exception to be thrown **/
        exceptionRule.expect(IllegalOperationException.class);
        /** Check expected Exception message **/
        exceptionRule.expectMessage(expectedExceptionMessage);

        /** Add money to the balance of the empty account **/
        customerAccount.withdrawAndReportBalance(moneyToWithDraw, rule);
    }

    /**
     * Tests that withdrawal is successful if withdrawal is permitted.
     *
     * @throws IllegalBalanceException, NoSuchFieldException, IllegalAccessException, IllegalOperationException
     */
    @Test
    public void testWithdrawIsSuccessfulIfWithdrawIsPermitted() throws IllegalBalanceException, NoSuchFieldException,
            IllegalAccessException, IllegalOperationException {
        /** Amount of money to withdraw from customer account **/
        Double moneyToWithDraw = 5.0;

        /** set balance since :
         there is no setter method for balance (which makes sense from a business point of view)
         And we should not to use add() method **/
        Field balance = CustomerAccount.class.getDeclaredField("balance");
        balance.setAccessible(true);
        balance.set(customerAccount, 5.0);

        /** New expected balance after withdraw **/
        Double newExpectedBalance = customerAccount.getBalance() - moneyToWithDraw;
        /** Stub return values of AccountRule Mock **/
        Mockito.when(rule.withdrawPermitted(newExpectedBalance)).thenReturn(true);

        /** check if new balance after withdraw is equal to : old balance minus the money to withdraw  **/
        Double newBalance = customerAccount.withdrawAndReportBalance(moneyToWithDraw, rule);
        assertEquals(newExpectedBalance, newBalance, 0.0);
        Mockito.verify(rule).withdrawPermitted(newExpectedBalance);
    }

}
