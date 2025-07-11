package com.rajmez.blackjackgame.dto;

import java.util.List;

import com.rajmez.blackjackgame.model.Card;

import lombok.Data;

@Data
public class GameState {
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private int playerScore;
    private int dealerScore;
    private boolean gameOver;
    private String winner;
}
