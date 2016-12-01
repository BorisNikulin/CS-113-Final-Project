
package edu.miracosta.cs113.final_project.group_b.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

public class MainController
{

	@FXML private ComboBox<?>	graphPicker;

	@FXML private ComboBox<?>	algorithmPicker;

	@FXML private Slider		speedSlider;

	@FXML private ToggleButton	stopButton;

	@FXML private ToggleGroup	playToggleGroup;

	@FXML private ToggleButton	stepButton;

	@FXML private ToggleButton	playButton;

	@FXML private Group			groupPane;

	@FXML private ImageView		imageView;

	@FXML
	void initialize()
	{
		assert graphPicker != null : "fx:id=\"graphPicker\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert algorithmPicker != null : "fx:id=\"algorithmPicker\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert speedSlider != null : "fx:id=\"speedSlider\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert stopButton != null : "fx:id=\"stopButton\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert playToggleGroup != null : "fx:id=\"playToggleGroup\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert stepButton != null : "fx:id=\"stepButton\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert playButton != null : "fx:id=\"playButton\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert groupPane != null : "fx:id=\"groupPane\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'initial prototype.fxml'.";

	}
}
