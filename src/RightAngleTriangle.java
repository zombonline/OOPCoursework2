public class RightAngleTriangle extends LineStrip{
	public RightAngleTriangle(){
		setNumVertices(3);
	}

	public RightAngleTriangle(boolean centrePosition, float w, float h, float x, float y){
		this();
		this.setClosed(true);
		Vec2[] verts;
		if(centrePosition) {
			verts = new Vec2[] {
					new Vec2(x-(w/2),y-(h/2)),
					new Vec2(x-(w/2),y+(h/2)),
					new Vec2(x+(w/2),y-(h/2))
			};
		} 
		else {
			verts = new Vec2[] {
					new Vec2(x,y+h),
					new Vec2(x,y),
					new Vec2(x+w,y)
			}; 
		}
		for(int i = 0; i < verts.length; i++) {
			this.setVertex(i, verts[i]);
		}
	}
}
