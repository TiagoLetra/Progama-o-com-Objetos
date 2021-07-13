package sth.core;

import sth.core.exception.*;

import java.io.Serializable;
import java.util.*;

public class Discipline implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private String _name;
    private int _capacity;
    private Collection<Project> _projects;
    private List<Teacher> _teaching;
    private List<Student> _enrolled;
    private Course _course;


    public Discipline(String name) {
        _name = name;
        _capacity = 200;
        _course = null;
        _projects = new HashSet<>();
        _teaching = new ArrayList<>();
        _enrolled = new ArrayList<>(_capacity);
    }

    protected String getName() {
        return _name;
    }

    protected void setCourse(Course course) {
        _course = course;
    }

    protected Course getCourse() {
        return _course;
    }

    protected Collection<Project> getALLProjects() {
        return _projects;
    }

    protected void addTeacher(Teacher teacher) {
        _teaching.add(teacher);
    }

    protected void enrollStudent(Student student) {
        if ((_course.getEnrollStudents().contains(student)) && _enrolled.size() < _capacity
                && student.getStudentsDiscipline().size() < 6) {
            _enrolled.add(student);
        } else {
            // TODO: execao
        }
    }

    protected void createProject(String nameProject) throws DuplicateProjectIdException {
        for (Project project : this.getALLProjects())
            if (project.getName().equals(nameProject)) {
                throw new DuplicateProjectIdException(nameProject);
            }
        Project project = new Project(nameProject, this);
        _projects.add(project);

    }

    protected Project getProject(String nameProject) {
        Project project1 = null;
        for (Project project : _projects) {
            if (project.getName().equals(nameProject)) {
                project1 = project;
                break;
            }
        }
        return project1;
    }

    protected List<Student> getStudents() {
        return _enrolled;
    }

    protected void closeProject(String nameProject) throws NoSuchProjectIdException, OpeningSurveyIdException {
        for (Project project : this.getALLProjects()) {
            if (project.getName().equals(nameProject)) {
                project.setClosed();
                if(project.get_survey() != null){
                    project.get_survey().openSurvey();
                }
                return;
            }
        }
        throw new NoSuchProjectIdException(nameProject);
    }

    protected String getSubmission(String project) throws NoSuchProjectIdException{
        for(Project p: _projects) {
            if (p.getName().equals(project)) {
                String output = p.toString();
                return output;
            }
        }
        throw new NoSuchProjectIdException(project);
    }


    protected String getSubmissionSurvey(String project) throws NoSuchProjectIdException, NoSurveyIdException{
        for(Project p : _projects){
            if(p.getName().equals(project)){
                return p.getSurvey();
            }
        }
        throw new NoSurveyIdException();

    }


    public void submitProject(Student student, String project, String message) throws NoSuchProjectIdException {
        for (Project p : _projects){
            if(p.getName().equals(project)){
                 p.submitProjectStudent(message, student);
                 return;
            }
        }
        throw new NoSuchProjectIdException(project);

    }

    public void submitSurveyStudent(String discipline, String project, String message,  int numHoras) throws NoSurveyIdException {
        for(Project p: _projects){
            if (p.getName().equals(project)) {
                p.submitSurveyStudent(message, numHoras);
            }
        }
    }

    protected String getSurveyResultStudent(String project) throws NoSurveyIdException {
        for(Project p : _projects){
            if(p.getName().equals(project)){
                return p.getSurveyStudent();
            }
        }
        throw new NoSurveyIdException();
    }

    public void createSurvey(String project) throws NoSuchProjectIdException, DuplicateSurveyIdException{
        for(Project p: _projects){
            if(p.getName().equals(project)){
                p.createSurvey();
                return;
            }
        }
        throw new NoSuchProjectIdException(project);
    }

    public void cancelSurvey(String project) throws NonEmptySurveyIdException, SurveyFinishedIdException {
        for(Project p: _projects){
            if(p.getName().equals(project)){
                p.cancelSurvey();
                return;
            }
        }
    }

    public void openSurvey(String project) throws OpeningSurveyIdException {
        for(Project p: _projects){
            if(p.getName().equals(project)){
                p.openSurvey();
                return;
            }
        }
    }

    public void finalizeSurvey(String project) throws FinishingSurveyIdException {
        for(Project p : _projects){
            if(p.getName().equals(project)){
                p.finalizeSurvey();
                return;
            }
        }
    }


}
