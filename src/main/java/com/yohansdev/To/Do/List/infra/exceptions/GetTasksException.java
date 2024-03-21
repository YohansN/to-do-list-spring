package com.yohansdev.To.Do.List.infra.exceptions;

public class GetTasksException extends RuntimeException{
    public GetTasksException() {
        super("Falha ao buscar tarefas.");
    }
    public GetTasksException(String message){
        super(message);
    }
}
