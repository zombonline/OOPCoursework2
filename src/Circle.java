// SVG circle element
// https://www.w3schools.com/graphics/svg_circle.asp
public class Circle extends Shape{

	private Attrib attribX;
	private Attrib attribY;
	private Attrib attribR;

	public Circle(){
		// Create attributes via Shape.newAttrib() and assign to members above. For example, here is the first one:
		attribX = newAttrib("cx");
		attribY = newAttrib("cy");
		attribR = newAttrib("r");
	}

	public Circle(float r, float x, float y){
		this();
		setScale(r);
		setPos(x,y);
	}

	@Override
	public String getTag() {
		return "circle";
	}
    @Override
    protected void updateAttribs() {
        super.updateAttribs();
        attribR.val = String.valueOf(getScale().x);
        attribX.val = String.valueOf(getPos().x);
        attribY.val = String.valueOf(-getPos().y);
    }
}

