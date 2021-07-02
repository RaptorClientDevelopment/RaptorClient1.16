package me.ritomg.raptor.gui;

import com.lukflug.panelstudio.widget.ITextFieldKeys;
import org.lwjgl.glfw.GLFW;

public class TextFieldKeys implements ITextFieldKeys {


    @Override
    public boolean isBackspaceKey(int scancode) {
        return GLFW.GLFW_KEY_BACKSPACE == scancode;
    }

    @Override
    public boolean isDeleteKey(int scancode) {
        return GLFW.GLFW_KEY_DELETE == scancode;
    }

    @Override
    public boolean isInsertKey(int scancode) {
        return GLFW.GLFW_KEY_INSERT == scancode;
    }

    @Override
    public boolean isLeftKey(int scancode) {
        return GLFW.GLFW_KEY_LEFT == scancode;
    }

    @Override
    public boolean isRightKey(int scancode) {
        return GLFW.GLFW_KEY_RIGHT == scancode;
    }

    @Override
    public boolean isHomeKey(int scancode) {
        return GLFW.GLFW_KEY_HOME == scancode;
    }

    @Override
    public boolean isEndKey(int scancode) {
        return GLFW.GLFW_KEY_END == scancode;
    }

    @Override
    public boolean isCopyKey(int scancode) {
        return GLFW.GLFW_KEY_F10 == scancode;
    }

    @Override
    public boolean isPasteKey(int scancode) {
        return GLFW.GLFW_KEY_F12 == scancode;
    }

    @Override
    public boolean isCutKey(int scancode) {
        return false;
    }

    @Override
    public boolean isAllKey(int scancode) {
        return GLFW.GLFW_KEY_4 == scancode;
    }
}
