package space.impact.packet_network.network

import com.google.common.io.ByteArrayDataInput
import java.io.DataOutput

interface IPacketDataSerializable {
    fun copy(): IPacketDataSerializable
    fun writeToByteBuf(output: DataOutput)
    fun readFromPacket(input: ByteArrayDataInput): IPacketDataSerializable
}

object NothingPacketDataSerializable : IPacketDataSerializable {
    override fun copy(): IPacketDataSerializable = NothingPacketDataSerializable
    override fun writeToByteBuf(output: DataOutput) = Unit
    override fun readFromPacket(input: ByteArrayDataInput) = NothingPacketDataSerializable
}
