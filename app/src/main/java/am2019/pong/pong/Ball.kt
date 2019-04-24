package am2019.pong.pong

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import kotlin.random.Random

class Ball(private var initX : Float, private var initY : Float) {
    var ballX = initX
        private set
    var ballY = initY
        private set
    val size = 50f
    var dx = 15f
        private set
    var dy = 15f
        private set
    private lateinit var gameView : GameView

    init {
        resetBall()
    }

    fun draw(canvas: Canvas) {
        val red = Paint()
        red.setARGB(255,255,255,255)
        canvas.drawOval(RectF(ballX, ballY,ballX + size,ballY + size), red)
    }

    fun resetBall() {
        ballX = initX
        ballY = initY
        dx = 15 + 5*Random.nextFloat() * Math.pow((-1).toDouble(), Random.nextInt(1).toDouble()).toFloat()
        dy = 15 + 5*Random.nextFloat() * Math.pow((-1).toDouble(), Random.nextInt(1).toDouble()).toFloat()
        changeHorizontalDirection()
    }

    fun freeze() {
        dx = 0f
        dy = 0f
    }

    fun setUpGameView(gameView: GameView) {
        this.gameView = gameView
    }

    fun changeHorizontalDirection() {
        dx = -dx
    }

    fun changeDirectionOnBounds(left: Float, right: Float) {
        if (ballX <= left || ballX + size >= right) {
            dx = -dx
        }

        if (ballY <= 0f || ballY + size >= gameView.height.toFloat()) {
            dy = -dy
        }
    }

    fun move() {
        ballX += dx
        ballY += dy
        changeDirectionOnBounds(0f, gameView.width.toFloat())
    }
}