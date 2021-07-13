package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.FinishingSurveyException;
import sth.core.SchoolManager;

//FIXME import other classes if needed

import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.6.5. Finish survey.
 */
public class DoFinishSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoFinishSurvey(SchoolManager receiver) {
    super(Label.FINISH_SURVEY, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */ 
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException {
    try {
      _receiver.finalizeSurvey(_discipline.value(), _project.value());

    } catch (FinishingSurveyIdException e) {
      throw new FinishingSurveyException(_discipline.value(), _project.value());
    }
  }
}
