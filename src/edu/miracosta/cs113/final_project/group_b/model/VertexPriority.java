package edu.miracosta.cs113.final_project.group_b.model;

/**
 * VertexPriority.java: Pairs a vertex with its priority value for the
 * ASTar frontier PriorityQueue used to choose the next best vertex.
 * 
 * @author Marina Mizar
 */
public class VertexPriority<V> implements Comparable<VertexPriority<V>> {
	
	private V vertex;
	private double priority;
	
	/**
	 * Full constructor.
	 * 
	 * @param vertex
	 *        Vertex in graph
	 * @param priority
	 *        Value representing likelihood vertex will be a good match for 
	 *        shortest path to goal (lower value indicates better match);
	 *        determined by heuristic
	 */
	public VertexPriority(V vertex, double priority) {
		this.vertex = vertex;
		this.priority = priority;
	}
	
	/**
	 * Accessor for vertex.
	 * 
	 * @return Vertex object
	 */
	public V getVertex() {
		return vertex;
	}
	
	/**
	 * Accessor for priority value.
	 * 
	 * @return Double representing priority of vertex in frontier
	 */
	public double getPriority() {
		return priority;
	}

	/**
	 * Method required for implementation of Comparable interface; allows
	 * object to be properly placed in PriorityQueue.
	 * 
	 * @param other
	 *        Other vertex that current vertex is being compared to
	 * @return -1 if this vertex is smaller (higher priority), 0 if
	 *         priorities are equal, 1 if other vertex is higher priority
	 */
	@Override
	public int compareTo(VertexPriority<V> other) {
		if (this.priority > other.priority) {
			return 1;
		}
		else if (this.priority < other.priority) {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Creates a String representation of the VertexPriority object.
	 * 
	 * @return A String containing vertex and priority data
	 */
	@Override
	public String toString() {
		return "Vertex (" + vertex + ") has priority " + priority + ".";
	}

	/**
	 * Determines if this VertexPriority is equal to another.
	 * 
	 * @param other
	 * 		  Other object for comparison
	 * @return True if other object is VertexPriority with same data
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!other.getClass().equals(this.getClass())) {
			return false;
		}
		VertexPriority<V> vp = (VertexPriority<V>) other;
		if (vp.vertex.equals(this.vertex) && vp.priority == this.priority) {
			return true;
		}
		return false;
	}
}	