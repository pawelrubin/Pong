package am2019.pong.pong

import am2019.pong.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.*


class GameView(context: Context, attrs: AttributeSet)
    : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val thread : GameThread
    private val scoreSound = MediaPlayer.create(context, R.raw.score)

    private lateinit var paddleA : Paddle
    private lateinit var paddleB : Paddle
    private lateinit var ball : Ball
    lateinit var game: Game
        private set

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        // Set up the paddles.
        paddleA = Paddle(Side.A,0f,height/2f)
        paddleB = Paddle(Side.B, width.toFloat(),height/2f)

        // Set up the ball.
        ball = Ball(width/2f, height/2f)
        ball.setUpGameView(this)

        // Set up the game.
        game = Game(paddleA, paddleB, ball)

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }


    fun update() {
        game.checkBounce()
        if (game.referee()) {
            scoreSound.start()
            ball.resetBall()
        }
        ball.move()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.also {
            paddleA.draw(it)
            paddleB.draw(it)
            ball.draw(it)
            updateScore(it)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (i in 0 until event.pointerCount) {
            if (event.getX(i) < width/2) {
                paddleA.movePaddle(event.getY(i))
            } else {
                paddleB.movePaddle(event.getY(i))
            }
        }
        return true
    }

    fun updateScore(canvas: Canvas?) {
        canvas?.also {
            val textPaint = TextPaint()
            textPaint.color = Color.WHITE
            textPaint.textSize = 500f
            textPaint.alpha = 50
            textPaint.textAlign = Paint.Align.CENTER
            val xPos = canvas.width / 2f
            val yPos = (canvas.height / 2f - (textPaint.descent() + textPaint.ascent()) / 2f)
            it.drawText("${game.pointsA} : ${game.pointsB}", xPos, yPos, textPaint)
        }
    }
}