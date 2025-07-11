package com.rajmez.blackjackgame.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rajmez.blackjackgame.dto.GameState;
import com.rajmez.blackjackgame.service.GameService;

import org.springframework.web.bind.annotation.PostMapping;

/*

Hit API endpoints in terminal like this to play the game:

curl.exe -X POST http://localhost:8080/start -H "Accept: application/json" -c cookie.txt

curl.exe -X POST http://localhost:8080/hit   -H "Accept: application/json" -b cookie.txt -c cookie.txt

curl.exe -X POST http://localhost:8080/pass  -H "Accept: application/json" -b cookie.txt

 */

@RestController
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(path="/start", produces="application/json")
    public ResponseEntity<GameState> start(HttpSession session) {
        return ResponseEntity.ok(gameService.start(session));
    }

    @PostMapping(path="/hit", produces="application/json")
    public ResponseEntity<GameState> hit(HttpSession session) {
        return ResponseEntity.ok(gameService.hit(session));
    }

    @PostMapping(path="/pass", produces="application/json")
    public ResponseEntity<GameState> pass(HttpSession session) {
        return ResponseEntity.ok(gameService.pass(session));
    }
}
