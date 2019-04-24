package am2019.pong.pong

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class Ball(private var ballX : Float, private var ballY : Float) {

    private val size = 50f
    private var dx = 15f
    private var dy = 15f
    private lateinit var gameView : GameView

    fun draw(canvas: Canvas) {
        val red = Paint()
        red.setARGB(255,255,255,255)
        canvas.drawOval(RectF(ballX, ballY,ballX + size,ballY + size), red)
    }

    fun setUpGameView(gameView: GameView) {
        this.gameView = gameView
    }

    fun move() {
        ballX += dx
        ballY += dy

        if (ballX <= 0 || ballX + size >= gameView.width) {
            dx = -dx
        }
        if (ballY <= 0 || ballY + size >= gameView.height) {
            dy = -dy
        }
    }
}