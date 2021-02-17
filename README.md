# README
## CS 351 - 002
### Project 2: Dominos
#### John Tran

### Introduction

### Instructions

### Afternotes

Any `List` structures used will be implemented as an `ArrayList`.

`Domino` class &mdash; `HALF_BLANK` object:*

As commented in the `Domino` class, the static `HALF_BLANK` object was created to store some sort of object that would hold the shifted position of a half domino. It is not meant to be used as a real domino (as such, the code should ignore it when counting the dots and the dominos themselves in each player's trays).

`playAreaDominos` list reference name abbreviation:

Any methods/variables that reference any `Domino` objects in `playAreaDominos` will only have "play" as part of the name (differentiate from `Domino` objects from the `Hand` object (`hand`) that each player has) &mdash; i.e. `getPlayNumDominos()` for `playAreaDominos` vs `getNumDominos()` for `hand`.

`HumanPlayer` class and `ComputerPlayer` class:

asdf (only those two that extends the `Player` class)

*Special problem created with `HALF_BLANK`:

The conditional

```java
if (playAreaDominos.contains(Domino.HALF_BLANK))
```
is implemented a good bit throughout the project to make sure `HALF_BLANK` doesn't mess things up (since it's coded as an actual `Domino` object), and it might have been better to just incorporate it when printing the play area...
