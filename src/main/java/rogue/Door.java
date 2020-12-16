package rogue;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Simple doors in rogue.
 */
public class Door implements Serializable {
    private ArrayList<Room> connectedRooms;
    private int wallPos;
    private int conRoomId;
    private static final long serialVersionUID = 6502566130648073736L;
    /**
     * Default constructor.
     */
    public Door() {
        connectedRooms = new ArrayList<>();
        wallPos = -1;
        conRoomId = -1;
    }

    /**
     * Sets the id of the connected room.
     * @param newConRoomId (int) New room id of the conneted room
     */
    public void setConRoomId(int newConRoomId) {
        this.conRoomId = newConRoomId;
    }

    /**
     * returns the id of the connected room.
     * @return (int) connected room id
     */
    public int getConRoomId() {
        return conRoomId;
    }

    /**
     * Specifies one of two rooms connected by our door.
     * @param r (Room) the room connected to our door
     */
    public void connectRoom(Room r) {
        connectedRooms.add(r);
    }
    /**
     * Get which rooms are connected by our door.
     * @return (Arraylist) List of rooms connected by our door.
     */
    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }
    /**
     * Gets the room connected to our door.
     * @param currentRoom (Room) the current room we are in.
     * @return (Room) The other room connected to our doors
     */
    public Room getOtherRoom(Room currentRoom) {
        for (Room r: connectedRooms) {
            if (r != currentRoom) {
                return r;
            }
        }
        return null;
    }
    /**
     * gets the Wall Position of the door.
     * @return (int) WallPos of the door.
     */
    public int getWallPos() {
        return wallPos;
    }
    /**
     * Sets the Wall Position of the door.
     * @param newWallPos (int) new Wall Position of the door.
     */
    public void setWallPost(int newWallPos) {
        this.wallPos = newWallPos;
    }
}
