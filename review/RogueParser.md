| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :----------: | :----------: | :----------: | :----------: | :----------: |
| RogueParser | default constructor | n/a | n/a | n/a | 1 |
| RogueParser | new instance of parser given a single file name | n/a | n/a | parse | 1 |
| RogueParser | new instance of parser given two file names | n/a | n/a | parse | 1 |
| nextRoom | gets the next room from the iterator | roomIterator | next, hasNext | Iterator | 4 |
| nextItem | gets the next item from the iterator | itemsIterator | next, hasNext | Iterator | 4 |
| getSymbol | gets symbols from hashmap given a key | symbols | n/a | n/a | 4 |
| getNumOfItems | gets the number of items | numOfItems | n/a | n/a | 1 |
| getNumOfRooms | gets the number of rooms | numOfRooms | n/a | n/a | 1 |
| parse | parse a single file name | n/a | extractInfo | FileReader, JSONObject | 18 |
| parse | parse two separate file names | n/a | extractInfo | FileReader, JSONObject | 18 |
| setupIterators | initializes iterators | roomIterator, itemIterator | n/a | Iterator | 2 |
| extractInfo | extracts all info from JSON file | n/a | extractRoomInfo, extractItemInfo, extractSymbolInfo, setupIterators | JSONObject | 4 |
| extractSymbolInfo | gets symbol data from JSON object | symbols | n/a | n/a | 6 |
| getSymbolsMap | gets the symbols map | symbols | n/a | n/a | 1 |
| extractRoomInfo | gets room infomation from a jsonobject | rooms, numOfRooms | singleRoom | JSONObject | 6 |
| singleRoom | creates a single room | itemLocations | setBasicRoomData, itemPosition | Room, JSONObject | 15 |
| setBasicRoomData | puts basic room data into its map | n/a | n/a | Map, JSONObject | 12 |
| itemPosition | creates a map containing information for an item in a room | n/a | n/a | Map, JSONObject | 6 |
| extractItemInfo | adds an item to the list of items | numOfItems, items | singleItem | JSONObject, JSONArray | 5 |
| singleItem | creates a single item | n/a | n/a | n/a | 14 |
