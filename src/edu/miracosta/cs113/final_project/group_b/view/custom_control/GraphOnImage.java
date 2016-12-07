
package edu.miracosta.cs113.final_project.group_b.view.custom_control;

import java.io.IOException;
import java.util.Collection;
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

// TODO make this class genericy with allowing graphs that do not use Point2D but force the addition of a function that when applied on the vertex gives back a Point2D

/**
 * @author Boris
 *
 */
public class GraphOnImage extends StackPane
{
	@FXML private Pane						graphPane;
	@FXML private ResizeToParentImageView	imageView;

	private ObjectProperty<Object> mousedOverVertex = new SimpleObjectProperty<Object>(this, "mousedOverVertex", null);
	private SetProperty<Object> selectedVertices = new SimpleSetProperty<>(this, "selectedVertices");
	//TODO use css api to make vertex size stylable through css
	private DoubleProperty vertexRadius = new SimpleDoubleProperty(this, "vertexSize", 20);
	
	private MapProperty<Circle, Object> circleToVertex = new SimpleMapProperty<>(this, "circleToVertex");
	private MapProperty<Object, Circle> vertexToCircle = new SimpleMapProperty<>(this, "vertexToCircle");
	private ObjectProperty<Bounds>			initBounds	= new SimpleObjectProperty<Bounds>(this, "initBounds");
	private DoubleProperty					widthRatio	= new SimpleDoubleProperty(this, "widthRatio");
	private DoubleProperty					heightRatio	= new SimpleDoubleProperty(this, "heightRatio");

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
		
		imageView.setImage(new Image("file:/D:/Documents/Script%20Files/Java/CS%20113/final_project/src/edu/miracosta/cs113/final_project/group_b/view/custom_control/UTm4Sg.jpg"));
		
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
			Circle circle1 = new Circle(i, i, 5,Color.YELLOW);
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
		
//		DoubleBinding dbx = Bindings.multiply(200, EasyBind.map(imageView.boundsInLocalProperty(), Bounds::getWidth));
//		Bindings.divide(EasyBind.map(imageView.boundsInLocalProperty(), Bounds::getWidth), initBounds.getWidth());
//		EasyBind.map(imageView.boundsInLocalProperty(), Bounds::getWidth);
		
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
//		imageView.boundsInLocalProperty().addListener((e) -> {
//			circleEnd.setCenterX(200 * imageView.getBoundsInLocal().getWidth() / initBounds.getWidth());
//			circleEnd.setCenterY(200 * imageView.getBoundsInLocal().getHeight() / initBounds.getHeight());
//		});
		circleEnd.centerXProperty().bind(endX);
		circleEnd.centerYProperty().bind(endY);
		System.out.println(circleEnd);		
		graphPane.getChildren().add(circleStart);
		graphPane.getChildren().add(circleEnd);
	}
	
	public void addVertex(Object vertex, Collection<Object> adjacent, Function<Object, Point2D> vertexToPoint)
	{
		if(!vertexToCircle.containsKey(vertex))
		{
			
		}
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
	public DoubleProperty vertexRadiuProperty()
	{
		return vertexRadius;
	}
	
	public final double getVertexRadiu()
	{
		return vertexRadius.doubleValue();
	}
	
	public final void setVertexRadiu(double radius)
	{
		vertexRadius.set(radius);
	}
}
//vertexSize
