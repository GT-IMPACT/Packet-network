package space.impact.packet_network.network.packets

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import io.netty.buffer.ByteBufOutputStream
import net.minecraft.client.Minecraft
import net.minecraft.world.IBlockAccess

@Suppress("UnstableApiUsage")
open class StreamPacketBase(
    packetId: Int,
    internal val commonProcess: StreamPacketBase.(Boolean, ByteArrayDataInput) -> Unit,
    internal val write: ByteArrayDataOutput? = null,
    internal val read: ByteArrayDataInput? = null,
) : ImpactPacket(packetId) {

    override fun processServer(): Unit {
        read?.also { commonProcess(true, it) }
    }

    override fun processClient(mc: Minecraft, world: IBlockAccess) {
        read?.also { commonProcess(false, it) }
    }

    override fun encode(output: ByteBufOutputStream) {
        write?.toByteArray()?.also { output.write(it) }
    }

    override fun decode(input: ByteArrayDataInput): ImpactPacket {
        return StreamPacketBase(packetId, commonProcess, read = input)
    }

    fun transaction(value: ByteArrayDataInput): StreamPacketBase {
        return StreamPacketBase(packetId, commonProcess, write = value)
    }

    fun transaction(value: Int): StreamPacketBase {
        val write = ByteStreams.newDataOutput(20 * 2 + 4)
        write.writeInt(value)
        return StreamPacketBase(packetId, commonProcess, write = write)
    }

    fun transaction(vararg value: Int): StreamPacketBase {
        val write = ByteStreams.newDataOutput(20 * 2 + value.size * 8)
        write.writeInt(value.size)
        value.forEach(write::writeInt)
        return StreamPacketBase(packetId, commonProcess, write = write)
    }

    fun transaction(value: String): StreamPacketBase {
        val write = ByteStreams.newDataOutput(20 * 2 + value.toCharArray().size * 4)
        write.writeUTF(value)
        return StreamPacketBase(packetId, commonProcess, write = write)
    }

    fun transaction(vararg value: String): StreamPacketBase {
        val bytes = value.sumOf { it.toCharArray().size } * 4
        val write = ByteStreams.newDataOutput(20 * 2 + bytes)
        write.writeInt(value.size)
        value.forEach(write::writeUTF)
        return StreamPacketBase(packetId, commonProcess, write = write)
    }

    fun transaction(value: Boolean): StreamPacketBase {
        val write = ByteStreams.newDataOutput(20 * 2 + 2)
        write.writeBoolean(value)
        return StreamPacketBase(packetId, commonProcess, write = write)
    }
}
