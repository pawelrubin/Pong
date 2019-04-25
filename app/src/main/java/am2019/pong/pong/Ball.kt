package am2019.pong.pong

import am2019.pong.R
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import kotlin.random.Random

class Ball(private var initX : Float, private var initY : Float) {
    var ballX = initX
        private set
    var ballY = initY
        private set
    val size = 50f

    var dx = 15f
        private set
    private var dy = 15f
    private lateinit var gameView : GameView

    init {
        resetBall()
    }

    fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = ContextCompat.getColor(gameView.context, R.color.colorAccent)

        canvas.drawOval(RectF(ballX, ballY,ballX + size,ballY + size), paint)
    }

    fun resetBall() {
        ballX = initX
        ballY = initY
        dx = (15 + 5*Random.nextFloat()) * Math.pow((-1).toDouble(), Random.nextInt(3).toDouble()).toFloat()
        dy = (15 + 5*Random.nextFloat()) * Math.pow((-1).toDouble(), Random.nextInt(3).toDouble()).toFloat()
        changeHorizontalDirection()
    }

    fun setUpGameView(gameView: GameView) {
        this.gameView = gameView
    }

    fun changeHorizontalDirection() {
        dx = -dx
    }

    private fun changeVerticalDirection() {
        dy = -dy
    }

    private fun funnyBounce() {
        dy *= Random.nextDouble(0.9, 1.2).toFloat()
        dx *= Random.nextDouble(0.9, 1.2).toFloat()
    }

    private fun changeDirectionOnBounds(left: Float, right: Float) {
        if (ballX <= left || ballX + size >= right) {
            playBounceSound()
            changeHorizontalDirection()
            funnyBounce()
        }

        if (ballY <= 0f || ballY + size >= gameView.height.toFloat()) {
            playBounceSound()
            changeVerticalDirection()
            funnyBounce()
        }
    }

    fun playBounceSound() {
        gameView.playBounceSound()
    }

    fun move() {
        ballX += dx
        ballY += dy
        changeDirectionOnBounds(0f, gameView.width.toFloat())
    }
}