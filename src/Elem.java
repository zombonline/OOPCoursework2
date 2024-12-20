import java.util.ArrayList;

// XML element consisting of a tag and content
public abstract class Elem{
	private ArrayList<Attrib> attribs = new ArrayList<Attrib>();
	protected String content = "";


	protected abstract String getTag();

	protected final Attrib newAttrib(String key, String val){
		Attrib newAttrib = new Attrib(key, val);
		attribs.add(newAttrib);
		return newAttrib;
	}
	protected final Attrib newAttrib(String key) {
		Attrib newAttrib = new Attrib(key, "");
		attribs.add(newAttrib);
		return newAttrib;
	}

	public final void addContent(Elem e){
		content += e.toString();
	}

	public final void clearContent(){
		content = "";
	}

	protected void updateAttribs(){ /* Implementation in subclasses only */ }

	@Override
	public String toString(){
		String result = "<"+getTag();
		for(Attrib attrib : attribs) {
			result+=" "+attrib.toString()+"";
		}
		if(content.isEmpty()) {
			result+= "/>";
		}
		else {
			result+= ">\n";
			result+= content + "\n";
			result+= "</"+getTag()+">";
		}
		
		return result;
	}
}