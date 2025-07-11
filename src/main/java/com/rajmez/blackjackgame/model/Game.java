package com.rajmez.blackjackgame.model;

import lombok.Data;

@Data
public class Game {
    private Deck deck = new Deck();
    private Participant player = new Participant();
    private Participant dealer = new Participant();
    private boolean gameOver = false;
    private String winner = null;

    public Game() {
        player.hit(deck);
        player.hit(deck);
        dealer.hit(deck);
        dealer.hit(deck);
    }
}
