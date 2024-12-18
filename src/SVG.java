import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SVG {
	private ViewBox vb;
	public SVG() {
        this.vb = new ViewBox();
	}
	public void toFile(String filename, ArrayList<Shape> shapes) {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"" + vb.toString()+"\">\n");
        for (Shape shape : shapes) {
            sb.append(shape.toString()).append("\n");
        }
        sb.append("</svg>");
     // Ensure the directory for the output exists
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the output directory if it doesn't exist
        }
        String outputFileName = filename + ".svg";
        try (PrintWriter writer = new PrintWriter(new File(directory, outputFileName))) {
            writer.write(sb.toString());
            System.out.println("SVG file created at " + directory.getAbsolutePath() + "/" + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
