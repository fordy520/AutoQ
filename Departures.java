package application;

import java.sql.Time;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.text.*;
import java.util.Date;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Departures {

	private String time;
	// private SimpleDateFormat = new SimpleDateFormat("HH:mm");
	private String destination;
	private String airline;
	private String flightnumber;
	private String terminal;
	private String remarks;
	
	public Departures(String time, String destination, String airline, String flightnumber, String terminal,
			String remarks) {
		super();
		this.time = time;
		this.destination = destination;
		this.airline = airline;
		this.flightnumber = flightnumber;
		this.terminal = terminal;
		this.remarks = remarks;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlightnumber() {
		return flightnumber;
	}

	public void setFlightnumber(String flightnumber) {
		this.flightnumber = flightnumber;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
