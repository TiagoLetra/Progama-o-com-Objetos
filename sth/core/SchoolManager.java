package sth.core;

import sth.core.exception.*;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.*;



/**
 * The façade class.
 */
public class SchoolManager implements Serializable {

  // Fixme add object attributes if needed

  private static final long serialVersionUID = 201810051538L;

  private School _school = new School("IST"); // criar uma escola
  private Person _logged;
  private Collection<Person> _all;

  private File _associatedFile = null;
  private String _fileOpned;

  static private Comparator<Person> compareByID = new Comparator<Person>() {
    public int compare(Person person1, Person person2) {
      return (person1.getID() - person2.getID());
    }
  };

  static protected Comparator<Person> compareName = new Comparator<Person>() {
    public int compare(Person Person1, Person Person2) {
      return Person1.getName().compareTo(Person2.getName());
    }
  };

  /** /Fixme implement constructors if needed */

  /**
   * @param datafile
   * @throws ImportFileException
   * @throws InvalidCourseSelectionException
   **/

  public String getFileOpen() {
    return _fileOpned;
  }

  public void setFileOpen(String name) {
    _fileOpned = name;
  }

  public void importFile(String datafile) throws ImportFileException, IOException {
    try {
      _school.importFile(datafile);
      _all = _school.getAllUsers();
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

  public void load(String filename)
      throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchPersonIdException {
    ObjectInputStream inob = new ObjectInputStream(new FileInputStream(filename));
    try {
      School _schooltemp = (School) inob.readObject();
      _all = _schooltemp.getAllUsers();
      login(_logged.getID());
      _school = _schooltemp;
    } catch (NoSuchPersonIdException nspid) {
      _all = _school.getAllUsers();
      throw new NoSuchPersonIdException(nspid.getId());
    } finally {
      inob.close();
    }
  }

  public void save(String filename) throws IOException, ImportFileException {
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
    if (filename == null) {
      throw new ImportFileException();
    }
    try {
      out.writeObject(_school);
    } finally {
      out.close();
    }
  }

  /**
   * Method used to Format the output of a Person (argument of function)
   * tipo|id|telemovel|name
   * 
   * @return String that represents the Person
   */

  /**
   * Do the login of the user with the given identifier.
   * 
   * @param id identifier of the user to login
   * @throws NoSuchPersonIdException if there is no uers with the given identifier
   */
  public void login(int id) throws NoSuchPersonIdException {
    for (Person person : _all) {
      if (person.getID() == id) {
        _logged = person;
        return;
      }
    }
    throw new NoSuchPersonIdException(id);
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean isLoggedUserAdministrative() {

    return (_logged instanceof Employee);
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean isLoggedUserProfessor() {
    return (_logged instanceof Teacher);
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean isLoggedUserStudent() {

    return (_logged instanceof Student);
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean isLoggedUserRepresentative() {
    return (_logged instanceof Student && ((Student) _logged).getRepresentative());

  }

  public ArrayList<String> doShowAllPersons() {
    return _school.doShowAllPersons();
  }

  public String doShowPersonById(int id) throws NoSuchPersonIdException {
    return _school.doShowPersonById(id);
  }

  public void reset() {
    _school.reset();
  }

  public void createProject(String projectName, String disciplineName)
      throws NoSuchDisciplineIdException, DuplicateProjectIdException {
    if (isLoggedUserProfessor()) {
      ((Teacher) _logged).createProject(projectName, disciplineName);
    }
  }

  public void closeProject(String projectName, String disciplineName)
      throws NoSuchDisciplineIdException, NoSuchProjectIdException, OpeningSurveyIdException {
    if (isLoggedUserProfessor()) {
      ((Teacher) _logged).closeProject(projectName, disciplineName);
    }
  }

  public String setPhoneNumber(int phoneNumber) {
    _logged.setPhoneNumber(phoneNumber);

    return _logged.toString();
  }

  public String showPerson() {
    return _logged.toString();
  }

  public String searchPerson(String name) {
    String _print = "";
    List<Person> _sortedPerson = new ArrayList<>();
    for (Person person : _all) {
      if ((person.getName()).contains(name)) {
        _sortedPerson.add(person);
      }
    }
    _sortedPerson.sort(compareName);
    for (Person person : _sortedPerson) {
      _print += person.toString();
    }
    return _print;
  }

  public String showAllUsers() {
    List<Person> _sortedList = _school.getAllUsers();
    String _print = "";
    _sortedList.sort(compareByID);

    for (Person person : _sortedList) {
      _print += person.toString();
    }

    return _print;
  }

  public String showStudentsOfDiscipline(String name) throws NoSuchDisciplineIdException {
    String print = "";
    if (isLoggedUserProfessor()) {
      List<Student> _sortedList = ((Teacher) _logged).getStudentOfDiscipline(name);
      _sortedList.sort(compareByID);

      for (Student student : _sortedList) {
        print += student.toString();
      }
    }

    return print;
  }

  public String showSubmissions(String d, String p) throws NoSuchDisciplineIdException, NoSuchProjectIdException{
      return _school.showSubmission(_logged.getID(),d, p);
  }

  public String showSubmissionsSurvey(String d, String p) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException {
      return _school.showSubmissionSurvey(_logged.getID(), d,p);
  }

  public void submitProjectStudent(String discipline, String project, String message) throws NoSuchProjectIdException, NoSuchDisciplineIdException{
      _school.submitProjectStudent((Student)_logged, discipline, project, message);
  }

  public void submitSurveyStudent(String discipline, String project, String message, int numHoras) throws NoSurveyIdException{
    ((Student)_logged).submitSurveyStudent(discipline, project, message, numHoras);
  }

  public String showSurveyResultStudent(String discipline, String project) throws NoSurveyIdException, NoSuchDisciplineIdException{
    return _school.showSurveyResultStudent(_logged.getID(), discipline, project);
  }

  public void createSurveyRepresentative(String discipline, String project) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException {
    if (isLoggedUserRepresentative()) {
      ((Student) _logged).createSurvey(discipline, project);
    }
  }

  public void CancelSurvey(String discipline, String project)throws NonEmptySurveyIdException, SurveyFinishedIdException, NoSuchProjectIdException, NoSuchDisciplineIdException{
    if(isLoggedUserRepresentative()){
      ((Student)_logged).cancelSurvey(discipline, project);
    }
  }

  public void openSurveyS(String discipline, String project) throws OpeningSurveyIdException, NoSuchDisciplineIdException {
    if(isLoggedUserRepresentative()){
      ((Student)_logged).openSurvey(discipline, project);
    }
  }

  public void finalizeSurvey(String discipline, String project) throws FinishingSurveyIdException, NoSuchDisciplineIdException {
    if(isLoggedUserRepresentative()){
      ((Student)_logged).finalizeSurvey(discipline, project);
    }
  }

  public void showSurveyRepresentative(String discipline, String project) throws FinishingSurveyIdException, NoSuchDisciplineIdException {
    if(isLoggedUserRepresentative()){
      ((Student)_logged).finalizeSurvey(discipline, project);
    }
  }

  public String showNotifications(){
    String output = "";
    for(Notification n: _logged.getNotifications()){ ;
      output += n.getMessage();
    }
    return output;
  }
}
