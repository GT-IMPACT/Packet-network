package space.impact.packet_network.network.basic.primitives

import com.google.common.io.ByteArrayDataInput
import space.impact.packet_network.network.IPacketDataSerializable
import java.io.DataOutput

class IntArrayPds(vararg value: Int = intArrayOf()) : IPacketDataSerializable {
    val array: IntArray = value

    override fun copy() = IntArrayPds(*array)

    override fun readFromPacket(input: ByteArrayDataInput): IntArrayPds {
        val array = IntArray(input.readInt()) { input.readInt() }
        return IntArrayPds(*array)
    }

    override fun writeToByteBuf(output: DataOutput) {
        output.writeInt(array.size)
        for (i in array) output.writeInt(i)
    }
}
