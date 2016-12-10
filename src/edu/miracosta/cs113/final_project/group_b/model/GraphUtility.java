package edu.miracosta.cs113.final_project.group_b.model;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * GraphUtility.java: Constructs graphs from text file data that includes a
 * list of coordinates paired with vertex IDs, and a list of edges (pairs of
 * vertex IDs) with their respective weights.
 * 
 * @author Marina Mizar
 */
public class GraphUtility<V> {
	
	private Scanner input;
	private HashMap<String, V> vertices;  // Pairs Vertex objects with ID given in file
	private Graph<V> graphFromFile;       // Graph constructed from file data
	private BiFunction<Integer, Integer, V> setVertex;
	
	/**
	 * Constructor for file utility class; initializes data structures.
	 * 
	 * @param fileName
	 *        Path to text file containing coordinate/edge weight data
	 * @throws FileNotFoundException
	 *          If file name is invalid
	 */
	public GraphUtility(String fileName, BiFunction<Integer, Integer, V> setVertex)
			                                         throws FileNotFoundException {
		this.setVertex = setVertex;
		graphFromFile = new Graph<V>();
		input = new Scanner(new File(fileName));
		vertices = new HashMap<String, V>();
	}
	
	/**
	 * Creates a graph by parsing vertex and edge data and inserting it
	 * into a Graph<V> object.
	 *
	 * @return A Graph<V> object with all pertinent data      
	 */
	public Graph<V> getGraph() {		
		parseVertices();
		parseWeights();
		input.close();
		return graphFromFile;
	}

	/**
	 * Private helper method that reads in vertex data, gets vertex ID and
	 * coordinates, and saves info to a HashMap for reference for Edge
	 * parsing/creation later. Adds vertices to Graph structure as well.
	 * 
	 * @throws InputMismatchException
	 *         If data is in wrong format & coordinates cannot be parsed
	 */
	private void parseVertices() throws InputMismatchException {
		String vertexID, rawData;
		String[] part, coordinate;
		V vertex;
		while (input.hasNextLine()) {
			rawData = input.nextLine();           // Gets line like: "A: 2,3"
			if (rawData.isEmpty()) {              // End method when blank line is reached
				return;
			}
			part = rawData.split(" ");            // Separate vertex ID & coordinates
			vertexID = part[0].replace(":", "");  // Remove excess character
			coordinate = part[1].split(",");      // Get x & y coordinate
			                                      // Given BiFunction sets takes coordinates & returns set object
			vertex = setVertex.apply(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1])); 
			vertices.put(vertexID, (V) vertex);   // Map ID to vertex
			graphFromFile.addVertex((V) vertex);  // Insert vertex into graph
		}
	}
	
	/**
	 * Private helper method that reads in edge data and adds to Graph.
	 * 
	 * @throws InputMismatchException
	 *         If data is in wrong format & double cannot be parsed
	 */
	private void parseWeights() throws InputMismatchException {
		double weight;
		String rawData, source, dest;
		String[] part, node;
		
		while (input.hasNextLine()) {              // Continue until end of file
			rawData = input.nextLine();            // Gets line like "A-B: 9"
			if (rawData.isEmpty()) {
				return;
			}
			part = rawData.split(" ");             // Separate vertices from edge weight
			node = part[0].split("-");             // Split source from destination vertex
			source = node[0];
			dest = node[1].replace(":", "");       // Remove excess character
			weight = Double.parseDouble(part[1]);  // Convert weight String into double
			graphFromFile.addEdge(vertices.get(source), vertices.get(dest), weight); // Add edge to graph
		}
	}
}
