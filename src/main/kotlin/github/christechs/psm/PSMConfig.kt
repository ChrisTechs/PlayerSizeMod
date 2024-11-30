package github.christechs.psm

import com.google.gson.annotations.Expose
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