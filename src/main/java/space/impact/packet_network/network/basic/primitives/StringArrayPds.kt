package space.impact.packet_network.network.basic.primitives

import com.google.common.io.ByteArrayDataInput
import space.impact.packet_network.network.IPacketDataSerializable
import java.io.DataOutput

class StringArrayPds(vararg value: String = arrayOf()) : IPacketDataSerializable {
    val array: Array<out String> = value

    override fun copy() = StringArrayPds(*array)

    override fun readFromPacket(input: ByteArrayDataInput): StringArrayPds {
        val array = Array<String>(input.readInt()) { input.readUTF() }
        return StringArrayPds(*array)
    }

    override fun writeToByteBuf(output: DataOutput) {
        output.writeInt(array.size)
        for (i in array) output.writeUTF(i)
    }
}
