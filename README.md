## Readme for CIS*2430 Assignment 3
****************************************
    Name: Benjamin Carlson
    Email: carlsonb@uoguelph.ca
    Student ID: 1044277
****************************************
>I state that all of my work is my own. Created using my ideas and developed using resources and concepts provided from lectures, textbook, stackoverflow, etc.

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

#### Running these commands should -hopefully- run a successful build of assignment 3, if any issues arise, contact the above email.

#### Responsibility analysis:
## all of the md sheets take methods called from all classes into account
