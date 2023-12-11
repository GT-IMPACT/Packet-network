package space.impact.packet_network.config

import space.impact.packet_network.network.NETWORK_PACKETS
import java.io.File

object RegisteredPackets {

    fun createFile() = runCatching {

        val root = File("IMPACT")
        root.mkdirs()

        val file = File(root, "PacketNetworkList.txt")
        if (!file.canRead()) file.createNewFile()

        val packets = NETWORK_PACKETS.keys.joinToString("\n")
        file.writeText(packets)
    }
}
