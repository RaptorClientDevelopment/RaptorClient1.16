package me.ritomg.raptor.module.modules.render;

import me.ritomg.raptor.module.Category;
import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.setting.values.*;
import me.ritomg.raptor.util.RCColor;

import java.util.Arrays;

@Module.info(name = "Test", category = Category.Render)

public class TestRender extends Module {

    DoubleSetting testSetting = registerDouble("test1", 2,1,3);
    IntegerSetting testSetting2 = registerInteger("test2", 2,1,3);
    ModeSetting testsetting3 = registerMode("Testt3", Arrays.asList("1", "2"), "1");
    StringSetting testsetting4 = registerString("Test4", "Test");
    BooleanSetting testsetting5 = registerBoolean("Test5", false);
    ColorSetting testsetting6 = registerColor("Test6", new RCColor(1,20,60));


}
