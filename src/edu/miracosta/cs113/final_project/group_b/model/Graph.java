package edu.miracosta.cs113.final_project.group_b.model;

import java.util.HashMap;

/**
 * Graph.java: Implementation of a weighted, non-directional graph class.
 * Vertex class type is generic. Includes methods  to add, check for, and 
 * remove edges. Maintains a map of adjacency lists (maps vertex of type V
 * to list of edges (LinkedList<Edge>)).
 * 
 * @author Marina Mizar
 */

import java.util.LinkedList;

public class Graph<V> {
     
    private HashMap<V, LinkedList<Edge<V>>> edges; // Adjacency list
    private int vertices;                          // Number of vertices
     
    /**
     * Default constructor; creates a Graph object.
     */
    public Graph() {
        edges = new HashMap<V, LinkedList<Edge<V>>>();
        vertices = 0;
    }
    
    /**
     * Adds vertex to graph by putting it in the map that links
     * vertices to edges. Increments vertex counter.
     * 
     * @param vertex
     *        Vertex object to add to graph
     */
    public void addVertex(V vertex) {
    	// Add vertex + its initialized adjacency list
    	edges.put(vertex, new LinkedList<Edge<V>>());
    	vertices++;
    }
    
    /**
     * Removes a vertex from the graph, as well as all the edges
     * connected to it.
     * 
     * @param vertex
     *        Vertex to remove from graph
     */
    public void removeVertex(V vertex) {
    	// Get vertex's adjacency list
    	LinkedList<Edge<V>> adjacencyList = edges.remove(vertex);
    	
    	// If list contains edges, remove them from graph
    	if (!adjacencyList.isEmpty()) {
    		for (Edge<V> edge : adjacencyList) {
    			removeEdge(edge.getDestination(), vertex);
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
    public boolean addEdge(V source, V destination, double weight) {
    	LinkedList<Edge<V>> sourceList = edges.get(source);
    	LinkedList<Edge<V>> destList = edges.get(destination);
    	
    	// If one or both vertices do not exist:
    	if (sourceList == null || destList == null) {
    		return false;
    	}
    	
        // Check to make sure edge doesn't already exist
        if (isEdge(source, destination)) {
        	return false;
        }
        
        // Graph is not directional, add edge to both lists
        sourceList.add(new Edge<V>(source, destination, weight));
        destList.add(new Edge<V>(destination, source, weight));
         
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
    public boolean removeEdge(V source, V destination) {
    	LinkedList<Edge<V>> sourceList = edges.get(source);
    	LinkedList<Edge<V>> destList = edges.get(destination);
    	
    	// If one or both vertices do not exist:
    	if (sourceList == null || destList == null) {
    		return false;
    	}
    	
    	// Graph is not directional, remove edge from both lists
        return sourceList.remove(new Edge<V>(source, destination)) &&
        		destList.remove(new Edge<V>(destination, source));
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
    public boolean isEdge(V source, V destination) {
    	LinkedList<Edge<V>> sourceList = edges.get(source);
    	LinkedList<Edge<V>> destList = edges.get(destination);
    	
    	// If one or both vertices do not exist:
    	if (sourceList == null || destList == null) {
    		return false;
    	}
        
    	// Check adjacency list for edge
    	return sourceList.contains(new Edge<V>(source, destination));
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
    public Edge<V> getEdge(V source, V destination) {
    	LinkedList<Edge<V>> sourceList = edges.get(source);
    	Edge<V> target = new Edge<V>(source, destination);
    	for (Edge<V> edge : sourceList) {
    		if (edge.equals(target)) {
    			return edge;
    		}
    	}
    	return null;
    }
    
    /**
     * Accesses the adjacency list for a single vertex.
     * 
     * @param vertex
     *        Vertex to evaluate for all adjacent vertices
     * @return A LinkedList of vertices adjacent to target
     */
    public LinkedList<Edge<V>> getEdges(V vertex) {
    	return edges.get(vertex.hashCode());
    }
}
