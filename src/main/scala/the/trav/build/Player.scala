package the.trav.build

import java.awt.{Color, Graphics2D}
import java.awt.event.KeyEvent._

object Player extends Obj{
  val friction = 0.8
  val power = 0.05
  val maxVel = 3

  var pos = Vector(10,10)
  var vel = Vector(0,0)
  var accel = Vector(0,0)

  var wood = 0

  def size = Vector(7, 7)

  def handleMovement() {
    accel =  if(Main.key(VK_LEFT)) {
      Vector(-power, accel.y)
    } else if (Main.key(VK_RIGHT)) {
      Vector(power, accel.y)
    } else {
      Vector(0, accel.y)
    }

    accel = if(Main.key(VK_UP)) {
      Vector(accel.x, -power)
    } else if (Main.key(VK_DOWN)) {
      Vector(accel.x, power)
    } else {
      Vector(accel.x, 0)
    }

    vel = limit((vel + accel) * friction, -maxVel, maxVel)

    val newPos = limit(limit(pos + vel, 0, Main.width), 0, Main.height)
    val collides = Game.collisions(newPos, size)
    pos = if(collides.isEmpty) newPos else pos
  }

  def handleChop() {
    if(Main.key(VK_SPACE)) {
      val axePos = pos + (accel*100)
      val axeSize = Vector(5, 5)
      Game.chop(axePos, axeSize)
      Main.keys += VK_SPACE -> false
    }
  }

  def update() {
    handleMovement()
    handleChop()
  }

  def limit(v:Vector, min:Int, max:Int) = {
    Vector(math.max(math.min(v.x, max), min),
           math.max(math.min(v.y, max), min))
  }

  def draw(g: Graphics2D) {
    g.setColor(Color.black)
    g.fillOval(pos.i.x-size.i.x/2, pos.i.y-size.i.y/2, size.i.x, size.i.y)
  }
}
