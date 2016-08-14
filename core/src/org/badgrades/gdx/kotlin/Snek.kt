package org.badgrades.gdx.kotlin

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import java.awt.Point
import java.util.*

class SnekGame : ApplicationAdapter() {

    /** A0290234l;kjasdfm,.nclkjadf */
    val random = Random()

    /** The width of the map in blocks */
    val MAP_WIDTH = 20f

    /** The height of the map in blocks */
    val MAP_HEIGHT = 20f

    /** The size of a single block in pixels */
    val BLOCK_SIZE = 16f

    /** Obviously snek */
    val snek: Snek = Snek(MAP_WIDTH / 2, MAP_HEIGHT / 2)

    /** This is a comment just so that every field is commented */
    val camera = OrthographicCamera()

    /** This draws shapes, renders them even */
    lateinit var shapeRenderer: ShapeRenderer

    /** How much time to wait between movements for snek, 1 = 1 second */
    var game_speed = 0.10

    /** The current active food */
    var food = createRandomFood()

    override fun create() {
        camera.setToOrtho(false,
                MAP_HEIGHT * BLOCK_SIZE,
                MAP_WIDTH * BLOCK_SIZE)
        shapeRenderer = ShapeRenderer()
    }

    var deltaAggregate = 0f
    override fun render() {
        // Camera and Shape renderer
        camera.update()
        shapeRenderer.projectionMatrix = camera.combined

        // Clear
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Input
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) snek.direction = Direction.DOWN
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) snek.direction = Direction.UP
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) snek.direction = Direction.LEFT
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snek.direction = Direction.RIGHT

        // Game `tick`
        deltaAggregate += Gdx.graphics.deltaTime
        if(deltaAggregate >= game_speed) {
            snek.move()
            checkCollisions()
            deltaAggregate = 0f
        }

        // Drawring
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        // Draw head of snek
        shapeRenderer.setColor(1f, 0f, 0f, 1f)
        shapeRenderer.rect(
                snek.x * BLOCK_SIZE,
                snek.y * BLOCK_SIZE,
                BLOCK_SIZE,
                BLOCK_SIZE)

        // Draw body of snek
        shapeRenderer.setColor(0f, 0f, 1f, 1f)
        for(cell in snek.cells) {
            shapeRenderer.rect(
                    cell.x.toFloat() * BLOCK_SIZE,
                    cell.y.toFloat() * BLOCK_SIZE,
                    BLOCK_SIZE,
                    BLOCK_SIZE)
        }

        // Food
        shapeRenderer.setColor(0f, 1f, 0f, 1f)
        shapeRenderer.rect(
                food.x * BLOCK_SIZE,
                food.y * BLOCK_SIZE,
                BLOCK_SIZE,
                BLOCK_SIZE)

        shapeRenderer.end()
    }

    override fun dispose() {
    }

    fun createRandomFood(): Food = Food(
            random.nextInt(MAP_WIDTH.toInt()).toFloat(),
            random.nextInt(MAP_HEIGHT.toInt()).toFloat())

    fun checkCollisions() {
        // Walls
        if(snek.x < 0 || snek.x > MAP_WIDTH || snek.y < 0 || snek.y > MAP_HEIGHT) {
            println("GAME OVER")
            System.exit(0)
        }

        // Food
        if(food.x == snek.x && food.y == snek.y) {
            println("FOOD CONSUMED")
            snek.increaseLength(2)
            food = createRandomFood()
        }

        // Self
        if(snek.cells.filter { it.x.toFloat() == snek.x && it.y.toFloat() == snek.y }.size > 0) {
            println("COLLIDED WITH SELF")
            System.exit(0)
        }
    }
}

/**
 *                  Snek
 *                           ____
 *  ________________________/ O  \___/
 * <_____________________________/   \
 *
 */
class Snek(var x: Float, var y: Float) {
    val cells = mutableListOf(Point(x.toInt(), y.toInt()))
    var direction = Direction.RIGHT
    init {
        increaseLength(5)
    }
    fun increaseLength(length: Int) {
        val butt = cells.last()
        for(i in 1..length) {
            println(i)
            cells.add(Point(butt.x, butt.y))
        }
    }

    fun move() {

        // Store the original position of the head
        val oldPoint: Point = Point(x.toInt(), y.toInt())

        // Move the head of the snek
        when(direction) {
            Direction.LEFT -> x--
            Direction.RIGHT -> x++
            Direction.DOWN -> y--
            else -> y++
        }

        // Move the butts
        // Each cell will move to the spot occupied by the cell ahead of it
        // Represents the old position of the point in the previous loop
        var previous = cells[0]
        for(i in 0..(cells.size - 1)) {

            // The position of the current cell before we move it
            val currentSavedPosition = cells[i]

            if(i == 0)
                cells[i] = oldPoint
            else if(currentSavedPosition != previous) {
                cells[i] = previous
            }

            previous = currentSavedPosition
        }
    }
}

/**
 *                  A food
 *     _()______
 *   /_'_/   /  `\            ,
 *      /'---\___/~~~~~~~~~~~~
 *     ~     ~~
 */
data class Food(val x: Float, val y: Float)

enum class Direction { UP, DOWN, LEFT, RIGHT }