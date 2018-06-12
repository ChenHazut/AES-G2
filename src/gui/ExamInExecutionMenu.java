package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class ExamInExecutionMenu {

    @FXML
    private TableColumn<?, ?> examIDCol;

    @FXML
    private TableColumn<?, ?> executionIDCol;

    @FXML
    private TableColumn<?, ?> courseNameCol;

    @FXML
    private Button executeNewExamBtn;

    @FXML
    private Button approveGradesViewBtn;

    @FXML
    void approveGradesViewBtnAction(ActionEvent event) 
    {
    	
    }

    @FXML
    void executeNewExamBtnAction(ActionEvent event) 
    {

    }

}
