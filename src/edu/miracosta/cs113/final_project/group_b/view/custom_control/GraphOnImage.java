
package edu.miracosta.cs113.final_project.group_b.view.custom_control;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;

// TODO make this class genericy with allowing graphs that do not use Point2D but force the addition of a function that when applied on the vertex gives back a Point2D
public class GraphOnImage extends Control
{
	@FXML private Group graphGroup;
	@FXML private ImageView imageView;
	
	public GraphOnImage ()
	{
		super();
		
	}
}
