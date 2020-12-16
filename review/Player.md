| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
| :----------: | :----------: | :----------: | :----------: | :----------: | :----------: |
| Player | default constructor | name, inventory, xyLocation | setXyLocation | Point, ArrayList | 4 |
| Player | name, xyLocation, inventory | setName, setXyLocation | Point, ArrayList | Point, ArrayList | 5 |
| getInventory | gets the player's inventory | inventory | n/a | ArrayList | 1 |
| setInventory | sets a new inventory to the player | inventory | n/a | ArrayList | 1 |
| addItem | adds an item to the player's inventory | inventory | n/a | Item | 3 |
| getName | gets the player's name | name | n/a | n/a | 1 |
| setName | sets the player's name | name | n/a | n/a | 1 |
| getXyLocation | gets the player's xy location | xyLoc | n/a | Point | 1 |
| setXyLocation | sets a new xy location of the player | xyLoc | n//a | Point | 1 |
| getCurrentRoom | gets the current room | currentRoom | n/a | Room | 1 |
| setCurrentRoom | sets the player's current room | currentRoom | n/a | Room | 1 |
| displayInventory | creates a string representation of the player's inventory | inventory | formatString | ArrayList, Item | 8 |
| formatString | takes an item in the player's inventory and converts it to a displayable string | instance vars used | other class methods called | objects used with method calls | 3 |