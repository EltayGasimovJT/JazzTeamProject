package service;

import entity.Point;
import entity.Triangle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.impl.FindTriangleParametersServiceImpl;

public class TriangleService {
    private static final Logger LOGGER = LogManager.getLogger();

    private TriangleService() {
    }

    public static boolean isTriangle(Triangle triangle) {
        return isTriangle(triangle.getFirstPoint(), triangle.getSecondPoint(), triangle.getThirdPoint());
    }

    public static boolean isTriangle(Point firstPoint, Point secondPoint, Point thirdPoint) throws IllegalArgumentException {
        if (firstPoint == null || secondPoint == null || thirdPoint == null) {
            String message = "Point cannot be null, " +
                    " first = " + firstPoint +
                    " second = " + secondPoint +
                    " third = " + thirdPoint;
            LOGGER.log(Level.ERROR, message);
            throw new IllegalArgumentException(message);
        }

        return isValidateOnTriangle(firstPoint, secondPoint, thirdPoint);
    }

    public static boolean isBelongsToTriangle(Point point, Triangle triangle) {
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
        FindTriangleParametersService findService = new FindTriangleParametersServiceImpl();

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
