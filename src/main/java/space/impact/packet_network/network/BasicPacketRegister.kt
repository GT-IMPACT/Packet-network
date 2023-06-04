package space.impact.packet_network.network

import space.impact.packet_network.network.basic.SerializationPacketGuiImp

fun registerBasicPackets() {
    registerPacket(SerializationPacketGuiImp())
}