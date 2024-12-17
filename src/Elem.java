import java.util.ArrayList;

// XML element consisting of a tag and content
public abstract class Elem{
	private ArrayList<Attrib> attribs = new ArrayList<Attrib>();
	protected String content = "";


	// Get this element's unadorned tag name, e.g., "svg" or "circle"
	public abstract String getTag();

	// Create new attribute to be included in tag
	//
	// The newly added attribute is returned and expected to be assigned to another variable.
	// When used inside a subclass, the return value should be assigned to a member variable (pointer) for future reference.
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

	// Add element to current content
	// Each element is placed on a new line.
	public final void addContent(Elem e){
		content += e.toString();
	}

	// Clear content
	public final void clearContent(){
		content = "";
	}

	// Update values of attributes from current object state (i.e., numerical members)
	protected void updateAttribs(){ /* Implementation in subclasses only */ }


	/* Returns a fully-formed XML element string

		Attributes go inside the start tag and the content goes between the start and end tag.
	
			<tag_name attrib1="value1" attrib2="value2" ...>
				content...
			</tag_name>
	
		An element without content should generate an empty-element tag, i.e.,
		
			<tag_name attrib1="value1" attrib2="value2" ... />
	*/	
	@Override
	public String toString(){
		String result = "<"+getTag();
		for(Attrib attrib : attribs) {
			result+=" "+attrib.toString()+"";
		}
		result+= "/>";
		return result;
	}
}