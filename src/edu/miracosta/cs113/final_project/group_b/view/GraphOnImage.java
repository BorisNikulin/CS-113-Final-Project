
package edu.miracosta.cs113.final_project.group_b.view;

import java.io.IOException;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * @author Boris
 *
 */
public class GraphOnImage extends StackPane
{
	@FXML private Pane graphPane;
	@FXML private ResizeToParentImageView imageView;

	private SetProperty<Object>	selectedVertices = new SimpleSetProperty<>(this, "selectedVertices");
	private DoubleProperty vertexRadius	= new SimpleDoubleProperty(this, "vertexSize", 10);
	private DoubleProperty edgeDodgeDistance = new SimpleDoubleProperty(this, "edgeDodgeDistance", 20 * 1d / 4);

	private MapProperty<Circle, Object> circleToVertex = new SimpleMapProperty<>(this, "circleToVertex");
	private MapProperty<Object, Circle> vertexToCircle = new SimpleMapProperty<>(this, "vertexToCircle");
	private ObjectProperty<Bounds> initBounds = new SimpleObjectProperty<Bounds>(this, "initBounds");
	private DoubleProperty widthRatio = new SimpleDoubleProperty(this, "widthRatio");
	private DoubleProperty heightRatio = new SimpleDoubleProperty(this, "heightRatio");

	/**
	 * Creates class object & connects to FXML loader.
	 */
	public GraphOnImage()
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

	/**
	 * Initializes view by drawing default background images and defining binding for
	 * images on screen (to keep images anchored properly when window is resized).
	 */
	@FXML
	void initialize()
	{
		imageView.imageProperty().addListener(e -> initBounds.set(imageView.getBoundsInLocal()));

		imageView.setImage(new Image("file:/C:/Users/W7104673/Desktop/pokemonMap.jpg"));

		DoubleBinding widthRatioBinding = new DoubleBinding()
		{
			{
				super.bind(initBounds, imageView.boundsInLocalProperty());
			}

			@Override
			protected double computeValue()
			{
				return imageView.getBoundsInLocal().getWidth() / initBounds.get().getWidth();
			}
		};

		DoubleBinding heightRatioBinding = new DoubleBinding()
		{
			{
				super.bind(initBounds, imageView.boundsInLocalProperty());
			}

			@Override
			protected double computeValue()
			{
				return imageView.getBoundsInLocal().getHeight() / initBounds.get().getHeight();
			}
		};

		widthRatio.bind(widthRatioBinding);
		heightRatio.bind(heightRatioBinding);

		DoubleBinding endX = new DoubleBinding()
		{
			{
				super.bind(imageView.boundsInLocalProperty(), widthRatio);
			}

			@Override
			protected double computeValue()
			{
				return 200 * widthRatio.doubleValue();
			}
		};

		DoubleBinding endY = new DoubleBinding()
		{
			{
				super.bind(imageView.boundsInLocalProperty(), heightRatio);
			}

			@Override
			protected double computeValue()
			{
				return 200 * heightRatio.doubleValue();
			}
		};
	}
	
	/**
	 * Adds a circle representation of a vertex to the screen.
	 * 
	 * @param vertex
	 *        Vertex on map, represented in view by a colored circle
	 * @param color
	 *        Color chosen for circle drawing
	 * @return True if vertex was added to view successfully
	 */
	public boolean addVertex(Point2D vertex, Color color)
	{
		if (!vertexToCircle.containsKey(vertex))
		{
			Circle vert = new Circle(0, 0, 0, color);
			vert.radiusProperty().bind(vertexRadius);
			vert.centerXProperty().bind(createHorizontalFixation(vertex.getX()));
			vert.centerYProperty().bind(createVerticalFixation(vertex.getY()));
			graphPane.getChildren().add(vert);
			return true;
		}
		return false;
	}

	/**
	 * Adds a line representation of an edge to the screen.
	 * 
	 * @param src
	 *        Source point for edge
	 * @param dest
	 *        Destination point for edge
	 * @param color
	 *        Color of line
	 * @return True if edge was added successfully
	 */
	public boolean addEdge(Point2D src, Point2D dest, Color color)
	{	
		Line lineTo = new Line();
		
		lineTo.startXProperty().bind(createHorizontalFixation(src.getX()));
		lineTo.startYProperty().bind(createVerticalFixation(src.getY()));
		lineTo.endXProperty().bind(createHorizontalFixation(dest.getX()));
		lineTo.endYProperty().bind(createVerticalFixation(dest.getY()));
		
		lineTo.setStrokeWidth(5);
		lineTo.setStroke(color);
		
		return graphPane.getChildren().add(lineTo);		
	}
	
	//////////////////////////////////////////////////////////////////////////
	//               SETTERS FOR BINDINGS & PROPERTIES                      //
	//////////////////////////////////////////////////////////////////////////

	private DoubleBinding createHorizontalFixation(final double x)
	{
		return new DoubleBinding()
		{
			{
				super.bind(imageView.boundsInLocalProperty(), widthRatio);
			}

			@Override
			protected double computeValue()
			{
				return x * widthRatio.doubleValue();
			}
		};
	}

	private DoubleBinding createVerticalFixation(final double y)
	{
		return new DoubleBinding()
		{
			{
				super.bind(imageView.boundsInLocalProperty(), heightRatio);
			}

			@Override
			protected double computeValue()
			{
				return y * heightRatio.doubleValue();
			}
		};
	}

	public ObjectProperty<Image> imageProperty()
	{
		return imageView.imageProperty();
	}

	public final Image getImage()
	{
		return imageView.imageProperty().get();
	}

	public final void setImage(Image image)
	{
		imageView.imageProperty().set(image);
	}

	public SetProperty<Object> selectedVerticesProperty()
	{
		return selectedVertices;
	}

	public final ObservableSet<Object> getSelectedVertices()
	{
		return selectedVertices.get();
	}

	public DoubleProperty vertexRadiusProperty()
	{
		return vertexRadius;
	}

	public final double getVertexRadius()
	{
		return vertexRadius.doubleValue();
	}

	public final void setVertexRadius(double radius)
	{
		vertexRadius.set(radius);
	}
	
	public DoubleProperty edgeDodgeDistanceProperty()
	{
		return edgeDodgeDistance;
	}

	public final double getEdgeDodgeDistance()
	{
		return edgeDodgeDistance.doubleValue();
	}

	public final void setEdgeDodgeDistance(double radius)
	{
		edgeDodgeDistance.set(radius);
	}
}
