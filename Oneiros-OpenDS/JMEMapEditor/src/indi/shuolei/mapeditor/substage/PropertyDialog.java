package indi.shuolei.mapeditor.substage;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PropertyDialog extends AnchorPane {
	@FXML
	private TableView<PropertyData> mPropertyTable;
    @FXML
    private TitledPane mTitledPane;
    
	private Stage propertyDialog;
	private OnPropertyDialogActionListener onPropertyDialogActionListener;
	private ObservableList<PropertyData> propertyDatas = FXCollections.observableArrayList();

	public PropertyDialog() {
		FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("PropertyDialog.fxml"));
		fXMLLoader.setRoot(PropertyDialog.this);
		fXMLLoader.setController(PropertyDialog.this);
		try {
			fXMLLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@SuppressWarnings("unchecked")
	public void showDialog() {
		if (propertyDialog == null) {
			propertyDialog = new Stage(StageStyle.TRANSPARENT);
			propertyDialog.setResizable(false);
			propertyDialog.setScene(new Scene(this));

			propertyDialog.show();
		}
	}

	public void hideDialog() {
		if (propertyDialog != null) {
			propertyDialog.hide();
		}
	}

	@FXML
	private void onOkDialogAction(ActionEvent event) {
		if (onPropertyDialogActionListener != null) {
			onPropertyDialogActionListener.onOkDialogAction(propertyDatas);
		}
		hideDialog();
	}

	@FXML
	private void onAddPropertyAction(ActionEvent event) {
		propertyDatas.add(new PropertyData("Key", "Value"));
		if (onPropertyDialogActionListener != null) {
			onPropertyDialogActionListener.onAddPropertyAction();
		}
	}

	@FXML
	private void onDeletePropertyAction(ActionEvent event) {
		propertyDatas.remove(mPropertyTable.getSelectionModel().getSelectedItem());
		if (onPropertyDialogActionListener != null) {
			onPropertyDialogActionListener.onDeletePropertyAction();
		}
	}

	@FXML
	private void onCancelDialogAction(ActionEvent event) {
		hideDialog();
	}

	public OnPropertyDialogActionListener getOnPropertyDialogActionListener() {
		return onPropertyDialogActionListener;
	}

	public void setOnPropertyDialogActionListener(OnPropertyDialogActionListener onPropertyDialogActionListener) {
		this.onPropertyDialogActionListener = onPropertyDialogActionListener;
	}

	public interface OnPropertyDialogActionListener {
		public void onInit(ObservableList<PropertyData> propertyDatas,TitledPane mTitledPane);

		public void onOkDialogAction(ObservableList<PropertyData> propertyDatas);

		public void onAddPropertyAction();

		public void onDeletePropertyAction();
	}
}
