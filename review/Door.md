| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :----------: | :----------: | :----------: | :----------: | :----------: |
| Door | Default door constructor | connectedRooms, wallPos, conRoomId | n/a | ArrayList, int int | 3 |
| setConRoomId | Sets the connected room id | conRoomId | n/a | int | 1 |
| getConRoomId | Gets the id of the connected room | conRoomId | n/a | n/a | 1 |
| connectRoom | Add one of the connected rooms to the door | connectedRooms | n/a | Room r | 1 |
| getConnectedRooms | Gets the list of connected rooms | connectedRooms | n/a | Room | 1 |
| getOtherRoom | Gets the "other room" connected to the door | connectedRooms | n/a | Room currentRoom | 6 |
| getWallPos | Gets the wall position of the door | wallPos | n/a | int | 1 |
| setWallPost |Sets a new wall position for the door | wallPos | n/a | int | 1 |