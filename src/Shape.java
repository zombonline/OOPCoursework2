// (Note that this class is nearly finished already. Check the UML diagram to ensure all access modifiers and other qualifiers are correct.)

// SVG shape with a geometric transform
public class Shape extends Elem {
	private Attrib attribStyle;

	int fill = ColorInt.from(0,0);
	int stroke = ColorInt.from(0);
	float strokeWidth = 1.f;

	Vec2 scale = new Vec2(1.f);
	Vec2 rotation = new Vec2(1.f, 0.f); // in complex form
	Vec2 pos = new Vec2(0.f);

	public Shape(){
		attribStyle = newAttrib("style");
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
	}


	// Insert missing setters and getters here...

	
	public Shape setRotation(float deg){
		final float d2r = (float)(Math.PI / 180.);
		rotation.x = (float)Math.cos(deg*d2r);
		rotation.y = (float)Math.sin(deg*d2r);
		return this;
	}
	
	// Apply transform in-place on vector
	private void transform(Vec2 p){
		p.mul(scale).cmul(rotation).add(pos);
	}
}