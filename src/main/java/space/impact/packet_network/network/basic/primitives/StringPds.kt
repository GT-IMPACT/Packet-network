package space.impact.packet_network.network.basic.primitives

import com.google.common.io.ByteArrayDataInput
import space.impact.packet_network.network.IPacketDataSerializable
import java.io.DataOutput

class StringPds(val value: String) : IPacketDataSerializable {

    override fun copy() = StringPds(value)

    override fun readFromPacket(input: ByteArrayDataInput): StringPds {
        return StringPds(input.readUTF())
    }

    override fun writeToByteBuf(output: DataOutput) {
        output.writeUTF(value)
    }
}
