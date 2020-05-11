package com.priceminister.account;

public class IllegalOperationException extends Exception {

    /** Illegal operation message **/
    public static final String ILLEGAL_OPERATION = "Illegal operation: ";

    /** Illegal operation message : "Only positive amount of money can be added !" **/
    public static final String POSITIVE_AMOUNT_OF_MONEY = "Only positive amount of money can be added !";

    public IllegalOperationException(String message) {
        super(ILLEGAL_OPERATION + message);
    }

}
