
package edu.miracosta.cs113.final_project.group_b.view.custom_control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// TODO make this class genericy with allowing graphs that do not use Point2D but force the addition of a function that when applied on the vertex gives back a Point2D
public class GraphOnImage extends StackPane
{
	@FXML private Group						graphGroup;
	@FXML private ResizeToParentImageView	imageView;

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
//		// EasyBind.when(EasyBind.map(sceneProperty(), sp -> sp !=
//		// null)).bind(graphGroup.he, this.heightProperty());
//		System.out.println(imageView);	
		imageView.setImage(new Image("file:/D:/Documents/Script%20Files/Java/CS%20113/final_project/src/edu/miracosta/cs113/final_project/group_b/view/custom_control/UTm4Sg.jpg"));
//		System.out.println(imageView);
//		System.out.println(imageView.getImage().getProgress());
//
		for (int i = 1; i <= 200; i += 10)
		{
			Circle circle1 = new Circle(i, i, 5);
			Circle circle2 = new Circle(100 - i, i, 5);
			graphGroup.getChildren().add(circle1);
			graphGroup.getChildren().add(circle2);
		}
		
		Circle circleStart = new Circle(0, 0, 5, Color.GREEN);
		Circle circleEnd = new Circle(200, 200, 5, Color.RED);
		graphGroup.getChildren().add(circleStart);
		graphGroup.getChildren().add(circleEnd);
	}
}
