
package csci.pkg446.project1;

/**
 *
 * @author Mathew Gostnell
 */
public class Point {
    private final double x;
    private final double y;
    private Color c;

    // color enumeration used with Point
    public enum Color {
        RED, BLUE, GREEN, YELLOW, COLORLESS;
    }
    
    /**
     * Constructor for Point class
     * @param x X coordinate for point between (0, 1) inclusive
     * @param y Y coordinate for point between (0, 1) inclusive
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.c = Color.COLORLESS;
    }
    
    // get private var x
    public double x() {
        return this.x;
    }

    // get private var y
    public double y() {
        return this.y;
    }

    // get color as a string for class outside Point
    public String c() {
        switch (c) {
            case RED:
                return "Red";
            case BLUE:
                return "Blue";
            case YELLOW:
                return "Yellow";
            case GREEN:
                return "Green";
            default:
                return "Colorless";
        }
    }
    
    // set color as a Color for classes outside Point
    public void setColor(String color) {
        color = color.toLowerCase();
        switch (color) {
            case "red":
                this.c = Color.RED;
                break;
            case "blue":
                this.c = Color.BLUE;
                break;
            case "green":
                this.c = Color.GREEN;
                break;
            case "yellow":
                this.c = Color.YELLOW;
                break;
            default:
                this.c = Color.COLORLESS;
                break;
        }
    }
    
    

    // print out Point color and coordinates
    public void print() {
        System.out.println(c() + "(" + x + ", " + y + ")");
    }
    
    @Override
    public String toString(){
        return c() + "(" + x + ", " + y + ")";
    }
}
