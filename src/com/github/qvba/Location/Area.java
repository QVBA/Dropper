package com.github.qvba.Location;

public class Area {

    private Coordinate leftTop;
    private Coordinate rightBottom;

    //Four corners. For internal use.
    private int minY;
    private int minX;
    private int maxY;
    private int maxX;

    public Area(Coordinate leftTop, Coordinate rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        minY = leftTop.getY();
        minX = leftTop.getX();
        maxY = rightBottom.getY();
        maxX = rightBottom.getX();
    }

    public Coordinate getRightBottom() {
        return rightBottom;
    }

    public Coordinate getLeftTop() {
        return leftTop;
    }

    public boolean areaContains(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        return !(x < minX || y < minY || x > maxX || y > maxY);
    }

    public boolean areaContains(Area area) {
        for(Coordinate coord : area.getCoordinates()) {
            if(areaContains(coord)) return true;
        }
        return false;
    }

    public Coordinate[] getCoordinates() {
        int yLength = maxY - minY;
        int xLength = maxX - minX;
        int listSize = yLength * xLength;

        int currentX = minX;
        int currentY = minY;

        Coordinate[] coords = new Coordinate[listSize];
        for(int i = 0; i < listSize; i++) {
            if(currentX > maxX) {
                currentY++;
                currentX = minX;
            }
            coords[i] = new Coordinate(currentX, currentY);
            currentX++;
        }

        return coords;
    }

}

