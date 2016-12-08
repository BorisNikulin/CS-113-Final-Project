package edu.miracosta.cs113.final_project.group_b.model;

/**
 * Stores information about the edges that link vertices, including
 * source vertex, destination, and weight.
 */
public class Edge<V> {
    
    private V source;
    private V destination;
    private double weight;
    
    /**
     * Constructor used to make objects to test for existence of edge
     * and to remove edges (no weight specified).
     * 
     * @param source
     * 		  First connected vertex
     * @param destination
     * 		  Second connected vertex
     */
    public Edge(V source, V destination) {
        this.source = source;
        this.destination = destination;
        this.weight = 1.0;
    }
    
    /** 
     * Constructor used to insert edges (weight specified).
     * 
     * @param source
     * 		  First vertex
     * @param destination
     * 		  Second vertex
     * @param weight
     * 		  Comparative value of edge
     */
    public Edge(V source, V destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    /**
     * Accessor for source vertex.
     * 
     * @return Value representing a vertex
     */
    public V getSource() {
        return source;
    }
     
    /**
     * Accessor for destination vertex.
     * 
     * @return Integer value representing a vertex
     */
    public V getDestination() {
        return destination;
    }
     
    /**
     * Accessor for edge weight.
     * 
     * @return Value of edge
     */
    public double getWeight() {
        return weight;
    }
     
    /**
     * Mutator for edge weight.
     * 
     * @param weight
     * 		  New value for edge's weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
     
    /**
     * Compares Edge with another object (only source and
     * destination fields). Used to remove and search for
     * Edges within adjacency list.
     * 
     * @param o
     * 		  Other object of comparison
     * @return True if other object is an Edge with same
     *         source and destination values.
     */
    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Edge<V> other = (Edge<V>) o;
        if (this.source == other.source &&
            this.destination == other.destination) {
            return true;
        }
        return false;
    }
     
    /**
     * Creates a String representation of the Edge object,
     * summarizing its two vertices and weight data.
     * 
     * @return A String stating source, destination & weight
     */
    @Override
    public String toString() {
        return source + " to " + destination + " = " + weight;
    }
}