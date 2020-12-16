| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :----------: | :----------: | :----------: | :----------: | :----------: |
| Rogue | default rogue constructor | player, parser | n/a | Player, RogueParser | 2 |
| Rogue | Creates an instance of rogue with a given parser | player, parser, symbols | parseData, connectAllDoors, verifyRooms | Player, RogueParser, Map, HashMap | 8 |
| printRooms | for debugging, prints key representation of the room | roomList | n/a | Room | 9 |
| connectAllDoors | connects all door instances to their proper room | DIRECTIONS | connectDoors | String | 3 |
| parseRooms | parses the room data | n/a | addRoom | RogueParser, Map | 4 |
| parseItems | parses the item data | n/a | addItem | RogueParser, Map | 4 |
| parseData | parses all data | n/a | parseRooms, parseItems | Map, RogueParser | 2 |
| verifyRooms | checks each room, making sure items/player/walls are in valid locations | roomList | handleNoDoorsException | Room, ArrayList | 7 |
| handleNoDoorsException | handler for no doors exception | roomList | doorExceptionCreation, verifyRoom | Room, Door, ArrayList | 6 |
| doorExceptionCreation | creates doors and connects the two rooms, handling the exception | DIRECTIONS | setExceptionDoor, connectExceptionDoors, checkSuccess, getDoorLocation, getConRoomId | HashMap, Room, Door | 14 |
| checkSuccess | checks if the door operation was successful, exits on an unsuccessful operation | n/a | n/a | int | 4 |
| connectExceptionDoors | connects exception doors | n/a | getOppositeDir | HashMap, Door | 3 |
| setExceptionDoor | sets basic data of exception doors | n/a | setConRoomId, getId, setWallPost, connectRoom | Door, Room | 3 |
| setPlayer | sets the player of the game | player | n/a | Player | 1 |
| getPlayer | gets the player of the game | player | n/a | Player | 1 |
| makeMove | checks for a valid move, if valid makes move and notifies player | message | generateMove, getPlayerRoom, updatePlayerLocation | Room, Point | 7 |
| generateMove | checks if a move is valid | message, player | generateRoom, getHeight, getWidth, setCurrentRoom, getX, getY, handleItemCollision, handleDoorCollision, updatePlayerandRoom | Room, Player, Point | 15 |
| updatePlayerAndRoom | updates the player's location and their room | player | setXyLocation, setPlayer | Player, Room, Point | 2 |
| handleItemCollision | handler for when a player steps on a item | player, roomItems, message | getXyLocation, getX, getName, getY, addItem, setRoomItems | Item, ArrayList, Player, Room | 12 |
| handleDoorCollision | handler for when a player steps on an door | n/a | getIntialDir, getDoor, getOtherRoom, setPlayer, generateMove, updatePlayerAndRoom | Door, Room, Point | 8 |
| getInitialDir | gets a direction given a point and room | n/a | getY, getX, getHeight, getWidth | Point, Room | 13 |
| generateMove | creates a move based on the direction, used for doors | n/a | getHeight, getWallPos, getWidth | Point, Room, Door | 14 |
| getOppositeDir | gets the opposite direction | n/a | n/a | n/a | 13 |
| getPlayerRoom | gets the room the player is in | roomList | isPlayerInRoom | Room | 6 |
| updatePlayerLocation | updates the player's location | n/a | validateMove, getXyLocation, getX, getY |  | 14 |
| validateX | checks if the x coordinate is valid | n/a | getHeight | Room | 7 |
| validateY | checks if the y coordinate is valid | n/a | getWidth | Room | 7 |
| validateMove | checks if a move's coordinates are valid | n/a | validateX, validateY | Room, Point | 4 |
| getNextDisplay | gets the next display | nextDisplay | getPlayerRoom, getNextRoom | Room | 4 |
| getNextRoom | gets the string representation of the next room | symbols | generateRoom, getHeight, getWidth | Room, HashMap | 9 |
| addRoom | creates a new room from parsed data | roomList | setDoors, setStartingRoom, setStarting setDoorLocation | Map, ArrayList, HashMap, Door | 8 |
| setStarting | sets the starting room to true/false | player | setCurrentRoom, setPlayer | Player, Room | 6 |
| setDoors | sets the room's doors | DIRECTIONS | setDoorInfo | HashMap | 5 |
| setDoorInfo | sets basic info of doors | setWallPost, setConRoomId | n/a | Door | 4 |
| connectDoors | connects each door to its corresponding room | roomList | getDoor, getConRoomId, getId, connectRoom | Door, Room | 9 |
| addItem | adds an item to the list of new items | itemList | createItem, setXyLocation, addItemToRoom | Point, Item, ArrayList | 9 |
| createItem | creates an item | n/a | itemBasedOnType | Map | 1 |
| itemBasedOnType | creates a new instance of an item based on its type | n/a | createMagicItem, createClothingItem, createFood, createPotion, createRing, createScroll, createBasicItem | Map | 17 |
| createScroll | creates a new instance of scroll | n/a | setBasicItemData | Scroll, Map | 3 |
| createBasicItem | creates a new instance of item | n/a | setBasicItemData | Item, Map | 3 |
| createPotion | creates a new instance of potion | n/a | setBasicItemData | Potion, Map | 3 |
| createRing | creates a new instance of ring | n/a | setBasicItemData | Ring, Map | 3 |
| createSmallFood | creates a new instance of small food | n/a | setBasicItemData | SmallFood, Map | 3 |
| createFood | creates a new instance of food | n/a | setBasicItemData | Food, Map | 3 |
| createMagicItem | creates a new instance of Magic | n/a | setBasicItemData | Magic, Map | 3 |
| createClothing | creates a new instance of clothing | n/a | setBasicItemData | Clothing, Map | 3 |
| setBasicItemData | sets all basic item data | n/a | setName, setType, setDescription, setId | Map | 4 |
| addItemToRoom | adds item into its corresponding room | roomList | setCurrentRoom, addItem, getRoomItems, handleImpossiblePosition | Item, Room | 11 |
| handleImpossiblePosition | handler for impossible position exception | n/a | generateRoom, getHeight, getWidth, setXyLocation | Point, Room | 12 |