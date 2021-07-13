package sth.core;

import java.io.Serializable;
import java.util.Collection;

import java.util.*;

import sth.core.exception.*;

public class Student extends Person implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private boolean _isRepresentative;
    private Course _course;
    private List<Discipline> _disciplines;

    public Student(String name, int phoneNumber, int id, boolean representative) {
        super(name, phoneNumber, id);
        _isRepresentative = representative;
        _course = null; // ver daqui a bocado
        _disciplines = new ArrayList<>();
    }

    protected String getWork(){
        if(_isRepresentative){
            return "DELEGADO";
        } else {
            return "ALUNO";
        }
    }

    protected boolean getRepresentative() {
        return _isRepresentative;
    }

    protected void setRepresentative(boolean representative) {
        _isRepresentative = representative;
    }

    public String getDescription() {
        List<Discipline> _sortedList = _disciplines;

        _sortedList.sort(compareDiscipline);
        _sortedList.sort(compareCourse);
        String _print = "";
            for (Discipline d : _disciplines) {
                _print += "\n* " + _course.getName() + " - " + d.getName() + "\n";
            }
            return _print;
        }


    protected Course getCourse() {
        return _course;
    }

    protected void setCourse(Course course) {
        _course = course;
    }

    protected void addDiscipline(Discipline discipline) {
        _disciplines.add(discipline);
    }

    protected void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] = lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        if (_course == null) {
            _course = school.parseCourse(components[0]);
            _course.addStudent(this);
        }

        Discipline dis = _course.parseDiscipline(components[1]);

        dis.enrollStudent(this);
        this.addDiscipline(dis);
    }

    protected Collection<Discipline> getStudentsDiscipline() {
        return _disciplines;
    }

    protected void submitProjectStudent(Student student, String discipline, String project, String message) throws NoSuchProjectIdException, NoSuchDisciplineIdException {
        for(Discipline d : _disciplines){
            if(d.getName().equals(discipline)){
                d.submitProject(this, project, message);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void submitSurveyStudent(String discipline, String project, String message, int numHoras) throws NoSurveyIdException{
        for(Discipline d: _disciplines){
            if(d.getName().equals(discipline)){
                d.submitSurveyStudent(discipline, project, message, numHoras);
                return;
            }
        }
    }

    protected String showSurveyResultStudent(String discipline, String project) throws NoSurveyIdException, NoSuchDisciplineIdException {
        for(Discipline d: _disciplines){
            if(d.getName().equals(discipline)){
               return d.getSurveyResultStudent(project);
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void createSurvey(String discipline, String project) throws NoSuchDisciplineIdException, DuplicateSurveyIdException, NoSuchProjectIdException {
            for(Discipline d: _disciplines){
                if(d.getName().equals(discipline)){
                    d.createSurvey(project);
                    return;
                }
            }
            throw new NoSuchDisciplineIdException(discipline);
    }

    protected void cancelSurvey(String discipline, String project) throws NonEmptySurveyIdException, SurveyFinishedIdException, NoSuchDisciplineIdException, NoSuchProjectIdException{
        for(Discipline d :_disciplines){
            if(d.getName().equals(discipline)){
                d.cancelSurvey(project);
                return;
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

    protected void openSurvey(String discipline, String project) throws OpeningSurveyIdException, NoSuchDisciplineIdException {
        Notification n;
        for(Discipline d: _disciplines){
            if(d.getName().equals(discipline)){
                d.openSurvey(project);
                n = createNotification("Pode preencher inquérito do projecto" + project + "da disciplina" + discipline + "\n");
                getNotifications().add(n);
                return;
            }
        }throw new NoSuchDisciplineIdException(discipline);
    }

    protected void finalizeSurvey(String discipline, String project) throws FinishingSurveyIdException, NoSuchDisciplineIdException{
        Notification n;
        for(Discipline d : _disciplines){
            if(d.getName().equals(discipline)){
                d.finalizeSurvey(project);
                n = createNotification("Resultados do inquérito do projecto" + project + "da disciplina" + discipline + "\n");
                getNotifications().add(n);
                return;
            }
        }throw new NoSuchDisciplineIdException(discipline);
    }

}
