package me.ritomg.raptor.manager;

import me.ritomg.raptor.RaptorClient;

import java.util.ArrayList;
import java.util.List;

public class ManagerLoader {

    private static final List<Manager> managers = new ArrayList<>();

    public static void init() {

    }

    private static void register(Manager manager) {
        managers.add(manager);
        RaptorClient.eventBus.subscribe(manager);
    }
}