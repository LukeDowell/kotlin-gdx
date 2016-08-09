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

    val random = Random()

    /** The width of the map in blocks */
    val MAP_WIDTH = 20

    /** The height of the map in blocks */
    val MAP_HEIGHT = 20

    /** The size of a single block in pixels */
    val BLOCK_SIZE = 16

    val camera = OrthographicCamera()

    val foods: MutableCollection<Food> = mutableListOf(createRandomFood())
    val snek: Snek = Snek(MAP_WIDTH / 2, MAP_HEIGHT / 2)

    lateinit var shapeRenderer: ShapeRenderer

    override fun create() {
        camera.setToOrtho(false,
                (MAP_HEIGHT * BLOCK_SIZE).toFloat(),
                (MAP_WIDTH * BLOCK_SIZE).toFloat())
        shapeRenderer = ShapeRenderer()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Logic
        update()

        // Camera and Shape renderer
        camera.update()
        shapeRenderer.projectionMatrix = camera.combined

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        // Food
        shapeRenderer.setColor(0f, 1f, 0f, 1f)
        for(food in foods) {
            shapeRenderer.rect(
                    food.x.toFloat(),
                    food.y.toFloat(),
                    BLOCK_SIZE.toFloat(),
                    BLOCK_SIZE.toFloat())
        }

        // Draw head of snek
        shapeRenderer.setColor(1f, 0f, 0f, 1f)
        shapeRenderer.rect(
                snek.x.toFloat(),
                snek.y.toFloat(),
                BLOCK_SIZE.toFloat(),
                BLOCK_SIZE.toFloat())

        // Draw body of snek
        shapeRenderer.setColor(0f, 0f, 1f, 1f)
        for(cell in snek.cells) {
            shapeRenderer.rect(
                    cell.x.toFloat(),
                    cell.y.toFloat(),
                    BLOCK_SIZE.toFloat(),
                    BLOCK_SIZE.toFloat())
        }

        shapeRenderer.end()
    }

    override fun dispose() {
    }

    fun update() {
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) snek.direction = Direction.DOWN
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) snek.direction = Direction.UP
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) snek.direction = Direction.LEFT
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snek.direction = Direction.RIGHT

        snek.move()

        // TODO check for collisions: food, wall, self
    }

    fun createRandomFood(): Food = Food(random.nextInt(MAP_WIDTH), random.nextInt(MAP_HEIGHT))
}

/**
 *                  Snek
 *                           ____
 *  ________________________/ O  \___/
 * <_____________________________/   \
 *
 */
class Snek(var x: Int, var y: Int) {
    val cells = mutableListOf(Point(x, y))
    var direction = Direction.RIGHT

    fun increaseLength(length: Int) = {
        val butt = cells.last()
        for(i in 1.rangeTo(length))
            cells.add(Point(butt.x, butt.y))
    }

    fun move() {

        // Store the original position of the head
        val oldHeadX = x
        val oldHeadY = y

        // Move the head of the snek
        when(direction) {
            Direction.LEFT -> x--
            Direction.RIGHT -> x++
            Direction.DOWN -> y--
            else -> y++
        }

        // Move the butts
        // Each cell will move to the spot occupied by the cell ahead of it
        for(i in 0.rangeTo(cells.size - 1)) {

            if(i == 0) {
                // If it's the first cell, move it to where the head used to be
                cells[i].x = oldHeadX
                cells[i].y = oldHeadY
            } else {
                cells[i].x = cells[i - 1].x
                cells[i].y = cells[i - 1].y
            }
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
data class Food(val x: Int, val y: Int)

/**
 *                  Map map map map
 *                  Map map map map
 * |<><><><><><><><><><><><><><><><><><><><><><><>|
 * |                      *                       |
 * |   /\~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~/\    |
 * |  (o )                .                ( o)   |
 * |   \/               .` `.               \/    |
 * |   /\             .`     `.             /\    |
 * |  (             .`         `.             )   |
 * |   )          .`      N      `.          (    |
 * |  (         .`   A    |        `.         )   |
 * |   )      .`     <\> )|(         `.      (    |
 * |  (     .`         \  |  (         `.     )   |
 * |   )  .`         )  \ |    (         `.  (    |
 * |    .`         )     \|      (         `.     |
 * |  .`     W---)--------O--------(---E     `.   |
 * |   `.          )      |\     (          .`    |
 * |   ) `.          )    | \  (          .` (    |
 * |  (    `.          )  |  \          .`    )   |
 * |   )     `.          )|( <\>      .`     (    |
 * |  (        `.         |         .`        )   |
 * |   )         `.       S       .`         (    |
 * |  (            `.           .`            )   |
 * |   \/            `.       .`            \/    |
 * |   /\              `.   .`              /\    |
 * |  (o )               `.`               ( o)   |
 * |   \/~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~~~~~\/    |
 * |                     -|-                   LGB|
 * |<><><><><><><><><><><><><><><><><><><><><><><>|
 */
enum class Direction { UP, DOWN, LEFT, RIGHT }