package sth.core;

import java.io.Serializable;

public class Employee extends Person implements Serializable {

    public Employee(String name, int phoneNumber, int id) {
        super(name, phoneNumber, id);
    }

    public String getWork(){
        return "FUNCIONÁRIO";
    }

    public String getDescription() {
        return "\n";
    }

}
