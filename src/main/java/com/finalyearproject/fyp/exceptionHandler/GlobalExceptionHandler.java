package com.finalyearproject.fyp.exceptionHandler;


import com.finalyearproject.fyp.common.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionErrorMessage> notFoundException(ResourceNotFoundException exception, HttpServletRequest request){
        return new ResponseEntity(new ExceptionErrorMessage(exception.getMessage(), request.getServletPath()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionErrorMessage> invalidInputException(MethodArgumentNotValidException exception, HttpServletRequest request){

        //RECIPIENT TO STORE THE K,V PAIR OF ERROR AND ERROR MESSAGE FROM EXCEPTION
        Map<String, String> bindingDetails = new HashMap<>();

        //EXTRACTION OF K,V FROM EXCEPTION AND POPULATING RECIPIENT
        exception.getBindingResult().getFieldErrors().forEach(
                        fieldError -> bindingDetails.put(fieldError.getField(),
                        fieldError.getDefaultMessage()));

        //CREATING,POPULATING AND RETURNING AN EXCEPTION ERROR MESSAGE OBJ VIA A BAD REQUEST RESPONSE ENTITY
        return new ResponseEntity(
                    new ExceptionErrorMessage(Message.invalidInput,request.getServletPath(), bindingDetails),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionErrorMessage> illegalArgumentException(IllegalArgumentException exception,HttpServletRequest request){
        return new ResponseEntity(
                new ExceptionErrorMessage(exception.getMessage(), request.getServletPath()),HttpStatus.NOT_FOUND);
    }

}
