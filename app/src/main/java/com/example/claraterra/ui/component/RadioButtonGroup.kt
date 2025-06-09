package com.example.claraterra.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @param options lista de pares (value, label)
 * @param selected valor actualmente seleccionado
 * @param onSelected callback cuando un radio es seleccionado
 */
@Composable
fun <T> RadioButtonGroup(
    options: List<Pair<T, String>>,
    selected: T,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        options.forEach { (value, label) ->
            RadioButton(
                selected = value == selected,
                onClick = { onSelected(value) }
            )
            Text(text = label, modifier = Modifier.padding(start = 4.dp, end = 16.dp))
        }
    }
}
