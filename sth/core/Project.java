package sth.core;

import sth.app.exception.DuplicateProjectException;
import sth.app.exception.DuplicateSurveyException;
import sth.core.exception.*;

import java.util.*;
import java.io.*;

public class Project implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private String _name;
    private String _description;
    private boolean _closed;
    private Survey _survey;
    private Discipline _discipline;
    private Project project;

    public List<Submission> _submission;
    public Collection<Survey> _surveyAll;

    static private Comparator<Submission> compareByID = new Comparator<Submission>() {
        public int compare(Submission s1, Submission s2) {
            return (s1.getStudentId() - s2.getStudentId());
        }
    };

    public Project(String name, Discipline discipline) {
        _name = name;
        _discipline = discipline;
        _description = "oaoJsolraConherreSsaiDariaereP";
        _closed = false;
        _submission = new ArrayList<>();
        _survey = null;
        _surveyAll = new ArrayList<>();
    }

    public String getName() {
        return _name;
    }

    protected Survey get_survey(){
        return _survey;
    }

    public String getDisciplineName(){
        return _discipline.getName();
    }

    protected void addSubmission(String message, Student student) {
        Submission s = new Submission(message, student);
        _submission.add(s);
    }

    protected Collection<Submission> getSubmission() {
        return _submission;
    }

    protected boolean getClosed(){
        return _closed == true;
    }

    protected void setClosed() {
        _closed = true;
    }

    public String toString(){
        String output = "";
        List<Submission> ordenada = _submission;
        _submission.sort(compareByID);
        /*output += (_discipline.getName() + " - " + project.getName()+"\n"); */
        for(Submission s: ordenada){
            output += s.toString();

        }
        return output;
    }

    protected String getSurvey() throws NoSurveyIdException {
        if (_survey == null) {
            throw new NoSurveyIdException();
        } else {
            return _survey.getResultTeacher();
        }
    }

    protected int numberSubmissions(){
        return _submission.size();
    }

    public void submitProjectStudent(String message, Student student) throws NoSuchProjectIdException {
        if(!_closed){
            Iterator<Submission> iterator = _submission.iterator();
            while(iterator.hasNext()){
                if(iterator.next().getStudentId() == student.getID()){
                    iterator.remove();
                }
            }
            Submission submissionnew = new Submission(message, student);
            _submission.add(submissionnew);
        } else {
            throw new NoSuchProjectIdException(_name);
        }

    }

    protected void deleteSurvey(){
        _survey = null;
    }

    public void submitSurveyStudent(String message, int numHoras) throws NoSurveyIdException {
        _survey.addAnswers(numHoras, message);
    }

    protected  String getSurveyStudent() throws NoSurveyIdException {
        if (_survey != null) {
            return _survey.getResultS();
        } else {
            throw new NoSurveyIdException();
        }
    }

    public void createSurvey() throws DuplicateSurveyIdException {
        if(_survey == null){
            _survey = new Survey();
            _survey.setProject(this);
            return;
        }
        throw new DuplicateSurveyIdException();
    }

    public void cancelSurvey() throws NonEmptySurveyIdException, SurveyFinishedIdException  {
        if(_survey != null){
            _survey.cancel();
            return;
        }
    }

    public void openSurvey() throws OpeningSurveyIdException {
        if(_survey != null){
            _survey.openSurvey();
            return;
        }
    }

    public void finalizeSurvey() throws FinishingSurveyIdException {
        if(_survey != null){
            _survey.finalize();
        }
    }
}

