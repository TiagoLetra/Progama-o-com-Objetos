package sth.core;

import java.io.Serializable;

public class Submission implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private String _message;
    private Student _student;

    public Submission(String message, Student student){
        _message = message;
        _student = student;
    }

    public String getMessage() {
        return _message;
    }

    public int getStudentId(){
        return _student.getID();

    }

    public String toString(){
        return "\n* " + getStudentId() + " - " + _message;
    }
}
