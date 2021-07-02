package me.ritomg.raptor.module;

import me.ritomg.raptor.module.modules.client.*;
import me.ritomg.raptor.module.modules.combat.*;
import me.ritomg.raptor.module.modules.exploits.*;
import me.ritomg.raptor.module.modules.hud.*;
import me.ritomg.raptor.module.modules.misc.*;
import me.ritomg.raptor.module.modules.movement.*;
import me.ritomg.raptor.module.modules.render.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ModuleManager {
    private static final LinkedHashMap<Class<? extends Module>, Module> modulesClassMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Module> modulesNameMap = new LinkedHashMap<>();

    public static void init() {

        addMod(new ClickGuiModule());
        addMod(new ColorMain());
        addMod(new TestCombat());
        addMod(new TestRender());
        addMod(new TestExploit());
        addMod(new TestHud());
        addMod(new TestMisc());
        addMod(new TestMisc());
        addMod(new TestMovement());
        addMod(new DiscordRPCModule());

    }
    public static void addMod(Module module) {
        modulesClassMap.put(module.getClass(), module);
        modulesNameMap.put(module.getName().toLowerCase(Locale.ROOT), module);
    }
    public static Collection<Module> getModules() {
        return modulesClassMap.values();
    }
    

    public static ArrayList<Module> getModulesInCategory(Category category) {
        ArrayList<Module> list = new ArrayList<>();

        for (Module module : modulesClassMap.values()) {
            if (!module.getCategory().equals(category)) continue;
            list.add(module);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Module> T getModule(Class<T> clazz) {
        return (T) modulesClassMap.get(clazz);
    }

    public static Module getModule(String name) {
        if (name == null) return null;
        return modulesNameMap.get(name.toLowerCase(Locale.ROOT));
    }

    public static boolean isModuleEnabled(Class<? extends Module> clazz) {
        Module module = getModule(clazz);
        return module != null && module.isEnabled();
    }

    public static boolean isModuleEnabled(String name) {
        Module module = getModule(name);
        return module != null && module.isEnabled();
    }
}
