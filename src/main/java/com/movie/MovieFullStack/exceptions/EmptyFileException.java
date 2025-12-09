package com.movie.MovieFullStack.exceptions;

public class EmptyFileException extends RuntimeException{
    public EmptyFileException(String msg){
        super(msg);
    }
}
