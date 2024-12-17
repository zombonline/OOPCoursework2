// Sequence of points connected with line segments
// https://www.w3schools.com/graphics/svg_polyline.asp
// https://www.w3schools.com/graphics/svg_polygon.asp
public class LineStrip extends Shape {

	private Attrib attribPoints;
	private float[] vertices; // 2D position data packed as [x1, y1, x2, y2, ...]
	protected boolean closed = false;

	public LineStrip(){
		attribPoints = newAttrib("points");	
	}

	// Returns "polygon" if closed, otherwise "polyline"
	@Override
	public String getTag(){
		// Your implementation...
	}

	// (This method is finished. No need to edit.)
	@Override
	protected void updateAttribs(){
		super.updateAttribs();

		String s = "";
		var p = new Vec2();
		for(int i=0; i<vertices.length; i++){
			p.set(getVertex(i,0), getVertex(i,1));
			transform(p);
			p.negY(); // SVG has flipped y-axis. We negate y so +y is up.
			s += p.x + "," + p.y + " ";
		}
		attribPoints.val = s;
	}

	public void setNumVertices(int n){
		// Your implementation...
	}

	public int getNumVertices(){
		// Your implementation...
	}
	
	// Set vertex at index
	public void setVertex(int i, float x, float y){
		// Your implementation...
	}
	
	// Set vertex at index
	public void setVertex(int i, Vec2 p){
		// Your implementation...
	}

	// Get vertex component (0:x or 1:y) at index
	public float getVertex(int i, int comp){
		// Your implementation...
	}
}