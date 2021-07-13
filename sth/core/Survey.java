package sth.core;

import sth.core.exception.*;


import java.io.Serializable;
import java.util.*;

public class Survey implements Serializable {

    private static final long serialVersionUID = 201810051538L;
    private Collection<Answer> _answers;
    private StateStatus status = StateStatus.CREATED;
    private Project p;
    private static Student student;
    private int count = 0;
    private int _numberMinimum;
    private int _numberMaximum;
    private int _numHoras;
    private String _message;
    private Notification notification;

    public Survey(){
        _answers = new ArrayList<>();
    }

    protected void setProject(Project project){
        p = project;
    }


    private enum StateStatus {

        CREATED {
            void open(Survey survey) {
                survey.setStatus(OPENED);
            }

            void close(Survey survey) throws ClosingSurveyIdException {
                throw new ClosingSurveyIdException(survey.getProject().getName());
            }

                void finalize (Survey survey) throws FinishingSurveyIdException {
                    throw new FinishingSurveyIdException(survey.getProject().getName());
                }

                void cancel (Survey survey) throws NonEmptySurveyIdException {
                    if(survey.getNumberAnswer() != 0){
                        throw new NonEmptySurveyIdException(survey.getProject().getName());
                    } else {
                        survey.getProject().deleteSurvey();
                    }
                }

            String getResultsStudent(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (por abrir)";
            }

            String getResultsTeacher(Survey survey){
                return (survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (por abrir)");
            }

            String getResultsRepresentative(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (por abrir)";
            }


            },

            OPENED{
                void open (Survey survey) throws OpeningSurveyIdException {
                    throw new OpeningSurveyIdException(survey.getProject().getName());
                }

                void cancel (Survey survey) throws NonEmptySurveyIdException {
                    if (survey.getNumberAnswer() != 0) {
                        throw new NonEmptySurveyIdException(survey.getProject().getName());
                    } else {
                        survey.getProject().deleteSurvey();
                    }
                }


                void close (Survey survey){
                    survey.setStatus(CLOSED);
                }


                void finalize (Survey survey) throws FinishingSurveyIdException{
                    throw new FinishingSurveyIdException(survey.getProject().getName());
                }


                String getResultsStudent(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (aberto)";
                }

                String getResultsTeacher(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (aberto)";
                }

                String getResultsRepresentative(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (aberto)";
                }
            },

            CLOSED{

                void open (Survey s) throws OpeningSurveyIdException{
                    s.setStatus(OPENED);
                    //continuar excecao
                }


                void close (Survey survey){
                    return;
                }

                void finalize (Survey survey){
                    survey.setStatus(FINALIZED);
                }

                void cancel (Survey survey){
                    survey.setStatus(OPENED);
                }

                String getResultsStudent(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (fechado)";
                }

                String getResultsTeacher(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (fechado)";
                }

                String getResultsRepresentative(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (fechado)";
                }

            },

            FINALIZED{

                void open (Survey survey) throws OpeningSurveyIdException{
                    throw new OpeningSurveyIdException(survey.getProject().getName());
                }

                void close (Survey survey) throws ClosingSurveyIdException{
                    throw new ClosingSurveyIdException(survey.getProject().getName());
                }

                void finalize (Survey survey){
                    return;
                }

                void cancel (Survey survey) throws SurveyFinishedIdException {
                    throw new SurveyFinishedIdException(survey.getProject().getName());
                }

                String getResultsStudent(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() +
                            "\n * Número de respostas: " + survey.getNumberAnswer() +
                            "\n * Tempo médio (horas): " + survey.getAverageNumberHours();
                }

                String getResultsTeacher(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() +
                            "\n * Número de submissões: " + survey.getProject().numberSubmissions() +
                            "\n * Número de respostas: "  + survey.getNumberAnswer() +
                            "\n * Tempos de resolução (horas) (mínimo, médio, máximo): " + survey.getHourMinimum() + " "
                            + survey.getAverageNumberHours()
                            + " " + survey.getHourMaximum();
                }

                String getResultsRepresentative(Survey survey){
                    return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() +
                            " - " +  survey.getNumberAnswer() + " respostas - " + survey.getAverageNumberHours()
                            + " horas";
                }



            };

            abstract void close(Survey s) throws ClosingSurveyIdException;
            abstract void open(Survey s)  throws OpeningSurveyIdException;
            abstract void finalize(Survey s) throws FinishingSurveyIdException;
            abstract void cancel(Survey s) throws NonEmptySurveyIdException, SurveyFinishedIdException;
            abstract String getResultsStudent(Survey survey);
            abstract String getResultsTeacher(Survey survey);
            abstract String getResultsRepresentative(Survey survey);

        }


        protected void openSurvey() throws OpeningSurveyIdException {
            if (p.getClosed()) {
                status.open(this);
            } else {
                throw new OpeningSurveyIdException(p.getName());
            }
        }

        protected Project getProject(){
            return p;
        }


        protected void setStatus(StateStatus s) {
            status = s;
        }

        protected StateStatus getStatus() {
            return status;
        }

        protected void addAnswers(int hours, String message) throws NoSurveyIdException {
            if(status == StateStatus.OPENED) {
                Answer answer = new Answer(message, hours);
                _answers.add(answer);
                if (count == 0){
                    _numberMinimum = hours;
                    _numberMaximum = hours;
                } else {
                    _numberMinimum = Math.min(_numberMinimum, hours);
                    _numberMaximum = Math.max(_numberMaximum, hours);
                }
                count++;
            } else {
                throw new NoSurveyIdException();
            }
        }

        protected void close() throws ClosingSurveyIdException{
            status.close(this);
        }


        protected void finalize() throws FinishingSurveyIdException {
            status.finalize(this);
        }

        protected void cancel() throws NonEmptySurveyIdException, SurveyFinishedIdException{
            status.cancel(this);
        }

        protected int getNumberAnswer(){
            return count;
        }

        protected int getAverageNumberHours(){
            int sumHours = 0;
            for (Answer answer : _answers) {
                sumHours += answer.getHours();
            }
            return (sumHours / count);
        }

        protected int getHourMinimum(){
            return _numberMinimum;
        }

        protected int getHourMaximum(){
            return _numberMaximum;
        }

        protected String getResultTeacher(){
            return status.getResultsTeacher(this);
        }

    protected String getResultS(){
        return status.getResultsStudent(this);
    }

    protected String getResultR(){
        return status.getResultsRepresentative(this);
    }
}

