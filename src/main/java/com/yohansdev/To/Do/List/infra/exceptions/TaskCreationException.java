package com.yohansdev.To.Do.List.infra.exceptions;

public class TaskCreationException extends RuntimeException{
    public TaskCreationException() {
        super("Falha ao criar tarefa.");
    }
    public TaskCreationException(String message){
        super(message);
    }
}
