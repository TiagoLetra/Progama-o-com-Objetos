package sth.app.exception;

import pt.tecnico.po.ui.DialogException;

/** Exception thrown when the requested person does not exist. */
public class NoSuchPersonException extends DialogException {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;

  /** Person id. */
  private int _id;

  /**
   * @param id
   */
  public NoSuchPersonException(int id) {
    _id = id;
  }

  /** @see pt.tecnico.po.ui.DialogException#getMessage() */
  @Override
  public String getMessage() {
    return Message.noSuchPerson(_id);
  }

}
