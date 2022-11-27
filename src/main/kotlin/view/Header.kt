package view

import ch.bailu.gtk.gtk.*
import ch.bailu.gtk.type.Str
import config.Strings
import controller.Controller
import model.Model

class Header(app: Application) {
    companion object {
        private val iconLocked = Str("changes-prevent-symbolic")
        private val iconUnlocked = Str("changes-allow-symbolic")
        fun getLockIcon(): Str {
            return if (Controller.isSelectedSlotLocked()) {
                iconLocked
            } else {
                iconUnlocked
            }
        }
    }

    val headerBar = HeaderBar()

    init {
        headerBar.showTitleButtons = true
        headerBar.packStart(Button.newFromIconNameButton(iconLocked).apply {
            onClicked {
                Controller.toggleLockSelectedSlot()
                iconName = getLockIcon()
            }
            Model.observePlace {_, index ->
                if (Controller.isSelectedSlot(index)) {
                    iconName = getLockIcon()
                }
            }
        })


        val box = Box(Orientation.HORIZONTAL, 0)
        box.addCssClass(Strings.linked)
        box.append(Button.newFromIconNameButton(Str("go-next-symbolic")).apply {
            onClicked {
                Controller.selectNextSlot()
            }
        })
        box.append(MainMenu(app).menuButton)

        headerBar.packEnd(box)
    }
}
