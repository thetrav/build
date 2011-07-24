package the.trav.build

import java.awt.event.{KeyEvent, KeyAdapter}
import javax.swing.{JPanel, JFrame}
import java.awt.{Color, Graphics2D, Graphics}

object Main {
  val width = 800
  val height = 800

  var keys = Map[Int, Boolean]()
  def key(k:Int) = keys.contains(k) && keys(k)

  def main(args:Array[String]) {
    val frame = new HudFrame("test") {
      def updateState {
        //ignore
      }
    }
    frame.setSize(width, height)

    frame.addKeyListener(new KeyAdapter {
      override def keyPressed(e:KeyEvent) {
        keys += e.getKeyCode() -> true
      }

      override def keyReleased(e:KeyEvent) {
        keys -= e.getKeyCode()
      }
    })
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    Hud.init(frame)
    frame.setVisible(true)

    val paintPanel = new JPanel() {
      override def paint(g1:Graphics) {
        val g = g1.asInstanceOf[Graphics2D]

        g.setColor(Color.white)
        g.fillRect(0,0, width, height)

        Game.draw(g)
        Hud.draw(g)
      }
    }

    frame.getContentPane().add(paintPanel)

    var quit = false

    val timePerFrame = 5L
    var timeCounter = 0L
    var lastTime = System.currentTimeMillis()
    while(!quit) {
      val time = System.currentTimeMillis()
      timeCounter += time-lastTime
      lastTime = time
      while(timeCounter > timePerFrame) {
        if(key(KeyEvent.VK_ESCAPE)) {
          System.exit(0)
        }
        Game.update()
        Hud.update()
        Hud.repaint()
        timeCounter -= timePerFrame
      }
    }
  }

}