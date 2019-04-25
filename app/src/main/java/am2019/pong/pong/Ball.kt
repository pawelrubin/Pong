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
        initX -= size/2
        initY -= size/2
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
        flipDirection(SpeedComponent.X)
    }

    fun setUpGameView(gameView: GameView) {
        this.gameView = gameView
    }

    fun flipDirection(component: SpeedComponent) {
        when (component) {
            SpeedComponent.X -> dx *= -1
            SpeedComponent.Y -> dy *= -1
        }
    }

    private fun funnyBounce() {
        dy *= Random.nextDouble(0.9, 1.2).toFloat()
        dx *= Random.nextDouble(0.9, 1.2).toFloat()
    }

    private fun checkWallBounce() {
        if (ballY <= 0f || ballY + size >= gameView.height.toFloat()) {
            playWallBounceSound()
            flipDirection(SpeedComponent.Y)
            funnyBounce()
        }
    }

    private fun playWallBounceSound() {
        gameView.playSound(R.raw.hit)
    }

    fun playPaddleBounceSound() {
        gameView.playSound(R.raw.hit2)
    }

    fun move() {
        ballX += dx
        ballY += dy
        checkWallBounce()
    }

    fun kill() {
        dx = 0f
        dy = 0f
        ballX = initX
        ballY = initY
    }

    enum class SpeedComponent {
        X,
        Y
    }
}