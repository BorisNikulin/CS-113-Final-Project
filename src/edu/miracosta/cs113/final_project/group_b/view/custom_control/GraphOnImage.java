
package edu.miracosta.cs113.final_project.group_b.view.custom_control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

// TODO make this class genericy with allowing graphs that do not use Point2D but force the addition of a function that when applied on the vertex gives back a Point2D
public class GraphOnImage extends StackPane
{
	@FXML private Group		graphGroup;
	@FXML private ImageView	imageView;

	public GraphOnImage ()
	{
		super();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("GraphOnImage.fxml"));
		loader.setController(this);
		loader.setRoot(this);

		try
		{
			loader.load();
		}
		catch (IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}

	@FXML
	void initialize()
	{
		
		Circle circle = new Circle(10, 10, 5);
		graphGroup.getChildren().add(circle);
	}
}
