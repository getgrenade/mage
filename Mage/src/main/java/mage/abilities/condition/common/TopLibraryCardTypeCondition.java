/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public class TopLibraryCardTypeCondition implements Condition {

    public static enum CheckType {
        CREATURE, LAND, SORCERY, INSTANT
    };

    private TopLibraryCardTypeCondition.CheckType type;

    public TopLibraryCardTypeCondition(TopLibraryCardTypeCondition.CheckType type) {
        this.type = type;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                switch (this.type) {
                    case CREATURE:
                        conditionApplies |= card.getCardType().contains(CardType.CREATURE);
                        break;
                    case LAND:
                        conditionApplies |= card.getCardType().contains(CardType.LAND);
                        break;
                    case SORCERY:
                        conditionApplies |= card.getCardType().contains(CardType.SORCERY);
                        break;
                    case INSTANT:
                        conditionApplies |= card.getCardType().contains(CardType.INSTANT);
                        break;
                }
            }
        }
        return conditionApplies;
    }
}
