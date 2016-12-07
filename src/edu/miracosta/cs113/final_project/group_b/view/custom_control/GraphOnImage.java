
package edu.miracosta.cs113.final_project.group_b.view.custom_control;

import java.io.IOException;
import java.util.Collection;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

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

// TODO make this class genericy with allowing graphs that do not use Point2D but force the addition of a function that when applied on the vertex gives back a Point2D

/**
 * @author Boris
 *
 */
public class GraphOnImage extends StackPane
{
	@FXML private Pane						graphPane;
	@FXML private ResizeToParentImageView	imageView;

	private ObjectProperty<Object>			mousedOverVertex	= new SimpleObjectProperty<Object>(
			this,
			"mousedOverVertex",
			null);
	private SetProperty<Object>				selectedVertices	= new SimpleSetProperty<>(this, "selectedVertices");
	// TODO use css api to make vertex size stylable through css
	private DoubleProperty					vertexRadius		= new SimpleDoubleProperty(this, "vertexSize", 20);
	private DoubleProperty edgeDodgeDistance = new SimpleDoubleProperty(this, "edgeDodgeDistance", 20 * 1d / 4);

	private MapProperty<Circle, Object>		circleToVertex		= new SimpleMapProperty<>(this, "circleToVertex");
	private MapProperty<Object, Circle>		vertexToCircle		= new SimpleMapProperty<>(this, "vertexToCircle");
	private ObjectProperty<Bounds>			initBounds			= new SimpleObjectProperty<Bounds>(this, "initBounds");
	private DoubleProperty					widthRatio			= new SimpleDoubleProperty(this, "widthRatio");
	private DoubleProperty					heightRatio			= new SimpleDoubleProperty(this, "heightRatio");

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
		imageView.imageProperty().addListener(e -> initBounds.set(imageView.getBoundsInLocal()));

		imageView.setImage(new Image(
				"file:/D:/Documents/Script%20Files/Java/CS%20113/final_project/src/edu/miracosta/cs113/final_project/group_b/view/custom_control/UTm4Sg.jpg"));

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

		for (int i = 1; i <= initBounds.get().getHeight(); i += 1)
		{
			final double x1 = i;
			final double y1 = i;
			final double x2 = initBounds.get().getWidth() - i;
			Circle circle1 = new Circle(i, i, 5, Color.YELLOW);
			Circle circle2 = new Circle(500 - i, i, 5, Color.CYAN);

			DoubleBinding circleBind1x = createHorizontalFixation(x1);
			DoubleBinding circleBind1y = createVerticalFixation(y1);
			DoubleBinding circleBind2x = createHorizontalFixation(x2);

			circle1.centerXProperty().bind(circleBind1x);
			circle1.centerYProperty().bind(circleBind1y);
			circle2.centerXProperty().bind(circleBind2x);
			circle2.centerYProperty().bind(circleBind1y);

			graphPane.getChildren().add(circle1);
			graphPane.getChildren().add(circle2);
		}

		System.out.println(initBounds);

		Circle circleStart = new Circle(0, 0, 5, Color.GREEN);
		Circle circleEnd = new Circle(200, 200, 5, Color.RED);

		// DoubleBinding dbx = Bindings.multiply(200,
		// EasyBind.map(imageView.boundsInLocalProperty(), Bounds::getWidth));
		// Bindings.divide(EasyBind.map(imageView.boundsInLocalProperty(),
		// Bounds::getWidth), initBounds.getWidth());
		// EasyBind.map(imageView.boundsInLocalProperty(), Bounds::getWidth);

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

		System.out.println(circleEnd);
		// imageView.boundsInLocalProperty().addListener((e) -> {
		// circleEnd.setCenterX(200 * imageView.getBoundsInLocal().getWidth() /
		// initBounds.getWidth());
		// circleEnd.setCenterY(200 * imageView.getBoundsInLocal().getHeight() /
		// initBounds.getHeight());
		// });
		circleEnd.centerXProperty().bind(endX);
		circleEnd.centerYProperty().bind(endY);
		System.out.println(circleEnd);
		
		graphPane.getChildren().add(new Circle(100, 100, 20, Color.RED));
		graphPane.getChildren().add(new Circle(200, 200, 20, Color.RED));
		addEdge(new Point2D(100, 100), new Point2D(200, 200), 1d, 1d, v -> (Point2D) v);
		
