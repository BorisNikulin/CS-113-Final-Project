package edu.miracosta.cs113.final_project.group_b.model;

import java.util.Stack;

/**
 * Algorithm.java: A general interface for algorithms used in the
 * animated path-finding application.
 * 
 * @author Marina Mizar
 */
public interface Algorithm<V> {
	
	// Performs one step of algorithm
	public V step();
	
	// Performs entire algorithm, finding path
	public void getPathTo(V goal);
	
	// Prints path found by previous method
	public void printPath();
	
	// Returns a stack with vertices in path order
	public Stack<V> pathStack();
}
