package github.christechs.psm

import github.christechs.psm.ServerType.*
import github.christechs.psm.commands.CommandManager
import github.christechs.psm.config.ConfigManager
import github.christechs.psm.config.PSMConfig
import net.hypixel.data.type.GameType
import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.scoreboard.Team
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.min


@Mod(modid = "psm", useMetadata = true)
class PlayerSizeMod {

    private var serverType: ServerType = OTHER

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket::class.java)

        HypixelModAPI.getInstance().createHandler(
            ClientboundLocationPacket::class.java
        ) { packet: ClientboundLocationPacket ->
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


    @SubscribeEvent
    fun onEntityJoinWorld(event: EntityJoinWorldEvent) {
        val entity = event.entity
        if (entity is EntityPlayer) {
            println(entity.name)
        }
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        CommandManager()
    }

    @SubscribeEvent
    fun onRenderPlayerPre(event: RenderPlayerEvent.Pre) {
        if (!config.generalConfig.enabled) return

        val player = event.entityPlayer

        if (player.team != null && player.team.registeredName.startsWith("fkt", true)) {
            return
        }

        val isNPC = player.team != null && player.team.nameTagVisibility == Team.EnumVisible.NEVER

        val isClientPlayer = player.uniqueID.equals(Minecraft.getMinecraft().thePlayer.uniqueID)

        GlStateManager.pushMatrix()
        GlStateManager.translate(event.x, event.y, event.z)

        var scale = if (isNPC) {
            config.generalConfig.npcSize
        } else if (isClientPlayer) {
            config.generalConfig.playerSize
        } else {
            config.generalConfig.otherPlayerSize
        }

        if (!serverType.isAllowed()) {
            scale = min(scale, 1.0F)
        }

        GlStateManager.scale(scale, scale, scale)
        GlStateManager.translate(-event.x, -event.y, -event.z)
    }

    @SubscribeEvent
    fun onRenderPlayerPost(event: RenderPlayerEvent.Post) {
        if (!config.generalConfig.enabled) return

        val player = event.entityPlayer

        if (player.team != null && player.team.registeredName.startsWith("fkt", true)) {
            return
        }

        GlStateManager.popMatrix()
    }

    companion object {
        lateinit var configManager: ConfigManager

        val config: PSMConfig
            get() = configManager.config ?: error("config is null")
    }
}
