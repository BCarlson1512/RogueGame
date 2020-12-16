package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.io.Serializable;

/**
 * Rogue game.
 */
public class Rogue implements Serializable {
    public static final char UP = 'h';
    public static final char DOWN = 'l';
    public static final char LEFT = 'j';
    public static final char RIGHT = 'k';
    private static final String[] DIRECTIONS = {"N", "S", "E", "W"};
    private String nextDisplay = "";
    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    private HashMap<String, Character> symbols = new HashMap<>();
    private Player player;
    private transient RogueParser parser;
    private String message;
    private static final long serialVersionUID = -6226964838890937898L;
    /**
     * Default constructor.
     */
    public Rogue() {
        player = new Player();
        parser = new RogueParser();
    }
    /**
     * Constructor that takes in a parser as an arg.
     * @param theDungeonParser (RogueParser) is the parser for dunegon info
     */
    public Rogue(RogueParser theDungeonParser) {
        this.parser = theDungeonParser;
        player = new Player();
        Map roomInfo = parser.nextRoom();
        Map itemInfo = parser.nextItem();
        parseData(itemInfo, roomInfo, parser);
        symbols = parser.getSymbolsMap();
        connectAllDoors();
        verifyRooms();
    }
    private void printRooms() { // used exclusively for debugging/modification of json files
        for (Room r: roomList) {
            String[][] temp = r.generateRoom(r.getHeight(), r.getWidth());
            for (int i = 0; i < r.getHeight(); i++) {
                for (int j = 0; j < r.getWidth(); j++) {
                    System.out.format("%s ", temp[i][j]);
                }
                System.out.println();
            }
        }
    }
    private void connectAllDoors() {
        for (String dir : DIRECTIONS) {
            connectDoors(dir);
        }
    }
    private void parseRooms(Map roomInfo, RogueParser dp) {
        while (roomInfo != null) {
            addRoom(roomInfo);
            roomInfo = dp.nextRoom();
        }
    }
    private void parseItems(Map itemInfo, RogueParser dp) {
        while (itemInfo != null) {
            addItem(itemInfo);
            itemInfo = dp.nextItem();
        }
    }
    private void parseData(Map itemInfo, Map roomInfo, RogueParser dp) {
        parseRooms(roomInfo, dp);
        parseItems(itemInfo, dp);
    }
    private void verifyRooms() {
        for (Room r : roomList) {
            try {
                r.verifyRoom();
            } catch (NotEnoughDoorsException e) {
                handleNoDoorsException(r);
            }
        }
    }
    private void handleNoDoorsException(Room r) {
        Door newDoor = new Door();
        for (Room s : roomList) {
            if (s != r) {
                doorExceptionCreation(s, r, newDoor);
            }
        }
    }
    private void doorExceptionCreation(Room newRoom, Room oldRoom, Door add) {
        HashMap<String, Door> newRoomDoors = newRoom.getDoorLocation();
        HashMap<String, Door> oldRoomDoors = oldRoom.getDoorLocation();
        Door newTempDoor;
        int success = 0;
        for (String dir : DIRECTIONS) {
            newTempDoor = newRoomDoors.get(dir);
            if (newTempDoor != null && newTempDoor.getConRoomId() == -1) {
                setExceptionDoor(newTempDoor, oldRoom);
                setExceptionDoor(add, newRoom);
                success = connectExceptionDoors(oldRoomDoors, newRoomDoors, newTempDoor, add, dir);
                break;
            }
        }
        checkSuccess(success);
    }
    private void checkSuccess(int success) {
        if (success == 0) {
            System.out.println("Cannot connect empty room, json file unusable... exiting program");
            System.exit(0);
        }
    }
    private int connectExceptionDoors(HashMap<String, Door> m1, HashMap<String, Door> m2, Door d1, Door d2, String dr) {
        m1.put(getOppositeDir(dr), d2);
        m2.put(dr, d1);
        return 1;
    }
    private void setExceptionDoor(Door d, Room r) {
        d.setConRoomId(r.getId());
        d.setWallPost(1);
        d.connectRoom(r);
    }
    /**
     * Sets the player of our game.
     * @param thePlayer (Player) the player of our game
     */
    public void setPlayer(Player thePlayer) {
        player = thePlayer;
    }
    /**
     * get the list of rooms.
     * @return roomList (ArrayList) list of rooms read from the json file
     */
    public ArrayList<Room> getRooms() {
        return roomList;
    }
    /**
     * get the list of items in a room.
     * @return itemList (ArrayList) list of items in each room
     */
    public ArrayList<Item> getItems() {
        return itemList;
    }
    /**
     * get the player.
     * @return player (Player) our player
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * This method will check to see if the player's move is valid and update the game board if so.
     * @param input (char) the player's move field.
     * @return the updated game field.
     * @throws InvalidMoveException when an invalid move is detected.
     */
    public String makeMove(char input) throws InvalidMoveException {
        Room r = getPlayerRoom();
        Point newLocation = updatePlayerLocation(input, r);
        message = "Nice move ;)";
        if (!generateMove(r, newLocation)) {
            throw new InvalidMoveException();
        }
        return message;
    }
    private boolean generateMove(Room r, Point newMove) {
        String[][] room = r.generateRoom(r.getHeight(), r.getWidth());
        player.setCurrentRoom(r);
        int x = (int) newMove.getX();
        int y = (int) newMove.getY();
        if (room[x][y].equals("NS_WALL") || room[x][y].equals("EW_WALL")) {
            return false;
        }
        handleItemCollision(r, x, y);
        if (room[x][y].equals("DOOR")) {
            handleDoorCollision(r, newMove, player);
            message = "Moving to next room...";
        } else {
            updatePlayerAndRoom(player, r, newMove);
        }
        return true;
    }
    private void updatePlayerAndRoom(Player p, Room r, Point newMove) {
        player.setXyLocation(newMove);
        r.setPlayer(player);
    }
    private String handleItemCollision(Room r, int x, int y) {
        ArrayList<Item> roomItems = r.getRoomItems();
        Item item = null;
        for (Item i: roomItems) {
            if (x == (int) i.getXyLocation().getX() && y == (int) i.getXyLocation().getY()) {
                item = i;
                message = "You picked up " + item.getName();
            }
        }
        player.addItem(item);
        roomItems.remove(item);
        r.setRoomItems(roomItems);
        return message;
    }
    private void handleDoorCollision(Room r, Point newMove, Player p) {
        String dir = getInitialDir(newMove, r);
        Door d = r.getDoor(dir);
        Room newRoom = d.getOtherRoom(r);
        Door newD = newRoom.getDoor(getOppositeDir(dir));
        r.setPlayer(null);
        newRoom.setPlayer(p);
        Point move = generateMove(dir, newRoom, newD);
        updatePlayerAndRoom(p, newRoom, move);
    }
    private String getInitialDir(Point p, Room r) {
        if (p.getY() == r.getWidth() - 1) {
            return "E";
        }
        if (p.getY() == 0) {
            return "W";
        }
        if (p.getX() == r.getHeight() - 1) {
            return "S";
        }
        if (p.getX() == 0) {
            return "N";
        }
        return null;
    }
    private Point generateMove(String dir, Room r, Door d) {
        Point move = null;
        if (dir.equals("N")) {
            move = new Point(r.getHeight() - 1, d.getWallPos());
        }
        if (dir.equals("S")) {
            move = new Point(0, d.getWallPos());
        }
        if (dir.equals("W")) {
            move = new Point(d.getWallPos(), r.getWidth() - 1);
        }
        if (dir.equals("E")) {
            move = new Point(d.getWallPos(), 0);
        }
        return move;
    }
    private String getOppositeDir(String dir) {
        if (dir.equals("N")) {
            return "S";
        }
        if (dir.equals("S")) {
            return "N";
        }
        if (dir.equals("W")) {
            return "E";
        }
        if (dir.equals("E")) {
            return "W";
        }
        return null;
    }
    private Room getPlayerRoom() {
        for (Room r: roomList) {
            if (r.isPlayerInRoom()) {
                return r;
            }
        }
        return null;
    }
    private Point updatePlayerLocation(char input, Room r) {
        Point newLocation;
        double x = player.getXyLocation().getX();
        double y = player.getXyLocation().getY();
        if (input == UP) {
            x--;
        } else if (input == DOWN) {
            x++;
        } else if (input == LEFT) {
            y--;
        } else if (input == RIGHT) {
            y++;
        }
        newLocation = validateMove(x, y, r);
        return newLocation;
    }
    private double validateX(double x, Room r) {
        if (x < 0) {
            x = 0;
        }
        if (x > r.getHeight() - 1) {
            x = r.getHeight() - 1;
        }
        return x;
    }
    private double validateY(double y, Room r) {
        if (y < 0) {
            y = 0;
        }
        if (y > r.getWidth() - 1) {
            y = r.getWidth() - 1;
        }
        return y;
    }
    private Point validateMove(double x, double y, Room r) {
        double validX = validateX(x, r);
        double validY = validateY(y, r);
        Point validMove = new Point((int) validX, (int) validY);
        return validMove;
    }
    /**
     * This returns the updated display string.
     * @return (String) generated display string.
     */
    public String getNextDisplay() {
        Room r = getPlayerRoom();
        nextDisplay = "";
        nextDisplay = getNextRoom(r);
        return nextDisplay;
    }
    private String getNextRoom(Room r) {
        String[][]keys = r.generateRoom(r.getHeight(), r.getWidth());
        String txtRoom = "";
        for (int i = 0; i < r.getHeight(); i++) {
            for (int j = 0; j < r.getWidth(); j++) {
                txtRoom += symbols.get(keys[i][j]);
            }
            txtRoom += "\n";
        }
        return txtRoom;
    }
    /**
     * Add a room from the parser into the list of rooms in the game.
     * @param toAdd (Map) the parsed room data.
     */
    public void addRoom(Map<String, String> toAdd) {
        int id = Integer.parseInt(toAdd.get("id"));
        int height = Integer.parseInt(toAdd.get("height"));
        int width = Integer.parseInt(toAdd.get("width"));
        HashMap<String, Door> doors = setDoors(toAdd);
        Room newRoom = new Room(id, width, height);
        newRoom.setStartingRoom(setStarting(toAdd.get("start"), newRoom));
        newRoom.setDoorLocation(doors);
        roomList.add(newRoom);
    }
    private boolean setStarting(String start, Room r) {
        if (start.equals("true")) {
            player.setCurrentRoom(r);
            r.setPlayer(player);
            return true;
        }
        return false;
    }
    private HashMap<String, Door> setDoors(Map<String, String> toAdd) {
        HashMap<String, Door> doors = new HashMap<>();
        for (String dir: DIRECTIONS) {
            setDoorInfo(dir, toAdd, doors);
        }
        return doors;
    }
    private void setDoorInfo(String dir, Map<String, String> toAdd, HashMap<String, Door> h) {
        Door d = new Door();
        d.setWallPost(Integer.parseInt(toAdd.get(dir + "_wall_pos")));
        d.setConRoomId(Integer.parseInt(toAdd.get(dir + "_con_room")));
        h.put(dir, d);
    }
    private void connectDoors(String dir) {
        for (Room r : roomList) {
            for (Room s : roomList) {
                Door tempDoor = r.getDoor(dir);
                if (tempDoor.getConRoomId() == s.getId()) {
                    tempDoor.connectRoom(s);
                    tempDoor.connectRoom(r);
                }
            }
        }
    }
    /**
     * Add an item into the game from the parser.
     * @param toAdd (Map) the parsed item data.
     */
    public void addItem(Map<String, String> toAdd) {
        Item newItem = createItem(toAdd);
        if (toAdd.containsKey("x") && toAdd.containsKey("y")) {
            Point newXyLocation = new Point(Integer.parseInt(toAdd.get("y")), Integer.parseInt(toAdd.get("x")));
            newItem.setXyLocation(newXyLocation);
        }
        if (toAdd.containsKey("room")) {
            int roomid = Integer.parseInt(toAdd.get("room"));
            addItemToRoom(newItem, roomid);
        }
        itemList.add(newItem);
    }
    private Item createItem(Map<String, String> toAdd) {
        return itemBasedOnType(toAdd);
    }
    private Item itemBasedOnType(Map<String, String> toAdd) {
        String itemType = toAdd.get("type");
        if (itemType.equals("Magic")) {
            return createMagicItem(toAdd);
        } else if (itemType.equals("Clothing")) {
            return createClothingItem(toAdd);
        } else if (itemType.equals("Food")) {
            return createFood(toAdd);
        } else if (itemType.equals("SmallFood")) {
            return createSmallFood(toAdd);
        } else if (itemType.equals("Potion")) {
            return createPotion(toAdd);
        } else if (itemType.equals("Ring")) {
            return createRing(toAdd);
        } else if (itemType.equals("Magic") && toAdd.get("name").contains("Scroll")) {
            return createScroll(toAdd);
        }
        return createBasicItem(toAdd);
    }
    private Item createScroll(Map<String, String> toAdd) {
        Item scrollItem = new Scroll();
        setBasicItemData(scrollItem, toAdd);
        return scrollItem;
    }
    private Item createBasicItem(Map<String, String> toAdd) {
        Item newItem = new Item();
        setBasicItemData(newItem, toAdd);
        return newItem;
    }
    private void setBasicItemData(Item i, Map<String, String> toAdd) {
        i.setName(toAdd.get("name"));
        i.setType(toAdd.get("type"));
        i.setDescription(toAdd.get("description"));
        i.setId(Integer.parseInt(toAdd.get("id")));
    }
    private Item createPotion(Map<String, String> toAdd) {
        Item potItem = new Potion();
        setBasicItemData(potItem, toAdd);
        return potItem;
    }
    private Item createRing(Map<String, String> toAdd) {
        Item ringItem = new Ring();
        setBasicItemData(ringItem, toAdd);
        return ringItem;
    }
    private Item createSmallFood(Map<String, String> toAdd) {
        Item smallFoodItem = new SmallFood();
        setBasicItemData(smallFoodItem, toAdd);
        return smallFoodItem;
    }
    private Item createFood(Map<String, String> toAdd) {
        Item foodItem = new Food();
        setBasicItemData(foodItem, toAdd);
        return foodItem;
    }
    private Item createMagicItem(Map<String, String> toAdd) {
        Item magicItem = new Magic();
        setBasicItemData(magicItem, toAdd);
        return magicItem;
    }
    private Item createClothingItem(Map<String, String> toAdd) {
        Item clothingItem = new Clothing();
        setBasicItemData(clothingItem, toAdd);
        return clothingItem;
    }
    private void addItemToRoom(Item i, int roomId) {
        Room r = roomList.get(roomId - 1);
        i.setCurrentRoom(roomList.get(roomId - 1));
        try {
            r.addItem(i);
        } catch (NoSuchItemException e) {
            r.getRoomItems().remove(i);
        } catch (ImpossiblePositionException e) {
            r.getRoomItems().remove(i);
            handleImpossiblePosition(i, r);
            r.getRoomItems().add(i);
        }
    }
    private void handleImpossiblePosition(Item c, Room r) {
        String[][] room = r.generateRoom(r.getHeight(), r.getWidth());
        Point newXyLocation;
        for (int i = 1; i < r.getHeight(); i++) {
            for (int j = 1; j < r.getWidth(); j++) {
                if (room[i][j].equals("FLOOR")) {
                    newXyLocation = new Point(i, j);
                    c.setXyLocation(newXyLocation);
                    break;
                }
            }
        }
    }
}
