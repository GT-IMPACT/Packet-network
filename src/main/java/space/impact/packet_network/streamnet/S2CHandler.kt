package space.impact.packet_network.streamnet

import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.MessageContext

internal class S2CHandler(
    private val net: StreamNet,
) : IMessageHandler<Envelope, IMessage?> {

    override fun onMessage(msg: Envelope, ctx: MessageContext): IMessage? {
        net.handleClient(msg)
        return null
    }
}
