
package edu.miracosta.cs113.final_project.group_b;

import java.io.IOException;

import edu.miracosta.cs113.final_project.group_b.controller.MainController;
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

	/**
	 * Starts up JavaFX-based GUI using FMXLLoader. Initializes controller & view.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/miracosta/cs113/final_project/group_b/view/Main.fxml")); 
		Parent root = loader.load();                                                            // Loads FXML file from above URL
		MainController controller = loader.getController();                                     // Gets MainController from FXML
		controller.setGraph("src/edu/miracosta/cs113/final_project/group_b/view/pokemap.txt");  // Sets graph from specified text file
		controller.setStart(new Point2D(100,600));                                              // Sets default starting point
		controller.setGoal(new Point2D(1000,300));                                              // Sets default goal of path
		controller.setAlgorithm();                                                              // Initializes algorithm
		
		primaryStage.setScene(new Scene(root));
		
		primaryStage.setTitle("Pathfinding Algorithms: Animated Examples");
		primaryStage.show();
	}
}
