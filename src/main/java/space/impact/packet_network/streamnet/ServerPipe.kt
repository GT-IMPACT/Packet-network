package space.impact.packet_network.streamnet

class ServerPipe internal constructor(
    private val net: StreamNet,
    private val id: Int
) {
    fun send(block: StreamOut.() -> Unit) {
        net.channel.sendToServer(Envelope(id, net.encode(block)))
    }
}