		graphPane.getChildren().add(circleStart);
		graphPane.getChildren().add(circleEnd);
	}

	public boolean addVertex(Object vertex, Collection<Object> adjacent, Function<Object, Point2D> vertToCenter)
	{
		boolean didAddVert = false;
		boolean didAddAdjacent = false;
		didAddVert = addVertex(vertex, vertToCenter);

		for (Object vert : adjacent)
		{
			didAddAdjacent = addVertex(vert, vertToCenter);
			if (addVertex(vert, vertToCenter) && didAddVert)
			{

			}
		}

		return didAddVert;
	}

	public boolean addVertex(Object vertex, Function<Object, Point2D> vertToCenter)
	{
		if (!vertexToCircle.containsKey(vertex))
		{
			Point2D vertCenter = vertToCenter.apply(vertex);
			Circle vert = new Circle();
			vert.radiusProperty().bind(vertexRadius);
			vert.centerXProperty().bind(createHorizontalFixation(vertCenter.getX()));
			vert.centerYProperty().bind(createVerticalFixation(vertCenter.getY()));
			return true;
		}
		return false;
	}

	public boolean addEdge(Object src, Object dest, Double weightToDest, Double weightToSrc, Function<Object, Point2D> vertToCenter)
	{
		// based on https://www.mathworks.com/matlabcentral/answers/120278-how-to-plot-a-line-parallel-to-a-line-with-a-distance-of-d-between-them#answer_127209
		// answer is based on a Cartesian coordinate system not for screen coordinates (why there are -1's everywhere :D)
		boolean didAddEdge = false;
		Point2D v1 = vertToCenter.apply(src);
		Point2D v2 = vertToCenter.apply(dest);
		
		double slope = -1 * (v2.getY() - v1.getY()) / (v2.getX() - v1.getX());
		double lineOffset = edgeDodgeDistance.doubleValue() * Math.sqrt(1 + slope * slope);
		
		DoubleUnaryOperator srcToDestFunc = x -> slope * (x - v1.getX()) - v1.getY();
		DoubleUnaryOperator lineToFunc = srcToDestFunc.andThen(x -> -1 * (x + lineOffset));
		DoubleUnaryOperator lineFromFunc = srcToDestFunc.andThen(x -> -1 * (x - lineOffset));
		
		//Line lineTo = new Line(v1.getX(), lineToFunc.applyAsDouble(v1.getX()), v2.getX(), lineToFunc.applyAsDouble(v2.getX()));
		//Line lineFrom = new Line(v1.getX(), lineFromFunc.applyAsDouble(v1.getX()), v2.getX(), lineFromFunc.applyAsDouble(v2.getX()));
		
		Line lineTo = new Line();
		Line lineFrom = new Line();
		
		lineTo.startXProperty().bind(createHorizontalFixation(v1.getX()));
		lineTo.startYProperty().bind(createVerticalFixation(lineToFunc.applyAsDouble(v1.getX())));
		lineTo.endXProperty().bind(createHorizontalFixation(v2.getX()));
		lineTo.endYProperty().bind(createVerticalFixation(lineToFunc.applyAsDouble(v2.getX())));
		
		lineFrom.startXProperty().bind(createHorizontalFixation(v1.getX()));
		lineFrom.startYProperty().bind(createVerticalFixation(lineFromFunc.applyAsDouble(v1.getX())));
		lineFrom.endXProperty().bind(createHorizontalFixation(v2.getX()));
		lineFrom.endYProperty().bind(createVerticalFixation(lineFromFunc.applyAsDouble(v2.getX())));
		
		lineTo.setStrokeWidth(5);
		lineFrom.setStrokeWidth(5);
		lineTo.setStroke(Color.PURPLE);
		lineFrom.setStroke(Color.GREEN);
		
		System.out.println(lineTo);
		System.out.println(lineFrom);
		
		graphPane.getChildren().add(lineTo);
		graphPane.getChildren().add(lineFrom);
		return didAddEdge;
		
	}

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

	public ObjectProperty<Object> mousedOverVertexProperty()
	{
		return mousedOverVertex;
	}

	public final Object getMousedOverVertex()
	{
		return mousedOverVertex.get();
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
