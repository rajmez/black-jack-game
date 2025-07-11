package com.rajmez.blackjackgame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.rajmez.blackjackgame.model.Card;
import com.rajmez.blackjackgame.model.Participant;
import com.rajmez.blackjackgame.model.Deck;

public class ParticipantTest {

    static class FixedAceDeck extends Deck {
        @Override
        public Card draw() {
            return new Card("A", "♥");
        }
    }

    static class FixedSevenDeck extends Deck {
        @Override
        public Card draw() {
            return new Card("7", "♥");
        }
    }

    @Test
    @DisplayName("Drawn ace should be considered 1 if total score > 21")
    void test1() {
        Participant player = new Participant();
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("A", "♠"));
        cards.add(new Card("9", "♠"));
        player.setHand(cards);
        player.setScore(20);
        player.setAcesAsEleven(1);

        player.hit(new FixedAceDeck());
        assertEquals(player.getScore(), 21);
        assertEquals(player.getAcesAsEleven(), 1);
    }

    @Test
    @DisplayName("Old ace should be considered 1 instead of 11, if new card drawn leads to total score > 21")
    void test2() {
        Participant player = new Participant();
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("A", "♠"));
        cards.add(new Card("4", "♠"));
        player.setHand(cards);
        player.setScore(15);
        player.setAcesAsEleven(1);

        player.hit(new FixedSevenDeck());
        assertEquals(player.getScore(), 12);
        assertEquals(player.getAcesAsEleven(), 0);
    }

}
