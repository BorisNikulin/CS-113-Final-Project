package edu.miracosta.cs113.final_project.group_b.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleBiFunction;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * AStar.java: Creates an AStar object that initializes the data structures
 * required to perform the path-finding algorithm. Contains a heuristic 
 * method used to predict which vertex in graph will be the best to search
 * next.
 * 
 * @author Marina Mizar
 */
public class AStar<V> {
	
	private PriorityQueue<VertexPriority<V>> frontier; // Vertices not yet evaluated
	private Map<V, Double> costSoFar;                  // Pairs vertices with their total path costs from start
	private MapProperty<V, V> cameFrom;                // Pairs vertices with their immediate predecessor
	private Graph<V> graph;                            // Graph to find path from start to goal
	private ToDoubleBiFunction<V, V> heuristic;        // Heuristic function used to predict next best vertex
	private DoubleProperty sleep;                      // Determines time to pause between steps
	private ReadOnlyObjectProperty<V> start;           // Start vertex
	private ReadOnlyObjectProperty<V> goal;            // End vertex
	private BooleanProperty isPlay;                    // True if steps are being animated in view (play button activated)
	private TimeUnit timeUnit;                         // Determines time unit given to sleep
	
	/**
	 * Initializes all data structures needed for AStar algorithm,
	 * including Properties used to bind view to model.
	 * 
	 * @param graph
	 *        Data structure containing all vertices possible for path
	 * @param start
	 *        Beginning vertex from which to find path
	 * @param goal
	 *        End vertex to seek path towards
	 * @param heuristic
	 *        Heuristic function that calculates best next vertex for path,
	 *        based on position in coordinate system
	 */
	public AStar(Graph<V> graph, V start, V goal, ToDoubleBiFunction<V, V> heuristic) {
		this.heuristic = heuristic;
		this.graph = graph;
		this.start = new SimpleObjectProperty<V>(this, "start", start);
		this.goal = new SimpleObjectProperty<V>(this, "goal", goal);
	
		timeUnit = TimeUnit.MILLISECONDS;
		sleep = new SimpleDoubleProperty(this, "sleep", 10000);
		isPlay = new SimpleBooleanProperty(this, "isPlay", false);
		costSoFar = new HashMap<V, Double>();
		cameFrom = new SimpleMapProperty<>(this, "cameFrom", FXCollections.observableHashMap());
		frontier = new PriorityQueue<VertexPriority<V>>();
		cameFrom.get().put(start, null);               // Add first vertex (no predecessor)
		costSoFar.put(start, 0.0);                     // Add first vertex (no path cost yet)
		frontier.add(new VertexPriority<V>(start, 0)); // Initialize frontier with start vertex
	}
	
	/**
	 * Performs one "step" of A* algorithm, where an item from the
	 * frontier is chosen and its adjacent vertices are evaluated.
	 */
	public void step() {
		VertexPriority<V> current;                                // Current vertex in frontier being evaluated
		LinkedList<Edge<V>> adjacentEdges;                        // All edges adjacent to current vertex
		double newCost, priority;                                 // Calculated cost between current & next; current priority
		V next;                                                   // Next adjacent vertex to current
		 
		if (!frontier.isEmpty()) {                                // Do step if there are vertices in frontier
			current = frontier.poll();                            // Get best new vertex from priority queue
			
			if (current.getVertex().equals(goal.get())) {
				isPlay.set(false);
				return;                                           // End search early once goal has been reached
			}

			adjacentEdges = graph.getEdges(current.getVertex());
			
			for (Edge<V> edge : adjacentEdges) {
				next = edge.getDestination();                      // Get next adjacent edge & cost between current & next
				newCost = costSoFar.get(current.getVertex()) + edge.getWeight();
				
				if (!costSoFar.containsKey(next) ||                // If cost has not been found for next vertex 
					newCost < costSoFar.get(next)) {               // Or new calculated cost is smaller
					costSoFar.put(next, newCost);                  // Replace cost with newer, smaller cost
					                                               // Calculate priority for next vertex, add to frontier
					priority = newCost + heuristic.applyAsDouble(goal.get(), next);					
					frontier.offer(new VertexPriority<V>(next, priority));
					cameFrom.get().put(next, current.getVertex()); // Record next vertex's predecessor (current vertex)
				}
			}
		}
	}
	
