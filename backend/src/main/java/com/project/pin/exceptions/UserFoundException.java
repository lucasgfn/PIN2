package com.project.pin.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("Usuário já existe!");
    }
}
