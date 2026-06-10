package space.impact.packet_network.streamnet

import io.netty.buffer.ByteBuf

class StreamOut internal constructor(
    private val buf: ByteBuf,
) {
    fun bool(v: Boolean) = buf.writeBoolean(v)
    fun byte(v: Int) = buf.writeByte(v)
    fun short(v: Int) = buf.writeShort(v)
    fun int(v: Int) = buf.writeInt(v)
    fun long(v: Long) = buf.writeLong(v)
    fun float(v: Float) = buf.writeFloat(v)
    fun double(v: Double) = buf.writeDouble(v)

    fun string(v: String) {
        val bytes = v.toByteArray(Charsets.UTF_8)
        int(bytes.size)
        buf.writeBytes(bytes)
    }

    fun bytes(v: ByteArray) {
        int(v.size)
        buf.writeBytes(v)
    }

    fun xyz(x: Int, y: Int, z: Int) {
        int(x)
        int(y)
        int(z)
    }
}
