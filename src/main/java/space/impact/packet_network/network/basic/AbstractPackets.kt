package space.impact.packet_network.network.basic

import space.impact.packet_network.network.ImpactPacket
import space.impact.packet_network.network.IPacketDataSerializable

abstract class IntPacket(val value: Int) : ImpactPacket()
abstract class StringPacket(val value: String) : ImpactPacket() //TODO
abstract class FloatPacket(val value: Float) : ImpactPacket() //TODO
abstract class DoublePacket(val value: Double) : ImpactPacket() //TODO
abstract class ShortPacket(val value: Short) : ImpactPacket() //TODO
abstract class BytePacket(val value: Byte) : ImpactPacket() //TODO

abstract class SerializationPacket(open val value: IPacketDataSerializable) : ImpactPacket()
