package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSurveyException;
import sth.app.exception.NoSuchProjectException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.4.5. Show survey results.
 */
public class DoShowSurveyResults extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchDisciplineException, NoSuchProjectException, NoSurveyException {
    try {
      Display disp = new Display();
      disp.add(_receiver.showSubmissionsSurvey(_discipline.value(), _project.value()));
      disp.display();

    } catch (NoSuchDisciplineIdException nsdie){
        throw new NoSuchDisciplineException(_discipline.value());
    } catch (NoSuchProjectIdException nspie) {
        throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (NoSurveyIdException nssie){
      throw new NoSurveyException(_discipline.value(), _project.value());
    }
  }
}


