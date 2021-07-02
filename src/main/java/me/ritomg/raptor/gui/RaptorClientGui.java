package me.ritomg.raptor.gui;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.layout.*;
import com.lukflug.panelstudio.mc16fabric.MinecraftHUDGUI;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SettingsAnimation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.hud.HUDGUI;
import com.lukflug.panelstudio.layout.ChildUtil.ChildMode;
import com.lukflug.panelstudio.popup.CenteredPositioner;
import com.lukflug.panelstudio.popup.MousePositioner;
import com.lukflug.panelstudio.popup.PanelPositioner;
import com.lukflug.panelstudio.popup.PopupTuple;

import com.mojang.blaze3d.platform.GlStateManager;
import me.ritomg.raptor.module.Category;
import me.ritomg.raptor.module.HUDModule;
import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.module.ModuleManager;
import me.ritomg.raptor.module.modules.client.ClickGuiModule;
import me.ritomg.raptor.module.modules.client.ColorMain;

import me.ritomg.raptor.setting.Setting;
import me.ritomg.raptor.setting.SettingsManager;
import me.ritomg.raptor.setting.values.*;
import me.ritomg.raptor.util.FontUtil;
import me.ritomg.raptor.util.RCColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RaptorClientGui extends MinecraftHUDGUI {

    public ClickGuiModule clickGuiModule = ModuleManager.getModule(ClickGuiModule.class);
    public ColorMain colorMain = ModuleManager.getModule(ColorMain.class);
    public static final int WIDTH = 100, HEIGHT = 12, FONT_HEIGHT = 9, DISTANCE = 10, HUD_BORDER = 2;
    public static IClient client;
    public static GUIInterface guiInterface;
    public static Comparator<IModule> modulesCompar;
    public static HUDGUI gui;
    private final ITheme theme, gameSenseTheme, clearTheme, rainbowTheme, windowstheme,cleargradientTheme, impacttheme;

    public RaptorClientGui() {

        MinecraftClient mc = MinecraftClient.getInstance();

        // Define interface and themes ..
        guiInterface = new GUIInterface(true) {
            @Override
            public void drawString(Point pos, int height, String s, Color c) {
                GlStateManager.pushMatrix();
                GlStateManager.translatef(pos.x,pos.y,0);
                double scale=height/(double)(FontUtil.getFontHeight());
                end(false);
                FontUtil.drawStringWithShadow(new MatrixStack(),mc.textRenderer,s,0,0,new RCColor(c));
                begin(false);
                GlStateManager.scaled(scale,scale,1);
                GlStateManager.popMatrix();
            }

            @Override
            public int getFontWidth(int height, String s) {
                double scale=height/(double)(FontUtil.getFontHeight());
                return (int)Math.round(FontUtil.getFontWidth(s)*scale);
            }

            @Override
            public double getScreenWidth() {
                return super.getScreenWidth();
            }

            @Override
            public double getScreenHeight() {
                return super.getScreenHeight();
            }

            @Override
            public String getResourcePrefix() {
                return "raptor:gui/";
            }
        };
        //themes
        gameSenseTheme = new GameSenseTheme(new RCColorScheme("gs",()->clickGuiModule.theme.getValue().equals("GamesenseTheme")),FONT_HEIGHT,3,5,": "+Formatting.GRAY);
        clearTheme = new ClearTheme(new RCColorScheme("clear",()->clickGuiModule.theme.getValue().equals("ClearTheme")),()->false,FONT_HEIGHT,3,1,": "+Formatting.GRAY);
        cleargradientTheme = new ClearTheme(new RCColorScheme("clearg",()->clickGuiModule.theme.getValue().equals("ClearGradientTheme")),()->true,FONT_HEIGHT,3,1,": "+Formatting.GRAY);
        rainbowTheme = new RainbowTheme(new RCColorScheme("rgb", ()->clickGuiModule.theme.getValue().equals("RainbowTheme")), ()->!clickGuiModule.ignoreDisabled.getValue(), ()->clickGuiModule.buttonRainbow.getValue(), ()->clickGuiModule.gradient.getValue(),FONT_HEIGHT,3,":"+Formatting.GRAY);
        windowstheme = new Windows31Theme(new RCColorScheme("win", ()->clickGuiModule.theme.getValue().equals("Windows")), FONT_HEIGHT, 3, 5, ":"+Formatting.GRAY);
        impacttheme = new ImpactTheme(new RCColorScheme("impact",()->clickGuiModule.theme.getValue().equals("ImpactTheme")));

        theme = new IThemeMultiplexer() {
            @Override
            public ITheme getTheme() {
                switch (clickGuiModule.theme.getValue()) {
                    case "Windows" :
                        return windowstheme;
                    case "RainbowTheme" :
                        return rainbowTheme;
                    case "ClearTheme" :
                        return clearTheme;
                    case "ClearGradientTheme" :
                        return cleargradientTheme;
                    case "ImpactTheme" :
                        return impacttheme;
                    default:
                        return gameSenseTheme;
                }
            }
        };

        // Define client structure
        client=()-> Arrays.stream(Category.values()).sorted((a, b)->a.toString().compareTo(b.toString())).map(category->new ICategory() {
            @Override
            public String getDisplayName() {
                return category.toString();
            }

            @Override
            public Stream<IModule> getModules() {
                return ModuleManager.getModulesInCategory(category).stream().sorted((a,b)->a.getName().compareTo(b.getName())).map(module->new IModule() {
                    @Override
                    public String getDisplayName() {
                        return module.getName();
                    }

                    @Override
                    public String getDescription() {
                        return module.getDescription();
                    }

                    @Override
                    public IToggleable isEnabled() {
                        return new IToggleable() {
                            @Override
                            public boolean isOn() {
                                return module.isEnabled();
                            }

                            @Override
                            public void toggle() {
                                module.toggle();
                            }
                        };
                    }

                    @Override
                    public Stream<ISetting<?>> getSettings() {
                        Stream<ISetting<?>> temp=SettingsManager.getSettingsForModule(module).stream().map(setting->createSetting(setting));
                        return Stream.concat(temp,Stream.concat(Stream.of(new IBooleanSetting() {
                            @Override
                            public String getDisplayName() {
                                return "Toggle Msgs";
                            }

                            @Override
                            public void toggle() {
                                module.setToggleMsg(!module.isToggleMsg());
                            }

                            @Override
                            public boolean isOn() {
                                return module.isToggleMsg();
                            }
                        }),Stream.of(new IKeybindSetting() {
                            @Override
                            public String getDisplayName() {
                                return "Keybind";
                            }

                            @Override
                            public int getKey() {
                                return module.getBind();
                            }

                            @Override
                            public void setKey(int key) {
                                module.setBind(key);
                            }

                            @Override
                            public String getKeyName() {
                                return GLFW.glfwGetKeyName(module.getBind(),module.getBind());
                            }
                        })));
                    }
                });
            }

        });

        // Define GUI object
        IToggleable guiToggle=new SimpleToggleable(false);
        IToggleable hudToggle=new SimpleToggleable(false) {
            @Override
            public boolean isOn() {
                if (guiToggle.isOn()&&super.isOn()) return clickGuiModule.showHUD.getValue();
                return super.isOn();
            }
        };
        gui = new HUDGUI(guiInterface,theme.getDescriptionRenderer(),new MousePositioner(new Point(10,10)),guiToggle,hudToggle);
        BiFunction<Context,Integer,Integer> scrollHeight=(context, componentHeight)->{
            if (clickGuiModule.scrolling.getValue().equals("Screen")) return componentHeight;
            else return Math.min(componentHeight,Math.max(HEIGHT*4,RaptorClientGui.this.height-context.getPos().y-HEIGHT));
        };
        Supplier<Animation> animation=()->new SettingsAnimation(()->clickGuiModule.animationSpeed.getValue(),()->guiInterface.getTime());
        PopupTuple popupType=new PopupTuple(new PanelPositioner(new Point(0,0)),false,new IScrollSize() {
            @Override
            public int getScrollHeight (Context context, int componentHeight) {
                return scrollHeight.apply(context,componentHeight);
            }
        });
        // Populate HUD
        for (Module module : ModuleManager.getModules()) {
            if (module instanceof HUDModule) {
                ((HUDModule)module).populate(theme);
                gui.addHUDComponent(((HUDModule)module).getComponent(),new IToggleable() {
                    @Override
                    public boolean isOn() {
                        return module.isEnabled();
                    }

                    @Override
                    public void toggle() {
                        module.toggle();
                    }
                },animation.get(),theme,HUD_BORDER);
            }
        }
        // Populate GUI
        // default panel
        IComponentAdder classicPanelAdder=new PanelAdder(new IContainer<IFixedComponent>() {
            @Override
            public boolean addComponent(IFixedComponent component) {
                return gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {
                    @Override
                    public void handleScroll (Context context, int diff) {
                        IFixedComponentProxy.super.handleScroll(context,diff);
                        if (clickGuiModule.scrolling.getValue().equals("Screen")) {
                            Point p = getPosition(guiInterface);
                            p.translate(0, -diff);
                            setPosition(guiInterface, p);
                        }
                    }

                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                });
            }

            @Override
            public boolean addComponent(IFixedComponent component, IBoolean visible) {
                return gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {
                    @Override
                    public void handleScroll (Context context, int diff) {
                        IFixedComponentProxy.super.handleScroll(context,diff);
                        if (clickGuiModule.scrolling.getValue().equals("Screen")) {
                            Point p = getPosition(guiInterface);
                            p.translate(0, -diff);
                            setPosition(guiInterface, p);
                        }
                    }

                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                },visible);
            }

            @Override
            public boolean removeComponent(IFixedComponent component) {
                return gui.removeComponent(component);
            }
        },false,()->clickGuiModule.layout.getValue().equals("Normal"),title->title) {
            @Override
            protected IScrollSize getScrollSize (IResizable size) {
                return new IScrollSize() {
                    @Override
                    public int getScrollHeight (Context context, int componentHeight) {
                        return scrollHeight.apply(context,componentHeight);
                    }
                };
            }
        };
        // for better usage
        IComponentGenerator generator=new ComponentGenerator(scancode->scancode==GLFW.GLFW_KEY_DELETE,character->character>=' ', new TextFieldKeys()){
            @Override
            public IComponent getColorComponent(IColorSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
                 return new ColorPickerComponent((IColorSetting)setting,new ThemeTuple(theme.theme,theme.logicalLevel,colorLevel));
            }
//            @Override
//            public IComponent getNumberComponent(INumberSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
//                return new Spinner(setting,theme,isContainer,true,new TextFieldKeys());
//            }

//            @Override
//            public IComponent getEnumComponent(IEnumSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
//                return new DropDownList(setting, theme, isContainer, false, new TextFieldKeys(), new IScrollSize() {
//                }, adder::addPopup) {
//     TODO               @Override
//                    protected Animation getAnimation() {
//                        return new SettingsAnimation(()->clickGuiModule.animationSpeed.getValue(), ()->getInterface().getTime());
//                    }
//
//                    @Override
//                    public boolean allowCharacter(char character) {
//                        return character>=' ';
//
//                    }
//
//                    @Override
//                    protected boolean isUpKey(int key) {
//                        return key == Keyboard.KEY_UP;
//                    }
//
//                    @Override
//                    protected boolean isDownKey(int key) {
//                        return key == Keyboard.KEY_DOWN;
//                    }
//
//                    @Override
//                    protected boolean isEnterKey(int key) {
//                        return key == Keyboard.KEY_RETURN;
//                    }
//                };
//            }
        };
        ILayout classicPanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,level->ChildMode.DOWN,level->ChildMode.DOWN,popupType);
        classicPanelLayout.populateGUI(classicPanelAdder,generator,client,theme);

        //single panel layout

        IComponentAdder singlePanelAdder = new SinglePanelAdder(new IContainer<IFixedComponent>() {
            @Override
            public boolean addComponent(IFixedComponent component) {
                return gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {
                    @Override
                    public void handleScroll (Context context, int diff) {
                        IFixedComponentProxy.super.handleScroll(context,diff);
                        if (clickGuiModule.scrolling.getValue().equals("Screen")) {
                            Point p = getPosition(guiInterface);
                            p.translate(0, -diff);
                            setPosition(guiInterface, p);
                        }
                    }

                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                });
            }

            @Override
            public boolean addComponent(IFixedComponent component, IBoolean visible) {
                return gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {
                    @Override
                    public void handleScroll (Context context, int diff) {
                        IFixedComponentProxy.super.handleScroll(context,diff);
                        if (clickGuiModule.scrolling.getValue().equals("Screen")) {
                            Point p = getPosition(guiInterface);
                            p.translate(0, -diff);
                            setPosition(guiInterface, p);
                        }
                    }

                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                },visible);
            }
            @Override
            public boolean removeComponent(IFixedComponent component) {
                return gui.removeComponent(component);
            }
        }, new Labeled("RaptorClient","Be Good Be Raptor", ()->true),theme,new Point(0,0), WIDTH + 500,animation, ()->clickGuiModule.layout.getValue().equals("Single"), "SinglePanel" );

        ILayout singlePanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,level->ChildMode.DOWN,level->ChildMode.DOWN,popupType);
        singlePanelLayout.populateGUI(singlePanelAdder,generator,client,theme);

        // CSGO Layout!
        PopupTuple colorPopup=new PopupTuple(new CenteredPositioner(()->new Rectangle(new Point(0,0),guiInterface.getWindowSize())),true,new IScrollSize() {});
        IComponentAdder horizontalCSGOAdder=new PanelAdder(gui,true,()->clickGuiModule.layout.getValue().equals("CSGO"),title->title);
        ILayout horizontalCSGOLayout=new CSGOLayout(new Labeled("RaptorClient","Be good be Raptor",()->true),new Point(100,100),470,WIDTH,animation,"Enabled",true,true,2,ChildMode.DOWN,colorPopup) {
            @Override
            public int getScrollHeight (Context context, int componentHeight) {
                return 320;
            }

            @Override
            protected boolean isUpKey (int key) {
                return key==GLFW.GLFW_KEY_UP;
            }

            @Override
            protected boolean isDownKey (int key) {
                return key== GLFW.GLFW_KEY_DOWN;
            }

            @Override
            protected boolean isLeftKey (int key) {
                return key==GLFW.GLFW_KEY_LEFT;
            }

            @Override
            protected boolean isRightKey (int key) {
                return key==GLFW.GLFW_KEY_RIGHT;
            }
        };
        horizontalCSGOLayout.populateGUI(horizontalCSGOAdder,generator,client,theme);

        //SEARCHABLE Layout!
        modulesCompar = new Comparator<IModule>() {
            @Override
            public int compare(IModule o1, IModule o2) {
                return o1.getDisplayName().toLowerCase().compareTo(o2.getDisplayName().toLowerCase());
            }
        };
        IComponentAdder searchAdder=new PanelAdder(gui,true,()->clickGuiModule.layout.getValue().equals("Search"),title->title);
        ILayout searchLayout = new SearchableLayout(new Labeled("RaptorClient", "Be good be Raptor", () -> true), new Labeled("Search", null, () -> true), new Point(100, 100), 480, WIDTH, animation, "Enabled", 2, ChildMode.DOWN, colorPopup, modulesCompar , character -> character >= ' ', new TextFieldKeys());
        searchLayout.populateGUI(searchAdder, generator,client,theme);
    }

    private ISetting<?> createSetting (Setting<?> setting) {
        if (setting instanceof BooleanSetting) {
            return new IBooleanSetting() {
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }

                @Override
                public IBoolean isVisible() {
                    return ()->setting.isVisible();
                }

                @Override
                public void toggle() {
                    ((BooleanSetting)setting).setValue(!((BooleanSetting)setting).getValue());
                }

                @Override
                public boolean isOn() {
                    return ((BooleanSetting)setting).getValue();
                }

                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    return null;
                }
            };
        } else if (setting instanceof IntegerSetting) {
            return new INumberSetting() {
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }

                @Override
                public IBoolean isVisible() {
                    return ()->setting.isVisible();
                }

                @Override
                public double getNumber() {
                    return ((IntegerSetting)setting).getValue();
                }

                @Override
                public void setNumber(double value) {
                    ((IntegerSetting)setting).setValue((int)Math.round(value));
                }

                @Override
                public double getMaximumValue() {
                    return ((IntegerSetting)setting).getMax();
                }

                @Override
                public double getMinimumValue() {
                    return ((IntegerSetting)setting).getMin();
                }

                @Override
                public int getPrecision() {
                    return 0;
                }

                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    return null;
					/*if (setting.getSubSettings().count()==0) return null;
					return setting.getSubSettings().map(subSetting->createSetting(subSetting));*/
                }
            };
        } else if (setting instanceof DoubleSetting) {
            return new INumberSetting() {
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }

                @Override
                public IBoolean isVisible() {
                    return ()->setting.isVisible();
                }

                @Override
                public double getNumber() {
                    return ((DoubleSetting)setting).getValue();
                }

                @Override
                public void setNumber(double value) {
                    ((DoubleSetting)setting).setValue(value);
                }

                @Override
                public double getMaximumValue() {
                    return ((DoubleSetting)setting).getMax();
                }

                @Override
                public double getMinimumValue() {
                    return ((DoubleSetting)setting).getMin();
                }

                @Override
                public int getPrecision() {
                    return 2;
                }

                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    return null;
                }
            };
        } else if (setting instanceof ModeSetting) {
            return new IEnumSetting() {
                private final ILabeled[] states=((ModeSetting)setting).getModes().stream().map(mode->new Labeled(mode,null,()->true)).toArray(ILabeled[]::new);

                @Override
                public String getDisplayName() {
                    return setting.getName();
                }

                @Override
                public IBoolean isVisible() {
                    return ()->setting.isVisible();
                }

                @Override
                public void increment() {
                    ((ModeSetting)setting).increment();
                }

                @Override
                public void decrement() {
                    ((ModeSetting)setting).decrement();
                }

                @Override
                public String getValueName() {
                    return ((ModeSetting)setting).getValue();
                }

                @Override
                public int getValueIndex() {
                    return ((ModeSetting)setting).getModes().indexOf(getValueName());
                }

                @Override
                public void setValueIndex(int index) {
                    ((ModeSetting)setting).setValue(((ModeSetting)setting).getModes().get(index));
                }

                @Override
                public ILabeled[] getAllowedValues() {
                    return states;
                }

                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    return null;
					/*if (setting.getSubSettings().count()==0) return null;
					return setting.getSubSettings().map(subSetting->createSetting(subSetting));*/
                }
            };
        } else if (setting instanceof ColorSetting) {
            return new IColorSetting() {
                @Override
                public String getDisplayName() {
                    return Formatting.BOLD+setting.getName();
                }

                @Override
                public IBoolean isVisible() {
                    return ()->setting.isVisible();
                }

                @Override
                public Color getValue() {
                    return ((ColorSetting)setting).getValue();
                }

                @Override
                public void setValue(Color value) {
                    ((ColorSetting)setting).setValue(new RCColor(value));
                }

                @Override
                public Color getColor() {
                    return ((ColorSetting)setting).getColor();
                }

                @Override
                public boolean getRainbow() {
                    return ((ColorSetting)setting).getRainbow();
                }

                @Override
                public void setRainbow(boolean rainbow) {
                    ((ColorSetting)setting).setRainbow(rainbow);
                }

                @Override
                public boolean hasAlpha() {
                    return ((ColorSetting)setting).alphaEnabled();
                }

                @Override
                public boolean allowsRainbow() {
                    return ((ColorSetting)setting).rainbowEnabled();
                }

                @Override
                public boolean hasHSBModel() {
                    return ModuleManager.getModule(ColorMain.class).colorModel.getValue().equalsIgnoreCase("HSB");
                }

                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    //Stream<ISetting<?>> temp=setting.getSubSettings().map(subSetting->createSetting(subSetting));
                    return /*Stream.concat(temp,*/Stream.of(new IBooleanSetting() {
                        @Override
                        public String getDisplayName() {
                            return "Sync Color";
                        }

                        @Override
                        public IBoolean isVisible() {
                            return ()->setting!=ModuleManager.getModule(ColorMain.class).enabledColor;
                        }

                        @Override
                        public void toggle() {
                            ((ColorSetting)setting).setValue(ModuleManager.getModule(ColorMain.class).enabledColor.getColor());
                            ((ColorSetting)setting).setRainbow(ModuleManager.getModule(ColorMain.class).enabledColor.getRainbow());
                        }

                        @Override
                        public boolean isOn() {
                            return ModuleManager.getModule(ColorMain.class).enabledColor.getColor().equals(((ColorSetting)setting).getColor());
                        }
                    })/*)*/;
                }
            };
        } else if (setting instanceof StringSetting) {
            return new IStringSetting() {
                @Override
                public String getValue() {
                    return ((StringSetting) setting).getValue();
                }

                @Override
                public void setValue(String string) {
                    ((StringSetting) setting).setValue(string);
                }

                @Override
                public String getDisplayName() {
                    return ((StringSetting) setting).getName();
                }
            };
        }
        return new ISetting<Void>() {
            @Override
            public String getDisplayName() {
                return setting.getName();
            }

            @Override
            public IBoolean isVisible() {
                return ()->setting.isVisible();
            }

            @Override
            public Void getSettingState() {
                return null;
            }

            @Override
            public Class<Void> getSettingClass() {
                return Void.class;
            }

            @Override
            public Stream<ISetting<?>> getSubSettings() {
                return null;
            }
        };
    }

    @Override
    protected HUDGUI getGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return ModuleManager.getModule(ClickGuiModule.class).scrollSpeed.getValue();
    }


    private final class RCColorScheme implements IColorScheme {
        private final String configName;
        private final Supplier<Boolean> isVisible;

        public RCColorScheme (String configName, Supplier<Boolean> isVisible) {
            this.configName=configName;
            this.isVisible=isVisible;
        }

        @Override
        public void createSetting(ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow) {
            ModuleManager.getModule(ClickGuiModule.class).registerColor(name,configName+"_"+name.replace(" ",""),isVisible,rainbow,allowsRainbow,hasAlpha,new RCColor(color));
        }

        @Override
        public Color getColor(String name) {
            return ((ColorSetting) SettingsManager.getSettingsForModule(ModuleManager.getModule(ClickGuiModule.class)).stream().filter(setting->setting.getConfigName().equals(configName+"_"+name.replace(" ",""))).findFirst().orElse(null)).getValue();
        }
    }
}