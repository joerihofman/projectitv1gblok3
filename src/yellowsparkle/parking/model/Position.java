package yellowsparkle.parking.model;

/**
 * Position object used to point at a specific parking space
 */
public class Position {
    private final String building;
    private final int floor;
    private final int row;
    private final int place;
    private int renderX;
    private int renderY;

    private final static int prime = 31;

    /**
     * Constructor for Position
     * @param building Building the position is in
     * @param floor    Floor the position is in
     * @param row      Row the position is in
     * @param place    Place in the row the position points to
     */
    public Position(String building, int floor, int row, int place) {
        this(building, floor, row, place, -1, -1);
    }

    /**
     * Constructor for Position
     *
     * @param building Building the position is in
     * @param floor    Floor the position is in
     * @param row      Row the position is in
     * @param place    Place in the row the position points to
     */
    public Position(String building, int floor, int row, int place, int renderX, int renderY) {
        this.building = building;
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.renderX = renderX;
        this.renderY = renderY;
    }

    /**
     * Getter for building
     * @return Building the position is in
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Getter for floor
     * @return Floor the position is in
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Getter for row
     * @return Row the position is in
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for place
     * @return Place in the row the position points to
     */
    public int getPlace() {
        return place;
    }

    /**
     * toString override
     *
     * @return String representation of this position
     */
    @Override
    public String toString() {
        return "Position:" + building + ":" + floor + ":" + row + ":" + place;
    }

    /**
     * HashCode override to ignore the given rendering coordinates
     * @return HashCode of this object
     */
    @Override
    public int hashCode() {
        return (((building.hashCode() * prime + floor) * prime + row) * prime + place) * prime;
    }

    /**
     * Override for equals to match
     *
     * @param o object to compare against
     * @return If the object is a position pointing at the same space
     */
    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Position && ((Position) o).building.equals(building) && ((Position) o).floor == floor && ((Position) o).row == row && ((Position) o).place == place;
    }

    /**
     * Getter for render X
     * @return Rendering X coordinate
     */
    public int getRenderX() {
        return renderX;
    }

    /**
     * Setter for render X
     * @param renderX Rendering X coordinate
     */
    public void setRenderX(int renderX) {
        this.renderX = renderX;
    }

    /**
     * Getter for render Y
     * @return Rendering Y coordinate
     */
    public int getRenderY() {
        return renderY;
    }

    /**
     * Setter for render Y
     * @param renderY Rendering Y coordinate
     */
    public void setRenderY(int renderY) {
        this.renderY = renderY;
    }
}
