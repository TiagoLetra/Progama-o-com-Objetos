package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.5.2. Answer survey.
 */
public class DoAnswerSurvey extends sth.app.common.ProjectCommand {

  private Input<String> _comment;
  private Input<Integer> _numHoras;

  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _comment = _form.addStringInput(Message.requestComment());
    _numHoras = _form.addIntegerInput(Message.requestProjectHours());

  }

  /**
   * @see sth.app.common.ProjectCommand#myExecute()
   */
  @Override
  public final void myExecute() throws DialogException {
    try {
      _receiver.submitSurveyStudent(_discipline.value(), _project.value(), _comment.value(), _numHoras.value());
    } catch (NoSurveyIdException e) {
      throw new NoSurveyException(_discipline.value(), _project.value());
    }
  }
}
