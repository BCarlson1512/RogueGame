| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :----------: | :----------: | :----------: | :----------: | :----------: |
| Room | default room constructor | width, height, identificationnum, itemList, doorLocation | n/a | n/a | 5 |
| Room | Constructor for room based on parsed data | width, height, identificationnum, itemList, doorLocation | n/a | n/a | 5 |
| getDoorLocation | Gets the doors map for room | doorLocation | n/a | n/a | 1 |
| setDoorLocation | Updates doors map for room | doorLocation | n/a | n/a | 1 |
| getWidth | Gets the width of the room | width | n/a | n/a | 1 |
| getHeight | Gets the height of the room | height | n/a | n/a | 1 |
| setWidth | Sets the width of the room | width | n/a | n/a | 1 |
| setHeight | Sets the height of the room | height | n/a | n/a | 1 |
| getId | Gets the id of the room | identificationNum | n/a | n/a | 1 |
| setId | Sets the id of the room | identificationNum | n/a | n/a | 1 |
| getRoomItems | Gets the list of items in the room | roomItems | n/a | n/a | 1 |
| setRoomItems | Sets the list of items in the room | roomItems | n/a | n/a | 1 |
| addItem | Adds an item into the list of room items | roomItems | n/a | toAdd | 13 |
| getPlayer | Gets the player from the room, null means player isnt present | player | n/a | toAdd | 1 |
| setPlayer | Sets player object in the room | player | n/a | newPlayer | 1 |
| isPlayerInRoom | Checks if player is in the room | player | n/a | n/a | 4 |
| getDoor | Gets the door at a direction (NSEW) | doorLocation | n/a | dir | 1 |
| getStartingRoom | Gets if a room is the starting room or not | startingRoom | n/a | n/a | 1 |
| setStartingRoom | Sets a t/f value for the starting room | startingRoom | n/a | newStartingRoom | 1 |
| generateRoom | Generates a 2d array, key representation of the room | height, width | setWalls, setDoors, setItems, setPlayerKey | n/a | 11 |
| setWalls | set wall locations in the key array | room | n/a | n/a | 6 |
| setDoors | sets doors in the room key array | doorLocation, width, height | n/a | n/a | 16 |
| setItems | adds items to the room key array | itemList | n/a | Item i | 5 |
| setPlayerKey | sets the player's key location in the array | n/a | isPlayerInRoom | n/a | 5 |
| getRoom | returns the key based array of the room | n/a | n/a | n/a | 1 |
| verfiyRoom | ensures that items, doors and walls are in correct locations | height, width, room | checkItems, checkDoors, readNSWalls, readEWWalls, checkPlayer | n/a | 16 |
| checkItems | checks that items are placed in the correct location | roomItems | n/a | toAdd | 13 |
| checkDoors | checks that doors are in the correct location | roomItems | readNSWalls, readEWWalls | toAdd | 11 |
| readNSWalls | reads the north and south walls looking for doors | roomItems | n/a | nDoor, sDoor | 6 |
| readEWWalls | reads the east and west walls looking for doors | roomItems | n/a | eDoor, wDoor | 6 |
| checkPlayer | checks that the player is placed in the correct spot | roomItems | isPlayerInRoom | player | 16 |
