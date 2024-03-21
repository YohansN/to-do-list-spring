package com.yohansdev.To.Do.List.infra;

import com.yohansdev.To.Do.List.infra.exceptions.DeleteTaskException;
import com.yohansdev.To.Do.List.infra.exceptions.GetTasksException;
import com.yohansdev.To.Do.List.infra.exceptions.TaskCreationException;
import com.yohansdev.To.Do.List.infra.exceptions.TaskNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler { //Classe para gerenciar o tratamento de exceções.
    //Exceptions cursomizados:
    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<ErrorWrapperDto> taskNotFoundHandler(TaskNotFoundException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(TaskCreationException.class)
    private ResponseEntity<ErrorWrapperDto> taskCreationFailHandler(TaskCreationException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(GetTasksException.class)
    private ResponseEntity<ErrorWrapperDto> getTasksFailHandler(GetTasksException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(DeleteTaskException.class)
    private ResponseEntity<ErrorWrapperDto> deleteTaskFailHandler(DeleteTaskException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //Exceptions genéricos
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> mapErrorFields = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            mapErrorFields.put(fieldName, errorMessage);
        });
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.BAD_REQUEST, "Houveram erros no preenchimento dos campos", mapErrorFields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(ConstraintViolationException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(MethodArgumentTypeMismatchException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.BAD_REQUEST, "Parâmetro inválido.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(HttpMessageNotReadableException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.BAD_REQUEST, "Valor inválido.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(NoResourceFoundException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.NOT_FOUND, "Página não encontrada.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(IllegalArgumentException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.BAD_REQUEST, "Valor inválido.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(HttpRequestMethodNotSupportedException ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.METHOD_NOT_ALLOWED, "Método não suportado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorWrapperDto> exceptionHandler(Exception ex){
        ErrorWrapperDto response = new ErrorWrapperDto(HttpStatus.METHOD_NOT_ALLOWED, "Houve uma falha durante a execução.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
