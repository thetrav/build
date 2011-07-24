package the.trav.build

import java.awt.Graphics2D
import util.Random

object Game {
  def tree(v:Vector) = v -> Tree(100, v, Vector(5, 5))

  var trees = (0 to  10).map(xml=>
    Vector(Random.nextInt(Main.width), Random.nextInt(Main.height))).map(tree).toMap

  def update() {
    Player.update()
  }
  
  def draw(g:Graphics2D) {
    Player.draw(g)
    trees.foreach(t => t._2.draw(g))
  }
  
  def collisions(pos:Vector, size:Vector) = {
    def collides(a:Vector, sa:Vector, b:Vector, sb:Vector) = {
      def minDist(a:Double, b:Double, sa:Double, sb:Double) = {
        val dist = math.sqrt((b - a) * (b - a))
        val min = (sb/2 + sa/2)
        dist < min
      }
      minDist(a.x, b.x, sa.x, sb.x) && minDist(a.y, b.y, sa.y, sb.y)
    }

    trees.filter(p => {
      collides(pos, size, p._1, p._2.size)
    })
  }

  def chop(pos:Vector, size:Vector) {
    val collides = collisions(pos, size)
    if(!collides.isEmpty) {
      println("chop!")
      val h = collides.head
      val pos = h._1
      val tree= h._2
      val chopped = tree.chop
      if(chopped.health > 0) {
        trees += (pos -> chopped)
      } else {
        trees -= pos
        Player.wood += 1
      }
    }
  }
}