import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sketch {
	public SVG svg;
	public Shape[] shapes;
	
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
    private void parseAnimTranslateValues(Shape shape, String[] parts) {
        // First part indicates the type of transform
        String type = parts[0];

        float begin = Float.parseFloat(parts[parts.length - 3]);
        float dur = Float.parseFloat(parts[parts.length - 2]);
        String repeatCount = parts[parts.length - 1];

        Animation newAnim;

        switch (type) {
            case "translate":
                // translate requires 4 positional values: fromX, fromY, toX, toY
                float fromX = Float.parseFloat(parts[1]);
                float fromY = Float.parseFloat(parts[2]);
                float toX = Float.parseFloat(parts[3]);
                float toY = Float.parseFloat(parts[4]);
                newAnim = new Animation(type, fromX, fromY, toX, toY, begin, dur, repeatCount);
                break;

            case "scale":
                fromX = Float.parseFloat(parts[1]);
                fromY = Float.parseFloat(parts[2]);
                toX = Float.parseFloat(parts[3]);
                toY = Float.parseFloat(parts[4]);
                newAnim = new Animation(type, fromX, fromY, toX, toY, begin, dur, repeatCount);
                break;

            case "rotate":
            	float fromA = Float.parseFloat(parts[1]);
                fromX = Float.parseFloat(parts[2]);
                fromY = Float.parseFloat(parts[3]);
                float toA = Float.parseFloat(parts[4]);
                toX = Float.parseFloat(parts[5]);
                toY = Float.parseFloat(parts[6]);
                newAnim = new Animation(type, fromA, fromX, fromY, toA, toX, toY, begin, dur, repeatCount);
                break;

            default:
                throw new IllegalArgumentException("Unsupported animation type: " + type);
        }

        shape.addContent(newAnim);
    }
    private Shape parseLine(String[] parts) {
        //line     start_x start_y end_x end_y
    	float x1 = Float.parseFloat(parts[0]);
        float y1 = Float.parseFloat(parts[1]);
        float x2 = Float.parseFloat(parts[2]);
        float y2 = Float.parseFloat(parts[3]);
        Line newLine = new Line(x1,y1,x2,y2);
    	parseOptionalValues(newLine, Arrays.copyOfRange(parts, 4 , parts.length));
        newLine.updateAttribs();
        return newLine;
    }
    private Shape parseCircle(String[] parts) {
    	//circle   radius center_x center_y

        float r = Float.parseFloat(parts[0]);
    	float cX = Float.parseFloat(parts[1]);
        float cY = Float.parseFloat(parts[2]);
        Circle newCircle = new Circle(r,cX,cY);
        parseOptionalValues(newCircle, Arrays.copyOfRange(parts, 3, parts.length));
        newCircle.updateAttribs();
        return newCircle;
    }
    private Shape parseArc(String[] parts) {
    	//arc      radius arc_angle arc_length center_x center_y
        float r = Float.parseFloat(parts[0]);
        float a = Float.parseFloat(parts[1]);
        float l = Float.parseFloat(parts[2]);
        float cX = Float.parseFloat(parts[3]);
        float cY = Float.parseFloat(parts[4]);
        Arc newArc = new Arc(r,a,l,cX,cY);
        parseOptionalValues(newArc, Arrays.copyOfRange(parts, 5, parts.length));
        newArc.updateAttribs();
        return newArc;
    }
    private Shape parseRect(String[] parts) {
    	//rect     width height center_x center_y
    	float w = Float.parseFloat(parts[0]);
    	float h = Float.parseFloat(parts[1]);
    	float cX = Float.parseFloat(parts[2]);
    	float cY = Float.parseFloat(parts[3]);
        Rect newRect = new Rect(w,h,cX,cY);
//        parseOptionalValues(newRect, Arrays.copyOfRange(parts, 4, parts.length));
        newRect.updateAttribs();
        return newRect;
    }
    private Shape parseSquare(String[] parts) {
    	//square   width center_x center_y
    	float w = Float.parseFloat(parts[0]);
    	float cX = Float.parseFloat(parts[1]);
    	float cY = Float.parseFloat(parts[2]);
        Rect newSquare = new Rect(w,w,cX,cY);
        parseOptionalValues(newSquare, Arrays.copyOfRange(parts, 3, parts.length));
        newSquare.updateAttribs();
        return newSquare;
    }
    private Shape parseRegPolygon(String[] parts) {
    	//ngon     sides radius center_x center_y
    	int s = Integer.parseInt(parts[0]);
        float r = Float.parseFloat(parts[1]);
        float cX = Float.parseFloat(parts[2]);
        float cY = Float.parseFloat(parts[3]);
        RegPolygon newPolygon = new RegPolygon(s,r,cX,cY);
        parseOptionalValues(newPolygon, Arrays.copyOfRange(parts, 4, parts.length));
        newPolygon.updateAttribs();
        return newPolygon;
    }
    private Shape parseRightTriangle(String[] parts) {
    	//righttriangle centrePos w h x y 
    	boolean centrePos = Boolean.parseBoolean(parts[0]);
    	float w = Float.parseFloat(parts[1]);
    	float h = Float.parseFloat(parts[2]);
    	float x = Float.parseFloat(parts[3]);
    	float y = Float.parseFloat(parts[4]);
        RightAngleTriangle newRightAngleTriangle = new RightAngleTriangle(centrePos,w,h,x,y);
        parseOptionalValues(newRightAngleTriangle, Arrays.copyOfRange(parts, 5, parts.length));
        newRightAngleTriangle.updateAttribs();
        return newRightAngleTriangle;
    }
    private Shape parseKite(String[] parts) {
    	//kite 20 40 75 25 
    	float w = Float.parseFloat(parts[0]);
    	float h = Float.parseFloat(parts[1]);
    	float cX = Float.parseFloat(parts[2]);
    	float cY = Float.parseFloat(parts[3]);
    	Kite newKite = new Kite(w,h,cX,cY);
        parseOptionalValues(newKite, Arrays.copyOfRange(parts, 4, parts.length));
        newKite.updateAttribs();
        return newKite;
	}

    public ArrayList<Shape> fromFile(String filePath) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    		String line;
            ArrayList<Shape> shapes = new ArrayList<Shape>();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) { continue; }
                String[] parts = line.split("\\s+");
                String shapeType = parts[0];
                switch (shapeType) {
                    case "line":
                    	shapes.add(parseLine(Arrays.copyOfRange(parts, 1, parts.length)));
                        break;
                    case "circle":
                    	shapes.add(parseCircle(Arrays.copyOfRange(parts, 1, parts.length)));
                    	break;
                    case "square":
                        shapes.add(parseSquare(Arrays.copyOfRange(parts,1, parts.length)));
                        break;
                    case "rect":
                    	shapes.add(parseRect(Arrays.copyOfRange(parts, 1, parts.length)));
                        break;
                    case "arc":
                    	shapes.add(parseArc(Arrays.copyOfRange(parts, 1, parts.length)));
                        break;
                    case "ngon":
                        shapes.add(parseRegPolygon(Arrays.copyOfRange(parts, 1, parts.length)));
                        break;
                    case "righttriangle":
                        shapes.add(parseRightTriangle(Arrays.copyOfRange(parts, 1, parts.length)));
                        break;
                    case "kite":
                    	shapes.add(parseKite(Arrays.copyOfRange(parts, 1, parts.length)));
                    	break;
                    case "animateTransform":
                    	parseAnimTranslateValues(shapes.get(shapes.size()-1), Arrays.copyOfRange(parts, 1, parts.length));
                    	break;
                    default:
                        System.out.println("Unknown shape: " + shapeType);
                }
			}	
           return shapes;
    	} 
    catch (Exception e) {
    	e.printStackTrace();
		return null;
	} 
}

    public void render(String dir, String filename) {
        if (dir == null || dir.isEmpty()) { dir = ""; }

        String absoluteFilePath = Paths.get(dir, filename + ".txt").toAbsolutePath().toString();
        System.out.println("Trying to read from: " + absoluteFilePath);

        File inputFile = new File(absoluteFilePath);
        if (!inputFile.exists()) {
            System.err.println("Input file not found: " + inputFile.getAbsolutePath());
            return; 
        }

        ArrayList<Shape> shapes = fromFile(absoluteFilePath);
        SVG svg = new SVG();
        svg.toFile(filename, shapes);
    }
}