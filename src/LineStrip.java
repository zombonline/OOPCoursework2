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
		return closed ? "polygon" : "polyline";
	}
	
	public void setClosed(boolean val) {
		this.closed = val;
	}

	// (This method is finished. No need to edit.)
	@Override
	protected void updateAttribs(){
		super.updateAttribs();

		String s = "";
		var p = new Vec2();
		for(int i=0; i<vertices.length/2; i++){
			p.set(getVertex(i,0), getVertex(i,1));
			transform(p);
			p.negY(); // SVG has flipped y-axis. We negate y so +y is up.
			s += p.x + "," + p.y + " ";
		}
		attribPoints.val = s;
	}

	public void setNumVertices(int n){
		this.vertices = new float[n*2];
	}

	public int getNumVertices(){
		return this.vertices.length/2;
	}
	
	// Set vertex at index
	public void setVertex(int i, float x, float y){
		int index = i*2;
		this.vertices[index]=x;
		this.vertices[index+1]=y;
	}
	
	// Set vertex at index
	public void setVertex(int i, Vec2 p){
		int index = i*2;
		this.vertices[index]=p.x;
		this.vertices[index+1]=p.y;
	}

	// Get vertex component (0:x or 1:y) at index
	public float getVertex(int i, int comp){
	    int index = i * 2 + comp; // Correctly calculate the index
	    return this.vertices[index];
	}
}