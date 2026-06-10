package space.impact.packet_network.streamnet

import cpw.mods.fml.common.network.NetworkRegistry
import net.minecraft.entity.player.EntityPlayerMP

class ClientPipe internal constructor(
    private val net: StreamNet,
    private val id: Int,
) {
    fun send(player: EntityPlayerMP, block: StreamOut.() -> Unit) {
        net.channel.sendTo(Envelope(id, net.encode(block)), player)
    }

    fun sendAll(block: StreamOut.() -> Unit) {
        net.channel.sendToAll(Envelope(id, net.encode(block)))
    }

    fun sendDimension(dimensionId: Int, block: StreamOut.() -> Unit) {
        net.channel.sendToDimension(Envelope(id, net.encode(block)), dimensionId)
    }

    fun sendAround(
        dimensionId: Int,
        x: Double,
        y: Double,
        z: Double,
        range: Double,
        block: StreamOut.() -> Unit
    ) {
        net.channel.sendToAllAround(
            Envelope(id, net.encode(block)),
            NetworkRegistry.TargetPoint(dimensionId, x, y, z, range)
        )
    }
}
