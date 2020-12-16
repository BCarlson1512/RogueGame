package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import java.io.Serializable;
/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room implements Serializable {

  private int width;
  private int height;
  private int identificationNum;
  private String[][] room;

  private static final String PASSAGE = "PASSAGE";
  private static final String DOOR = "DOOR";
  private static final String FLOOR = "FLOOR";
  private static final String PLAYER = "PLAYER";
  private static final String NS_WALL = "NS_WALL";
  private static final String EW_WALL = "EW_WALL";
  private Player player;

  private ArrayList<Item> itemList;

  private HashMap<String, Door> doorLocation;

  private boolean startingRoom;

  private static final long serialVersionUID = 1820927923646774988L;
/**
 * Default constructor.
 * */
 public Room() {
  this.width = 0;
  this.height = 0;
  this.identificationNum = -1;
  itemList = new ArrayList<>();
  doorLocation = new HashMap<>();
 }

 /**
  * Constructor for new rooms, given the following params.
  * @param id (int) the identification number of the room
  * @param w (int) The maximum width (number of columns) of the room
  * @param h (int) The maximum height (number of rows) of the room
  */
 public Room(int id, int w, int h) {
    setWidth(w);
    setHeight(h);
    setId(id);
    itemList = new ArrayList<>();
    doorLocation = new HashMap<>();
 }

  /**
   * Gets the hashmap that stores door locations in the room.
   * @return (HashMap) hashmap containing door locations.
   */
 public HashMap<String, Door> getDoorLocation() {
    return doorLocation;
 }
 /**
  * sets the hashmap for door locations in the room.
  * @param doorLoc (HashMap) the hashmap containing door locations
  */
 public void setDoorLocation(HashMap<String, Door> doorLoc) {
    this.doorLocation = doorLoc;
 }
/**
 * This function returns the value of width.
 * @return (int) width
 */
 public int getWidth() {
   return width;
 }
 /**
 * This program sets a new width for our object room.
 * @param newWidth (int) the new width
 */
 public void setWidth(int newWidth) {
      width = newWidth;
 }
 /**
  * Returns the height of our room.
  * @return (int) value for height
  */
 public int getHeight() {
   return height;
 }
 /**
  * Sets a new height for our object.
  * @param newHeight (int) our new height
  */
 public void setHeight(int newHeight) {
      height = newHeight;
 }
 /**
  * returns the Id number of our room.
  * @return (int) room's identification number
  */
 public int getId() {
    return identificationNum;
 }
 /**
 * Sets a new Id number for our room.
 * @param newId (int) the new Id number for our room
 */
 public void setId(int newId) {
   identificationNum = newId;
 }
 /**
 * gets the list of items from our room.
 * @return (ArrayList) list of items
 */
 public ArrayList<Item> getRoomItems() {
    return itemList;
 }
 /**
 *sets the list of items in our room.
 * @param newRoomItems (ArrayList) is the list of items to be stored in our room object
 */
 public void setRoomItems(ArrayList<Item> newRoomItems) {
      itemList = newRoomItems;
 }
 /**
  * adds item to list of items in the room.
  * @param toAdd (Item) The item being added into the room
  * @throws ImpossiblePositionException -When the item being placed is out of bounds, on a wall, existing item or player
  * @throws NoSuchItemException -No items with Id # belong in this room
  */
 public void addItem(Item toAdd) throws NoSuchItemException, ImpossiblePositionException {
      Point xyLoc = toAdd.getXyLocation();
      itemList.add(toAdd);
      if (!verifyPosition(xyLoc)) {
        throw new ImpossiblePositionException();
      }
      for (Item e : itemList) {
        if (e.getCurrentRoom().getId() != toAdd.getCurrentRoom().getId()) {
          throw new NoSuchItemException();
        }
      }
 }
/**
 * adds item to room. usage case is for toss.
 * @param toAdd (ITEM) ITEM THAT WAS YEETED INTO THE ROOM
 */
 public void addItemToRoom(Item toAdd) {
  itemList.add(toAdd);
 }
 private boolean verifyPosition(Point xyLoc) {
   if (xyLoc.getX() < 0 || xyLoc.getX() > width || xyLoc.getY() < 0 || xyLoc.getY() > height) {
    return false;
   }
   if (xyLoc.getX() == 0 || xyLoc.getX() == width || xyLoc.getY() == 0 || xyLoc.getY() == height) {
    return false;
  }
   return true;
 }
 /**
  * Gets the player in the room.
  * @return the player in the room, null represents no player
  */
 public Player getPlayer() {
    return player;
 }
 /**
  * creates a new player in the room.
  * @param newPlayer (Player) the new player being added to the room
  */
 public void setPlayer(Player newPlayer) {
   player = newPlayer;
 }
 /**
  * This gets the direction of our door N,S,E,W.
  * @param direction N,S,E,W determines which wall the door is on
  * @return (Door) returns the door object corresponding with that location
  */
 public Door getDoor(String direction) {
    return doorLocation.get(direction);
 }
/**
 * This function checks if our player is currently in the room, null means the player is not.
 * @return false when our player is not in the room, true when our player is in the room
 */
public boolean isPlayerInRoom() {
   if (player == null) {
        return false;
   }
    return true;
}
/**
 * Returns the value of startingRoom.
 * @return (boolean) if the room is the starting room return true, false otherwise
 */
public boolean getStartingRoom() {
    return startingRoom;
}
/**
 * Sets the value of starting room.
 * @param newStartingRoom (true) is the room is the starting room, (false) if its not
 */
public void setStartingRoom(boolean newStartingRoom) {
    startingRoom = newStartingRoom;
}
/**
 * This method generates a matrix of string based keys, this will be generated as symbols later.
 * @param ht (int) is the height of the room used as rows in our 2d array
 * @param wdt (int) is the width of the room, used as columns in our 2d array
 * @return (String[][]) an array of string based keys that represents the room.
 */
public String[][] generateRoom(int ht, int wdt) {
    room = new String[ht][wdt];
    for (int i = 0; i < ht; i++) {
        for (int j = 0; j < wdt; j++) {
            room[i][j] = FLOOR;
            setWalls(i, j);
        }
    }
    setDoors();
    setItems();
    setPlayerKey();
    return room;
}
private void setWalls(int x, int y) {
  if (y == 0 || y == width - 1) {
    room[x][y] = EW_WALL;
  }
  if (x == 0 || x == height - 1) {
    room[x][y] = NS_WALL;
  }
}
private void setDoors() {
  Door nDoor = doorLocation.get("N");
  Door sDoor = doorLocation.get("S");
  Door wDoor = doorLocation.get("W");
  Door eDoor = doorLocation.get("E");
  if (nDoor.getWallPos() > 0 && nDoor.getWallPos() < width) {
    room[0][nDoor.getWallPos()] = DOOR;
  }
  if (sDoor.getWallPos() > 0 && sDoor.getWallPos() < width) {
    room[height - 1][sDoor.getWallPos()] = DOOR;
  }
  if (wDoor.getWallPos() > 0 && sDoor.getWallPos() < height) {
    room[wDoor.getWallPos()][0] = DOOR;
  }
  if (eDoor.getWallPos() > 0 && sDoor.getWallPos() < height) {
    room[eDoor.getWallPos()][width - 1] = DOOR;
  }
}

private void setItems() {
  for (Item i : itemList) {
    int x = (int) i.getXyLocation().getX();
    int y = (int) i.getXyLocation().getY();
    room[x][y] = i.getType().toUpperCase();
    if (i.getName().contains("Scroll")) {
      room[x][y] = "SCROLL";
    }
  }
}
private void setPlayerKey() {
  if (isPlayerInRoom()) {
    int xLoc = (int) player.getXyLocation().getX();
    int yLoc = (int) player.getXyLocation().getY();
    room[xLoc][yLoc] = PLAYER;
  }
}
/**
 * Gets a key generated version of the room.
 * @return (String[][]) array of string keys that represent data in the room.
 */
public String[][] getRoom() {
  return room;
}
  /**
    * Verfies that the room is correctly displayed.
    * @return (true) if the room is verified, (false) if it is not verified.
    * @throws NotEnoughDoorsException Thrown when no doors are generated into a room.
    */
  public boolean verifyRoom() throws NotEnoughDoorsException {
        int numDoors = 0;
        String[][]r = generateRoom(height, width);
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            if (r[i][j].equals("DOOR")) {
              numDoors++;
            }
          }
        }
        if (numDoors == 0) {
          throw new NotEnoughDoorsException();
        }
    if (checkDoors() && checkItems() && checkPlayer()) {
      return true;
    }
    return false;
  }
  private boolean checkItems() {
    String[][]r = generateRoom(height, width);
    int x;
    int y;
    for (Item i: itemList) {
      x = (int) i.getXyLocation().getX();
      y = (int) i.getXyLocation().getY();
      if (r[x][y] != i.getType()) {
        return false;
      }
    }
    return true;
  }
  private boolean checkDoors() {
    int numDoors = 0;
    Door nDoor = doorLocation.get("N");
    Door sDoor = doorLocation.get("S");
    Door wDoor = doorLocation.get("W");
    Door eDoor = doorLocation.get("E");
    numDoors += readEWWalls(height, eDoor, wDoor);
    numDoors += readNSWalls(width, nDoor, sDoor);
    if (numDoors == doorLocation.size()) {
      return true;
    }
    return false;
  }

  private int readNSWalls(int w, Door nDoor, Door sDoor) {
    int numDoors = 0;
    for (int i = 0; i < w; i++) {
      if (i == nDoor.getWallPos() || i == sDoor.getWallPos()) {
        numDoors++;
      }
    }
    return numDoors;
  }

  private int readEWWalls(int h, Door eDoor, Door wDoor) {
    int numDoors = 0;
    for (int i = 0; i < h; i++) {
      if (i == wDoor.getWallPos() || i == eDoor.getWallPos()) {
        numDoors++;
      }
    }
    return numDoors;
  }

  private boolean checkPlayer() {
    int numPlayer = 0;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (i == player.getXyLocation().getX() && j == player.getXyLocation().getY()) {
          numPlayer++;
        }
      }
    }
    if (!isPlayerInRoom() && numPlayer == 0) {
      return true;
    }
    if (isPlayerInRoom() && numPlayer == 1) {
      return true;
    }
    return false;
  }
}
