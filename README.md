# Roshambo

Java Backend application that works together with [roshambo-frontend][roshambo-frontend-git].

## Versions

```bash
java --version
openjdk 20.0.1 2023-04-18

gradle --version
8.1.1
```

## Installation and Setup

For building the project, you may use `gradle build` (add `--continuous` flag if you'd like).
For running the server, you may use `gradle bootRun`.

## Database setup

The application should be able to automatically create two databases:

- `roshambo` -> database used together with `gradle bootRun`
- `roshamboTest` -> database used in tests

Each Database will have a Player table that contains:

- `Long id` -> primary key
- `String name` -> name of the player
- `String type` -> either `player` or `computer`
- `Integer health` -> a number from 0 to 100
- `String image` -> image url/path


## Tests

If you want to run the Unit tests, you may use `gradle clean test`.
You could also use `gradle build --continuous`.

You will find the tests under the `test/` directory.

## Available endpoints

### GET /api/game/options

Returns the possible player options (in this case ROCK, PAPER, SCISSOR)

#### Payload

```json
{ "data": { "options": ["ROCK", "PAPER", "SCISSOR"] } }
```

### GET /api/game/players

Returns the players stored in the database. 

#### When there are no players in the database

They will be created and returned in the payload.

#### When there are players but no one is KO'd (health = 0) yet

They will be returned in the payload, as a way to maintain the status of the fight.

#### When there are players but one of them is KO'd

Reset all players health and return them in the payload

#### Payload

```json
{ "data": { "players": [Player, Computer] } }
```

### POST /api/game/resolve

Receives the player choice and processes the game turn to return the outcome, the damage inflicted and if the game is over.

#### Request Body

```json
{ "playerChoice": "string" } // "ROCK" | "PAPER" | "SCISSOR"
```

#### Payload

```json
{ 
  "data": { 
    "message": "string", // String message to display the result outcome,
    "computerChoice": "string", // "Choice randomly made by the Computer",
    "players": [Player, Computer], // Updated Players attributes including their health
    "gameOver": boolean // if the game is over
  }
}
```

### POST /api/game/reset

Resets the current game, resetting the players health bar and bringing their updated values back in the payload.


#### Payload

```json
{ "data": { "players": [Player, Computer] } }
```

[roshambo-frontend-git]: https://github.com/alecarneiro1993/roshambo-frontend