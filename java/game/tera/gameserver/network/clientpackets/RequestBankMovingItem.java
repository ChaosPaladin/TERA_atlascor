package tera.gameserver.network.clientpackets;

import tera.gameserver.model.npc.interaction.dialogs.BankDialog;
import tera.gameserver.model.npc.interaction.dialogs.Dialog;
import tera.gameserver.model.playable.Player;

public class RequestBankMovingItem extends ClientPacket
{

	private int oldBankItem;

	private int newBankItem;


	private Player player;

	@Override
	public void finalyze()
	{
		player = null;
	}

	@Override
	public boolean isSynchronized()
	{
		return false;
	}

	@Override
	public void readImpl()
	{
		player = owner.getOwner();

		readLong();
		readLong();

		readInt();

		oldBankItem = readInt();
		newBankItem = readInt();
	}

	@Override
	public void runImpl()
	{
		if(player == null)
			return;


		Dialog dialog = player.getLastDialog();


		if(dialog == null || !(dialog instanceof BankDialog))
			return;

		BankDialog bank = (BankDialog) dialog;

		bank.movingItem(oldBankItem, newBankItem);
	}
}