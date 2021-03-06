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
package mage.sets.darkascension;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class ArchangelsLight extends CardImpl {

    public ArchangelsLight(UUID ownerId) {
        super(ownerId, 1, "Archangel's Light", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{7}{W}");
        this.expansionSetCode = "DKA";

        // You gain 2 life for each card in your graveyard, then shuffle your graveyard into your library.
        this.getSpellAbility().addEffect(new ArchangelsLightEffect());

    }

    public ArchangelsLight(final ArchangelsLight card) {
        super(card);
    }

    @Override
    public ArchangelsLight copy() {
        return new ArchangelsLight(this);
    }
}

class ArchangelsLightEffect extends OneShotEffect {

    public ArchangelsLightEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 2 life for each card in your graveyard, then shuffle your graveyard into your library";
    }

    public ArchangelsLightEffect(final ArchangelsLightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        DynamicValue value = new CardsInControllerGraveyardCount();
        if (controller != null) {
            controller.gainLife(value.calculate(game, source, this) * 2, game);
            for (Card card: controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }            
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public ArchangelsLightEffect copy() {
        return new ArchangelsLightEffect(this);
    }

}
