import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.Desktop;

public class SVG extends Elem{
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
            File svgFile = new File(directory.getAbsolutePath() + "/" + outputFileName);
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(svgFile);
                    System.out.println("Opened SVG file in default application.");
                } catch (IOException e) {
                    System.err.println("Error opening SVG file: " + e.getMessage());
                }
            } else {
                System.err.println("Desktop operations are not supported on this system.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	@Override
	protected String getTag() {
		return "svg";
	}
	
}
