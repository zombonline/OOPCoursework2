// (Note that this class is nearly finished already. Check the UML diagram to ensure all access modifiers and other qualifiers are correct.)

// SVG shape with a geometric transform
public class Shape extends Elem {
	private int fill = ColorInt.from(0,0);
	private int stroke = ColorInt.from(0);
	private float strokeWidth = 1.f;

	private Vec2 scale = new Vec2(1.f);
	private Vec2 rotation = new Vec2(1.f, 0.f); // in complex form
	private Vec2 pos = new Vec2(0.f);
	private Vec2 origin = new Vec2(0.f);

	private Attrib attribStyle;
	private Attrib attribOrigin;
	
	public Shape(){
		attribStyle = newAttrib("style");
		attribOrigin = newAttrib("transform-origin");
	}


	// This updates the style element
	// If overriding in a subclass, make sure to call this method via super.updateAttribs().
	@Override
	protected void updateAttribs(){
		var style = "fill:" + (ColorInt.isClear(fill) ? "none" : "#"+ColorInt.hexString(fill));
		if(strokeWidth > 0.f && !ColorInt.isClear(stroke)){
			style += ";stroke-width:" + strokeWidth;
			style += ";stroke:#" + ColorInt.hexString(stroke);
		}
		attribStyle.val = style;
		attribOrigin.val = String.valueOf(origin.x) + " " + String.valueOf(origin.y);
	}
	
	// Insert missing setters and getters here...
	public void setFill(int val) {
		this.fill = val;
	}
	public int getFill() {
		return this.fill;
	}
	public void setStroke(int val) {
		this.stroke = val;
	}
	public int getStroke() {
		return this.stroke;
	}
	public void setStrokeWidth(float val) {
		this.strokeWidth = val;
	}
	public float getStrokeWidth() {
		return this.strokeWidth;
	}
	public Vec2 getScale() {
		return scale;
	}
	public void setScale(float val) {
		this.scale.set(val, val);
	}
	public Vec2 getRotation() {
		return rotation;
	}
	public void setRotation(Vec2 rotation) {
		this.rotation = rotation;
	}
	public Vec2 getPos() {
		return pos;
	}
	public void setPos(float x, float y) {
		this.pos.set(x,y);
	}	
	public Vec2 getOrigin() {
		return origin;
	}
	public void setOrigin(float x, float y) {
		this.origin.set(x,y);
	}	
	public Shape setRotation(float deg){
		final float d2r = (float)(Math.PI / 180.);
		rotation.x = (float)Math.cos(deg*d2r);
		rotation.y = (float)Math.sin(deg*d2r);
		return this;
	}
	// Apply transform in-place on vector
	protected void transform(Vec2 p){
		p.mul(getScale()).cmul(getRotation()).add(getPos());
	}
	@Override
	public String getTag() {
		return "Shape";
	}
}