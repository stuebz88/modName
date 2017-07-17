package ttftcuts.atg.settings;

import java.util.ArrayList;
import java.util.List;

import com.example.examplemod.ExampleMod;

import ttftcuts.atg.compat.BiomeModule;

public class DefaultWorldSettings extends WorldSettings {

    public DefaultWorldSettings() {
        this.biomeSettings = new DefaultBiomeSettings();
    }

    public void applyDefaultModuleStack() {
        List<BiomeModule> modules = new ArrayList<>();

        for (BiomeModule module : ExampleMod.globalRegistry.biomeModules) {
            if (module.active) {
                modules.add(module);
            }
        }

        modules.sort(null); // natural ordering since they implement Comparable

        for (int i=modules.size()-1; i>=0; i--) {
            BiomeModule module = modules.get(i);
            ExampleMod.logger.info("Applying Biome Module: {} from {}", module.name, module.owner);
            this.biomeSettings.apply(module.settings);
        }
    }
}
