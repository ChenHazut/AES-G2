package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import logic.Question;

public abstract class QuestionListViewCell<T> extends ListCell<QuestionInExam>{
	
    @FXML
    private AnchorPane pane;
	@FXML
    private Label questionNumber;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private RadioButton radioButton3;
    @FXML
    private RadioButton radioButton4;
    @FXML
    private Text questionBody;
    @FXML
    private Text answerText1;
    @FXML
    private Text answerText2;
    @FXML
    private Text answerText3;
    @FXML
    private Text answerText4;
    @FXML
    private Label points;
    @FXML
    private Label correctAnswerLabel;

	private FXMLLoader mLLoader;

	@Override
	protected void updateItem(QuestionInExam qie, boolean empty) {
		super.updateItem(qie, empty);

	    if(empty || qie == null) {
	    	setText(null);
	        setGraphic(null);
        } else {
        	if (mLLoader == null) {
        		mLLoader = new FXMLLoader(getClass().getResource("ExamFormQuestionCell.fxml"));
	            mLLoader.setController(this);
                try {
                	mLLoader.load();
	            } catch (IOException e) {
	            	System.out.println("Can't load the mudafaka\n");
	                e.printStackTrace();
	            }

        	}
        	Question q = (logic.Question) ((gui.QuestionInExam) qie).getQuestion();
        	questionBody.setText(q.getQuestionTxt());
        	answerText1.setText(q.getAnswers()[0]);
        	answerText2.setText(q.getAnswers()[1]);
        	answerText3.setText(q.getAnswers()[2]);
        	answerText4.setText(q.getAnswers()[3]);
        	correctAnswerLabel.setText("The correct answer is "+q.getCorrectAnswer());
        	correctAnswerLabel.setVisible(this.setCorrectAnswerLabel());
        	
        	int temp = ((gui.QuestionInExam) qie).getPointsInExam();
        	String s="("+temp+" point";
        	s+=( temp==1 ? ")" : "s)" );
        	points.setText(s);
        	questionNumber.setText(Integer.toString(((gui.QuestionInExam)qie).getSerialNumberInExam())+")");

	        setGraphic(pane);
	        }

	    }
	public abstract Boolean setCorrectAnswerLabel();

}
