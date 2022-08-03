package controller

class Slot {
    companion object {
        const val MAX = 5
    }

    var selected = 0
        private set

    var count = 0
        private set

    init {
        count = SlotFiles.countSlots()
        selected = SlotFiles.getNewestSlot(count)
    }

    fun select(index: Int) {
        if (index > -1 && index < count) {
            selected = index
        }
    }

    fun selectNextFreeOrOldestSlot() {
        selected = if (count < MAX) {
            count++
            count-1
        } else {
            SlotFiles.getOldestUnlockedSlot(count)
        }
    }

    fun selectNext() {
        if (selected < count-1) {
            selected++
        } else {
            selected = 0
        }
    }
}
