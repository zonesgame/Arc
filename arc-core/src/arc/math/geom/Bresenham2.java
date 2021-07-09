package arc.math.geom;

import arc.struct.Array;
import arc.func.Intc2;
import arc.util.pooling.Pool;
import arc.util.pooling.Pools;

/**
 * Returns a list of points at integer coordinates for a line on a 2D grid, using the Bresenham algorithm.
 * <p>
 * <p>
 * Instances of this class own the returned array of points and the points themselves to avoid garbage struct as much as
 * possible. Calling any of the methods will result in the reuse of the previously returned array and vectors.
 * @author badlogic
 */
public class Bresenham2{
    private final Array<Point2> points = new Array<>();
    private final Pool<Point2> pool = Pools.get(Point2.class, Point2::new);

    /**
     * Iterates through a list of {@link Point2} instances along the given line, at integer coordinates.
     * @param startX the start x coordinate of the line
     * @param startY the start y coordinate of the line
     * @param endX the end x coordinate of the line
     * @param endY the end y coordinate of the line
     */
    public static void line(int startX, int startY, int endX, int endY, Intc2 consumer){

        int w = endX - startX;
        int h = endY - startY;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if(w < 0){
            dx1 = -1;
            dx2 = -1;
        }else if(w > 0){
            dx1 = 1;
            dx2 = 1;
        }
        if(h < 0)
            dy1 = -1;
        else if(h > 0) dy1 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if(longest <= shortest){
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if(h < 0)
                dy2 = -1;
            else if(h > 0) dy2 = 1;
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for(int i = 0; i <= longest; i++){
            consumer.get(startX, startY);
            numerator += shortest;
            if(numerator > longest){
                numerator -= longest;
                startX += dx1;
                startY += dy1;
            }else{
                startX += dx2;
                startY += dy2;
            }
        }
    }

    /**
     * Returns a list of {@link Point2} instances along the given line, at integer coordinates.
     * @param start the start of the line
     * @param end the end of the line
     * @return the list of points on the line at integer coordinates
     */
    public Array<Point2> line(Point2 start, Point2 end){
        return line(start.x, start.y, end.x, end.y);
    }

    /**
     * Returns a list of {@link Point2} instances along the given line, at integer coordinates.
     * @param startX the start x coordinate of the line
     * @param startY the start y coordinate of the line
     * @param endX the end x coordinate of the line
     * @param endY the end y coordinate of the line
     * @return the list of points on the line at integer coordinates
     */
    public Array<Point2> line(int startX, int startY, int endX, int endY){
        pool.freeAll(points);
        points.clear();
        return line(startX, startY, endX, endY, pool, points);
    }

    /**
     * Returns a list of {@link Point2} instances along the given line, at integer coordinates.
     * @param startX the start x coordinate of the line
     * @param startY the start y coordinate of the line
     * @param endX the end x coordinate of the line
     * @param endY the end y coordinate of the line
     * @param pool the pool from which Point2 instances are fetched
     * @param output the output array, will be cleared in this method
     * @return the list of points on the line at integer coordinates
     */
    public Array<Point2> line(int startX, int startY, int endX, int endY, Pool<Point2> pool, Array<Point2> output){

        int w = endX - startX;
        int h = endY - startY;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if(w < 0){
            dx1 = -1;
            dx2 = -1;
        }else if(w > 0){
            dx1 = 1;
            dx2 = 1;
        }
        if(h < 0)
            dy1 = -1;
        else if(h > 0) dy1 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if(longest <= shortest){
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if(h < 0)
                dy2 = -1;
            else if(h > 0) dy2 = 1;
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for(int i = 0; i <= longest; i++){
            Point2 point = pool.obtain();
            point.set(startX, startY);
            output.add(point);
            numerator += shortest;
            if(numerator > longest){
                numerator -= longest;
                startX += dx1;
                startY += dy1;
            }else{
                startX += dx2;
                startY += dy2;
            }
        }
        return output;
    }

    /**
     * Returns a list of {@link Point2} instances along the given line at integer coordinates, with no diagonals.
     * @param startX the start x coordinate of the line
     * @param startY the start y coordinate of the line
     * @param endX the end x coordinate of the line
     * @param endY the end y coordinate of the line
     * @param pool the pool from which Point2 instances are fetched
     * @param output the output array, will be cleared in this method
     * @return the list of points on the line at integer coordinates
     */
    public Array<Point2> lineNoDiagonal(int startX, int startY, int endX, int endY, Pool<Point2> pool, Array<Point2> output){
        int xDist = Math.abs(endX - startX);
        int yDist = -Math.abs(endY - startY);
        int xStep = (startX < endX ? +1 : -1);
        int yStep = (startY < endY ? +1 : -1);
        int error = xDist + yDist;

        output.add(pool.obtain().set(startX, startY));

        while(startX != endX || startY != endY){
            if(2 * error - yDist > xDist - 2 * error){
                error += yDist;
                startX += xStep;
            }else{
                error += xDist;
                startY += yStep;
            }

            output.add(pool.obtain().set(startX, startY));
        }
        return output;
    }
}
