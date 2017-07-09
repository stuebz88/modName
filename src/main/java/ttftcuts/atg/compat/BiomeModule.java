package ttftcuts.atg.compat;

import com.example.examplemod.ExampleMod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import ttftcuts.atg.configuration.ConfigHandler;
import ttftcuts.atg.settings.BiomeSettings;

public class BiomeModule implements Comparable<BiomeModule> {

    public BiomeSettings settings;
    public String name;
    public String owner;
    public boolean active = false;
    public final boolean startsEnabled;

    public BiomeModule(String name, String owner, BiomeSettings settings, boolean startsEnabled) {
        this.name = name;
        this.owner = owner;
        this.settings = settings;
        this.startsEnabled = startsEnabled;
        this.active = startsEnabled;
    }

    public static void fromIMC(FMLInterModComms.IMCMessage message) {
        if (!message.isNBTMessage()) {
        	ExampleMod.logger.warn("Invalid IMC sent by {} - biomeModule expects NBT", message.getSender());
            return;
        }

        NBTTagCompound tag = message.getNBTValue();

        if (!tag.hasKey("name") || tag.getString("name").isEmpty()) {
        	ExampleMod.logger.warn("Invalid IMC sent by {} - biomeModule requires a name in the 'name' tag", message.getSender());
            return;
        }
        if (!tag.hasKey("json") || tag.getString("json").isEmpty()) {
        	ExampleMod.logger.warn("Invalid IMC sent by {} - biomeModule requires biome settings data in the 'json' tag", message.getSender());
            return;
        }

        BiomeSettings settings = new BiomeSettings();

        try {
            settings.readFromJson(tag.getString("json"));
        } catch (Exception e) {
        	ExampleMod.logger.warn("Invalid IMC sent by {} - biomeModule received invalid biome json data", message.getSender());
            return;
        }

        BiomeModule module = new BiomeModule(tag.getString("name"), message.getSender(), settings, tag.getBoolean("enabled"));

        ExampleMod.globalRegistry.biomeModules.add(module);
    }

    @Override
    public int compareTo(BiomeModule o) {
        return this.settings.compareTo(o.settings);
    }
}
