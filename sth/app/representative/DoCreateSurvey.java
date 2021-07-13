package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.DuplicateProjectException;
import sth.app.exception.DuplicateSurveyException;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchProjectException;
import sth.core.SchoolManager;

import sth.core.exception.DuplicateSurveyIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 4.5.1. Create survey.
 */
public class DoCreateSurvey extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoCreateSurvey(SchoolManager receiver) {
    super(Label.CREATE_SURVEY, receiver);
    //FIXME initialize input fields if needed

  }

  /**
   * @see sth.app.common.ProjectCommand#myExecute()
   */
  @Override
  public final void myExecute() throws DialogException, NoSuchProjectException, NoSuchDisciplineIdException{

    try {

      _receiver.createSurveyRepresentative(_discipline.value(), _project.value());

    } catch (NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());
    } catch (NoSuchProjectIdException e) {
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (DuplicateSurveyIdException e){
      throw new DuplicateSurveyException(_discipline.value(), _project.value());
    }

  }
}
