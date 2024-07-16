package com.mozzartbet.greekkeno.core.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.mozzartbet.greekkeno.core.ui.R

@Composable
fun GreekKenoAlertDialog(
    title: String,
    description: String,
    shouldShowDialog: MutableState<Boolean>,
    action: () -> Unit = { }
) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
                action()
            },
            title = { Text(text = title) },
            text = { Text(text = description) },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        action()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.ok)
                    )
                }
            }
        )
    }
}
