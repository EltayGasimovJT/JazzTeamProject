package service;

import entity.Point;
import entity.Triangle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.impl.FindServiceImpl;

public class TriangleService {
    private static final Logger LOGGER = LogManager.getLogger();

    private TriangleService() {
    }

    public static boolean isTriangle(Point firstPoint, Point secondPoint, Point thirdPoint) {
        if (firstPoint == null || secondPoint == null || thirdPoint == null) {
            LOGGER.log(Level.ERROR, "Point cannot be null, " +
                    " first = " + firstPoint +
                    " second = " + secondPoint +
                    " third = " + thirdPoint);
            return false;
        }

        boolean validateOnTriangle = isValidateOnTriangle(firstPoint, secondPoint, thirdPoint);
        return validateOnTriangle;
    }

    public static boolean isTriangle(Triangle triangle) {
        if (triangle.getFirstPoint() == null || triangle.getSecondPoint() == null
                || triangle.getThirdPoint() == null) {
            LOGGER.log(Level.ERROR, "Point cannot be null, " +
                    " first = " + triangle.getFirstPoint() +
                    " second = " + triangle.getSecondPoint() +
                    " third = " + triangle.getThirdPoint());
            return false;
        }

        boolean validateOnTriangle = isValidateOnTriangle(triangle.getFirstPoint(),
                triangle.getSecondPoint(), triangle.getThirdPoint());
        return validateOnTriangle;
    }

    public static boolean checkIfPointBelongsToTriangle(Point point, Triangle triangle) {
        boolean isBelongs = false;

        int firstSideVector = getVector(point, triangle.getFirstPoint(), triangle.getSecondPoint());
        int secondSideVector = getVector(point, triangle.getSecondPoint(), triangle.getThirdPoint());
        int thirdSideVector = getVector(point, triangle.getThirdPoint(), triangle.getFirstPoint());

        if ((firstSideVector <= 0 && secondSideVector <= 0 && thirdSideVector <= 0)
                || (firstSideVector >= 0 && secondSideVector >= 0 && thirdSideVector >= 0)) {
            isBelongs = true;
        }

        return isBelongs;
    }

    private static int getVector(Point point, Point point1, Point point2) {
        return ((point1.getX() - point.getX())
                * (point2.getY() - point1.getY()))
                - ((point2.getX() - point1.getX())
                * (point1.getY() - point.getY()));
    }

    private static boolean isValidateOnTriangle(Point x, Point y, Point z) {
        FindService findService = new FindServiceImpl();

        double firstSide = findService.findSide(x, y);
        double secondSide = findService.findSide(y, z);
        double thirdSide = findService.findSide(z, x);

        if (firstSide + secondSide > thirdSide) {
            if (secondSide + thirdSide > firstSide) {
                if (thirdSide + firstSide > secondSide) {
                    return true;
                }
            }
        }

        return false;
    }
}