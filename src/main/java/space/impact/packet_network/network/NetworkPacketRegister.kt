package space.impact.packet_network.network

internal val NETWORK_PACKETS: HashMap<String, ImpactPacket> = hashMapOf()

fun registerPacket(packet: ImpactPacket) {
    NETWORK_PACKETS[packet.getPacketId()] = packet
}
