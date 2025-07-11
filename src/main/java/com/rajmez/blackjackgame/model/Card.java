package com.rajmez.blackjackgame.model;

import lombok.Getter;

@Getter
public class Card {
    String rank;
    String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

 }