package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser {

    private ArrayList<Map<String, String>> rooms = new ArrayList<>();
    private ArrayList<Map<String, String>> items = new ArrayList<>();
    private ArrayList<Map<String, String>> itemLocations = new ArrayList<>();
    private HashMap<String, Character> symbols = new HashMap<>();

    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> itemIterator;

    private int numOfRooms = -1;
    private int numOfItems = -1;

    /**
     * Default constructor.
     */
    public RogueParser() {

    }

    /**
     * Constructor that takes filename and sets up the object.
     * @param filename  (String) name of file that contains file location for rooms and symbols
     */
    public RogueParser(String filename) {
        parse(filename);
    }
    /**
     * Takes two filenames to setup a new parser.
     * @param fileName1 (String) file location of the rooms file.
     * @param fileName2 (String) file location of filelocations.json
     */
    public RogueParser(String fileName1, String fileName2) {
        parse(fileName1, fileName2);
    }

    /**
     * Return the next room.
     * @return (Map) Information about a room
     */
    public Map nextRoom() {
        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the next item.
     * @return (Map) Information about an item
     */
    public Map nextItem() {
        if (itemIterator.hasNext()) {
            return itemIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Get the character for a symbol.
     * @param symbolName (String) Symbol Name
     * @return (Character) Display character for the symbol
     */
    public Character getSymbol(String symbolName) {
        if (symbols.containsKey(symbolName)) {
            return symbols.get(symbolName);
        }
        return null;
    }

    /**
     * Get the number of items.
     * @return (int) Number of items
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     * Get the number of rooms.
     * @return (int) Number of rooms
     */
    public int getNumOfRooms() {
        return numOfRooms;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     */
    private void parse(String filename) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject configurationJSON = (JSONObject) obj;
            String roomsFileLocation = (String) configurationJSON.get("Rooms");
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");
            Object roomsObj = parser.parse(new FileReader(roomsFileLocation));
            JSONObject roomsJSON = (JSONObject) roomsObj;
            Object symbolsObj = parser.parse(new FileReader(symbolsFileLocation));
            JSONObject symbolsJSON = (JSONObject) symbolsObj;
            extractInfo(symbolsJSON, roomsJSON);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }
    }

    private void parse(String filename, String roomsFilename) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject configurationJSON = (JSONObject) obj;
            String roomsFileLocation = roomsFilename;
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");
            Object roomsObj = parser.parse(new FileReader(roomsFileLocation));
            JSONObject roomsJSON = (JSONObject) roomsObj;
            Object symbolsObj = parser.parse(new FileReader(symbolsFileLocation));
            JSONObject symbolsJSON = (JSONObject) symbolsObj;
            extractInfo(symbolsJSON, roomsJSON);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }
    }

    private void extractInfo(JSONObject symbolsJSON, JSONObject roomsJSON) {
            extractRoomInfo(roomsJSON);
            extractItemInfo(roomsJSON);
            extractSymbolInfo(symbolsJSON);
            setupIterators();
    }

    private void setupIterators() {
        roomIterator = rooms.iterator();
        itemIterator = items.iterator();
    }

    /**
     * Get the symbol information.
     * @param symbolsJSON  (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject symbolsJSON) {
        JSONArray symbolsJSONArray = (JSONArray) symbolsJSON.get("symbols");
        // Make an array list of room information as maps
        for (int i = 0; i < symbolsJSONArray.size(); i++) {
            JSONObject symbolObj = (JSONObject) symbolsJSONArray.get(i);
            symbols.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")).charAt(0));
        }
    }
    /**
     * Gets the symbols hashmap (for other classes).
     * @return hashmap containing all symbols
     */
    public HashMap<String, Character> getSymbolsMap() {
        return symbols;
    }
    /**
     * Get the room information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject roomsJSON) {
        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");
        // Make an array list of room information as maps
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            rooms.add(singleRoom((JSONObject) roomsJSONArray.get(i)));
            numOfRooms += 1;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {
        Map<String, String> room = new HashMap<>();
        setBasicRoomData(room, roomJSON);
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int j = 0; j < doorArray.size(); j++) {
            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = doorObj.get("dir").toString();
            room.replace(dir + "_con_room", doorObj.get("con_room").toString());
            room.replace(dir + "_wall_pos", doorObj.get("wall_pos").toString());
        }
        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        for (int j = 0; j < lootArray.size(); j++) {
            JSONObject lootObj = (JSONObject) lootArray.get(j);
            itemLocations.add(itemPosition((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }
        return room;
    }

    private void setBasicRoomData(Map<String, String> rm, JSONObject roomJSON) {
        rm.put("id", roomJSON.get("id").toString());
        rm.put("start", roomJSON.get("start").toString());
        rm.put("height", roomJSON.get("height").toString());
        rm.put("width", roomJSON.get("width").toString());
        rm.put("N_wall_pos", "-1");
        rm.put("S_wall_pos", "-1");
        rm.put("W_wall_pos", "-1");
        rm.put("E_wall_pos", "-1");
        rm.put("N_con_room", "-1");
        rm.put("S_con_room", "-1");
        rm.put("W_con_room", "-1");
        rm.put("E_con_room", "-1");
    }

    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map) Contains information about the item, where it is and what room
     */
    private Map<String, String>  itemPosition(JSONObject lootJSON, String roomID) {
        HashMap<String, String> loot = new HashMap<>();
        loot.put("room", roomID);
        loot.put("id", lootJSON.get("id").toString());
        loot.put("x", lootJSON.get("x").toString());
        loot.put("y", lootJSON.get("y").toString());
        return loot;
    }
    /**
     * Get the Item information from the Item key.
     * @param roomsJSON (JSONObject) The entire JSON file that contains keys for room and items
     */
    private void extractItemInfo(JSONObject roomsJSON) {
        JSONArray itemsJSONArray = (JSONArray) roomsJSON.get("items");
        for (int i = 0; i < itemsJSONArray.size(); i++) {
            items.add(singleItem((JSONObject) itemsJSONArray.get(i)));
            numOfItems += 1;
        }
    }

    /**
     * Get a single item from its JSON object.
     * @param itemsJSON (JSONObject) JSON version of an item
     * @return (Map) Contains information about a single item
     */
    private Map<String, String>  singleItem(JSONObject itemsJSON) {
        HashMap<String, String> item = new HashMap<>();
        item.put("id", itemsJSON.get("id").toString());
        item.put("name", itemsJSON.get("name").toString());
        item.put("type", itemsJSON.get("type").toString());
        item.put("description", itemsJSON.get("description").toString());
        for (Map<String, String> itemLocation : itemLocations) {
            if (itemLocation.get("id").toString().equals(item.get("id").toString())) {
                item.put("room", itemLocation.get("room"));
                item.put("x", itemLocation.get("x"));
                item.put("y", itemLocation.get("y"));
                break;
            }
        }
        return item;
    }

}
