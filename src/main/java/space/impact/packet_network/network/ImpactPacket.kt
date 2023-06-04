package space.impact.packet_network.network

import com.google.common.io.ByteArrayDataInput
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.network.INetHandler
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import net.minecraft.world.WorldServer
import net.minecraftforge.common.DimensionManager
import java.io.DataOutput

private const val NOT_VALID_DIM = -100500
private const val NOT_VALID_PLAYER = -100500

abstract class ImpactPacket {
    var dimId: Int = NOT_VALID_DIM; internal set

    var playerId: Int = NOT_VALID_PLAYER; internal set

    var x: Int = -1; internal set

    var y: Int = -1; internal set

    var z: Int = -1; internal set

    protected val serverWorld: WorldServer?
        get() = DimensionManager.getWorld(dimId)

    protected val serverPlayer: EntityPlayerMP?
        get() = serverWorld?.getEntityByID(playerId) as? EntityPlayerMP

    protected val tileEntity: TileEntity?
        get() = if (MinecraftSide.isServer) serverWorld?.getTileEntity(x, y, z)
        else Minecraft.getMinecraft()?.thePlayer?.worldObj?.getTileEntity(x, y, z)

    abstract fun encode(output: DataOutput)
    abstract fun decode(input: ByteArrayDataInput): ImpactPacket
    internal fun getPacketId(): String = this.javaClass.canonicalName
    open fun processClient(mc: Minecraft, world: IBlockAccess) = Unit
    open fun processServer() = Unit
    open fun setNetHandler(handler: INetHandler) = Unit
}
