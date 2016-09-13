
package csci.pkg446.project1;

/**
 *
 * @author Mathew Gostnell
 */
public class Point {
    private final double x;
    private final double y;
    private Color c;

    public enum Color {
        RED, BLUE, GREEN, YELLOW, COLORLESS;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.c = Color.COLORLESS;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

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
    
    public boolean match(double x, double y) {
        int check1 = Double.compare(this.x, x);
        int check2 = Double.compare(this.y, y);
        return check1 == 0 && check2 == 0;
    }

    public void print() {
        System.out.println(c() + "(" + x + ", " + y + ")");
    }
}
