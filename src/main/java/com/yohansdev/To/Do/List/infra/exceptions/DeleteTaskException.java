package com.yohansdev.To.Do.List.infra.exceptions;

public class DeleteTaskException extends RuntimeException{
    public DeleteTaskException() {
        super("Falha ao excluir tarefa.");
    }
    public DeleteTaskException(String message){
        super(message);
    }
}
