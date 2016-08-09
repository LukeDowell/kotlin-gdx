package org.badgrades.gdx.kotlin.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.badgrades.gdx.kotlin.SnekGame

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(SnekGame(), config)
    }
}
