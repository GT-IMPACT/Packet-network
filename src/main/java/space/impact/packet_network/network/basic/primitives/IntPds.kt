package space.impact.packet_network.network.basic.primitives

import com.google.common.io.ByteArrayDataInput
import space.impact.packet_network.network.IPacketDataSerializable
import java.io.DataOutput

class IntPds(val value: Int) : IPacketDataSerializable {

    override fun copy() = IntPds(value)

    override fun readFromPacket(input: ByteArrayDataInput): IntPds {
        return IntPds(input.readInt())
    }

    override fun writeToByteBuf(output: DataOutput) {
        output.writeInt(value)
    }
}
