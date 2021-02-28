# README
## CS 351 - 002
### Project 2: Dominos
#### John Tran

### Introduction

A game of dominos that contains two versions: the console version and the GUI version. This game involves a human player playing against the computer (preset algorithm &mdash; chooses the first valid move it can make). The players take turns setting a domino in the play area (parallel on separate "lanes") where the dominos placed by each player is displaced by half a domino and the half domino part that the two dominos touch should match in value. As explained in the project spec, the game ends when the boneyard is empty and either one of the players no longer has dominos in their hand or both could not take a turn (i.e. boneyard is empty so that they can't draw another domino into their hand from the boneyard).

Note: two late day extensions will most likely be used for this project (might be a few minutes after the deadline, but I do hope that it won't be too much of a stretch to count as late for 3 days, though late as it is)

Second note: all the domino images used for the GUI version are courtesy of Hugh McFall (we thank him for letting us use his domino images)

Third note: some of the comments provided were a little rushed, but should document the majority of the things needed to understand the project

### Instructions

For the GUI, just click on one of the dominos and press the play button on the right side of the pane to play the domino (there will be a `Label` that will display the currently selected domino on the left side of the pane).

The `.jar` files are configured with the debug messages ON. I might have left some vital checking inside the debug parts, so I didn't want to break the program by turning them off (and lack of time to comb through and edit the debug messages).

The `.jar` files are found at the root and can be run with the usual
```
`java -jar JAR_NAME`
```
where `JAR_NAME` is the name of the jar file. The `.jar` for the console is `dominosConsole.jar` and the one for the GUI is `dominosGUI.jar`.

### doc Note

Given time constraints, there wasn't enough time to produce a document detailing the objects mentioned in the design. However, I hope the information provided here and in the code itself should make the usage and implementation of the objects apparent.

### Rushed Comments in the Code

Again, given the time constraints, many of the comments were a little rushed and only the public methods were commented. Further, if there were some methods that were overridden, only the topmost super class will have a comment (generally speaking, but the only difference should be implementation and the design document should help clarify the differences between the two versions). If not, then the document to the class should contain some details...

Also, any get and set methods are generally inferable, so they won't be commented (including simple methods, like drawing a domino). The exceptions are also an exception (no pun intended) since the exception itself can be inferred from the class name.

### Design Change with GUI

Add in generics and created `*Base` classes so some functionality can be shared between the console and GUI versions

I unfortunately wasn't able to rename the console version Class names with `Console` endings to distinguish them from the `*Base` classes mentioned above (that's also why I added the `*Base` endings &mdash; originally wanted the base designs to have plain names and both version incorporate name endings)

### Known Bugs

The `Label` that displays the current domino selected by the user doesn't update to a new (and valid domino currently in the hand &mdash; at this point, it displays the last domino played and can lead to the program crashing if the user doesn't select a new domino to play for the next turn).

Also, I haven't thoroughly tested the end game (if only we had more time...), so there most likely are still some bugs towards the end game.

Update1: I have confirmed a situation towards the end of the game where if one of the players has more dominos in their play area than the other player, the shift won't be implemented correctly (the dominos will still match, but it won't be displayed correctly &mdash; the shift will be applied to the wrong player).

### Afternotes

Both versions used a `DEBUG` global variable in the respective scope of the main method (the `*Base` classes had their own main that served primarily for debugging and a separate `DEBUG`). Just set the `DEBUG` global variable to false in each of the three main methods (one for the `Base` classes, one for the console version, and one for the GUI version &mdash; should be found at the root of their respective directories).

There were some `Map` objects that were created, but I think one of them wasn't used.

Also, there are some inconsistencies with references to methods and method calls (i.e. call a get method when access to the variable itself is within scope).

#### Console Version

Important: a last minute change was made with the checking of the human player in the GUI version and the console version wasn't updated accordingly (only apparent when turns are missed and persisted for several turns).

As always, there might be errors with parsing the input (all the exceptions are not caught), but given the right input, the program should work as intended.

Any `List` structures used will be implemented as an `ArrayList`.

The sides of the board `LEFT` and `RIGHT` are encoded as 0 and 1, respectively, in the `SideOfBoard` enum.

`Domino` class &mdash; `HALF_BLANK` object:*

As commented in the `Domino` class, the static `HALF_BLANK` object was created to store some sort of object that would hold the shifted position of a half domino. It is not meant to be used as a real domino (as such, the code should ignore it when counting the dots and the dominos themselves in each player's trays).

`playAreaDominos` list reference name abbreviation:

Any methods/variables that reference any `Domino` objects in `playAreaDominos` will only have "play" as part of the name (differentiate from `Domino` objects from the `Hand` object (`hand`) that each player has) &mdash; i.e. `getPlayNumDominos()` for `playAreaDominos` vs `getNumDominos()` for `hand`.

`HumanPlayer` class and `ComputerPlayer` class:

asdf (only those two that extends the `Player` class)

also, different implementations of the methods (abstract) in `Player` class

- when checking if the player has a domino piece that can extend the line of play (for both the `HumanPlayer` or the `ComputerPlayer`), the domino piece will be rotated automatically to match the correct orientation ()

- `searchDomino()` and `searchDominoAutoRotate()` &mdash; the issue occurs with the different checking procedures for each type of player where the domino should not be automatically rotated for the `HumanPlayer` (otherwise, the domino rotates AFTER the player chooses to play a domino and isn't reflected in the play area since it's only printed at the beginning of the turn)

- inconsistencies in variable naming (`matchIndex` with `leftRight`)

`getName()` method in `Player` abstract class:

implemented later on (not utilized in every place that it should)

`Domino` constructor (used for a deep clone): not used

*Special problem created with `HALF_BLANK`:

The conditional

```java
if (playAreaDominos.contains(Domino.HALF_BLANK))
```
is implemented a good bit throughout the project to make sure `HALF_BLANK` doesn't mess things up (since it's coded as an actual `Domino` object), and it might have been better to just incorporate it when printing the play area...

#### GUI version

The combo boxes are set to an initial value so I don't have to handle the case where the user forgot to choose an option before playing a domino.

The implementation of the human and computer players in the GUI version used a good chunk of the same logic as the console version. However, due to time constraints, I wasn't able to properly move them up to the parent class, or even created a better design with maybe human and computer abstract classes instead of the single `PlayerBase` abstract class where both players share implementation from. So, a lot of the code is just copy and paste from the console version (however, there were specific variables that had to be moved around and changed a little to accommodate the GUI elements &mdash; i.e. updating the various `Label` objects).

There were conflicts on whether the lists should be maintained separately along with the list of dominos themselves, or whether a map should be used to link them together. In the end, both were updated for some (like in the hand object), but for the play area, I found there really wasn't much of an advantage since I have to override methods and update the list of images anyways...

For displaying the play area, the scroll pane was used so that the line of dominos could be viewed by scrolling back and forth (also used for the human hand for unfortunate cases...).
