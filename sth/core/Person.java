package sth.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.*;
import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchDisciplineIdException;

public abstract class Person implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private List<Notification> _notifications;
    private String _name;
    private int _phoneNumber;
    private int _id;

    public Person(String name, int phoneNumber, int id) {
        _name = name;
        _phoneNumber = phoneNumber;
        _id = id;
        _notifications = new ArrayList<>();
    }

    public String getName() {
        return _name;
    }

    public int getID() {
        return _id;
    }

    public int getPhoneNumber() {
        return _phoneNumber;
    }

    protected boolean equals(Person person) {
        return this._id == person._id;
    }

    protected void setPhoneNumber(int phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    void setName(String name) {
        _name = name;
    }

    static protected Comparator<Discipline> compareCourse = new Comparator<Discipline>() {
        public int compare(Discipline discipline1, Discipline discipline2) {
            return discipline1.getCourse().getName().compareTo(discipline2.getCourse().getName());
        }
    };

    static protected Comparator<Discipline> compareDiscipline = new Comparator<Discipline>() {
        public int compare(Discipline discipline1, Discipline discipline2) {
            return discipline1.getName().compareTo(discipline2.getName());
        }
    };

    protected void parseContext(String context, School school) throws BadEntryException {
        throw new BadEntryException("Should not have extra context: " + context);
    }


    public Notification createNotification(String s){
        Notification n = new Notification(s);
        return n;
    }

    public List<Notification> getNotifications(){
        return _notifications;
    }

    protected abstract String getDescription();
    protected abstract String getWork();

    public final String toString(){
        return getWork() + "|" + getID() + "|" + getPhoneNumber() + "|" + getName() + getDescription() +"\n";
    }
}
