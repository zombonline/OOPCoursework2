public class RegPolygon extends LineStrip {
	public RegPolygon(int s, float r, float cX, float cY){
		setNumVertices(s);
		this.setClosed(true);
		setOrigin(cX,-cY);
		for (int i = 0; i < s; i++) {
	        double angle = 2 * Math.PI * i / s + Math.PI / 2;
	        
	        float x = cX + (float) (r * Math.cos(angle));
	        float y = cY + (float) (r * Math.sin(angle));
	        
	        this.setVertex(i, new Vec2(x, y));
	    }
	}
}