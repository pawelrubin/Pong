package am2019.pong.pong

import am2019.pong.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.concurrent.schedule


class GameView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val thread: GameThread

    private lateinit var paddleA: Paddle
    private lateinit var paddleB: Paddle
    private lateinit var ball: Ball
    private lateinit var game: Game
    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("best_score", Context.MODE_PRIVATE)

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.running = false
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        // Set up the paddles.
        paddleA = Paddle(Side.A, 0f, height / 2f)
        paddleB = Paddle(Side.B, width.toFloat(), height / 2f)

        // Set up the ball.
        ball = Ball(width / 2f, height / 2f)
        ball.setUpGameView(this)

        // Set up the game.
        game = Game(paddleA, paddleB, ball, sharedPreferences.getInt("best_score", 0))

        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    fun playSound(resId: Int) {
        Thread {
            val player = MediaPlayer.create(context, resId)
            player.start()
            player.setOnCompletionListener {
                it.release()
            }
        }.start()
    }

    fun update() {
        game.checkBounce()
        if (game.referee()) {
            playSound(R.raw.score_sound2)
            sharedPreferences.edit().putInt("best_score", game.bestScore).apply()
            ball.kill()
            Timer().schedule(500) {
                ball.resetBall()
            }
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
            if (event.getX(i) < width / 2) {
                paddleA.movePaddle(event.getY(i))
            } else {
                paddleB.movePaddle(event.getY(i))
            }
        }
        return true
    }

    private fun updateScore(canvas: Canvas?) {
        canvas?.also { it ->
            val textPaint = TextPaint()
            textPaint.color = Color.WHITE
            textPaint.textSize = 500f
            textPaint.alpha = 50
            textPaint.textAlign = Paint.Align.CENTER
            val xPos = canvas.width / 2f
            val yPos = (canvas.height / 2f - (textPaint.descent() + textPaint.ascent()) / 2f)
            it.drawText("${game.points}", xPos, yPos, textPaint)
            textPaint.textSize = 100f
            it.drawText("${game.bestScore}", xPos, 100f, textPaint)
        }
    }
}