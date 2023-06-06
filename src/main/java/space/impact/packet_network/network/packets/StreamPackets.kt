package space.impact.packet_network.network.packets

import com.google.common.io.ByteArrayDataInput
import space.impact.packet_network.dsl.PacketNetworkDsl

@PacketNetworkDsl
fun createPacketStream(id: Int, process: StreamPacketBase.(isServer: Boolean, read: ByteArrayDataInput) -> Unit): StreamPacketBase {
    return object : StreamPacketBase(id, process) {}
}

interface IStreamPacketReceiver {
    fun receive(data: ByteArrayDataInput)
}
