package the.trav.build

import java.awt.{Color, Graphics2D}

case class Tree(health:Int, pos:Vector, size:Vector) extends Obj {
  def draw(g:Graphics2D) {
    g.setColor(new Color(0, 200,0, 50))
    g.fillOval(pos.i.x-6, pos.i.y-6, 15, 15)
    g.setColor(new Color(150,75,0))
    g.fillOval(pos.i.x-2, pos.i.y-2, 5, 5)
  }

  def chop = Tree(health-10, pos, size)
}