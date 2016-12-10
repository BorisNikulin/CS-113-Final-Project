
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
	
	private GraphUtility<Point2D> graphReader;
	private Graph<Point2D> graph;
	private AStar<Point2D> pathFinder;
	private Point2D start;  // TODO: Set method
 	private Point2D goal;   // TODO: Set method
	
 	/**
 	 * Creates algorithm object with specified graph, start & end point, and
 	 * heuristic function for finding path.
 	 */
	public void setAlgorithm() {
		// TODO: In future, if we have more algorithms: include branching/option
		pathFinder = new AStar<Point2D>(graph, start, goal, (a, b) -> Math.abs(a.getX() - b.getX()) +
                                                                      Math.abs(a.getY() - b.getY()));
		pathFinder.sleepProperty().bind(speedSlider.valueProperty());
		pathFinder.sleepProperty().addListener(e -> System.out.println(pathFinder.getSleep()));
		// TODO: Bind other properties?
	}
	
	/**
	 * Reads graph from given file and creates graph object with data.
	 * Defines and uses PointSetter class (a BiFunction subclass) to
	 * give coordinates from file to vertices.
	 * 
	 * @param fileName
	 *        Name of file containing map data
	 * @throws FileNotFoundException
	 *         If path to file cannot be found
	 */
	public void setGraph(String fileName) throws FileNotFoundException {
		class PointSetter implements BiFunction<Integer, Integer, Point2D> {
			@Override
			public Point2D apply(Integer x, Integer y) {
				return new Point2D(x, y);
			}
		}
		PointSetter pointSetter = new PointSetter();
		graphReader = new GraphUtility<Point2D>(fileName, pointSetter);
		graph = graphReader.getGraph();
	}
	
	@FXML
	private void selectGraph(ActionEvent e)
	{
		String graphName;
		StringBuilder fileName;
		graphName = graphPicker.getValue().toString();
		fileName = new StringBuilder(graphName);
		fileName.append(".txt");
		// TODO: Insert directory in front of file name
		try {
			setGraph(fileName.toString());
		}
		catch (FileNotFoundException exception) {
			System.out.println("Graph file not found!");
		}
		// TODO: Fetch matching image file & give to View
	}
	
	@FXML
	private void selectAlgorithm(ActionEvent e)
	{
		// TODO: In future, if we have more algorithms: get selection from combo box
		setAlgorithm();
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
		// TODO: View implementation (draw path & highlight next vertex?)
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
		// TODO: View implementation (same as step but continuous)
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
	}
}
