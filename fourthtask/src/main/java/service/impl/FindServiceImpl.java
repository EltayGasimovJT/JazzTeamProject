package service.impl;

import entity.Point;
import service.FindService;

public class FindServiceImpl implements FindService {
    @Override
    public double findSide(Point x, Point y) {
        double firstPointPow = Math.pow(x.getX() - y.getX(), 2);
        double secondPointPow = Math.pow(x.getY() - y.getY(), 2);

        double toAbs = Math.sqrt(firstPointPow + secondPointPow);
        double result = Math.abs(toAbs);

        return result;
    }
}
