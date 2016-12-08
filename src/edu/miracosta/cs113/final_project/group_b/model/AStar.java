package edu.miracosta.cs113.final_project.group_b.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * AStar.java: Creates an AStar object that initializes the data structures
 * required to perform the path-finding algorithm. Contains a heuristic 
 * method used to predict which vertex in graph will be the best to search
 * next.
 * 
 * @author Marina Mizar
 */
public class AStar<V> {
	
	PriorityQueue<VertexPriority<V>> frontier; // Vertices not yet evaluated
	Map<V, Double> costSoFar;                  // Pairs vertices with their total path costs from start
	Map<V, V> cameFrom;                        // Pairs vertices with their immediate predecessor
	Graph<V> theGraph;                         // Graph to find path from start to goal
	
	/**
	 * Initializes all data structures needed for AStar algorithm.
	 * 
	 * @param graph
	 *        Data structure containing all vertices possible for path
	 * @param start
	 *        Beginning vertex from which to find path
	 */
	public AStar(Graph<V> graph, V start) {
		theGraph = graph;
		costSoFar = new HashMap<V, Double>();
		cameFrom = new HashMap<V, V>();
		frontier = new PriorityQueue<VertexPriority<V>>();
		cameFrom.put(start, null);             // Add first vertex (no predecessor)
		costSoFar.put(start, 0.0);             // Add first vertex (no path cost yet)
	}
	
	/**
	 * Performs AStar path-finding algorithm from start vertex to
	 * given goal vertex.
	 * 
	 * @param goal
	 *        Ending vertex
	 */
	public void getPathTo(V goal) {
		VertexPriority<V> current;            // Current vertex in frontier being evaluated
		LinkedList<Edge<V>> adjacentEdges;    // All edges adjacent to current vertex
		double newCost, priority;             // Calculated cost between current & next; current priority
		V next;                               // Next adjacent vertex to current
		
		while (!frontier.isEmpty()) {         // Continue evaluating vertices until frontier is exhausted
			current = frontier.poll();        // Get best new vertex from priority queue
			
			if (current.getVertex().equals(goal)) {
				return;                       // End search early once goal has been reached
			}
			
			adjacentEdges = theGraph.getEdges(current.getVertex());
			
			for (Edge<V> edge : adjacentEdges) {
				next = edge.getDestination();                   // Get next adjacent edge & cost between current & next
				newCost = costSoFar.get(current) + edge.getWeight();
				
				if (!costSoFar.containsKey(next) ||             // If cost has not been found for next vertex 
					newCost < costSoFar.get(next)) {            // Or new calculated cost is smaller
					costSoFar.put(next, newCost);               // Replace cost with newer, smaller cost
					priority = newCost + heuristic(goal, next); // Calculate priority for next vertex, add to frontier
					frontier.offer(new VertexPriority<V>(next, priority));
					cameFrom.put(next, current.getVertex());    // Record next vertex's predecessor (current vertex)
				}
			}
		}
	}
	
	private double heuristic(V goal, V start) {
		return 0.0;
		// return abs(a.x - b.x) + abs(a.y - b.y); // Manhattan heuristic
	}
}
