package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchPersonIdException;
import sth.app.exception.NoSuchPersonException;
import pt.tecnico.po.ui.DialogException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  private Input<String> _filename;

  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.load(_filename.value());
      _receiver.setFileOpen(_filename.value());
      _display.add(_receiver.showNotifications());
      _display.display();
    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      // shouldn't happen in a controlled test setup
      e.printStackTrace();
    } catch (NoSuchPersonIdException nspid) {
      throw new NoSuchPersonException(nspid.getId());
    }
  }
}
