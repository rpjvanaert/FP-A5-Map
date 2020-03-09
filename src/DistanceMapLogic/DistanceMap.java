package DistanceMapLogic;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;

public class DistanceMap {
    private String mapName;
    private int[][] map;
    private Queue<Point2D> queue;
    private Boolean[][] visited;
    private Boolean[][] walkableMap;

    /**
     * Makes a distanceMap which can be used to find the shortest path
     * @param mapName The name of the map
     * @param targetArea The point from which the DistanceMap starts (the 0 point)
     * @param walkableMap A map used to know if the surface is walkable for the NPC's
     */
    public DistanceMap(String mapName, TargetArea targetArea, WalkableMap walkableMap) {
        this.mapName = mapName;
        this.map = new int[100][100]; //todo uitlezen uit json
        this.visited = new Boolean[100][100];
        this.walkableMap = walkableMap.getMap();

        this.queue = new LinkedList<>();
        this.queue.offer(targetArea.getMiddlePoint());
        this.map[(int)targetArea.getMiddlePoint().getX()][(int)targetArea.getMiddlePoint().getY()] = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                this.visited[i][j] = false;
            }
        }

        this.visited[(int)targetArea.getMiddlePoint().getX()][(int)targetArea.getMiddlePoint().getY()] = true;
        BFS();
    }

    /**
     * The Breadth-First Search Algorithm
     * Loops trough every accessible point in the map and gives it the corresponding distance value
     */
    private void BFS() {
        if (!this.queue.isEmpty()) {
            Point2D currentPoint = this.queue.poll();
            int currentX = (int) currentPoint.getX();
            int currentY = (int) currentPoint.getY();
            //north
                try {
                    if (this.visited[currentX][currentY + 1] == false && this.walkableMap[currentX][currentY + 1] == true) {
                        this.map[currentX][currentY + 1] = this.map[currentX][currentY] + 1;
                        this.queue.offer(new Point2D.Double(currentX, currentY + 1));
                        this.visited[currentX][currentY + 1] = true;
                    }
                } catch (Exception Ignore) {}
            //east
                try {
                    if (this.visited[currentX + 1][currentY] == false && this.walkableMap[currentX + 1][currentY] == true) {
                        this.map[currentX + 1][currentY] = this.map[currentX][currentY] + 1;
                        this.queue.offer(new Point2D.Double(currentX + 1, currentY));
                        this.visited[currentX + 1][currentY] = true;
                    }
                } catch (Exception Ignore) {}
            //south
                try {
                    if (this.visited[currentX][currentY - 1] == false && this.walkableMap[currentX][currentY - 1] == true) {
                        this.map[currentX][currentY - 1] = this.map[currentX][currentY] + 1;
                        this.queue.offer(new Point2D.Double(currentX, currentY - 1));
                        this.visited[currentX][currentY - 1] = true;
                    }
                } catch (Exception Ignore) {}
            //west
                try {
                    if (this.visited[currentX - 1][currentY] == false && this.walkableMap[currentX - 1][currentY] == true) {
                        this.map[currentX - 1][currentY] = this.map[currentX][currentY] + 1;
                        this.queue.offer(new Point2D.Double(currentX - 1, currentY));
                        this.visited[currentX - 1][currentY] = true;
                    }
                } catch (Exception Ignore) {}
            BFS();
        }
    }

    public int[][] getMap() {
        return map;
    }

    public String getMapName() {
        return mapName;
    }
}
