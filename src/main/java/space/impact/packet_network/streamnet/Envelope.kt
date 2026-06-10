package space.impact.packet_network.streamnet

import cpw.mods.fml.common.network.simpleimpl.IMessage
import io.netty.buffer.ByteBuf

class Envelope() : IMessage {

    var id: Int = 0
    var payload: ByteArray = ByteArray(0)

    constructor(id: Int, payload: ByteArray) : this() {
        this.id = id
        this.payload = payload
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(id)
        buf.writeInt(payload.size)
        buf.writeBytes(payload)
    }

    override fun fromBytes(buf: ByteBuf) {
        id = buf.readInt()
        val size = buf.readInt()
        require(size in 0..1048576) { "Invalid packet size: $size" }
        payload = ByteArray(size)
        buf.readBytes(payload)
    }
}
