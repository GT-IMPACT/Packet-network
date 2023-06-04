package space.impact.packet_network.network

import com.google.common.io.ByteArrayDataInput
import net.minecraft.client.Minecraft
import net.minecraft.network.INetHandler
import net.minecraft.world.IBlockAccess
import java.io.DataOutput

private const val NOT_VALID_DIM = -100500
private const val NOT_VALID_PLAYER = -100500

abstract class ImpactPacket {
    var dimId: Int = NOT_VALID_DIM
    var playerId: Int = NOT_VALID_PLAYER
    var x: Int = -1
    var y: Int = -1
    var z: Int = -1
    abstract fun encode(output: DataOutput)
    abstract fun decode(input: ByteArrayDataInput): ImpactPacket
    fun getPacketId(): String = this.javaClass.canonicalName
    open fun processClient(mc: Minecraft, world: IBlockAccess) = Unit
    open fun processServer() = Unit
    open fun setNetHandler(handler: INetHandler) = Unit
}
