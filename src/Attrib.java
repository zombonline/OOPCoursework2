// Key-value pair (placed inside tag)
public class Attrib{
	public String key, val;
	// Add members here...
	public Attrib(String key, String val) {
		this.key = key;
		this.val = val;
	}
	// Convert to string in form key="val"
	public String toString(){
		return key+"=\""+val+"\"";
	}
}