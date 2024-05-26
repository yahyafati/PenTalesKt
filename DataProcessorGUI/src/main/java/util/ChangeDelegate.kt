package util

class ChangeDelegate<T>(
    private var value: T,
    private val onChange: () -> Unit
) {

    operator fun getValue(thisRef: Any?, property: Any?): T = value

    operator fun setValue(thisRef: Any?, property: Any?, value: T) {
        if (this.value != value) {
            val oldValue = this.value
            this.value = value
            onChange()
        }
    }
}