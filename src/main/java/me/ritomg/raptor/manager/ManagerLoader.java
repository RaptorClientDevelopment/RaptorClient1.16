package me.ritomg.raptor.manager;

import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.manager.managers.ClientEventManager;

import java.util.ArrayList;
import java.util.List;

public class ManagerLoader {

    private static final List<Manager> managers = new ArrayList<>();

    public static void init() {
        register(ClientEventManager.INSTANCE);
    }

    private static void register(Manager manager) {
        managers.add(manager);
        RaptorClient.eventBus.subscribe(manager);
    }
}