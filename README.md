# README
## CS 351 - 002
### Project 2: Dominos
#### John Tran

### Introduction

### Instructions

### Afternotes

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

`Domino` constructor (used for a deep clone):

not used

*Special problem created with `HALF_BLANK`:

The conditional

```java
if (playAreaDominos.contains(Domino.HALF_BLANK))
```
is implemented a good bit throughout the project to make sure `HALF_BLANK` doesn't mess things up (since it's coded as an actual `Domino` object), and it might have been better to just incorporate it when printing the play area...
