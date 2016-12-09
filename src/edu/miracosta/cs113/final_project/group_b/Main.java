
package edu.miracosta.cs113.final_project.group_b;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
		controller.Astar = new ASTAR;
				new Grap;
				contro;;er. graph = grap;
		Parent root = loader.load();
		
		primaryStage.setScene(new Scene(root));
		
		primaryStage.setTitle("TODO Make a title of some sort");
		primaryStage.show();
	}

}
