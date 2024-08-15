package cn.ksmcbrigade.blink;


import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(value = "blink")
public class Blink {

    public Blink(IEventBus modEventBus, ModContainer modContainer){
        modEventBus.addListener(this::registerPayload);
        System.out.println("Hello Blink Minecraft NeoForge Server.");
    }

    public void registerPayload(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("340");
        registrar.playBidirectional(EmptyDate.TYPE,EmptyDate.STREAM_CODEC,new DirectionalPayloadHandler<>(PacketHandler::handle, PacketHandler::handle));
    }

    public record EmptyDate(String context) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<EmptyDate> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("blink", "empty_context"));

        public static final StreamCodec<ByteBuf, EmptyDate> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                EmptyDate::context,
                EmptyDate::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class PacketHandler {
        public static void handle(final EmptyDate date, final IPayloadContext context){
        }
    }
}
