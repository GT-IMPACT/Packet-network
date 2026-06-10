package space.impact.packet_network.streamnet

import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.relauncher.Side
import io.netty.buffer.Unpooled
import net.minecraft.entity.player.EntityPlayerMP

class StreamNet(channelName: String) {

    internal val channel: SimpleNetworkWrapper =
        NetworkRegistry.INSTANCE.newSimpleChannel(channelName)

    private val serverHandlers = hashMapOf<Int, (EntityPlayerMP, StreamIn) -> Unit>()
    private val clientHandlers = hashMapOf<Int, (StreamIn) -> Unit>()

    init {
        channel.registerMessage(C2SHandler(this), Envelope::class.java, 0, Side.SERVER)
        channel.registerMessage(S2CHandler(this), Envelope::class.java, 1, Side.CLIENT)
    }

    fun server(id: Int, handler: (EntityPlayerMP, StreamIn) -> Unit): ServerPipe {
        serverHandlers[id] = handler
        return ServerPipe(this, id)
    }

    fun client(id: Int, handler: (StreamIn) -> Unit): ClientPipe {
        clientHandlers[id] = handler
        return ClientPipe(this, id)
    }

    internal fun encode(block: StreamOut.() -> Unit): ByteArray {
        val buf = Unpooled.buffer()
        StreamOut(buf).block()
        val bytes = ByteArray(buf.readableBytes())
        buf.readBytes(bytes)
        return bytes
    }

    internal fun handleServer(player: EntityPlayerMP, msg: Envelope) {
        serverHandlers[msg.id]?.invoke(player, StreamIn(Unpooled.wrappedBuffer(msg.payload)))
            ?: error("Unknown server pipe id: ${msg.id}")
    }

    internal fun handleClient(msg: Envelope) {
        clientHandlers[msg.id]?.invoke(StreamIn(Unpooled.wrappedBuffer(msg.payload)))
            ?: error("Unknown client pipe id: ${msg.id}")
    }
}