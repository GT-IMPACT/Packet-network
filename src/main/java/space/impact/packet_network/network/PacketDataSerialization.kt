package space.impact.packet_network.network

import com.google.common.io.ByteArrayDataInput
import space.impact.packet_network.network.basic.primitives.*
import java.io.DataOutput

interface IPacketDataSerializable {
    fun copy(): IPacketDataSerializable
    fun writeToByteBuf(output: DataOutput)
    fun readFromPacket(input: ByteArrayDataInput): IPacketDataSerializable
}

object RedirectPacketDataSerializable : IPacketDataSerializable {
    override fun copy(): IPacketDataSerializable = RedirectPacketDataSerializable
    override fun writeToByteBuf(output: DataOutput) = Unit
    override fun readFromPacket(input: ByteArrayDataInput) = when(input.readByte()) {
        TypePrimitive.INT.ordinal.toByte() -> IntPds().readFromPacket(input)
        TypePrimitive.INT_ARRAY.ordinal.toByte() -> IntArrayPds().readFromPacket(input)
        TypePrimitive.STRING.ordinal.toByte() -> StringPds().readFromPacket(input)
        TypePrimitive.STRING_ARRAY.ordinal.toByte() -> StringArrayPds().readFromPacket(input)
        else -> RedirectPacketDataSerializable
    }
}
