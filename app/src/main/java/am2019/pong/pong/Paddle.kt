package am2019.pong.pong

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class Paddle(private val side : Side, x: Float, y: Float) {

    var paddleX = x
        private set
    var paddleY = y
        private set

    val height : Float = 500f
    val width : Float = 50f

    private fun updatePosition(newPos: Float) {
        paddleY = newPos
    }

    fun draw(canvas: Canvas) {
        val rect : RectF = when (side) {
             Side.A -> {
                 RectF(paddleX, paddleY, paddleX + width, paddleY + height)
             }
             Side.B -> {
                 RectF(paddleX - width, paddleY, paddleX + width, paddleY + height)
             }
        }
        canvas.drawRect(
            rect,
            Paint().also {
                it.setARGB(255, 21, 37, 69)
            }
        )
    }

    fun movePaddle(newY: Float) {
        updatePosition(newY - height/2)
    }
}

enum class Side {
    A,
    B
}