package hunseong.com.box_office.extension

import java.text.DecimalFormat

fun Float.toFormatDot(format: String): String = DecimalFormat(format).format(this)