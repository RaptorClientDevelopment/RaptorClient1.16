package me.ritomg.raptor.event.events;

import me.ritomg.raptor.event.RaptorClientMainEvent;

public class KeyinputEvent extends RaptorClientMainEvent {

    private int key;
    private int scanCode;

    public KeyinputEvent(int key, int scanCode) {
        this.key = key;
        this.scanCode = scanCode;
    }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }

    public static class Global extends KeyinputEvent {

        private int action;
        private int modifiers;

        public Global(int key, int scanCode, int action, int modifiers) {
            super(key, scanCode);
            this.action = action;
            this.modifiers = modifiers;
        }

        public int getAction() {
            return action;
        }

        public int getModifiers() {
            return modifiers;
        }

    }

    public static class InWorld extends KeyinputEvent {

        public InWorld(int key, int scanCode) {
            super(key, scanCode);
        }

    }

    public static class InChat extends KeyinputEvent {

        private int modifiers;

        public InChat(int key, int scanCode, int modifiers) {
            super(key, scanCode);
            this.modifiers = modifiers;
        }

        public int getModifiers() {
            return modifiers;
        }
    }
}
