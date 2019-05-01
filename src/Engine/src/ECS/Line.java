package Engine.src.ECS;

public class Line {
    private double myX1;
    private double myX2;
    private double myY1;
    private double myY2;
    private double mySlope;
    private double myIntercept;

    public Line (double x1, double y1, double x2, double y2){
        myX1 = x1;
        myY1 = y1;
        myX2 = x2;
        myY2 = y2;
        mySlope = (y2 - y1) / (x2 - x1);
        myIntercept = y1 - (mySlope * x1);
    }

    public boolean intersects(Line other){
        double denominator = mySlope - other.getMySlope();
        if(denominator != 0){
            double xIntersection = (other.getMyIntercept() - myIntercept) / denominator;
            return between(xIntersection, myX1, myX2) && between(xIntersection, other.getMyX1(), other.getMyX2());
        }
        return false;
    }

    private boolean between(double innerPoint, double outerPoint1, double outerPoint2){
        return (innerPoint <= outerPoint1 && innerPoint >= outerPoint2)
                || (innerPoint >= outerPoint1 && innerPoint <= outerPoint2);
    }

    public double getMyX1() {
        return myX1;
    }

    public double getMyX2() {
        return myX2;
    }

    public double getMyIntercept() {
        return myIntercept;
    }

    public double getMySlope() {
        return mySlope;
    }
}
