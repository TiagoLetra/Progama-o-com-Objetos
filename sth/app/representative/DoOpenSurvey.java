package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.OpeningSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.OpeningSurveyIdException;

/**
 * 4.6.3. Open survey.
 */
public class DoOpenSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoOpenSurvey(SchoolManager receiver) {
    super(Label.OPEN_SURVEY, receiver);
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */ 
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
    try{
      _receiver.openSurveyS(_discipline.value(), _project.value());
    } catch (OpeningSurveyIdException e){
      throw new OpeningSurveyException(_discipline.value(), _project.value());

    } catch (NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }

}
