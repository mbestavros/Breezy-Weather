package org.breezyweather.settings.preference.composables

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.breezyweather.R
import org.breezyweather.common.ui.widgets.Material3CardListItem
import org.breezyweather.common.ui.widgets.defaultCardListItemElevation
import org.breezyweather.theme.compose.DayNightTheme
import org.breezyweather.theme.compose.rememberThemeRipple

@Composable
fun SwitchPreferenceView(
    @StringRes titleId: Int,
    @DrawableRes iconId: Int? = null,
    @StringRes summaryOnId: Int,
    @StringRes summaryOffId: Int,
    checked: Boolean,
    enabled: Boolean = true,
    card: Boolean = true,
    onValueChanged: (Boolean) -> Unit,
) = SwitchPreferenceView(
    title = stringResource(titleId),
    iconId = iconId,
    summary = { context, it ->
        context.getString(if (it) summaryOnId else summaryOffId)
    },
    checked = checked,
    enabled = enabled,
    card = card,
    onValueChanged = onValueChanged,
)

@Composable
fun SwitchPreferenceView(
    title: String,
    @DrawableRes iconId: Int? = null,
    summary: (Context, Boolean) -> String?,
    checked: Boolean,
    enabled: Boolean = true,
    card: Boolean = true,
    onValueChanged: (Boolean) -> Unit,
) {
    val state = remember { mutableStateOf(checked) }
    val currentSummary = summary(LocalContext.current, state.value)

    // TODO: Redundancy
    if (card) {
        Material3CardListItem(
            elevation = if (enabled) defaultCardListItemElevation else 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (enabled) 1f else 0.5f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberThemeRipple(),
                        onClick = {
                            state.value = !state.value
                            onValueChanged(state.value)
                        },
                        enabled = enabled,
                    )
                    .padding(dimensionResource(R.dimen.normal_margin)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = DayNightTheme.colors.titleColor,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (currentSummary?.isNotEmpty() == true) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.little_margin)))
                        Text(
                            text = currentSummary,
                            color = DayNightTheme.colors.bodyColor,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.little_margin)))
                Switch(
                    enabled = enabled,
                    checked = state.value,
                    onCheckedChange = {
                        state.value = it
                        onValueChanged(it)
                    }
                )
            }
        }
    } else {
        ListItem(
            tonalElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (enabled) 1f else 0.5f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberThemeRipple(),
                    onClick = {
                        state.value = !state.value
                        onValueChanged(state.value)
                    },
                    enabled = enabled,
                )
                .padding(PaddingValues(vertical = 8.dp)),
            leadingContent = if (iconId != null) {
                {
                    Icon(
                        painter = painterResource(iconId),
                        tint = DayNightTheme.colors.titleColor,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                }
            } else null,
            headlineContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = title,
                            color = DayNightTheme.colors.titleColor,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            },
            supportingContent = if (currentSummary?.isNotEmpty() == true) {
                {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.little_margin)))
                    Text(
                        text = currentSummary,
                        color = DayNightTheme.colors.bodyColor,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            } else null,
            trailingContent = {
                Switch(
                    enabled = enabled,
                    checked = state.value,
                    onCheckedChange = {
                        state.value = it
                        onValueChanged(it)
                    }
                )
            }
        )
    }
}