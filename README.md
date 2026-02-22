# Black Jack Game API (Spring Boot)

A lightweight REST API for playing a single-player Black Jack game (player vs dealer) over HTTP.
The game state is stored in the server-side HTTP session, so a cookie is required to continue the same game across requests.

## Tech stack

- Java 17
- Spring Boot
- Maven
- JUnit 5

## Project structure

- `controller/` – HTTP endpoints (`/start`, `/hit`, `/pass`)
- `service/` – gameplay orchestration and session access
- `model/` – core domain objects (`Game`, `Deck`, `Card`, `Participant`)
- `dto/` – API response objects
- `exception/` – JSON error handling for invalid game flow

## Game flow implemented in this codebase

1. **Start** a game (`POST /start`)
   - Creates a new shuffled deck.
   - Deals 2 cards to player and 2 cards to dealer.
   - Returns game state with **only dealer's first card visible**.
2. **Hit** (`POST /hit`)
   - Draws one card for the player.
   - If player score exceeds 21, game ends and dealer wins.
   - Dealer remains hidden while game is in progress.
3. **Pass** (`POST /pass`)
   - Dealer draws until dealer score is at least 17.
   - Game ends and winner is calculated:
     - `PLAYER` if dealer busts or player score is higher
     - `DEALER` if dealer score is higher
     - `DRAW` for equal scores
   - Dealer hand and score are fully revealed.

## API endpoints

### `POST /start`
Starts a new game in the current HTTP session.

### `POST /hit`
Draws one card for the player in the current game.

### `POST /pass`
Ends player's turn and runs dealer logic to completion.

## Response format

Successful responses return JSON like:

```json
{
  "playerHand": [{ "rank": "10", "suit": "♠" }, { "rank": "7", "suit": "♥" }],
  "dealerHand": [{ "rank": "K", "suit": "♦" }],
  "playerScore": 17,
  "dealerScore": 0,
  "gameOver": false,
  "winner": null
}
```

When game is over (after bust or pass), `dealerHand`, `dealerScore`, and `winner` are fully populated.

## Error responses

If you call `/hit` or `/pass` before `/start`, or try to continue a finished game, the API returns HTTP `400` with payload:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Game not started yet. Call /start to begin."
}
```

(or corresponding message for a finished game).

## Run locally

### Option 1: Maven Wrapper (recommended)

```bash
./mvnw spring-boot:run
```

### Option 2: Build + run jar

```bash
./mvnw clean package
java -jar target/black-jack-game-*.jar
```

Server starts on `http://localhost:8080`.

## Play from terminal (session cookie required)

> On Linux/macOS, use `curl`. On Windows PowerShell, `curl.exe` also works.

Start game:

```bash
curl -X POST http://localhost:8080/start -H "Accept: application/json" -c cookie.txt
```

Hit:

```bash
curl -X POST http://localhost:8080/hit -H "Accept: application/json" -b cookie.txt -c cookie.txt
```

Pass:

```bash
curl -X POST http://localhost:8080/pass -H "Accept: application/json" -b cookie.txt
```

After each request, inspect the returned JSON for current state and winner.

## Tests

Run tests with:

```bash
./mvnw test
```

