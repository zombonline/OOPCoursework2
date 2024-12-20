// (Note that this class is nearly finished already. Check the UML diagram to ensure all access modifiers and other qualifiers are correct.)

// SVG shape with a geometric transform
public class Animation extends Elem {
	private Attrib attributeName;
	private Attrib type;
	private Attrib from;
	private Attrib to;
	private Attrib begin;
	private Attrib dur;
	private Attrib repeatCount;
	private Attrib origin;
	private Attrib additive;
	public Animation(){
		attributeName = newAttrib("attributeName", "transform");
		type = newAttrib("type");
		from = newAttrib("from");
		to = newAttrib("to");
		begin = newAttrib("begin");
		dur = newAttrib("dur");
		repeatCount = newAttrib("repeatCount");
		additive = newAttrib("additive","sum");
	}
	public Animation(String type, float fromX, float fromY, float toX, float toY, float begin, float dur, String repeatCount) {
		this();
		setType(type);
		setFrom(fromX, fromY);
		setTo(toX,toY);
		setBegin(begin);
		setDur(dur);
		setRepeatCount(repeatCount);
	}
	public Animation(String type, float fromA, float fromX, float fromY, float toA, float toX, float toY, float begin, float dur, String repeatCount) {
		this();
		setType(type);
		setFrom(fromA, fromX, fromY);
		setTo(toA, toX, toY);
		setBegin(begin);
		setDur(dur);
		setRepeatCount(repeatCount);
	}
	public void setType(String type) {
		this.type.val = type;
	}
	public void setFrom(float x, float y) {
		this.from.val = String.valueOf(x) + " " + String.valueOf(y);
	}
	public void setFrom(float a, float cX, float cY) {
		this.from.val = String.valueOf(a) + " " + String.valueOf(cX) + " " + String.valueOf(cY);
	}
	public void setTo(float x, float y) {
		this.to.val = String.valueOf(x) + " " + String.valueOf(y);
	}
	public void setTo(float a, float cX, float cY) {
		this.to.val = String.valueOf(a) + " " + String.valueOf(cX) + " " + String.valueOf(cY);
	}
	public void setBegin(float val) {
		this.begin.val = String.valueOf(val);
	}
	public void setDur(float val) {
		this.dur.val = String.valueOf(val);
	}
	public void setRepeatCount(String val) {
		this.repeatCount.val = val;
	}
	@Override
	public String getTag() {
		return "animateTransform";
	}
}