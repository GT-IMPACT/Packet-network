package space.impact.packet_network.network

import com.google.common.io.ByteStreams
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.FMLLog
import cpw.mods.fml.common.network.FMLEmbeddedChannel
import cpw.mods.fml.common.network.FMLOutboundHandler
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.internal.FMLProxyPacket
import cpw.mods.fml.relauncher.Side
import io.netty.buffer.ByteBufOutputStream
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.MessageToMessageCodec
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.World
import java.io.DataOutput
import java.util.*
import kotlin.collections.HashMap

@ChannelHandler.Sharable
object NetworkHandler : MessageToMessageCodec<FMLProxyPacket, ImpactPacket>() {

    private const val PACKET_NAME = "ImpactPacketNetwork"
    private lateinit var channel: EnumMap<Side, FMLEmbeddedChannel>

    fun init() {
        FMLLog.info("$PACKET_NAME initiated")
        channel = NetworkRegistry.INSTANCE.newChannel(PACKET_NAME, this, HandlerShared())
    }

    override fun encode(ctx: ChannelHandlerContext?, msg: ImpactPacket?, out: MutableList<Any>?) {
        try {
            msg?.also {
                val buffer = Unpooled.buffer()
                val output: DataOutput = ByteBufOutputStream(buffer)
                output.writeUTF(msg.getPacketId())
                msg.encode(output)
                ctx?.channel()?.attr(NetworkRegistry.FML_CHANNEL)?.get()?.also {
                    out?.add(FMLProxyPacket(buffer, it))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("UnstableApiUsage")
    override fun decode(ctx: ChannelHandlerContext?, packet: FMLProxyPacket, out: MutableList<Any?>) {
        try {
            val input = ByteStreams.newDataInput(packet.payload().array())
            val packetId = NETWORK_PACKETS[input.readUTF()]!!
            val tPacket = packetId.decode(input)
            tPacket.setNetHandler(packet.handler())
            out.add(tPacket)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @ChannelHandler.Sharable
    private class HandlerShared : SimpleChannelInboundHandler<ImpactPacket>() {
        override fun channelRead0(ctx: ChannelHandlerContext?, msg: ImpactPacket?) {
            if (FMLCommonHandler.instance().effectiveSide == Side.CLIENT) {
                val mc = Minecraft.getMinecraft()
                msg?.processClient(mc, mc.thePlayer.worldObj)
            } else msg?.processServer()
        }
    }

    @JvmStatic
    fun sendToPlayer(
        packet: ImpactPacket,
        player: EntityPlayerMP
    ) {
        channel[Side.SERVER]?.attr(FMLOutboundHandler.FML_MESSAGETARGET)?.set(FMLOutboundHandler.OutboundTarget.PLAYER)
        channel[Side.SERVER]?.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS)?.set(player)
        channel[Side.SERVER]?.writeAndFlush(packet.apply { playerId = player.entityId; dimId = player.dimension })
    }

    @JvmStatic
    fun sendToAllAround(
        packet: ImpactPacket,
        dimension: Int,
        x: Int, y: Int, z: Int,
        range: Int
    ) {
        val position = NetworkRegistry.TargetPoint(dimension, x.toDouble(), y.toDouble(), z.toDouble(), range.toDouble())
        channel[Side.SERVER]?.attr(FMLOutboundHandler.FML_MESSAGETARGET)?.set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT)
        channel[Side.SERVER]?.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS)?.set(position)
        channel[Side.SERVER]?.writeAndFlush(packet)
    }

    @JvmStatic
    fun sendToServer(
        packet: ImpactPacket,
        player: EntityPlayer
    ) {
        channel[Side.CLIENT]?.attr(FMLOutboundHandler.FML_MESSAGETARGET)?.set(FMLOutboundHandler.OutboundTarget.TOSERVER)
        channel[Side.CLIENT]?.writeAndFlush(packet.apply { playerId = player.entityId; dimId = player.dimension })
    }

    @JvmStatic
    fun sendPacketToAllPlayersInRange(
        packet: ImpactPacket,
        world: World,
        x: Int, z: Int
    ) {
        if (!world.isRemote) {
            for (player in world.playerEntities) {
                if (player is EntityPlayerMP) {
                    val tChunk = world.getChunkFromBlockCoords(x, z)
                    if (player.serverForPlayer.playerManager.isPlayerWatchingChunk(player, tChunk.xPosition, tChunk.zPosition)) {
                        sendToPlayer(packet, player)
                    }
                }
            }
        }
    }
}
