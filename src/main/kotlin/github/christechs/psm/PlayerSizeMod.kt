package github.christechs.psm

import github.christechs.psm.ServerType.*
import net.hypixel.data.type.GameType
import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod(modid = PlayerSizeMod.MOD_ID, version = "0.1",  useMetadata = true)
class PlayerSizeMod {

    private var serverType: ServerType = OTHER

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket::class.java)

        HypixelModAPI.getInstance().createHandler(
            ClientboundLocationPacket::class.java
        ) { packet: ClientboundLocationPacket ->
            println(packet)
            serverType = if (packet.lobbyName.isPresent) {
                LOBBY
            } else if (packet.serverType.isPresent && packet.serverType.get() == GameType.SKYBLOCK) {
                SKYBLOCK
            } else {
                OTHER
            }
        }

        configManager = ConfigManager()

        MinecraftForge.EVENT_BUS.register(configManager)

        MinecraftForge.EVENT_BUS.register(this)
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        CommandManager()
    }

    @SubscribeEvent
    fun onRenderPlayerPre(event: RenderPlayerEvent.Pre) {
        if (!serverType.isAllowed()) return
        if (!config.generalConfig.enabled) return

        val player: AbstractClientPlayer = event.entityPlayer as AbstractClientPlayer

        GlStateManager.pushMatrix()
        GlStateManager.translate(event.x, event.y, event.z)

        val scale = if (player.uniqueID.equals(Minecraft.getMinecraft().thePlayer.uniqueID)) {
            config.generalConfig.playerSize
        } else {
            config.generalConfig.otherPlayerSize
        }

        GlStateManager.scale(scale, scale, scale)
        GlStateManager.translate(-event.x, -event.y, -event.z)
    }

    @SubscribeEvent
    fun onRenderPlayerPost(event: RenderPlayerEvent.Post) {
        if (!serverType.isAllowed()) return
        if (!config.generalConfig.enabled) return
        GlStateManager.popMatrix()
    }

    companion object {
        lateinit var configManager: ConfigManager

        const val MOD_ID = "psm"

        val config: PSMConfig
            get() = configManager.config ?: error("config is null")
    }
}
