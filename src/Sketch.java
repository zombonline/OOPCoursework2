import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sketch {
    private List<Shape> shapes;

    public Sketch() {
        shapes = new ArrayList<>();
    }

    // Add a shape to the sketch
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    // Converts shapes to an SVG string
    public String toSVG() {
        StringBuilder sb = new StringBuilder();
        ViewBox vb = new ViewBox();
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"" + vb.toString()+"\">\n");
        
        
        for (Shape shape : shapes) {
            sb.append(shape.toString()).append("\n");
        }
        sb.append("</svg>");
        return sb.toString();
    }
    private boolean isFloat(String val) {
    	try {
    		Float.parseFloat(val);
    		return true;
    	}
    	catch(Exception e){
    		return false;
    	}
    }
    private int getHexColorInt(String val) {
    	int r = Integer.parseInt(val.substring(0, 2), 16);
    	int g = Integer.parseInt(val.substring(2, 4), 16);
    	int b = Integer.parseInt(val.substring(4, 6), 16);
    	int a = Integer.parseInt(val.substring(6, 8), 16);
    	return ColorInt.from(r, g, b, a);
    }
    private void parseOptionalValues(Shape shape, String[] parts) {
    	int index = 0;

        if (index < parts.length && isFloat(parts[index])) {
            shape.setStrokeWidth(Float.parseFloat(parts[index]));
            index++; // Move to the next part
        }
        if (index < parts.length) {
            shape.setStroke(getHexColorInt(parts[index]));
            index++; // Move to the next part
        }
        if (index < parts.length) {
            shape.setFill(getHexColorInt(parts[index]));
        }
    }
    private void parseLine(String[] parts) {
        //line     start_x start_y end_x end_y
    	float x1 = Float.parseFloat(parts[0]);
        float y1 = Float.parseFloat(parts[1]);
        float x2 = Float.parseFloat(parts[2]);
        float y2 = Float.parseFloat(parts[3]);
        Line newLine = new Line(x1,y1,x2,y2);
    	parseOptionalValues(newLine, Arrays.copyOfRange(parts, 4 , parts.length));
        newLine.updateAttribs();
        addShape(newLine);
    }
    private void parseCircle(String[] parts) {
    	//circle   radius center_x center_y

        float r = Float.parseFloat(parts[0]);
    	float cX = Float.parseFloat(parts[1]);
        float cY = Float.parseFloat(parts[2]);
        Circle newCircle = new Circle(r,cX,cY);
        parseOptionalValues(newCircle, Arrays.copyOfRange(parts, 3, parts.length));
        newCircle.updateAttribs();
        addShape(newCircle);
    }
    private void parseArc(String[] parts) {
    	//arc      radius arc_angle arc_length center_x center_y
        float r = Float.parseFloat(parts[0]);
        float a = Float.parseFloat(parts[1]);
        float l = Float.parseFloat(parts[2]);
        float cX = Float.parseFloat(parts[3]);
        float cY = Float.parseFloat(parts[4]);
        Arc newArc = new Arc(r,a,l,cX,cY);
        parseOptionalValues(newArc, Arrays.copyOfRange(parts, 5, parts.length));
        newArc.updateAttribs();
        addShape(newArc);
    }
    private void parseRect(String[] parts) {
    	//rect     width height center_x center_y
    	float w = Float.parseFloat(parts[0]);
    	float h = Float.parseFloat(parts[1]);
    	float cX = Float.parseFloat(parts[2]);
    	float cY = Float.parseFloat(parts[3]);
        Rect newRect = new Rect(w,h,cX,cY);
        parseOptionalValues(newRect, Arrays.copyOfRange(parts, 4, parts.length));
        newRect.updateAttribs();
        addShape(newRect);
    }
    private void parseSquare(String[] parts) {
    	//square   width center_x center_y
    	float w = Float.parseFloat(parts[0]);
    	float cX = Float.parseFloat(parts[1]);
    	float cY = Float.parseFloat(parts[2]);
        Rect newRect = new Rect(w,w,cX,cY);
        parseOptionalValues(newRect, Arrays.copyOfRange(parts, 3, parts.length));
        newRect.updateAttribs();
        addShape(newRect);
    }
    private void parseRegPolygon(String[] parts) {
    	//ngon     sides radius center_x center_y
    	int s = Integer.parseInt(parts[0]);
        float r = Float.parseFloat(parts[1]);
        float cX = Float.parseFloat(parts[2]);
        float cY = Float.parseFloat(parts[3]);
        RegPolygon newPolygon = new RegPolygon(s,r,cX,cY);
        parseOptionalValues(newPolygon, Arrays.copyOfRange(parts, 4, parts.length));
        newPolygon.updateAttribs();
        addShape(newPolygon);
    }

    // Parses the input file and adds shapes dynamically
    public void loadShapesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                String shapeType = parts[0];

                try {
                    switch (shapeType) {
                        case "line":
                        	parseLine(Arrays.copyOfRange(parts, 1, parts.length));
                            break;

                        case "circle":
                        	parseCircle(Arrays.copyOfRange(parts, 1, parts.length));
                            break;

                        case "square":
                            parseSquare(Arrays.copyOfRange(parts,1, parts.length));
                            break;

                        case "rect":
                        	parseRect(Arrays.copyOfRange(parts, 1, parts.length));
                            break;

                        case "arc":
                        	parseArc(Arrays.copyOfRange(parts, 1, parts.length));
                            break;

                        case "ngon":
                            parseRegPolygon(Arrays.copyOfRange(parts, 1, parts.length));
                            break;

                        default:
                            System.out.println("Unknown shape: " + shapeType);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }

    // Generates SVG and writes it to a file
    public void render(String dir, String filename) {
        if (dir == null || dir.isEmpty()) {
            dir = ""; // Default to the current directory if no directory is provided
        }

        // Construct the absolute path for the input file
        String absoluteFilePath = Paths.get(dir, filename + ".txt").toAbsolutePath().toString();
        System.out.println("Trying to read from: " + absoluteFilePath);

        // Check if the input file exists
        File inputFile = new File(absoluteFilePath);
        if (!inputFile.exists()) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
            return; // If the input file is not found, do not proceed
        }

        // Load shapes from the input file
        loadShapesFromFile(absoluteFilePath);

        // Ensure the directory for the output exists
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the output directory if it doesn't exist
        }

        // Write the SVG file to the "output" directory
        String outputFileName = filename + ".svg";
        try (PrintWriter writer = new PrintWriter(new File(directory, outputFileName))) {
            writer.write(toSVG());
            System.out.println("SVG file created at " + directory.getAbsolutePath() + "/" + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}