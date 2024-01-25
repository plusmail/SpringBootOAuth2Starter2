package com.yi.exception;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InsufficientBalanceException extends RuntimeException{
    String message= null;

    public InsufficientBalanceException() {
        this.message="잔액이 부족합니다.";
    }

    public InsufficientBalanceException(String message) {
        this.message="잔액이 부족합니다.";
    }

}