package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSuchDisciplineIdException;

//FIXME import other classes if needed

/**
 * 4.6.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

  //FIXME add input fields if needed
  private Input<String> _discipline;
  private Input<String> _project;


  /**
   * @param receiver
   */
  public DoShowDisciplineSurveys(SchoolManager receiver) {
    super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void  execute() throws DialogException {
    //FIXME implement command
    /*try {
      _form.parse();
      _display.display();
      /*_display.addLine(_receiver.showSurveyRepresentative(_discipline.value(), _project.value()));
    } catch(FinishingSurveyIdException e){
  } catch(NoSuchDisciplineIdException e){
    } */
    }
  }