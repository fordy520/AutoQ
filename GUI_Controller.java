package application;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.TimerTask;

import javax.swing.Timer;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings("rawtypes")
public class GUI_Controller implements Initializable, ChangeListener {

	FastTrack fastTrack = new FastTrack();
	Help help = new Help();
	Settings settings = new Settings();
	// -----------------------------------------------------------------------------

	@FXML
	private AnchorPane paneBG, paneFastTrack, paneDepartures, paneHelp, paneHelp2, paneMain, paneSettings;
	@FXML
	private VBox vboxSide, vboxStaffLogin;
	// -----------------------------------------------------------------------------
	@FXML
	private JFXSlider sliderVolume, sliderBrightness;
	@FXML
	private ToggleButton toggleVoiceFeedback;
	@FXML // Side Pane Menu Buttons
	private Button btnHome, btnFastTrack, btnHelp, btnSettings, btnDepartures;
	@FXML // Help Menu Buttons
	private Button btnHelp_Mobility, btnHelp_Other, btnHelp_BoardingPass, btnBeginStaffLogin;
	@FXML // Pin Buttons
	private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnClear, btnEnter;
	@FXML
	private Label lblClock, lblHelpText2, lblStatus;
	@FXML
	private JFXPasswordField passwordStaffPin;
	// -----------------------------------------------------------------------------
	@FXML
	private TableView<Departures> tableDepartures;
	@FXML
	private TableColumn<Departures, String> time;
	@FXML
	private TableColumn<Departures, String> destination;
	@FXML
	private TableColumn<Departures, String> airline;
	@FXML
	private TableColumn<Departures, String> flightnumber;
	@FXML
	private TableColumn<Departures, String> terminal;
	@FXML
	private TableColumn<Departures, String> remarks;

	private ObservableList<Departures> departuresList;

