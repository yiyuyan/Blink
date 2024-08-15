package cn.ksmcbrigade.blink.mixin;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerCommonPacketListener;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetworkRegistry.class,remap = false)
public class NetworkRegistryMixin {
    @Inject(method = "checkPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/protocol/common/ServerCommonPacketListener;)V",at = @At(value = "INVOKE", target = "Ljava/lang/UnsupportedOperationException;<init>(Ljava/lang/String;)V",shift = At.Shift.BEFORE), cancellable = true)
    private static void check(Packet<?> packet, ServerCommonPacketListener listener, CallbackInfo ci){
        ci.cancel();
    }
}
