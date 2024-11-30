package github.christechs.psm

import com.google.gson.annotations.Expose
import io.github.moulberry.moulconfig.annotations.ConfigEditorBoolean
import io.github.moulberry.moulconfig.annotations.ConfigEditorSlider
import io.github.moulberry.moulconfig.annotations.ConfigOption

class GeneralConfig {

    @Expose
    @ConfigOption(name = "Master Toggle", desc = "Category Enabled.")
    @ConfigEditorBoolean
    @JvmField
    var enabled: Boolean = false

    @Expose
    @ConfigOption(name = "Player Size", desc = "Size of you own player model.")
    @ConfigEditorSlider(minValue = 0.1F, maxValue = 2.0F, minStep = 0.1F)
    @JvmField
    var playerSize: Float = 1.0F

    @Expose
    @ConfigOption(name = "Other Players Size", desc = "Size of other player models.")
    @ConfigEditorSlider(minValue = 0.1F, maxValue = 2.0F, minStep = 0.1F)
    @JvmField
    var otherPlayerSize: Float = 1.0F

}