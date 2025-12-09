package com.movie.MovieFullStack.exceptions;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException (String msg){
        super(msg);
    }
}
