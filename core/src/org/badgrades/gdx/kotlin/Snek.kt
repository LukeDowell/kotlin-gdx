package org.badgrades.gdx.kotlin

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import java.awt.Point
import java.util.*

class Snek : ApplicationAdapter() {

    val random = Random()
    val MAP_WIDTH = 20
    val MAP_HEIGHT = 20

    val foods: MutableCollection<Food> = mutableListOf(createRandomFood())
    val snake: Snake = Snake(MAP_WIDTH / 2, MAP_HEIGHT / 2)

    override fun create() {
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) snake.direction = Direction.DOWN
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) snake.direction = Direction.UP
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) snake.direction = Direction.LEFT
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snake.direction = Direction.RIGHT


    }

    override fun dispose() {
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
class Snake(x: Int, y: Int) {
    val cells = mutableListOf(Point(x, y))
    var direction = Direction.RIGHT

    fun increaseLength(length: Int) = {
        val butt = cells.last()
        for(i in 1.rangeTo(length))
            cells.add(Point(butt.x, butt.y))
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