## Readme for RogueGame
****************************************
Contacting Me:
Email: 
>* benproskilled@gmail.com (Personal)
>* ben_carlson@web-us.ca (Business)
****************************************

## Background
This program started out as an assignment for an Object Oriented Programming class I was taking at the University of Guelph. I thought that it had potential to be refined, as well as additional functionality added to the program after the course was finished. This program has been posted with permission.

## Using the program
****************************************

### To run this program:

#### Ensure that you have at least gradle 6, JDK 8 and Java 11 installed, otherwise issues will arise.
##### In Powershell, Bash terminal, OSX terminal, etc, run the following commands:
>* gradle.build
>* java -jar build/libs/A3.jar
>* ~~If you wish to use a json file for symbols/rooms other than the default, please open fileLocations.json and add your desired json file under the rooms section.~~
>* This build is GUI based, selection of JSON files can be done by opening the game options/open file menu

## Basic commands:
>* Movement -> arrow keys / ijkl
>* Quit game -> Q
>* Eat item (Eatable) -> e
>* Toss item (Tossable) -> t
>* Wear item (Wearable) -> w
>* Saving the game (GUI) Game options/save
>* Load a save game (GUI) Game options/load game

## Default symbols:
>* Walls: '-' (North and South) '|' (East and West)
>* Passages: '#'
>* Doors: '+'
>* Floor: '.'
>* Player (This is you) '@'
>* Items:
    >* Gold: '*'
    >* Potion: '!'
    >* Scroll: '?'
    >* Food: ':' / ';' (Small)
    >* Clothing: ']' / '^' (Ring)

#### Running these commands should -hopefully- run a successful build of this project, if any issues arise, contact the above email/make an issue on the github repository.