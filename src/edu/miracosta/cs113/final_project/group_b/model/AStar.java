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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

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
	private MapProperty<V, V> cameFrom = new SimpleMapProperty<>(this, "cameFrom", FXCollections.observableHashMap());                        // Pairs vertices with their immediate predecessor
	private Graph<V> graph;                            // Graph to find path from start to goal
	private ToDoubleBiFunction<V, V> heuristic;
	private DoubleProperty sleep = new SimpleDoubleProperty(this, "sleep", 0);
//	private ReadOnlyObjectProperty<V> start = new SimpleReadOnlyObjectProperty();
	private BooleanProperty isPlay = new SimpleBooleanProperty(this, "isPlay", false);
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	{
		try {
			Thread.sleep(timeUnit.toMillis((long) sleep.get()));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		// or no time unit and assume milliseconds
	}
	
	/**
	 * Initializes all data structures needed for AStar algorithm.
	 * 
	 * @param graph
	 *        Data structure containing all vertices possible for path
	 * @param start
	 *        Beginning vertex from which to find path
	 */
	public AStar(Graph<V> graph, V start, ToDoubleBiFunction<V, V> heuristic) {
		this.heuristic = heuristic;
		this.graph = graph;
		costSoFar = new HashMap<V, Double>();
//		cameFrom = new HashMap<V, V>();
		frontier = new PriorityQueue<VertexPriority<V>>();
		cameFrom.get().put(start, null);             // Add first vertex (no predecessor)
		costSoFar.put(start, 0.0);             // Add first vertex (no path cost yet)
		frontier.add(new VertexPriority<V>(start, 0));
	}
	
	/**
	 * Performs AStar path-finding algorithm from start vertex to
	 * given goal vertex.
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
					cameFrom.put(next, current.getVertex());    // Record next vertex's predecessor (current vertex)
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
		next = cameFrom.get(goal);
		System.out.println("Total path length: " + costSoFar.get(goal) + "\n");
		while (next != null) {
			path.push(next);
			next = cameFrom.get(next);
		}
		while (!path.empty()) {
			System.out.print(path.pop() + " --> ");
		}
		System.out.print(goal);
	}
	
	public DoubleProperty sleepProperty()
	{
		return sleep;
	}
	
	public final double getSleep()
	{
		return sleep.doubleValue();
	}
	
	public final void setDouble(double value)
	{
		sleep.set(value);
	}
}
