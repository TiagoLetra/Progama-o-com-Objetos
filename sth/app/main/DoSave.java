package sth.app.main;

import java.io.IOException;
import sth.core.exception.*;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {
  private Input<String> _namefile;

  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
    _namefile = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try {
      if (_namefile.value() == null && _receiver.getFileOpen() == null) {
        _form.parse();
        _receiver.save(_namefile.value());
      }
      if (_receiver.getFileOpen() != null) {
        _receiver.save(_receiver.getFileOpen());
      } else {
        _receiver.save(_namefile.value());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ImportFileException e) {
      e.printStackTrace();
    }
  }
}
