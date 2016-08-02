package org.badgrades.gdx.kotlin

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task

class KotlinGdxMain : ApplicationAdapter() {

    lateinit var batch: SpriteBatch
    lateinit var textureAtlas: TextureAtlas
    lateinit var sprite: Sprite

    var currentFrame: Int = 1
    var currentAtlasKey: String = "0001"

    override fun create() {
        batch = SpriteBatch()
        textureAtlas = TextureAtlas(Gdx.files.internal("spritesheet.atlas"))
        val region: TextureAtlas.AtlasRegion? = textureAtlas.findRegion(currentAtlasKey)

        sprite = Sprite(region)
        sprite.setPosition(120f, 100f)
        sprite.scale(2.5f)

        Timer.schedule(object: Task() {
            override fun run() {
                currentFrame++
                if(currentFrame > 20)
                    currentFrame = 1

                currentAtlasKey = String.format("%04d", currentFrame)
                sprite.setRegion(textureAtlas.findRegion(currentAtlasKey))
            }
        }, 0f, 1/30.0f)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        sprite.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        textureAtlas.dispose()
    }
}
