package sth.core;

import java.util.*;
import java.io.Serializable;

import sth.app.exception.NoSuchDisciplineException;
import sth.core.exception.*;
import sth.core.exception.DuplicateProjectIdException;
import java.io.*;

public class Teacher extends Person implements Serializable {

    private Set<Discipline> _teaching;

    public Teacher(String name, int phoneNumber, int id) {
        super(name, phoneNumber, id);
        _teaching = new HashSet<>();
    }

    protected String getWork(){
        return "DOCENTE";
    }

    protected String getDescription() {
        List<Discipline> _sortedList = new ArrayList<>(_teaching);
        _sortedList.sort(compareDiscipline);
        _sortedList.sort(compareCourse);

        String _print = "";
        for (Discipline d : _sortedList) {
            _print += "\n* " + d.getCourse().getName() + " - " + d.getName() + "\n";
        }
        return _print;
    }

    protected void addDiscipline(Discipline discipline) {
        _teaching.add(discipline);
    }

    protected void createProject(String nameProject, String disciplineName)
            throws NoSuchDisciplineIdException, DuplicateProjectIdException {
        for (Discipline discipline : _teaching) {
            if (discipline.getName().equals(disciplineName)) {
                discipline.createProject(nameProject);
                return;
            }
        }
        throw new NoSuchDisciplineIdException("Professor não leciona a disciplina" + disciplineName);
    }


     /*protected Collection<Submission> getProjectSubmissions(Project project) {
        return project.getSubmission();
    } */


    protected List<Student> getStudentOfDiscipline(String name) throws NoSuchDisciplineIdException {
        for (Discipline discipline : _teaching) {
            if (discipline.getName().equals(name))
                return discipline.getStudents();
        }

        throw new NoSuchDisciplineIdException(name);
    }

    protected void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] = lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        Course course = school.parseCourse(components[0]);
        Discipline discipline = course.parseDiscipline(components[1]);

        discipline.addTeacher(this);
        this.addDiscipline(discipline);
    }

    protected void closeProject(String projectName, String disciplineName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException, OpeningSurveyIdException {
        for (Discipline discipline : _teaching) {
            if (discipline.getName().equals(disciplineName)) {
                discipline.closeProject(projectName);
                return;
            }
        }
        throw new NoSuchDisciplineIdException("Professor não leciona a disciplina" + disciplineName);
    }

    protected String showProjectResultTeacher(String discipline, String project) throws NoSuchProjectIdException, NoSuchDisciplineIdException{
        String _out = discipline + " - " + project + "\n";
        for(Discipline d : _teaching){
            if(d.getName().equals(discipline)){
                return _out + d.getSubmission(project);
            }
        }
        throw new NoSuchDisciplineIdException(discipline);
    }

   protected String showSurveyResultTeacher(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException {
        for(Discipline d : _teaching){
            if(d.getName().equals(discipline))
                return d.getSubmissionSurvey(project);
        }
       throw new NoSuchDisciplineIdException(discipline);
   }


}
