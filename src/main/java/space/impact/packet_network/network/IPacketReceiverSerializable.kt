package space.impact.packet_network.network

interface IPacketReceiverSerializable {
    fun receive(value: IPacketDataSerializable)
}
