package space.impact.packet_network.network

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput

interface IPacketDataSerializable {
    fun copy(): IPacketDataSerializable
    fun write(output: ByteArrayDataOutput)
    fun read(input: ByteArrayDataInput): IPacketDataSerializable
}
