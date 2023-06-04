package space.impact.packet_network.network.basic

import com.google.common.io.ByteArrayDataInput
import net.minecraft.client.Minecraft
import net.minecraft.world.IBlockAccess
import space.impact.packet_network.network.IPacketDataSerializable
import space.impact.packet_network.network.IPacketReceiverSerializable
import space.impact.packet_network.network.ImpactPacket
import space.impact.packet_network.network.NothingPacketDataSerializable
import java.io.DataOutput

class SerializationPacketGui(
    override val value: IPacketDataSerializable = NothingPacketDataSerializable,
) : SerializationPacket(value) {

    override fun encode(output: DataOutput) {
        value.writeToByteBuf(output)
    }

    override fun decode(input: ByteArrayDataInput): ImpactPacket {
        val data = value.readFromPacket(input)
        return SerializationPacketGui(data)
    }

    override fun processServer() {
        serverPlayer?.also { player ->
            val container = player.openContainer
            if (container is IPacketReceiverSerializable) {
                container.receive(value)
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
