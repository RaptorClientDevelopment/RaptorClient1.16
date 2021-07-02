package me.ritomg.raptor.event;

public interface MultiPhase<T extends RaptorClientMainEvent> {

    Phase getPhase();

    T nextPhase();
}