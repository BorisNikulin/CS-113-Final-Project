
package edu.miracosta.cs113.final_project.group_b.model.test;

import static org.junit.Assert.*;
import java.awt.Point;
import org.junit.Before;
import org.junit.Test;

import edu.miracosta.cs113.final_project.group_b.model.AStar;
import edu.miracosta.cs113.final_project.group_b.model.Graph;
import edu.miracosta.cs113.final_project.group_b.model.GraphUtility;

public class GraphTest
{
	Graph<Point> testGraph;
	GraphUtility<Point> graphReader;
	AStar<Point> algorithm;

	/**
	 * Initializes helper object with text file containing graph info.
	 * 
	 * @throws Exception
	 * 		   If file does not exist
	 */
	@Before
	public void setUp() throws Exception
	{
		graphReader = new GraphUtility<Point>("src\\edu\\miracosta\\cs113\\final_project\\group_b\\model\\test\\test.txt");
	}
	
	/**
	 * Tests Graph, Edge, and GraphUtility classes by creating a graph from file,
	 * checking for specified edge & weight, and testing remove method.
	 */
	@Test
	public void readGraphTest() {
		Point a, b;
		a = new Point(1, 1);
		b = new Point(2, 2);
		testGraph = graphReader.getGraph();
		assertTrue(testGraph.isEdge(a, b));
		assertEquals((Double)3.0, (Double)testGraph.getEdge(a, b).getWeight());
		assertTrue(testGraph.removeEdge(a, b));
	}
	
	/**
	 * Tries out AStar method on random graph to ensure it properly creates a path.
	 * Prints path to console.
	 * @throws Exception
	 *         If file with test graph data is not found
	 */
	@Test
	public void aStarTest() throws Exception {
		Point start = new Point(1, 1);
		Point end = new Point(99, 99);
		graphReader = new GraphUtility<Point>("src\\edu\\miracosta\\cs113\\final_project\\group_b\\model\\test\\astartest.txt");
		testGraph = graphReader.getGraph();
		algorithm = new AStar<Point>(testGraph, start);
		algorithm.getPathTo(end);
		algorithm.printPath(end);
	}
}
