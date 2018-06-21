package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.OvertimeDetails;
import logic.PrincipalController;

public class OvertimeRequestMenuGUI {

	@FXML
	private TableView<OvertimeDetails> table;

	@FXML
	private TableColumn<OvertimeDetails, String> examIDCol;

	@FXML
	private TableColumn<OvertimeDetails, Integer> execIDCol;

	@FXML
	private TableColumn<OvertimeDetails, Integer> timeCol;

	@FXML
	private TableColumn<OvertimeDetails, String> reasonCol;

	@FXML
	private Button approveBtn;

	@FXML
	private Button denyBtn;

	private PrincipalController pc;

	private ObservableList<OvertimeDetails> overtimeOL;

	@FXML
	void approveBtnAction(ActionEvent event) {
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		pc.approveOvertime(table.getSelectionModel().getSelectedItem());
	}

	@FXML
	void denyBtnAction(ActionEvent event) {
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		pc.denyOvertime(table.getSelectionModel().getSelectedItem());
	}

	public void initData() {
		examIDCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, String>("examID"));
		execIDCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, Integer>("ExecutionID"));
		timeCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, Integer>("requestedTime"));
		reasonCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, String>("reason"));
		pc = new PrincipalController();
		overtimeOL = FXCollections.observableArrayList();
		overtimeOL.addAll(pc.getAllOverTimeRequests());
		table.getItems().addAll(overtimeOL);
	}

}
