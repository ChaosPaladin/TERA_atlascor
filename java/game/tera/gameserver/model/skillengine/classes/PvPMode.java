package tera.gameserver.model.skillengine.classes;

import tera.Config;
import tera.gameserver.model.Character;
import tera.gameserver.model.playable.Player;
import tera.gameserver.templates.SkillTemplate;

/**
 * Модель скила боевой оборонительной стойки.
 *
 * @author Ronn
 */
public class PvPMode extends AbstractSkill
{
	/**
	 * @param template темплейт скила.
	 */
	public PvPMode(SkillTemplate template)
	{
		super(template);
	}

	@Override
	public boolean checkCondition(Character attacker, float targetX, float targetY, float targetZ)
	{
		if(!attacker.isPlayer())
			return false;

		Player player = attacker.getPlayer();

		if(!Config.WORLD_PK_AVAILABLE)
		{
			player.sendMessage("PvP Mode temporarily unavailable.");
			return false;
		}

		if(player.getKarma() > 0)
		{
			player.sendMessage("Do not use if there is karma(" + player.getKarma() + ").");
			return false;
		}

		//if(player.isPkMode())
		//{
		///	player.sendMessage("You activate PK mode.");
		///	return false;
		//}

		if(player.isPvPMode() && player.isBattleStanced())
		{
			player.sendMessage("Do not use in a fighting stance.");
			return false;
		}

		if(!player.isPvPMode() && player.getDuel() != null)
		{
			player.sendMessage("Cannot be used in a duel.");
			return false;
		}

		return super.checkCondition(attacker, targetX, targetY, targetZ);
	}

	@Override
	public void useSkill(Character character, float targetX, float targetY, float targetZ)
	{
		character.setPvPMode(!character.isPvPMode());
	}
}
