package space.impact.packet_network.streamnet

import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.MessageContext

internal class C2SHandler(
    private val net: StreamNet,
) : IMessageHandler<Envelope, IMessage?> {

    override fun onMessage(msg: Envelope, ctx: MessageContext): IMessage? {
        net.handleServer(ctx.serverHandler.playerEntity, msg)
        return null
    }
}
