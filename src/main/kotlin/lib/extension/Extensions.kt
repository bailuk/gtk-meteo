package lib.extension

fun String.ellipsize(max: Int): String {
    if (this.length <= max) return this
    return this.substring(0, max) + "…"
}
