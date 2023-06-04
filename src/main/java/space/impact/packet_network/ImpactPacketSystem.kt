@file:Suppress("unused")
package space.impact.packet_network

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import space.impact.packet_network.network.NetworkHandler
import space.impact.packet_network.network.registerBasicPackets

@Mod(
    modid = MODID,
    name = MODNAME,
    version = VERSION,
    acceptedMinecraftVersions = "[1.7.10]",
    modLanguageAdapter = MODADAPTER,
)
object ImpactPacketSystem {

    init {
        registerBasicPackets()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent?) {
        NetworkHandler.init()
    }
}
