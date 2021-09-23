package hunseong.com.box_office.extension

fun Int.personFormat() : String {

    return when(this) {
        in 0..1_000 -> {
            this.toString()
        }

        in 1_000..1_000_000 -> {
            "${(this / 1_000f).toFormatDot("#.#")}K"
        }

        else -> {
            "${(this / 1_000_000f).toFormatDot("#.#")}M"
        }
    }
}