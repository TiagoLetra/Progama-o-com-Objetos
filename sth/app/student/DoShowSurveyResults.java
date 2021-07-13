package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.core.SchoolManager;
import sth.app.common.ProjectCommand;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.app.exception.NoSurveyException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.5.3. Show survey results.
 */
public class DoShowSurveyResults extends ProjectCommand {

    /**
     * @param receiver
     */
    public DoShowSurveyResults(SchoolManager receiver) {
        super(Label.SHOW_SURVEY_RESULTS, receiver);
    }

    /**
     * @see pt.tecnico.po.ui.Command#execute()
     */

    @Override
    public final void myExecute() throws DialogException {
        try {
            _display.add(_receiver.showSurveyResultStudent(_discipline.value(), _project.value()));
            _display.display();
            _display.clear();
        } catch (NoSurveyIdException e) {
            throw new NoSurveyException(_discipline.value(), _project.value());
        } catch (NoSuchDisciplineIdException e){
            throw new NoSuchDisciplineException(_discipline.value());
        }

    }
}
