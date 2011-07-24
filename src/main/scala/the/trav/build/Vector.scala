package the.trav.build

case class Vector(x:Double, y:Double) {
  def i = IVector(x.asInstanceOf[Int], y.asInstanceOf[Int])

  def +(o:Vector) = Vector(x+o.x, y+o.y)
  def -(o:Vector) = Vector(x-o.x, y-o.y)

  def *(s:Double) = Vector(x*s, y*s)
  def /(s:Double) = Vector(x/s, y/s)

  def distance(o:Vector) = {
    math.sqrt(((x - o.x) * (x - o.x)) / ((y - o.y)*(y - o.y)))
  }

  def size = math.abs(x) + math.abs(y)
}

case class IVector(x:Int, y:Int) {
  def d = Vector(x, y)
}