package github.christechs.psm.config

import com.google.gson.annotations.Expose
import github.christechs.psm.PlayerSizeMod
import io.github.moulberry.moulconfig.Config
import io.github.moulberry.moulconfig.annotations.Category

class PSMConfig: Config() {

    override fun getTitle(): String {
        return "Player Size Mod Configuration"
    }

    override fun saveNow() {
        PlayerSizeMod.configManager.save()
    }

    @Expose
    @Category(name = "General", desc = "")
    @JvmField
    var generalConfig: GeneralConfig = GeneralConfig()

}