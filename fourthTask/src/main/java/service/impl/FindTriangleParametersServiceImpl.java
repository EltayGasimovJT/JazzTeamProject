package service.impl;

import entity.Point;
import service.FindTriangleParametersService;

public class FindTriangleParametersServiceImpl implements FindTriangleParametersService {
    @Override
    public double findSide(Point x, Point y) {
        double firstPointPow = Math.pow(x.getX() - y.getX(), 2);
        double secondPointPow = Math.pow(x.getY() - y.getY(), 2);

        double toAbs = Math.sqrt(firstPointPow + secondPointPow);
        double result = Math.abs(toAbs);

        return result;
    }
}
