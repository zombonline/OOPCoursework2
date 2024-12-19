public class Kite extends LineStrip {

	public Kite(){
		setNumVertices(4);
	}

	public Kite(float w, float h, float cX, float cY){
		this();
		this.setClosed(true);
		Vec2[] verts = new Vec2[] {
				new Vec2(cX, cY-(h/2)),
				new Vec2(cX+(w/2),cY+(h/4)),
				new Vec2(cX, cY+(h/2)),
				new Vec2(cX-(w/2),cY+(h/4))
		};
		for(int i = 0; i < verts.length; i++) {
			this.setVertex(i, verts[i]);
		}
	}
}