package space.impact.packet_network.network

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side

object MinecraftSide {
    val isServer: Boolean
        get() = FMLCommonHandler.instance().effectiveSide == Side.SERVER

    val isClient: Boolean
        get() = FMLCommonHandler.instance().effectiveSide == Side.CLIENT
}
