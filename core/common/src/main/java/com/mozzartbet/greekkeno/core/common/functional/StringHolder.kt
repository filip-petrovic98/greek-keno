package com.mozzartbet.greekkeno.core.common.functional

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class StringHolder(
    private var value: @RawValue Any = "",
    private var args: Array<out @RawValue Any> = emptyArray(),
    private var quantity: Int? = null,
    private var additionalStrings: MutableList<@RawValue Any> = mutableListOf()
) : Parcelable {

    // region Constructor

    constructor(string: String) : this() {
        value = string
        args = emptyArray()
        quantity = null
    }

    constructor(stringRes: Int, vararg formatArgs: Any) : this() {
        value = stringRes
        args = formatArgs
        quantity = null
    }

    constructor(stringRes: Int, quantity: Int, vararg formatArgs: Any) : this() {
        value = stringRes
        args = formatArgs
        this.quantity = quantity
    }

    // endregion

    // region Overrides

    override fun equals(other: Any?): Boolean {
        return (other as? StringHolder)?.let {
            it.value == value && it.args.contentEquals(args)
        } ?: false
    }

    override fun hashCode(): Int {
        return 31 * value.hashCode() + args.contentHashCode()
    }

    // endregion

    // region Public API

    fun getValue(context: Context?): String {
        var finalString = getStringValue(context)

        additionalStrings.forEach {
            finalString += (it as? StringHolder)?.getValue(context) ?: it.toString()
        }

        return finalString
    }

    operator fun plus(increment: Any) = this.apply {
        additionalStrings.add(increment)
    }

    // endregion

    // region Private API

    private fun getStringValue(context: Context?): String = (value as? Int)?.let {
        val finalArgs = Array(args.size) { index ->
            (args[index] as? StringHolder)?.getValue(context) ?: args[index]
        }

        quantity.let { itemsQuantity ->
            if (itemsQuantity != null) {
                context?.resources?.getQuantityString(it, itemsQuantity, *finalArgs).orEmpty()
            } else {
                context?.getString(it, *finalArgs).orEmpty()
            }
        }
    } ?: value as String

    // endregion
}
