package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.app.exception.NoSuchDisciplineException;
import sth.core.exception.NoSuchDisciplineIdException;

//FIXME import other classes if needed

/**
 * 4.4.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {
  private Input<String> _discipline;

  /**
   * @param receiver
   */
  public DoShowDisciplineStudents(SchoolManager receiver) {
    super(Label.SHOW_COURSE_STUDENTS, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _display.add(_receiver.showStudentsOfDiscipline(_discipline.value()));
      _display.display();
    } catch (NoSuchDisciplineIdException nsde) {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }

}
