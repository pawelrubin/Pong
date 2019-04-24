package am2019.pong.pong

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class Paddle(private val side : Side, private val paddleX : Float, private var paddleY: Float) {

    private val height : Float = 500f
    private val width : Float = 50f

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
        updatePosition(newY)
    }
}

enum class Side {
    A,
    B
}