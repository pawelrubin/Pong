package am2019.pong.pong

import android.util.Log

class Game (private val paddleA: Paddle, private val paddleB: Paddle, private val ball: Ball) {
    var pointsA = 0
        private set
    var pointsB = 0
        private set


    fun checkBounce() {
        val left = paddleA.paddleX + paddleA.width
        val right = paddleB.paddleX - paddleB.width

        if (((ball.ballX in (left .. (left - ball.dx))) && (ball.ballY + ball.size/2 in (paddleA.paddleY .. paddleA.paddleY+paddleA.height))) ||
            (ball.ballX + ball.size in (right-ball.dx .. right)) && (ball.ballY + ball.size/2 in (paddleB.paddleY .. paddleB.paddleY+paddleA.height))) {
            ball.playPaddleBounceSound()
            ball.changeHorizontalDirection()
            ball.move()
        }
    }

    fun referee() : Boolean {
        return when {
            ball.ballX < paddleA.paddleX -> {
                pointsB++
                Log.d("DEBUG", "point for B")
                true
            }
            ball.ballX + ball.size > paddleB.paddleX -> {
                pointsA++
                Log.d("DEBUG", "point for A")
                true
            }
            else -> false
        }
    }
}