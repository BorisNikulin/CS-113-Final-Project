
package edu.miracosta.cs113.final_project.group_b.controller;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;
import edu.miracosta.cs113.final_project.group_b.model.AStar;
import edu.miracosta.cs113.final_project.group_b.model.Graph;
import edu.miracosta.cs113.final_project.group_b.model.GraphUtility;
import edu.miracosta.cs113.final_project.group_b.view.GraphOnImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MainController
{

	@FXML private ComboBox<?>	graphPicker;
	@FXML private ComboBox<?>	algorithmPicker;
	@FXML private Slider		speedSlider;
	@FXML private Group			groupPane;
	@FXML private ImageView		imageView;
	@FXML private GraphOnImage  view;
	
	private GraphUtility<Point2D> graphReader;
	private Graph<Point2D> graph;
	private AStar<Point2D> pathFinder;
	private Point2D start;
 	private Point2D goal;
 	private Point2D last;
 	private boolean goalReached;
	
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
		goalReached = false;
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
		class PointSetter implements BiFunction<Integer, Integer, Point2D> { // Function sets point coordinates
			@Override
			public Point2D apply(Integer x, Integer y) {
				return new Point2D(x, y);
			}
		}
		PointSetter pointSetter = new PointSetter();
		graphReader = new GraphUtility<Point2D>(fileName, pointSetter);      // Reads graph from text file
		graph = graphReader.getGraph();
	}
	
	/**
	 * Sets the start coordinate and adds colored circles to the view
	 * representing start and all other vertices.
	 * 
	 * @param start
	 *        The starting point of the path
	 */
	public void setStart(Point2D start) {
		this.start = start;
		Set<Point2D> allVerts = graph.getVertices();
		for (Point2D vertex : allVerts) {
			view.addVertex(vertex, Color.DARKGRAY);
		}
		view.addVertex(start, Color.RED);
		last = start;
	}
	
	/**
	 * Sets goal point for path finding and colors circle a special color.
	 * @param goal
	 */
	public void setGoal(Point2D goal) {
		this.goal = goal;
		view.addVertex(goal, Color.BLUE);
	}
	
	/**
	 * Selects graph from combo box. (NOTE: Currently only one graph; this
	 * functionality is limited/broken for now.)
	 * 
	 * @param e
	 *        ActionEvent that triggers method (graph combo box selection)
	 */
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
		// TODO: Fetch matching image file & give to View (currently specified in GraphOnImage)
	}
	
	/**
	 * Allows user to select a path-finding algorithm from the combo box to
	 * see animated on screen. (NOTE: currently only one algorithm, so this 
	 * feature not in use.)
	 * 
	 * @param e
	 *        ActionEvent that triggers method (algorithm combo box selection)
	 */
	@FXML
	private void selectAlgorithm(ActionEvent e)
	{
		// TODO: In future, if we have more algorithms: have setAlgorithm() take parameter
		setAlgorithm();
	}
	
	/**
	 * Stops animation of path-finding.
	 * 
	 * @param e
	 *        ActionEvent that triggers method (stop button pressed)
	 */
	@FXML
	private void handleStop(ActionEvent e)
	{
		pathFinder.setPlay(false);
	}
	
	/**
	 * Animates one path-finding step.
	 * 
	 * @param e
	 *        ActionEvent that triggers method (step button pressed)
	 */
	@FXML
	private void handleStep(ActionEvent e)
	{
		ToggleButton stepButton = (ToggleButton) e.getSource();
		stepButton.setSelected(false);
		animateStep();
	}
	
	/**
	 * Animation method used for step and play functions. Gets new
	 * points from AStar (model) and gives their data to GraphOnImage
	 * (view).
	 */
	private void animateStep() {
		Point2D vertex = pathFinder.step();
		if (goalReached) {                                       // If goal has been found, end early
			return;
		}
		if (last == goal) {                                      // If last vertex was goal:
			Point2D prev;
			Stack<Point2D> pathStack = pathFinder.pathStack();   // Get full path
			view.addVertex(last, Color.BLUE);                    // Re-color goal vertex to stand out
			while (!pathStack.isEmpty() && last != null) {
				prev = pathFinder.getPathMap().get(last);
				if (prev != null) {
					view.addEdge(last, prev, Color.RED);         // Draw full path, edge by edge by getting predecessors
				}
				last = prev;
			}
			goalReached = true;                                  // Set boolean flag
			return;
		}
		if (vertex != null) {
			view.addVertex(vertex, Color.GREEN);                 // Color explored vertices before eventually drawing actual path
			last = vertex;                                       // Keeps track of vertex found in last step
		}
	}
	
	@FXML
	private void handlePlay(ActionEvent e) throws InterruptedException
	{
		System.out.println("Play");
		pathFinder.setPlay(true);
		while (pathFinder.getPlay()) {   // Check if still playing, if so:
			animateStep();               // Do a step of algorithm
			                             // TODO: Get thread/sleep to work (may be necessary to change in AStar class)
		}
		animateStep();
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
		assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'initial prototype.fxml'.";
	}
}
