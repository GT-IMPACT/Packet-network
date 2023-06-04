package space.impact.packet_network.network.basic

import com.google.common.io.ByteArrayDataInput
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.DimensionManager
import space.impact.packet_network.network.IPacketDataSerializable
import space.impact.packet_network.network.IPacketReceiverSerializable
import space.impact.packet_network.network.ImpactPacket
import space.impact.packet_network.network.NothingPacketDataSerializable
import java.io.DataOutput

class SerializationPacketGuiImp(
    override val value: IPacketDataSerializable = NothingPacketDataSerializable
) : SerializationPacket(value) {

    override fun encode(output: DataOutput) {
        value.writeToByteBuf(output)
    }

    override fun decode(input: ByteArrayDataInput): ImpactPacket {
        val data = value.readFromPacket(input)
        return SerializationPacketGuiImp(data)
    }

    override fun processServer() {
        DimensionManager.getWorld(dimId)?.also { world ->
            (world.getEntityByID(playerId) as? EntityPlayerMP)?.also { player ->
                val container = player.openContainer
                if (container is IPacketReceiverSerializable) {
                    container.receive(value)
                }
            }
        }
    }

    override fun processClient(mc: Minecraft, world: IBlockAccess) {
        val screen = mc.currentScreen
        if (screen is IPacketReceiverSerializable) {
            screen.receive(value)
        }
    }
}
