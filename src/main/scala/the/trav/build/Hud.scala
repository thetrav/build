package the.trav.build

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing._
import java.awt.{Color, Graphics2D}

trait HudFrame extends JFrame {
  def updateState:Unit
}

class InventoryFrame extends HudFrame {
  setName(Hud.inventory)
  setSize(300,200)
  val panel = new JPanel()
  getContentPane().add(panel)
  panel.add(new JLabel("Wood:"))
  val woodField = new JTextField("0")
  woodField.setEditable(false)
  panel.add(woodField)
  setLocation(Main.width, 0)
  setVisible(false)
  setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE)

  def updateState {
    woodField.setText(Player.wood + "")
  }
}

object Hud {
  val inventory = "Inventory"
  val mainFrame = "Main Frame"


  val msgFade = 1000*10
  var msgs = List[(String, Long)](msg("Welcome"), msg("arrows to move, space to chop"))

  def msg(s:String) = {
    (s, System.currentTimeMillis())
  }

  var windows = Map[String, HudFrame](
    inventory -> new InventoryFrame()
  )

  def init(frame:JFrame) {
    windows += mainFrame -> frame
    val menuBar = new JMenuBar
    frame.setJMenuBar(menuBar)
    val charMenu = new JMenu("Character")
    menuBar.add(charMenu)
    val inventoryItem = new JMenuItem(inventory)
    charMenu.add(inventoryItem)
    inventoryItem.addActionListener(new ActionListener {
      override def actionPerformed(e:ActionEvent) {
        windows(inventory).setVisible(true)
      }
    })

  }


  def update() {
    windows.foreach( _._2.updateState)
    val time = System.currentTimeMillis()
    msgs = msgs.filter(_._2 > time - msgFade)
  }

  def repaint() {
    windows.foreach(t => {
      val frame = t._2
      if(frame.isVisible()) {
      frame.invalidate()
      frame.validate()
      frame.repaint()
    }})
  }

  def draw(g:Graphics2D) {
    g.setColor(new Color(0,0,0,50))
    g.fillRect(0, Main.height-200, Main.width, 200)
    g.setColor(new Color(0,255,0,100))
    g.drawRect(0, Main.height-200, Main.width, 200)

    g.setColor(Color.white)

    if(msgs.size < 5) {
      printMsgs(g, 5)
    } else {

    }
  }
}