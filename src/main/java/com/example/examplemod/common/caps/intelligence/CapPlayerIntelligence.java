package com.example.examplemod.common.caps.intelligence;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.example.examplemod.Ref;
import com.example.examplemod.common.caps.CapabilityUtils;
import com.example.examplemod.common.caps.SimpleCapProvider;
import com.example.examplemod.common.map.tile.TilePos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

public class CapPlayerIntelligence
{
	@CapabilityInject(IIntelligence.class)
	public static final Capability<IIntelligence> INTELLIGENCE_CAP = null;
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	public static final ResourceLocation ID = new ResourceLocation(Ref.MODID, "PlayerIntelligence");

	public static void register() {
		CapabilityManager.INSTANCE.register(IIntelligence.class, new Capability.IStorage<IIntelligence>() {
			@Override
			public NBTBase writeNBT(Capability<IIntelligence> capability, IIntelligence instance, EnumFacing side) {
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagList seenNBTList = new NBTTagList();
				NBTTagList inViewNBTList = new NBTTagList();
				ArrayList<TilePos> seenTiles = instance.getSeenTiles();
				ArrayList<TilePos> tilesInView = instance.getTilesInView();
				
				for(int i = 0; i < seenTiles.size(); i++)
				{
					NBTTagCompound tileNBT = new NBTTagCompound();
					
					tileNBT.setInteger("x", seenTiles.get(i).x);
					tileNBT.setInteger("z", seenTiles.get(i).z);
					seenNBTList.appendTag(tileNBT);
				}
				
				for(int i = 0; i < tilesInView.size(); i++)
				{
					NBTTagCompound tileNBT = new NBTTagCompound();
					
					tileNBT.setInteger("x", tilesInView.get(i).x);
					tileNBT.setInteger("z", tilesInView.get(i).z);
					inViewNBTList.appendTag(tileNBT);
				}
				
				nbt.setTag("seenTiles", seenNBTList);
				nbt.setTag("tilesInView", inViewNBTList);
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<IIntelligence> capability, IIntelligence instance, EnumFacing side, NBTBase nbt) {
				ArrayList<TilePos> seenTiles = new ArrayList<TilePos>();
				ArrayList<TilePos> tilesInView = new ArrayList<TilePos>();
				
				NBTTagCompound nbtCompound = ((NBTTagCompound) nbt);
				NBTTagList seenNBTList = nbtCompound.getTagList("seenTiles", 10);
				NBTTagList inViewNBTList = nbtCompound.getTagList("tilesInView", 10);
				
				for(int i = 0; i < seenNBTList.tagCount(); i++)
				{
					NBTTagCompound tileNBT = seenNBTList.getCompoundTagAt(i);
					int tileX = tileNBT.getInteger("x");
					int tileZ = tileNBT.getInteger("z");
					
					seenTiles.add(new TilePos(tileX, tileZ));
				}
				
				for(int i = 0; i < inViewNBTList.tagCount(); i++)
				{
					NBTTagCompound tileNBT = inViewNBTList.getCompoundTagAt(i);
					int tileX = tileNBT.getInteger("x");
					int tileZ = tileNBT.getInteger("z");
					
					
					tilesInView.add(new TilePos(tileX, tileZ));
				}
				
				
				instance.set(seenTiles, tilesInView);
			}
		}, () -> new PlayerIntelligence(null));

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	/**
	 * Get the {@link IIntelligence} from the specified entity.
	 *
	 * @param entity The entity
	 * @return The ITile
	 */
	@Nullable
	public static IIntelligence getTiles(EntityPlayer player) {
		return CapabilityUtils.getCapability(player, INTELLIGENCE_CAP, DEFAULT_FACING);
	}

	/**
	 * Create a provider for the specified {@link IIntelligence} instance.
	 *
	 * @param maxHealth The ITile
	 * @return The provider
	 */
	public static ICapabilityProvider createProvider(IIntelligence playerTiles) {
		return new SimpleCapProvider<>(INTELLIGENCE_CAP, DEFAULT_FACING, playerTiles);
	}

	/**
	 * Format a max health value.
	 *
	 * @param maxHealth The max health value
	 * @return The formatted text.
	 */

	/**
	 * Event handler for the {@link IIntelligence} capability.
	 */
	public static class EventHandler {
		/**
		 * Attach the {@link IIntelligence} capability to all living entities.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
			if (e.getObject() instanceof EntityPlayer) {
				final PlayerIntelligence playerIntelligence = new PlayerIntelligence((EntityPlayer) e.getObject());
				e.addCapability(ID, createProvider(playerIntelligence));
			}
		}

		/**
		 * Copy the player's bonus max health when they respawn after dying or returning from the end.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public void playerClone(PlayerEvent.Clone e) {
			final IIntelligence oldTiles = getTiles(e.getOriginal());
			final IIntelligence newTiles = getTiles(e.getEntityPlayer());

			if (newTiles != null && oldTiles != null) {
				newTiles.set(oldTiles);
			}
		}
	}
}
