package edu.miracosta.cs113.final_project.group_b.model.test;

import java.util.Iterator;

/**
 * Graph.java: Implementation of a weighted, non-directional graph class
 * with inner Edge class. Includes methods to add, check for, and remove
 * edges. Maintains an adjacency list (Array of LinkedLists of Edges).
 * 
 * @author Marina Mizar
 */

import java.util.LinkedList;

public class Graph {
	
	/**
	 * Stores information about the edges that link vertices, including
	 * source vertex, destination, and weight. Private inner class.
	 */
	private class Edge {
        
        private int source;
        private int destination;
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
        public Edge(int source, int destination) {
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
        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        
        /**
         * Accessor for source vertex.
         * 
         * @return Integer value representing a vertex
         */
        public int getSource() {
            return source;
        }
         
        /**
         * Accessor for destination vertex.
         * 
         * @return Integer value representing a vertex
         */
        public int getDestination() {
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
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            Edge other = (Edge) o;
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
     
    private int vertices;             // Number of vertices
    private LinkedList<Edge>[] edges; // Adjacency list
     
    /**
     * Creates a Graph with default minimum number of vertices (1).
     */
    @SuppressWarnings("unchecked")
	public Graph() {
        vertices = 1;
        edges = (LinkedList<Edge>[]) new LinkedList[vertices];
    }
     
    /**
     * Creates a Graph with specified number of vertices (does not
     * allow zero or negative number).
     * 
     * @param vertices
     * 		  Number of nodes within data structure
     */
    @SuppressWarnings("unchecked")
	public Graph(int vertices) {
        if (vertices > 0) {
            this.vertices = vertices;
        }
        else {
            vertices = 1;
        }
        edges = (LinkedList<Edge>[]) new LinkedList[vertices];
    }
     
    /**
     * Accessor for number of vertices.
     * 
     * @return Integer representing number of vertices (with indices
     *         from 0 to number - 1) contained within Graph
     */
    public int getVertices() {
        return vertices;
    }
     
    /**
     * Inserts an edge linking two vertices by adding new Edge object
     * to two LinkedLists, one for source and one for destination.
     * 
     * @param source
     * 		  Beginning vertex
     * @param destination
     * 		  Ending vertex
     * @param weight
     * 		  Value of edge
     * @return True if insertion is successful (edge has valid source &
     *         destination and doesn't already exist in Graph)
     */
    public boolean addEdge(int source, int destination, double weight) {
        // Check to make sure vertices are valid for graph
        if (invalidVertex(source) | invalidVertex(destination)) {
            return false;
        }
         
        // Check to make sure edge doesn't already exist
        if (!isEdge(source, destination)) {
        	return false;
        }
         
        // Graph is not directional, add edge to both lists
        edges[source].add(new Edge(source, destination, weight));
        edges[source].add(new Edge(destination, source, weight));
         
        return true;
    }
     
    /**
     * Removes the edge linking the specified vertices.
     * 
     * @param source
     * 		  Start vertex
     * @param destination
     * 		  End vertex
     * @return True if edge is valid, existed in Graph, and was
     *         successfully removed
     */
    public boolean removeEdge(int source, int destination) {
    	// Check to make sure vertices are valid for graph
        if (invalidVertex(source) | invalidVertex(destination)) {
            return false;
        }
        
        // Graph is not directional, remove edge from both lists
        return edges[source].remove(new Edge(source, destination)) &&
        		edges[destination].remove(new Edge(destination, source));
    }
    
    /**
     * Initializes LinkedList for each vertex if it does not
     * already exist, otherwise checks whether LinkedList at source
     * index contains the specified edge.
     * 
     * @param source
     * 		  Start vertex
     * @param destination
     * 		  End vertex
     * @return True if vertices each have a LinkedList of edges and
     * 	       source list contains Edge
     */
    public boolean isEdge(int source, int destination) {
    	boolean listsExist = true;
    	// Need to initialize list of edges for node if not already done
        if (edges[source] == null) {
            edges[source] = new LinkedList<Edge>();
            listsExist = false;
        }
        if (edges[destination] == null) {
            edges[destination] = new LinkedList<Edge>();
            listsExist = false;
        }
        
        if (listsExist) {
        	return edges[source].contains(new Edge(source, destination));
        }
        
        // One of the lists for a vertex was not initialized, edge cannot exist
    	return false; 
    }
    
    /**
     * Searches for an edge with specified source and destination, returns
     * matching Edge object, which includes weight data.
     * 
     * @param source
     * 		  Number of start vertex
     * @param destination
     * 		  Number of destination vertex
     * @return The edge linked the specified vertices, or null if not found
     */
    public Edge getEdge(int source, int destination) {
    	Edge target = new Edge(source, destination);
    	for (Edge edge : edges[source]) {
    		if (edge.equals(target)) {
    			return edge;
    		}
    	}
    	return null;
    }
    
    /**
     * Creates an iterator for a vertex's LinkedList of edges.
     * 
     * @param vertex
     * 	      Index of vertex over whose edges to iterate
     * @return An Iterator<Edge> object
     */
    public Iterator<Edge> edgeIterator(int vertex) {
    	return edges[vertex].iterator();
    }
     
    /**
     * Ensures vertex index given is in the range of zero to one less
     * than number of vertices.
     * 
     * @param vertex
     * 		  Integer representing index of vertex
     * @return True if vertex is invalid, false if OK
     */
    private boolean invalidVertex(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            return true;
        }
        return false;
    }
}
