package com.rajmez.blackjackgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = { "♠", "♥", "♦", "♣" };
        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

        for (String suit: suits) {
            for (String rank: ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.remove(cards.size() - 1); // remove the last card
    }
}
