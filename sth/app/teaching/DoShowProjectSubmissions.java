package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.app.exception.*;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.4.3. Show project submissions.
 */
public class DoShowProjectSubmissions extends sth.app.common.ProjectCommand {
  /**
   * @param receiver
   */
  public DoShowProjectSubmissions(SchoolManager receiver) {
    super(Label.SHOW_PROJECT_SUBMISSIONS, receiver);
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchDisciplineException, NoSuchProjectException {
      try {
    String output = _receiver.showSubmissions(_discipline.value(), _project.value());
    _display.addLine(output);
    _display.display();
  } catch (NoSuchDisciplineIdException nsdie){
          throw new NoSuchDisciplineException(_discipline.value());
      } catch (NoSuchProjectIdException nspie) {
          throw new NoSuchProjectException(_discipline.value(), _project.value());
      }
  }

}
