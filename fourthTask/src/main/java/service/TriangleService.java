package service;

import entity.Point;
import entity.Triangle;
import lombok.extern.slf4j.Slf4j;
import service.impl.FindTriangleParametersServiceImpl;

@Slf4j
public class TriangleService {
    private static final FindTriangleParametersService findService = new FindTriangleParametersServiceImpl();

    private TriangleService() {
    }

    public static void isTriangle(Triangle triangle) throws IllegalArgumentException {
        isTriangle(triangle.getFirstPoint(), triangle.getSecondPoint(), triangle.getThirdPoint());
    }

    public static void isTriangle(Point firstPoint, Point secondPoint, Point thirdPoint) throws IllegalArgumentException {
        if (firstPoint == null || secondPoint == null || thirdPoint == null) {
            String message = "Point cannot be null, " +
                    " first = " + firstPoint +
                    " second = " + secondPoint +
                    " third = " + thirdPoint;
            log.error(message);
            throw new IllegalArgumentException(message);
        }

        validateIsTriangle(firstPoint, secondPoint, thirdPoint);
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

    private static void validateIsTriangle(Point x, Point y, Point z) throws IllegalArgumentException {
        double firstSide = findService.findSide(x, y);
        double secondSide = findService.findSide(y, z);
        double thirdSide = findService.findSide(z, x);

        if (!(firstSide + secondSide > thirdSide) &&
                !(secondSide + thirdSide > firstSide) &&
                !(thirdSide + firstSide > secondSide)) {
            throw new IllegalArgumentException("This points, don't can be used to create triangle");
        }
    }
}