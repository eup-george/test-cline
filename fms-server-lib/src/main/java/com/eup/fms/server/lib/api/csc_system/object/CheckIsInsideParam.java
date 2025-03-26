package com.eup.fms.server.lib.api.csc_system.object;

import com.eup.fms.java.lib.shape.Circle;
import com.eup.fms.java.lib.shape.Polygon;
import com.eup.fms.java.lib.shape.AbstractShape;
import java.awt.geom.Point2D;
import java.util.List;

public class CheckIsInsideParam {

    private String team_ID;
    private double checkGISX;
    private double checkGISY;
    private String car_Unicode;
    private AbstractShape shapeBase;
    private boolean isCheckLocArea;
    private boolean isCheckCarDistance;

    public String getTeamId() {
        return team_ID;
    }

    public void setTeamId(String team_ID) {
        this.team_ID = team_ID;
    }

    public double getCheckGISX() {
        return checkGISX;
    }

    public void setCheckGISX(double checkGISX) {
        this.checkGISX = checkGISX;
    }

    public double getCheckGISY() {
        return checkGISY;
    }

    public void setCheckGISY(double checkGISY) {
        this.checkGISY = checkGISY;
    }

    public String getCarUnicode() {
        return car_Unicode;
    }

    public void setCarUnicode(String car_Unicode) {
        this.car_Unicode = car_Unicode;
    }

    public AbstractShape getShape() {
        return shapeBase;
    }

    public void setShape(List<Point2D> pointList) {
        shapeBase = new Polygon(pointList);
    }

    public void setShape(double circleGISX, double circleGISY, double diameter) {
        Point2D circle = new Point2D.Double(circleGISX, circleGISY);
        shapeBase = new Circle(circle, diameter);
    }

    public boolean isCheckLocArea() {
        return isCheckLocArea;
    }

    public void setCheckLocArea(boolean checkLocArea) {
        isCheckLocArea = checkLocArea;
    }

    public boolean isCheckCarDistance() {
        return isCheckCarDistance;
    }

    public void setCheckCarDistance(boolean checkCarDistance) {
        isCheckCarDistance = checkCarDistance;
    }
}
