import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class SVG extends Elem{
	private ViewBox vb;
	
	private Attrib attribViewBox;
	private Attrib attribXmlns;
	public SVG() {
        this.vb = new ViewBox();
        attribXmlns = newAttrib("xmlns","http://www.w3.org/2000/svg");
        attribViewBox = newAttrib("viewBox", vb.toString());
	}
	public void toFile(String filename) {
        String fileString = toString();
     // Ensure the directory for the output exists
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the output directory if it doesn't exist
        }
        String outputFileName = filename + ".svg";
        try (PrintWriter writer = new PrintWriter(new File(directory, outputFileName))) {
            writer.write(fileString);
            System.out.println("SVG file created at " + directory.getAbsolutePath() + "/" + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	@Override
	protected String getTag() {
		return "svg";
	}
	
	
}
