package controller

import config.Files
import java.io.File

object LockFiles {
    fun isLocked(index: Int): Boolean {
        return getLockFile(index).isFile
    }

    private fun lock(index: Int) {
        getLockFile(index).createNewFile()
    }

    private fun unLock(index: Int) {
        getLockFile(index).delete()
    }

    fun toggleLock(index: Int) {
        if (isLocked(index)) {
            unLock(index)
        } else {
            lock(index)
        }
    }

    private fun getLockFile(index: Int): File {
        return File(Files.configDirectory, "slot-${index}.lock")
    }
}
