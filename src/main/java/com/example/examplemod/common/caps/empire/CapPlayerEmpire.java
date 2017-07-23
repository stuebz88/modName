package com.example.examplemod.common.caps.empire;

import javax.annotation.Nullable;

import com.example.examplemod.Ref;
import com.example.examplemod.common.caps.CapabilityUtils;
import com.example.examplemod.common.caps.SimpleCapProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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

public class CapPlayerEmpire
{
		@CapabilityInject(IEmpire.class)
		public static final Capability<IEmpire> EMPIRE_CAP = null;
		
		public static final EnumFacing DEFAULT_FACING = null;
		
		public static final ResourceLocation ID = new ResourceLocation(Ref.MODID, "PlayerEmpire");

		public static void register() {
			CapabilityManager.INSTANCE.register(IEmpire.class, new Capability.IStorage<IEmpire>() {
				@Override
				public NBTBase writeNBT(Capability<IEmpire> capability, IEmpire instance, EnumFacing side) {
					NBTTagCompound data = new NBTTagCompound();
					
					data.setString("")
					return data;
				}

				@Override
				public void readNBT(Capability<IEmpire> capability, IEmpire instance, EnumFacing side, NBTBase nbt) {
					instance.set(((NBTTagInt) nbt).getInt());
				}
			}, () -> new PlayerEmpire(null));

			MinecraftForge.EVENT_BUS.register(new EventHandler());
		}

		/**
		 * Get the {@link IEmpire} from the specified entity.
		 *
		 * @param entity The entity
		 * @return The IEmpire
		 */
		@Nullable
		public static IEmpire getEmpire(EntityPlayer player) {
			return CapabilityUtils.getCapability(player, EMPIRE_CAP, DEFAULT_FACING);
		}

		/**
		 * Create a provider for the specified {@link IEmpire} instance.
		 *
		 * @param maxHealth The IEmpire
		 * @return The provider
		 */
		public static ICapabilityProvider createProvider(IEmpire playerEmpire) {
			return new SimpleCapProvider<>(EMPIRE_CAP, DEFAULT_FACING, playerEmpire);
		}

		/**
		 * Format a max health value.
		 *
		 * @param maxHealth The max health value
		 * @return The formatted text.
		 */

		/**
		 * Event handler for the {@link IEmpire} capability.
		 */
		public static class EventHandler {
			/**
			 * Attach the {@link IEmpire} capability to all living entities.
			 *
			 * @param event The event
			 */
			@SubscribeEvent
			public void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
				if (e.getObject() instanceof EntityPlayer) {
					final PlayerEmpire playerEmpire = new PlayerEmpire((EntityPlayer) e.getObject());
					e.addCapability(ID, createProvider(playerEmpire));
				}
			}

			/**
			 * Copy the player's bonus max health when they respawn after dying or returning from the end.
			 *
			 * @param event The event
			 */
			@SubscribeEvent
			public void playerClone(PlayerEvent.Clone e) {
				final IEmpire oldEmpire = getEmpire(e.getOriginal());
				final IEmpire newEmpire = getEmpire(e.getEntityPlayer());

				if (newEmpire != null && oldEmpire != null) {
					newEmpire.set(oldEmpire.getEmpire());
				}
			}
		}
	}
