package am2019.pong.pong

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.*

class GameView(context: Context, attrs: AttributeSet)
    : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val thread : GameThread

    private lateinit var paddleA : Paddle
    private lateinit var paddleB : Paddle
    private lateinit var ball : Ball

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

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    fun update() {
        ball.move()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.also {
            paddleA.draw(it)
            paddleB.draw(it)
            ball.draw(it)
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
}