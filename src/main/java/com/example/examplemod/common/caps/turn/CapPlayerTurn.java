package com.example.examplemod.common.caps.turn;

import javax.annotation.Nullable;

import com.example.examplemod.Ref;
import com.example.examplemod.common.caps.CapabilityUtils;
import com.example.examplemod.common.caps.SimpleCapProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapPlayerTurn
{
	@CapabilityInject(ITurn.class)
	public static final Capability<ITurn> TURN_CAP = null;
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	
	
	public static final ResourceLocation ID = new ResourceLocation(Ref.MODID, "PlayerTurn");

	public static void register() {
		CapabilityManager.INSTANCE.register(ITurn.class, new Capability.IStorage<ITurn>() {
			@Override
			public NBTBase writeNBT(Capability<ITurn> capability, ITurn instance, EnumFacing side) {
				return new NBTTagInt(instance.getTurn());
			}

			@Override
			public void readNBT(Capability<ITurn> capability, ITurn instance, EnumFacing side, NBTBase nbt) {
				instance.set(((NBTTagInt) nbt).getInt());
			}
		}, () -> new PlayerTurn(null));

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	/**
	 * Get the {@link ITurn} from the specified entity.
	 *
	 * @param entity The entity
	 * @return The ITurn
	 */
	@Nullable
	public static ITurn getTurn(EntityPlayer player) {
		return CapabilityUtils.getCapability(player, TURN_CAP, DEFAULT_FACING);
	}

	/**
	 * Create a provider for the specified {@link ITurn} instance.
	 *
	 * @param maxHealth The ITurn
	 * @return The provider
	 */
	public static ICapabilityProvider createProvider(ITurn playerTurn) {
		return new SimpleCapProvider<>(TURN_CAP, DEFAULT_FACING, playerTurn);
	}

	/**
	 * Format a max health value.
	 *
	 * @param maxHealth The max health value
	 * @return The formatted text.
	 */

	/**
	 * Event handler for the {@link ITurn} capability.
	 */
	public static class EventHandler {
		/**
		 * Attach the {@link ITurn} capability to all living entities.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
			if (e.getObject() instanceof EntityPlayer) {
				final PlayerTurn playerTurn = new PlayerTurn((EntityPlayer) e.getObject());
				e.addCapability(ID, createProvider(playerTurn));
			}
		}

		/**
		 * Copy the player's bonus max health when they respawn after dying or returning from the end.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public void playerClone(PlayerEvent.Clone e) {
			final ITurn oldTurn = getTurn(e.getOriginal());
			final ITurn newTurn = getTurn(e.getEntityPlayer());

			if (newTurn != null && oldTurn != null) {
				newTurn.set(oldTurn.getTurn());
			}
		}
	}
}
