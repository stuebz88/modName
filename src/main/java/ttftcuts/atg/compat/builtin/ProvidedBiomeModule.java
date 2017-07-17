package ttftcuts.atg.compat.builtin;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Ref;

import ttftcuts.atg.compat.BiomeModule;
import ttftcuts.atg.settings.BiomeSettings;

public class ProvidedBiomeModule extends BiomeModule {
    public final String modid;

    public ProvidedBiomeModule(String name, String modid) {
        super(name, Ref.MODID, new BiomeSettings(), true);
        this.modid = modid;

        ExampleMod.modCompat.builtInBiomeModules.add(this);
        ExampleMod.logger.info("Created built-in biome module: {} for {}", this.name, this.modid);
    }
}
