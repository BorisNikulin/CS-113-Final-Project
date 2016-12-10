
package edu.miracosta.cs113.final_project.group_b;

import java.io.IOException;

import edu.miracosta.cs113.final_project.group_b.controller.MainController;
import edu.miracosta.cs113.final_project.group_b.model.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Main.fxml"));
		MainController controller = loader.getController();
		// Graph<Point2D> graph = new Graph<Point2D>();
		// controller.setAStar(new AStar<V>());
		// controller.setGraph(new Graph<V>());
		Parent root = loader.load();
		
		primaryStage.setScene(new Scene(root));
		
		primaryStage.setTitle("TODO Make a title of some sort");
		primaryStage.show();
	}

}
