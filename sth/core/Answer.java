package sth.core;

import java.io.Serializable;

public class Answer implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private String _message;
    private int _hours;

    public Answer(String message, int hours){

        _message = message;
        _hours = hours;
    }

    public String getMessage() {
        return _message;
    }

    public int getHours() {
        return _hours;
    }
}
