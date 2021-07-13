package sth.core;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private Collection<Student> _enrolled;
    private Collection<Discipline> _disciplines;
    private Collection<Student> _representative;

    private String _name;

    public Course(String name) {
        _name = name;
        _enrolled = new HashSet<>();
        _disciplines = new HashSet<>();
        _representative = new HashSet<>(7);
    }

    public String getName() {
        return _name;
    }

    protected void addDiscipline(Discipline discipline) {
        _disciplines.add(discipline);
    }

    protected void addStudent(Student student) {
        if (student.getCourse() != null)
            _enrolled.add(student);
        student.setCourse(this);
    }

    protected void addRepresentative(Student student) {
        if (student.getRepresentative() && _representative.size() < 7)
            _representative.add(student);
        else {
            // TODO: execepção
        }
    }

    protected Collection<Student> getEnrollStudents() {
        return _enrolled;
    }

    protected void removeRepresentative(Student student) {
        _representative.remove(student);
    }

    protected Discipline parseDiscipline(String disciplineParse) {
        Discipline discipline1 = null;
        for (Discipline discipline : _disciplines) {
            if ((discipline.getName()).equals(disciplineParse)) {
                discipline1 = discipline;
                break;
            }
        }
        if (discipline1 == null) {
            discipline1 = new Discipline(disciplineParse);
            discipline1.setCourse(this);
        }
        this.addDiscipline(discipline1);
        return discipline1;
    }

}
