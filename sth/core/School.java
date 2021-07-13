package sth.core;

import sth.core.exception.*;

import java.io.IOException;
import java.util.*;

/**
 * School implementation.
 */
public class School implements java.io.Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;
  private static School _instance = null;
  private String _name;
  private int _nextPersonId;
  private List<Person> _users;
  private Collection<Course> _courses;
  private NewParser _parse;
  private int _count;
  private Map<Integer, Person> _persons;

  /**
   * Constructuor of the class School
   *
   * @param name receives the name of the school
   */

  public School(String name) {
    _name = name;
    _nextPersonId = 100000;
    _users = new ArrayList<>();
    _courses = new HashSet<>();
    _parse = new NewParser(this);
    _persons = new TreeMap<>();
  }

  /**
   * Restes de TreeMap
   *
   */

  protected void reset() {
    _persons = new TreeMap<>();
    _count = 0;
  }

  protected int getCount() {
    return _count;
  }

  /**
   * Returns a Collection persons
   *
   * @return Collection of Persons
   */

  protected Collection<Person> getPersons() {
    return _persons.values();
  }

  /**
   * Returns a person with a specefic id that is given. This person is in the
   * Collection of persons.
   *
   * @param id an int that indentifies the person
   * @return the person with the id needed
   */

  protected Person getPerson(int id) {
    return _persons.get(id);
  }

  /**
   * Returns a list of persons. This is list is later use to present all the
   * persons in a school.
   *
   * @return a list with all the person
   */

  protected ArrayList<String> doShowAllPersons() {
    ArrayList<String> _list = new ArrayList<String>();
    for (Map.Entry<Integer, Person> entry : _persons.entrySet()) {
      _list.add(entry.toString());
    }
    return _list;
  }

  /**
   * Shows a specefic person with a specific id that is given. If this person with
   * that id doesn't exit it will send an exception. It is used in the command
   * doShowPersonById.
   *
   * @param id an int that indentifies the person
   * @throws NoSuchPersonIdException
   * @return shows a person with the id needed
   */

  protected String doShowPersonById(int id) throws NoSuchPersonIdException {
    for (int i = 0; i < _count; i++) {
      Person person = _persons.get(i);
      if (person.getID() == id) {
        return ("" + person.getID() + "|" + person.getName());
      }
      throw new NoSuchPersonIdException(id);
    }
    return null;
  }

  protected Course parseCourse(String courseParse) {
    Course course1 = null;
    for (Course course : _courses) {
      if ((course.getName()).equals(courseParse)) {
        course1 = course;
        break;
      }
    }
    if (course1 == null) {
      course1 = new Course(courseParse);
    }
    this.addCourse(course1);
    return course1;
  }

  /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  void importFile(String filename) throws IOException, BadEntryException {
    _parse.parseFile(filename);
  }
  /**
   * Get All Registered Persons
   * 
   * @return Array of all Persons registered in the system
   */
  /*
   * Person[] getPersons() { Person[] array = _personsByID.values(). toArray(new
   * Person[_personsByID.size()]); return array; }
   */

  /** /FIXME implement other methods */

  /**
   * Adds a person and incremments de id
   *
   * @param person an int that indentifies the person
   */

  void addPerson(Person person) {
    _users.add(person);
    _nextPersonId = person.getID() + 1;

  }

  /**
   * Adds a course to the course created by the schoolManager
   *
   * @param course an int that indentifies the person
   */

  protected void addCourse(Course course) {
    _courses.add(course);
  }

  /**
   * Returns a collection with all users
   *
   * @return Collection of users
   */

  protected List<Person> getAllUsers() {
    return _users;
  }

  protected String showSubmission(int id, String discipline, String project) throws NoSuchProjectIdException, NoSuchDisciplineIdException {
    for(Person p : _users){
      if(p.getID() == id){
        return ((Teacher)p).showProjectResultTeacher(discipline, project);
      }
    }
    return null;

  }

  protected String showSubmissionSurvey(int id, String discipline, String project) throws NoSuchProjectIdException, NoSuchDisciplineIdException,NoSurveyIdException{
    for(Person p : _users){
      if(p.getID() == id){
        return ((Teacher) p).showSurveyResultTeacher(discipline, project);
      }
    }
    throw new NoSuchProjectIdException(project);
  }

  protected void submitProjectStudent(Student student, String discipline, String project, String message) throws NoSuchDisciplineIdException, NoSuchProjectIdException{
      for(Person p : _users){
        if(p.equals(student)){
          ((Student) p).submitProjectStudent(student, discipline, project, message);
          return;
         }
      }
  }


  public String showSurveyResultStudent(int id, String discipline, String project) throws NoSurveyIdException, NoSuchDisciplineIdException {
    for(Person p : _users){
      if(p.getID() == id){
        return ((Student)p).showSurveyResultStudent(discipline, project);
      }
    }
    return null;
  }
}
