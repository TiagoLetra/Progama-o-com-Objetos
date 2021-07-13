package sth.core.exception;

/** Exception thrown when the requested project does not exist. */
public class OpeningSurveyIdException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201809021324L;

    /** Project id. */
    private String _surveyName;

    /**
     * @param id
     */
    public OpeningSurveyIdException(String id) {
        _surveyName = id;
    }

    /** @return id */
    public String getId() {
        return _surveyName;
    }

}

