package com.rajmez.blackjackgame.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Participant {
    private List<Card> hand = new ArrayList<>();
    private int score;
    private int acesAsEleven;

    public void hit(Deck deck) {
        Card card = deck.draw();
        hand.add(card);
        String rank = card.getRank();
        if(rank == "J" || rank == "Q" || rank == "K") {
            score += 10;
        }
        else if(rank == "A") {
            acesAsEleven++;
            score += 11;
        }
        else {
            score += Integer.parseInt(rank);
        }

        while(score > 21 && acesAsEleven > 0) {
            score -= 10;
            acesAsEleven--;
        }
    }
}
