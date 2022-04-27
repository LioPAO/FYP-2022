package com.finalyearproject.fyp.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ExceptionErrorMessage {

    String message;
    String path;
    LocalDateTime timeStamp;
    Map<String, String> details;

    public ExceptionErrorMessage(String message, String path){
        this.message=message;
        this.path=path;
        this.timeStamp = LocalDateTime.now();
    }
    public ExceptionErrorMessage(String message, String path, Map<String, String> details ){
        this.message=message;
        this.path=path;
        this.details=details;
        this.timeStamp = LocalDateTime.now();
    }

}
