package space.impact.packet_network.streamnet

import io.netty.buffer.ByteBuf

class StreamIn internal constructor(
    private val buf: ByteBuf,
) {
    fun bool(): Boolean = buf.readBoolean()
    fun byte(): Byte = buf.readByte()
    fun short(): Short = buf.readShort()
    fun int(): Int = buf.readInt()
    fun long(): Long = buf.readLong()
    fun float(): Float = buf.readFloat()
    fun double(): Double = buf.readDouble()

    fun string(): String {
        val size = int()
        require(size in 0..131068) { "Invalid string size: $size" }
        val bytes = ByteArray(size)
        buf.readBytes(bytes)
        return String(bytes, Charsets.UTF_8)
    }

    fun bytes(): ByteArray {
        val size = int()
        require(size in 0..1048576) { "Invalid byte array size: $size" }
        val bytes = ByteArray(size)
        buf.readBytes(bytes)
        return bytes
    }

    fun xyz(): Xyz = Xyz(int(), int(), int())
}
