package cn.ksmcbrigade.blink.mixin;

import cn.ksmcbrigade.blink.client.BlinkClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@OnlyIn(Dist.CLIENT)
@Mixin(Connection.class)
public abstract class ConnectionMixin {

    @ModifyVariable(method = "doSendPacket",at = @At("HEAD"),argsOnly = true)
    public Packet<?> doSendPacket(Packet<?> value){
        if(Minecraft.getInstance().player!=null) BlinkClient.Hack.heal = Math.max(BlinkClient.Hack.heal,Minecraft.getInstance().player.getHealth());
        if((value instanceof ServerboundMovePlayerPacket) && BlinkClient.Hack.enabled && BlinkClient.Hack.noCracked() && Minecraft.getInstance().player!=null && Minecraft.getInstance().player.getHealth()<BlinkClient.Hack.heal){
            BlinkClient.Hack.heal = Minecraft.getInstance().player.getHealth();
            BlinkClient.Hack.pos = Minecraft.getInstance().player.position();
            Minecraft.getInstance().player.displayClientMessage(Component.literal("[Blink] rest the original position because the other entities' attack."),true);
        }
        if((value instanceof ServerboundMovePlayerPacket packet) && BlinkClient.Hack.enabled && BlinkClient.Hack.noCracked() && BlinkClient.Hack.pos!=null && Minecraft.getInstance().player!=null && BlinkClient.Hack.pos.distanceTo(Minecraft.getInstance().player.position())<=8.5D){
            return new ServerboundMovePlayerPacket.PosRot(BlinkClient.Hack.pos.x,BlinkClient.Hack.pos.y,BlinkClient.Hack.pos.z,packet.getXRot(0),packet.getYRot(0),packet.isOnGround());
        }
        else if((value instanceof ServerboundMovePlayerPacket) && BlinkClient.Hack.enabled && BlinkClient.Hack.noCracked() && BlinkClient.Hack.pos!=null && Minecraft.getInstance().player!=null && BlinkClient.Hack.pos.distanceTo(Minecraft.getInstance().player.position())>8.5D){
            BlinkClient.Hack.pos = Minecraft.getInstance().player.position();
            Minecraft.getInstance().player.displayClientMessage(Component.literal("[Blink] rest the original position because of the distance."),true);
        }
        return value;
    }
}
