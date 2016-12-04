package edu.miracosta.cs113.final_project.group_b.model;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Graph.java: Implementation of a weighted, non-directional graph class 
 * with inner Edge class. Vertex class type is generic. Includes methods 
 * to add, check for, and remove edges. Maintains a map of adjacency lists
 * (maps vertex of type E to list of edges (LinkedList<Edge>)).
 * 
 * @author Marina Mizar
 */

import java.util.LinkedList;

public class Graph<E> {
	
	/**
	 * Stores information about the edges that link vertices, including
	 * source vertex, destination, and weight. Private inner class.
	 */
	private class Edge {
        
        private E source;
        private E destination;
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
        public Edge(E source, E destination) {
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
        public Edge(E source, E destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        
        /**
         * Accessor for source vertex.
         * 
         * @return Value representing a vertex
         */
        public E getSource() {
            return source;
        }
         
        /**
         * Accessor for destination vertex.
         * 
         * @return Integer value representing a vertex
         */
        public E getDestination() {
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
     
    private HashMap<E, LinkedList<Edge>> edges; // Adjacency list
    private int vertices;                             // Number of vertices
     
    /**
     * Default constructor; creates a Graph object.
     */
    public Graph() {
        edges = new HashMap<E, LinkedList<Edge>>();
        vertices = 0;
    }
    
    /**
     * Adds vertex to graph by putting it in the map that links
     * vertices to edges. Increments vertex counter.
     * 
     * @param vertex
     *        Vertex object to add to graph
     */
    public void addVertex(E vertex) {
    	// Add vertex + its initialized adjacency list
    	edges.put(vertex, new LinkedList<Edge>());
    	vertices++;
    }
    
    /**
     * Removes a vertex from the graph, as well as all the edges
     * connected to it.
     * 
     * @param vertex
     *        Vertex to remove from graph
     */
    public void removeVertex(E vertex) {
    	// Get vertex's adjacency list
    	LinkedList<Edge> adjacencyList = edges.remove(vertex);
    	
    	// If list contains edges, remove them from graph
    	if (!adjacencyList.isEmpty()) {
    		for (Edge edge : adjacencyList) {
    			removeEdge(edge.destination, vertex);
    		}
    	}
    	
    	// Then remove vertex from map
    	edges.remove(vertex);
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
     * @return True if insertion is successful (edge doesn't already
     *         exist in Graph)
     */
    public boolean addEdge(E source, E destination, double weight) {
    	LinkedList<Edge> sourceList = edges.get(source.hashCode());
    	LinkedList<Edge> destList = edges.get(source.hashCode());
    	
    	// If one or both vertices do not exist:
    	if (sourceList == null || destList == null) {
    		return false;
    	}
    	
        // Check to make sure edge doesn't already exist
        if (!isEdge(source, destination)) {
        	return false;
        }
        
        // Graph is not directional, add edge to both lists
        sourceList.add(new Edge(source, destination, weight));
        destList.add(new Edge(destination, source, weight));
         
        return true;
    }
     
    /**
     * Removes the edge linking the specified vertices.
     * 
     * @param source
     * 		  Start vertex
     * @param destination
     * 		  End vertex
     * @return True if edge existed in Graph and was successfully removed
     */
    public boolean removeEdge(E source, E destination) {
    	LinkedList<Edge> sourceList = edges.get(source.hashCode());
    	LinkedList<Edge> destList = edges.get(source.hashCode());
    	
    	// If one or both vertices do not exist:
    	if (sourceList == null || destList == null) {
    		return false;
    	}
    	
    	// Graph is not directional, remove edge from both lists
        return sourceList.remove(new Edge(source, destination)) &&
        		destList.remove(new Edge(destination, source));
    }
    
    /**
     * Initializes LinkedList for each vertex if it does not
     * already exist, otherwise checks whether LinkedList at source
     * contains the specified edge.
     * 
     * @param source
     * 		  Start vertex
     * @param destination
     * 		  End vertex
     * @return True if vertices each have a LinkedList of edges and
     * 	       source list contains Edge
     */
    public boolean isEdge(E source, E destination) {
    	LinkedList<Edge> sourceList = edges.get(source.hashCode());
    	LinkedList<Edge> destList = edges.get(source.hashCode());
    	
    	// If one or both vertices do not exist:
    	if (sourceList == null || destList == null) {
    		return false;
    	}
        
    	// Check adjacency list for edge
    	return sourceList.contains(new Edge(source, destination));
    }
    
    /**
     * Searches for an edge with specified source and destination, returns
     * matching Edge object, which includes weight data.
     * 
     * @param source
     * 		  Start vertex
     * @param destination
     * 		  Destination vertex
     * @return The edge linked the specified vertices, or null if not found
     */
    public Edge getEdge(E source, E destination) {
    	LinkedList<Edge> sourceList = edges.get(source.hashCode());
    	Edge target = new Edge(source, destination);
    	for (Edge edge : sourceList) {
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
    public Iterator<Edge> edgeIterator(E vertex) {
    	LinkedList<Edge> edgeList = edges.get(vertex.hashCode());
    	return edgeList.iterator();
    }
}
