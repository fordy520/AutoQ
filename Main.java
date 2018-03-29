package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import java.sql.*;

public class Main extends Application {

	GUI_Controller gui = new GUI_Controller();

	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("/application/mainGUI.fxml"));
			ColorAdjust brightnessAdjust = new ColorAdjust();
			// brightnessAdjust.setBrightness(gui.changed(observable, oldValue, newValue);

			root.setEffect(brightnessAdjust);
			Scene scene = new Scene(root);
						
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("AutoQ");
//			primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(1.5));
//			primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1.5));
			primaryStage.show();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// DBConnection.getFlight_time();
		launch(args);
	}
}
