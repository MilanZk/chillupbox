package com.zgz.mobile.android.prepexam.app.common.validation

import timber.log.Timber
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

/**
 * Created by Daniel on 9/2/2015.
 */
object CurrencyUtils {

    private const val MAXIMUM_CURRENCY_FRACTION_DIGITS = 2

    fun formatToCurrencyWithSymbol(amount: Double, currency: String): String {
        val nf = NumberFormat.getCurrencyInstance()
        val decimalFormatSymbols = (nf as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        nf.setMaximumFractionDigits(MAXIMUM_CURRENCY_FRACTION_DIGITS)
        nf.decimalFormatSymbols = decimalFormatSymbols
        nf.setRoundingMode(RoundingMode.HALF_UP)
        return if (currency.isEmpty()) {
            nf.format(amount)
        } else String.format("%s %s", currency, nf.format(amount))
    }

    fun parseAmount(amountAsText: String): Double {
        var amountString = amountAsText
        val nf = DecimalFormat.getInstance(Locale.getDefault())

        val groupingSeperator = (nf as DecimalFormat).decimalFormatSymbols.groupingSeparator
        amountString = amountString.replace(groupingSeperator.toString().toRegex(), "")
        try {
            return nf.parse(amountString).toDouble()
        } catch (e: ParseException) {
            Timber.d(e, "Exception: %s", e.toString())
        }

        Timber.d("Returning 0, it seems something happened.")
        return 0.0
    }
}
