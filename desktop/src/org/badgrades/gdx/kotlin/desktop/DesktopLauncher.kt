package org.badgrades.gdx.kotlin.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.badgrades.gdx.kotlin.Snek

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(Snek(), config)
    }
}
