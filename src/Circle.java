// SVG circle element
// https://www.w3schools.com/graphics/svg_circle.asp
public class Circle {

	private Attrib attribX;
	private Attrib attribY;
	private Attrib attribR;

	public Circle(){
		// Create attributes via Shape.newAttrib() and assign to members above. For example, here is the first one:
		attribX = newAttrib("cx");
		// Finish implementation...
	}

	public Circle(float r, float x, float y){
		this();
		setScale(r);
		setPos(x,y);
	}

}
