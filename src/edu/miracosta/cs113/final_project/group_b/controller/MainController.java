
package edu.miracosta.cs113.final_project.group_b.controller;

import edu.miracosta.cs113.final_project.group_b.view.custom_control.GraphOnImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class MainController
{

	@FXML private ComboBox<?>	graphPicker;
	@FXML private ComboBox<?>	algorithmPicker;
	@FXML private Slider		speedSlider;
	@FXML private GraphOnImage	graphOnImage;

	@FXML
	private void selectGraph(ActionEvent e)
	{
		System.out.println("A");
	}

	@FXML
	private void selectAlgorithm(ActionEvent e)
	{
		System.out.println("B");
	}

	@FXML
	private void handleStop(ActionEvent e)
	{
		System.out.println("C");
	}
	
	@FXML
	private void handleStep(ActionEvent e)
	{
		ToggleButton stepButton = (ToggleButton) e.getSource();
		stepButton.setSelected(false);
		System.out.println("D");
	}
	
	@FXML
	private void handlePlay(ActionEvent e)
	{
		System.out.println("E");
	}

	@FXML
	void initialize()
	{
		assert graphPicker != null : "fx:id=\"graphPicker\" was not injected: check your FXML file 'Main.fxml'.";
		assert algorithmPicker != null : "fx:id=\"algorithmPicker\" was not injected: check your FXML file 'Main.fxml'.";
		assert speedSlider != null : "fx:id=\"speedSlider\" was not injected: check your FXML file 'Main.fxml'.";
		// assert stopButton != null : "fx:id=\"stopButton\" was not injected: check your FXML file 'Main.fxml'.";
		// assert playToggleGroup != null : "fx:id=\"playToggleGroup\" was not injected: check your FXML file 'Main.fxml'.";
		// assert stepButton != null : "fx:id=\"stepButton\" was not injected: check your FXML file 'Main.fxml'.";
		// assert playButton != null : "fx:id=\"playButton\" was not injected: check your FXML file 'Main.fxml'.";
		// assert groupPane != null : "fx:id=\"groupPane\" was not injected: check your FXML file 'Main.fxml'.";
		// assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'Main.fxml'.";
		assert graphOnImage != null : "fx:id=\"graphOnimage\" was not injected: check your FXML file 'Main.fxml'.";

		// TODO initialize the combo boxes
	}
}
