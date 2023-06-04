package space.impact.packet_network.network

import space.impact.packet_network.network.basic.SerializationPacketGui

fun registerBasicPackets() {
    registerPacket(SerializationPacketGui())
}