	@SuppressWarnings("unchecked")
	@Override
	// Initializes Scene on run
	public void initialize(URL location, ResourceBundle resources) {
		departuresList = FXCollections.observableArrayList();
		setDeparturesTable();
		loadDepartureList();
		sliderVolume.valueProperty().addListener(this);
		sliderBrightness.valueProperty().addListener(this);
		clock();
		// Begin FadeTransition for Label Messages
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// Sets a fade transition for the label using the timeline
				FadeTransition fadeinTransition = new FadeTransition(Duration.seconds(1), lblHelpText2);
				fadeinTransition.setFromValue(0.2);
				fadeinTransition.setToValue(1.0);
				fadeinTransition.setCycleCount(1);
				//fadeinTransition.jumpTo(Duration.seconds(0));
				fadeinTransition.play();
			}
		}), new KeyFrame(Duration.seconds(1.5))); // Show frame for 1 second
		// Used to specify an animation that repeats indefinitely, until the stop()
		// method is called.
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play(); // play Timeline Indefinitley

	}

	// Clock Method which uses a Timeline and Eventhandler to update the time
	// displayed on lblClock
	public void clock() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {

			@Override // When change in Date/Time
			public void handle(ActionEvent event) {// Set date format i.e Mon 12 October 13:45:11
				DateFormat dateFormat = new SimpleDateFormat("EEE dd MMMMMMMMM HH:mm:ss");
				// get current date time with Date()
				// Date date = new Date();
				// System.out.println(dateFormat.format(date));
				// get current date time with Calendar()
				Calendar cal = Calendar.getInstance();
				String currentTime = (dateFormat.format(cal.getTime()));
				// System.out.println(currentTime);
				lblClock.setText(currentTime);
				// Sets a fade transition for the label using the timeline
				// FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),
				// lblClock);
				// fadeTransition.setFromValue(1.0);
				// fadeTransition.setToValue(0.0);
				// fadeTransition.setCycleCount(Animation.INDEFINITE);
				// fadeTransition.play();

			}
		}), new KeyFrame(Duration.seconds(1))); // Show frame for 1 second
		// Used to specify an animation that repeats indefinitely, until the stop()
		// method is called.
		timeline.setCycleCount(Animation.INDEFINITE);

		timeline.play(); // play Timeline Indefinitley
	};

	public void setDeparturesTable() { // Initialises Table with Column headers
		time.setCellValueFactory(new PropertyValueFactory<Departures, String>("Time"));
		destination.setCellValueFactory(new PropertyValueFactory<Departures, String>("Destination"));
		airline.setCellValueFactory(new PropertyValueFactory<Departures, String>("Airline"));
		flightnumber.setCellValueFactory(new PropertyValueFactory<Departures, String>("Flightnumber"));
		terminal.setCellValueFactory(new PropertyValueFactory<Departures, String>("Terminal"));
		remarks.setCellValueFactory(new PropertyValueFactory<Departures, String>("Remarks"));
		remarks.setPrefWidth(120);

	}

	// loads the data from mySQL table into initialised DeparturesTable
	public void loadDepartureList() {
		try {
			// Get connection to database
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoqdb", "root", "");
			System.out.println("Successful Connection");
			// Create Statement
			Statement myStmtDepartures = myConnection.createStatement();
			// Execute SQL query to retrieve all data from the flightinfo table
			ResultSet myRsDepartures = myStmtDepartures.executeQuery("SELECT * FROM flightinfo");
			// Process the result set
			while (myRsDepartures.next()) {
				departuresList.add(new Departures(myRsDepartures.getString(1), myRsDepartures.getString(2),
						myRsDepartures.getString(3), myRsDepartures.getString(4), myRsDepartures.getString(5),
						myRsDepartures.getString(6)));
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		tableDepartures.setItems(departuresList);
	}

	@Override
	// When listener detects a change in the valuesProperties of Volume and
	// Brightness sliders, the values are Observed and stored

	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		double volumeValue = sliderVolume.getValue();
		System.out.println(" Volume " + volumeValue);

		double brightnessValue = sliderBrightness.getValue();
		// System.out.println(" Brightness " + brightnessValue);
		Double percentage = (brightnessValue / 100);
		System.out.println(" percentage " + (percentage * 100));
		paneBG.setOpacity(percentage);

	}

	// Handles Side pane Menu button navigation
	@FXML
	public void handleSideButtonAction(ActionEvent eventSideButton) { // Handles Side Pane Button Navigation
		if (eventSideButton.getTarget() == btnHome) { // btnHome clicked
			paneBG.setVisible(true);
			vboxSide.setVisible(true);
			paneMain.setVisible(true);
			paneFastTrack.setVisible(false);
			paneDepartures.setVisible(false);
			paneHelp.setVisible(false);
			paneHelp2.setVisible(false);
			paneSettings.setVisible(false);
		} else if (eventSideButton.getTarget() == btnFastTrack) { // btnFastTrack clicked
			paneBG.setVisible(true);
			vboxSide.setVisible(true);
			paneMain.setVisible(false);
			paneFastTrack.setVisible(true);
			paneDepartures.setVisible(false);
			paneHelp.setVisible(false);
			paneHelp2.setVisible(false);
			paneSettings.setVisible(false);
		} else if (eventSideButton.getTarget() == btnHelp) { // btnHelp clicked
			paneBG.setVisible(true);
			vboxSide.setVisible(true);
			paneMain.setVisible(false);
			paneFastTrack.setVisible(false);
			paneDepartures.setVisible(false);
			paneHelp.setVisible(true);
			paneHelp2.setVisible(false);
			paneSettings.setVisible(false);
		} else if (eventSideButton.getTarget() == btnSettings) { // btnSettings clicked
			paneBG.setVisible(true);
			vboxSide.setVisible(true);
			paneMain.setVisible(false);
			paneFastTrack.setVisible(false);
			paneDepartures.setVisible(false);
			paneHelp.setVisible(false);
			paneHelp2.setVisible(false);
			paneSettings.setVisible(true);
		} else if (eventSideButton.getTarget() == btnDepartures) { // btnDepartures clicked
			paneBG.setVisible(true);
			vboxSide.setVisible(true);
			paneMain.setVisible(false);
			paneFastTrack.setVisible(false);
			paneDepartures.setVisible(true);
			paneHelp.setVisible(false);
			paneHelp2.setVisible(false);
			paneSettings.setVisible(false);
		}
	}

	// Method fetches the Disabled property of ToggleButton 'toggleVoiceFeedback'
	// upon ActionEvent and in turn enables/ disables Slider 'sliderVolume'
	@FXML
	public void processSettings_ToggleVoiceFeedback(ActionEvent eventToggleVoiceFeedback) {
		boolean isVoiceFeedbackDisabled = settings.voiceFeedback(toggleVoiceFeedback);
		if (isVoiceFeedbackDisabled) {
			sliderVolume.setDisable(true);
		} else {
			sliderVolume.setDisable(false);
			// sliderVolume.setValue(50.0);
		}
	}

	@FXML
	public void processHelpSelection(ActionEvent eventHelpButton) {
		paneHelp.setVisible(false);
		paneHelp2.setVisible(true);
		String helpType = null;
		String helpLabelMessage = null;

		if (eventHelpButton.getTarget() == btnHelp_Mobility) {
			helpType = "Mobility";
			helpLabelMessage = help.handleHelpButton(helpType);

		} else if (eventHelpButton.getTarget() == btnHelp_Other) {
			helpType = "Other";
			helpLabelMessage = help.handleHelpButton(helpType);

		} else if (eventHelpButton.getTarget() == btnHelp_BoardingPass) {
			helpType = "BoardingPass";
			helpLabelMessage = help.handleHelpButton(helpType);
		}
		lblHelpText2.setText(helpLabelMessage);
		//Disable user from navigation
		vboxSide.setDisable(true);
		lblStatus.setVisible(true);
		vboxStaffLogin.setVisible(false);
		btnBeginStaffLogin.setVisible(true);

		
	}

	@FXML // Verify Staff login Pin at Help Menu
	public void processStaffLogin(ActionEvent eventLogin) throws IOException {
		String value = null;
		if(eventLogin.getTarget() == btnBeginStaffLogin) {
			vboxStaffLogin.setVisible(true);
			btnBeginStaffLogin.setVisible(false);		
		}
		else if (eventLogin.getTarget() == btnEnter) {
			//If correct Pin is entered...
			if (passwordStaffPin.getText().equals("0510")) {
				lblStatus.setText("Login Success");
				passwordStaffPin.setText("");
				PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
				visiblePause.setOnFinished(event -> {
					lblStatus.setVisible(false);
					lblStatus.setText("");
					paneHelp2.setVisible(false);
					paneMain.setVisible(true);
					//Enable user navigation after staff login
					vboxSide.setDisable(false);
				});
				visiblePause.play();
			} else { // If incorrect Display..
				lblStatus.setText("Incorrect Pin");
				passwordStaffPin.setText("");
			}
		} else if (eventLogin.getTarget() == btnClear) {
			// clear the Current pin
			passwordStaffPin.setText("");
		} else {
			value = ((Button) eventLogin.getSource()).getText();
			lblStatus.setText("");
			passwordStaffPin.setText(passwordStaffPin.getText() + value);
		}
	}

	public void processFastTrack() {

	}

}
