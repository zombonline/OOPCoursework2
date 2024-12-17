// SVG line element
// https://www.w3schools.com/graphics/svg_line.asp
public class Line extends LineStrip{

	public Line(){
		setNumVertices(2);
	}

	// Construct from two endpoints
	public Line(float x1, float y1, float x2, float y2){
		this();
		this.setVertex(0, new Vec2(x1,y1));
		this.setVertex(1, new Vec2(x2,y2));
	}
}
