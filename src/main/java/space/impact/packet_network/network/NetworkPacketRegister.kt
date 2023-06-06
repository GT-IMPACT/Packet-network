package space.impact.packet_network.network

import space.impact.packet_network.network.packets.ImpactPacket

internal val NETWORK_PACKETS: HashMap<Int, ImpactPacket> = hashMapOf()

/**
 * Register Packets
 * - 1000 - 1999 ids IMPACT reserved
 */
fun registerPacket(packet: ImpactPacket) {
    NETWORK_PACKETS[packet.getPacketId()] = packet
}
