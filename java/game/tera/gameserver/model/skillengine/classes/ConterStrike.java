package tera.gameserver.model.skillengine.classes;

import tera.gameserver.model.Character;
import tera.gameserver.templates.SkillTemplate;

/**
 * Модель контр удара после блокирования щитом.
 * 
 * @author Ronn
 */
public class ConterStrike extends Strike
{
	/**
	 * @param template темплейт скила.
	 */
	public ConterStrike(SkillTemplate template)
	{
		super(template);
	}
	
	@Override
	public boolean checkCondition(Character attacker, float targetX, float targetY, float targetZ)
	{
		if(!attacker.isPlayer() || !attacker.isDefenseStance())
			return false;
		
		long last = attacker.getPlayer().getLastBlock();
		
		if(System.currentTimeMillis() - last > 1000)
		{
			attacker.sendMessage("It can only be used after a successful block.");
			return false;
		}
		
		return super.checkCondition(attacker, targetX, targetY, targetZ);
	}
	
	@Override
	public boolean isWaitable()
	{
		return false;
	}
}
