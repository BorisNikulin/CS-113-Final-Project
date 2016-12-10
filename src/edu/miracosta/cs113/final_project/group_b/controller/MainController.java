
package edu.miracosta.cs113.final_project.group_b.controller;

import java.io.FileNotFoundException;
import java.util.function.BiFunction;

import edu.miracosta.cs113.final_project.group_b.model.AStar;
import edu.miracosta.cs113.final_project.group_b.model.Graph;
import edu.miracosta.cs113.final_project.group_b.model.GraphUtility;
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
	
	private PointSetter pointSetter;
	private GraphUtility<Point2D> graphReader;
	private Graph<Point2D> graph;
	private AStar<Point2D> pathFinder;
	
	class PointSetter implements BiFunction<Integer, Integer, Point2D> {
		@Override
		public Point2D apply(Integer x, Integer y) {
			return new Point2D(x, y);
		}
	}
	
	// TODO: Set this info elsewhere (separate methods?)
	public MainController() throws FileNotFoundException {
		pointSetter = new PointSetter();
		graphReader = new GraphUtility<Point2D>("src\\edu\\miracosta\\cs113\\final_project\\group_b\\model\\test\\astartest.txt", pointSetter);
		graph = graphReader.getGraph();
		pathFinder = new AStar<Point2D>(graph, new Point2D(1, 1), new Point2D(99, 99), (a, b) -> Math.abs(a.getX() - b.getX()) +
                                                                                                 Math.abs(a.getY() - b.getY()));
	}

	@FXML
	private void selectGraph(ActionEvent e)
	{
		System.out.println("A");
		// TODO: Set graph based on selected item in combo box
	}
	
	@FXML
	private void selectAlgorithm(ActionEvent e)
	{
		System.out.println("B");
	}
	
	@FXML
	private void handleStop(ActionEvent e)
	{
		System.out.println("Stop");
		pathFinder.setPlay(false);
	}
	
	@FXML
	private void handleStep(ActionEvent e)
	{
		ToggleButton stepButton = (ToggleButton) e.getSource();
		stepButton.setSelected(false);
		System.out.println("Step");
		pathFinder.step();
		pathFinder.printPath(new Point2D(99, 99));
		// TODO: View implementation of step (draw path & highlight next vertex?)
		// TODO: Get thread/sleep to work (may be necessary to change in AStar class)
		// (new Thread(AStar::step)).start();
	}
	
	@FXML
	private void handlePlay(ActionEvent e)
	{
		System.out.println("Play");
		pathFinder.setPlay(true);
		pathFinder.play();
		pathFinder.printPath(new Point2D(99, 99));
		// TODO: View implementation (draw path)
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
		// TODO: Bind other properties?
	}
}
