package com.rajmez.blackjackgame.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rajmez.blackjackgame.dto.GameState;
import com.rajmez.blackjackgame.model.Game;
import java.util.List;

@Service
public class GameService {

    private static final String SESSION_KEY = "BLACKJACK_KEY";

    private GameState toGameState(Game game, boolean revealDealer) {
        GameState gs = new GameState();

        if (revealDealer) { 
            gs.setDealerHand(game.getDealer().getHand()); 
            gs.setDealerScore(game.getDealer().getScore());
        }
        else { 
            gs.setDealerHand(List.of(game.getDealer().getHand().get(0))); 
            gs.setDealerScore(0);
        }

        gs.setPlayerHand(game.getPlayer().getHand());
        gs.setPlayerScore(game.getPlayer().getScore());
        gs.setGameOver(game.isGameOver());
        gs.setWinner(game.getWinner());
        return gs;
    }

    private Game getGame(HttpSession session) {
        Game game = (Game) session.getAttribute(SESSION_KEY);
        if (game == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Game not started yet. Call /start to begin."
            );
        }
        if (game.isGameOver()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "This game is over. Call /start to begin new game."
            );
        }
        return game;
    }

    public GameState start(HttpSession session) {
        Game game = new Game();
        session.setAttribute(SESSION_KEY, game);
        return toGameState(game, false);
    }

    public GameState hit(HttpSession session) {
        Game game = getGame(session);
        game.getPlayer().hit(game.getDeck());

        if (game.getPlayer().getScore() > 21) {
            game.setGameOver(true);
            game.setWinner("DEALER");
        }

        return toGameState(game, false);
    }

    public GameState pass(HttpSession session) {
        Game game = getGame(session);

        while (game.getDealer().getScore() < 17) {
            game.getDealer().hit(game.getDeck());
        }

        game.setGameOver(true);

        if (game.getDealer().getScore() > 21 || game.getPlayer().getScore() > game.getDealer().getScore()) {
            game.setWinner("PLAYER");
        }
        else if (game.getPlayer().getScore() < game.getDealer().getScore()) {
            game.setWinner("DEALER");
        }
        else {
            game.setWinner("DRAW");
        }

        return toGameState(game, true);  
    }

}
