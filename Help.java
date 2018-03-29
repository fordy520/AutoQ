package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class Help {
	
	
	public String handleHelpButton(String helpType) {
		String helpLabelMessage = null;
      
		if (helpType == "Mobility") {
			helpLabelMessage = "AutoQ has alerted a member of staff regarding \r\n"
					+ "your requested Mobility assistance, please \r\n"
					+ "remain at this Kiosk until a member of staff has arrived";
		} else if (helpType == "BoardingPass") {
			helpLabelMessage = "AutoQ has alerted a member of staff regarding \r\n"
					+ "problems with your Boarding Pass, please \r\n"
					+ "remain at this Kiosk until a member of staff has arrived";
		} else if (helpType == "Other") {
			helpLabelMessage = "AutoQ has alerted a member of staff, please \r\n"
					+ "remain at this Kiosk until a member of staff has arrived";
		} else {
			helpLabelMessage = "An error has occured, please remain at this Kiosk \r\n"
					+ "until a member of staff arrives to assist you";
		}
		return helpLabelMessage;
	}
}
