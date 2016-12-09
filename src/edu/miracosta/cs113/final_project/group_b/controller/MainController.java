
package edu.miracosta.cs113.final_project.group_b.controller;

import edu.miracosta.cs113.final_project.group_b.model.AStar;
import edu.miracosta.cs113.final_project.group_b.model.Graph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

public class MainController
{

	@FXML private ComboBox<?>	graphPicker;
	@FXML private ComboBox<?>	algorithmPicker;
	@FXML private Slider		speedSlider;
	@FXML private Group			groupPane;
	@FXML private ImageView		imageView;
	
	private Graph<Point2D> graph;
	private AStar<Point2D> pathFinder = new AStar<Point2D>(graph, new Point2D(55, 6), (cur, end) -> 0);

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
		(new Thread(AStar::step)).start();
	}
	
	@FXML
	private void handlePlay(ActionEvent e)
	{
		System.out.println("E");
	}

	@FXML
	void initialize()
	{
		assert graphPicker != null : "fx:id=\"graphPicker\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert algorithmPicker != null : "fx:id=\"algorithmPicker\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert speedSlider != null : "fx:id=\"speedSlider\" was not injected: check your FXML file 'initial prototype.fxml'.";
		// assert stopButton != null : "fx:id=\"stopButton\" was not injected: check your FXML file 'initial prototype.fxml'.";
		// assert playToggleGroup != null : "fx:id=\"playToggleGroup\" was not injected: check your FXML file 'initial prototype.fxml'.";
		// assert stepButton != null : "fx:id=\"stepButton\" was not injected: check your FXML file 'initial prototype.fxml'.";
		// assert playButton != null : "fx:id=\"playButton\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert groupPane != null : "fx:id=\"groupPane\" was not injected: check your FXML file 'initial prototype.fxml'.";
		assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'initial prototype.fxml'.";

		pathFinder.sleepProperty().bind(speedSlider.valueProperty());
		pathFinder.sleepProperty().addListener(e -> System.out.println(pathFinder.getSleep()));
	}
}
