package controller

import config.Files
import java.util.regex.Pattern

object SlotFiles {
    private val PATTERN: Pattern = Pattern.compile("days-([0-9]+)\\.json")

    fun getOldestSlot(max: Int): Int {
        var index = 0
        var time = System.currentTimeMillis()
        eachSlotOnFileSystem { i, t ->
            if (i < max && t < time) {
                index = i
                time = t
            }
        }
        return index
    }

    fun getNewestSlot(max: Int): Int {
        var index = 0
        var time = 0L
        eachSlotOnFileSystem { i, t ->
            if (i < max && t > time) {
                index = i
                time = t
            }
        }
        return index
    }

    fun countSlots(): Int {
        var result = 0
        eachSlotOnFileSystem { _, _ -> result++ }
        return result
    }

    private fun eachSlotOnFileSystem(cb: (slot: Int, modified: Long) -> Unit) {
        Files.configDirectory.listFiles()?.forEach {
            val matcher = PATTERN.matcher(it.name)
            if (matcher.find()) {
                cb(matcher.group(1).toInt(), it.lastModified())
            }
        }
    }
}
