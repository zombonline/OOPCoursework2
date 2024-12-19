// Key-value pair (placed inside tag)
public class Attrib{
	public String key, val;
	public Attrib(String key, String val) {
		this.key = key;
		this.val = val;
	}
	public String toString(){
		return key+"=\""+val+"\"";
	}
}