package space.impact.packet_network.network.packets

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import io.netty.buffer.ByteBufOutputStream
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.network.INetHandler
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import net.minecraft.world.WorldServer
import net.minecraftforge.common.DimensionManager
import space.impact.packet_network.network.MinecraftSide
import java.io.DataOutput

private const val NOT_VALID_DIM = -100500
private const val NOT_VALID_PLAYER = -100500

abstract class ImpactPacket(
    internal val packetId: Int
) {
    var dimId: Int = NOT_VALID_DIM; internal set
    var playerId: Int = NOT_VALID_PLAYER; internal set

    var x: Int = -1; internal set

    var y: Int = -1; internal set

    var z: Int = -1; internal set

    open val serverWorld: WorldServer?
        get() = DimensionManager.getWorld(dimId)

    open val serverPlayer: EntityPlayerMP?
        get() = serverWorld?.getEntityByID(playerId) as? EntityPlayerMP

    open val tileEntity: TileEntity?
        get() = if (MinecraftSide.isServer) serverWorld?.getTileEntity(x, y, z)
        else Minecraft.getMinecraft()?.thePlayer?.worldObj?.getTileEntity(x, y, z)

    abstract fun encode(output: ByteBufOutputStream)
    abstract fun decode(input: ByteArrayDataInput): ImpactPacket
    open fun getPacketId(): Int = packetId
    open fun processClient(mc: Minecraft, world: IBlockAccess) = Unit
    open fun processServer() = Unit
    open fun setNetHandler(handler: INetHandler) = Unit
}
