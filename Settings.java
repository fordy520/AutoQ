package application;

import java.io.IOException;

import com.jfoenix.controls.JFXSlider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Settings {

	@FXML
	private JFXSlider sliderVolume = new JFXSlider();


	public boolean voiceFeedback(ToggleButton toggleVoiceFeedback) {
		if (toggleVoiceFeedback.isSelected()) { // enable volume slider
			sliderVolume.setDisable(false);
		} else if (!toggleVoiceFeedback.isSelected()) { // disable volume slider
			sliderVolume.setDisable(true);
		}
		return sliderVolume.isDisabled();
	}

}
