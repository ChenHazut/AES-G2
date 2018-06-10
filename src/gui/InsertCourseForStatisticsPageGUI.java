package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;
import logic.User;

public class InsertCourseForStatisticsPageGUI implements Initializable
{
	@FXML
	Button BeckButton;
	@FXML
	Button OkButton;
	
	private User principle;
	
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("InsertCourseForStatisticsPage.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show(); 
	}
	
	public void BeckButtonAction(ActionEvent ae) throws Exception
	{
		StatisticsMenuGUI qrg=new StatisticsMenuGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void OkButtonAction(ActionEvent ae) throws Exception
	{
		
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}
}
