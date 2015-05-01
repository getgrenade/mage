package org.mage.test.cards.copy;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Volrath's Shapeshifter
 * 
 * As long as the top card of your graveyard is a creature card, 
 * Volrath's Shapeshifter has the full text of that card and has the 
 * text "2: Discard a card." (Volrath's Shapeshifter has that card's name,
 *  mana cost, color, types, abilities, power, and toughness.)
 *
 */
public class VolrathsShapshifterTest extends CardTestPlayerBase {

    /**
     * Tests copy simple creature
     */
    @Test
    public void testCopySimpleCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Volrath's Shapeshifter", 1);

        // Flying 3/2
        addCard(Zone.GRAVEYARD, playerA, "Assault Griffin", 1);
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        skipInitShuffling();
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Assault Griffin", 1);
        assertPowerToughness(playerA, "Assault Griffin", 3, 2);

        Permanent shapeshifter = getPermanent("Assault Griffin", playerA.getId());
        Assert.assertTrue(shapeshifter.getSubtype().contains("Griffin"));
        Assert.assertTrue("Volrath's Shapeshifter must have flying", shapeshifter.getAbilities().contains(FlyingAbility.getInstance()));
        boolean hasShapeshifterOriginalAbility = false;
        for (Ability ability : shapeshifter.getAbilities()) {
			if(ability instanceof SimpleActivatedAbility) {
				SimpleActivatedAbility simpleActivatedAbility = (SimpleActivatedAbility)ability;
				hasShapeshifterOriginalAbility = simpleActivatedAbility.getZone() == Zone.BATTLEFIELD && simpleActivatedAbility.getEffects().size() == 1 &&
					simpleActivatedAbility.getEffects().get(0) instanceof DiscardControllerEffect && simpleActivatedAbility.getManaCosts().size() == 1 
							&& simpleActivatedAbility.getManaCosts().get(0) instanceof GenericManaCost && simpleActivatedAbility.getManaCosts().get(0).convertedManaCost() == 2;
			}
		}
        Assert.assertTrue("Volrath's Shapeshifter must have {2} : Discard a card", hasShapeshifterOriginalAbility);
    }
    
    /**
     * Tests turing back into Volrath's Shapeshifter after a new card is put on top that isn't a creature
     */
    @Test
	public void testLosingCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Volrath's Shapeshifter", 1);
        // Codex Shredder - Artifact
        // {T}: Target player puts the top card of his or her library into his or her graveyard.
        // {5}, {T}, Sacrifice Codex Shredder: Return target card from your graveyard to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        // Flying 3/2
        addCard(Zone.GRAVEYARD, playerA, "Assault Griffin", 1);
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player puts the top card of his or her library into his or her graveyard.", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Volrath's Shapeshifter", 1);
        assertPowerToughness(playerA, "Volrath's Shapeshifter", 0, 1);

        Permanent shapeshifter = getPermanent("Volrath's Shapeshifter", playerA.getId());
        Assert.assertTrue(shapeshifter.getSubtype().contains("Shapeshifter"));
        boolean hasShapeshifterOriginalAbility = false;
        for (Ability ability : shapeshifter.getAbilities()) {
			if(ability instanceof SimpleActivatedAbility) {
				SimpleActivatedAbility simpleActivatedAbility = (SimpleActivatedAbility)ability;
				hasShapeshifterOriginalAbility = simpleActivatedAbility.getZone() == Zone.BATTLEFIELD && simpleActivatedAbility.getEffects().size() == 1 &&
					simpleActivatedAbility.getEffects().get(0) instanceof DiscardControllerEffect && simpleActivatedAbility.getManaCosts().size() == 1 
							&& simpleActivatedAbility.getManaCosts().get(0) instanceof GenericManaCost && simpleActivatedAbility.getManaCosts().get(0).convertedManaCost() == 2;
			}
		}
        Assert.assertTrue("Volrath's Shapeshifter must have {2} : Discard a card", hasShapeshifterOriginalAbility);
    }
}