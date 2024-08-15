package cn.ksmcbrigade.blink.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
@Mod(value = BlinkClient.MODID,dist = Dist.CLIENT)
public class BlinkClient {

    public static final String MODID = "blink";
    public static final KeyMapping key = new KeyMapping("key.blink.blink", GLFW.GLFW_KEY_B,"key.blink.blink");

    public BlinkClient(IEventBus modEventBus, ModContainer modContainer){
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(BlinkClient::onRegisterKeys);
    }

    @OnlyIn(Dist.CLIENT)
    public static void onRegisterKeys(RegisterKeyMappingsEvent event){
        event.register(key);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onInput(InputEvent.Key event){
        if(key.isDown()){
            Hack.set(!Hack.enabled);
            if(Hack.enabled && Minecraft.getInstance().player==null){
                Hack.set(false);
            }
            else if(Hack.enabled){
                Hack.pos = Minecraft.getInstance().player.position();
                Hack.heal = Minecraft.getInstance().player.getHealth();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Hack {
        public static boolean enabled = false;
        public static Vec3 pos;
        public static float heal = 20.0F;

        public static void set(boolean action){
            enabled = action;
            if(Minecraft.getInstance().player!=null) Minecraft.getInstance().player.displayClientMessage(Component.translatable("key.blink.blink").append(":").append(CommonComponents.SPACE).append(String.valueOf(enabled)),true);
        }

        public static boolean noCracked(){
            return true;
        }
    }
}