	/**
	 * Continuous play version of algorithm; moves through steps until
	 * goal is reached (then isPlay is set to false, ending method).
	 */
	public void play() {
		int i = 0;
		while (isPlay.get() && i < 50) {   // Check if still playing, if so:
			step();              // Do a step of algorithm
			goToSleep();         // Pause for time specified by user
			i++;
		}
		System.out.println("Done playing");
	}
	
	/**
	 * Performs AStar path-finding algorithm from start vertex to
	 * given goal vertex. Not used in GUI, just for testing purposes.
	 * 
	 * @param goal
	 *        Ending vertex
	 */
	public void getPathTo(V goal) {
		VertexPriority<V> current;                             // Current vertex in frontier being evaluated
		LinkedList<Edge<V>> adjacentEdges;                     // All edges adjacent to current vertex
		double newCost, priority;                              // Calculated cost between current & next; current priority
		V next;                                                // Next adjacent vertex to current
		
		while (!frontier.isEmpty()) {                          // Continue evaluating vertices until frontier is exhausted
			current = frontier.poll();                         // Get best new vertex from priority queue
			
			if (current.getVertex().equals(goal)) {
				return;                                        // End search early once goal has been reached
			}

			adjacentEdges = graph.getEdges(current.getVertex());
			
			for (Edge<V> edge : adjacentEdges) {
				next = edge.getDestination();                   // Get next adjacent edge & cost between current & next
				newCost = costSoFar.get(current.getVertex()) + edge.getWeight();
				
				if (!costSoFar.containsKey(next) ||             // If cost has not been found for next vertex 
					newCost < costSoFar.get(next)) {            // Or new calculated cost is smaller
					costSoFar.put(next, newCost);               // Replace cost with newer, smaller cost
					                                            // Calculate priority for next vertex, add to frontier
					priority = newCost + heuristic.applyAsDouble(goal, next);					
					frontier.offer(new VertexPriority<V>(next, priority));
					cameFrom.get().put(next, current.getVertex());// Record next vertex's predecessor (current vertex)
				}
			}
		}
	}
	
	/**
	 * Temporary method used to test AStar algorithm. Traces back path from
	 * goal to start, printing total cost and each vertex visited.
	 * 
	 * @param goal
	 *        End point of path
	 */
	public void printPath(V goal) {
		V next;
		Stack<V> path = new Stack<V>();
		next = cameFrom.get().get(goal);
		System.out.println("Total path length: " + costSoFar.get(goal) + "\n");
		while (next != null) {
			path.push(next);
			next = cameFrom.get().get(next);
		}
		while (!path.empty()) {
			System.out.print(path.pop() + " --> ");
		}
		System.out.print(goal);
		System.out.println("\n");
	}
	
	/**
	 * Puts the current thread to sleep for time given.
	 * 
	 * @param time
	 *        Double representing amount of time to sleep
	 * @param unit
	 *        Specifies the unit of measurement (seconds, etc.)
	 */
	public void goToSleep() {
		try {
			Thread.sleep(timeUnit.toMillis((long) sleep.get()));
		}
		catch (InterruptedException e) {
			e.printStackTrace();			
		}
	}
	
	/**
	 * Property wrapper for sleep amount; used in controller.
	 * 
	 * @return Sleep property object
	 */
	public DoubleProperty sleepProperty() {
		return sleep;
	}
	
	/**
	 * Gets sleep value as a double.
	 * 
	 * @return Sleep as a double
	 */
	public final double getSleep() {
		return sleep.doubleValue();
	}
	
	/**
	 * Sets sleep to specified double value.
	 * 
	 * @param value
	 *        Number of time units to sleep.
	 */
	public final void setSleep(double value) {
		sleep.set(value);
	}
	
	public BooleanProperty playProperty() {
		return isPlay;
	}
	
	public final boolean getPlay() {
		return isPlay.get();
	}
	
	public void setPlay(boolean value) {
		isPlay.set(value);
	}
	
	public MapProperty<V, V> pathMapProperty() {
		return cameFrom;
	}
	
	public ObservableMap<V, V> getPathMap() {
		return cameFrom.get();
	}
	
	public ReadOnlyObjectProperty<V> getStartProperty() {
		return start;
	}
	
	public ReadOnlyObjectProperty<V> getGoalProperty() {
		return goal;
	}
	
	public V getStart() {
		return start.getValue();
	}
	
	public V getGoal() {
		return goal.getValue();
	}
}
