package com.productservice.dto.enums;

public enum PaymentMethod {
    ACCOUNT("Account"),
    CARD("Card"),
    CASH("Cash"),
    CREDIT("Credit"),
    LOYALTY("Loyalty"),
    TRANSFER("Transfer"),
    WALLET("Wallet");

    String value;

    PaymentMethod(String value){
        this.value = value;
    }

    public String toString(){
    return value;
    }
}
