package org.badgrades.gdx.kotlin

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task

class KotlinGdxMain : ApplicationAdapter(), InputProcessor {


    lateinit var batch: SpriteBatch
    lateinit var textureAtlas: TextureAtlas
    lateinit var rotateUpAnimation: Animation
    lateinit var rotateDownAnimation: Animation
    var elapsedTime: Float = 0f

    override fun create() {
        batch = SpriteBatch()
        textureAtlas = TextureAtlas(Gdx.files.internal("spritesheet.atlas"))
        rotateUpAnimation = Animation(1/15f,
                textureAtlas.findRegion("0001"),
                textureAtlas.findRegion("0002"),
                textureAtlas.findRegion("0003"),
                textureAtlas.findRegion("0004"),
                textureAtlas.findRegion("0005"),
                textureAtlas.findRegion("0006"),
                textureAtlas.findRegion("0007"),
                textureAtlas.findRegion("0008"),
                textureAtlas.findRegion("0009"),
                textureAtlas.findRegion("0010"))

        rotateDownAnimation = Animation(1/15f,
                textureAtlas.findRegion("0011"),
                textureAtlas.findRegion("0012"),
                textureAtlas.findRegion("0013"),
                textureAtlas.findRegion("0014"),
                textureAtlas.findRegion("0015"),
                textureAtlas.findRegion("0016"),
                textureAtlas.findRegion("0017"),
                textureAtlas.findRegion("0018"),
                textureAtlas.findRegion("0019"),
                textureAtlas.findRegion("0020"))

        Gdx.input.inputProcessor = this
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        elapsedTime += Gdx.graphics.deltaTime
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        textureAtlas.dispose()
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyTyped(character: Char): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrolled(amount: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyUp(keycode: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyDown(keycode: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
