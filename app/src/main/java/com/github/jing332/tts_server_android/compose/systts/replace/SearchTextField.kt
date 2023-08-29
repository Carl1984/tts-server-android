package com.github.jing332.tts_server_android.compose.systts.replace

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jing332.tts_server_android.R
import com.github.jing332.tts_server_android.compose.theme.AppTheme
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SearchType(@StringRes val strId: Int) : Parcelable {
    GROUP_NAME(R.string.group_name),
    NAME(R.string.display_name),
    PATTERN(R.string.replace_rule),
    REPLACEMENT(R.string.systts_replace_as),
}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    searchType: SearchType,
    onSearchTypeChange: (SearchType) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        singleLine = true,
        leadingIcon = {
            var showTypeOptions by remember { mutableStateOf(false) }
            IconButton(onClick = { showTypeOptions = true }) {
                Icon(
                    Icons.Default.AccountTree, stringResource(id = R.string.type)
                )

                DropdownMenu(
                    expanded = showTypeOptions,
                    onDismissRequest = { showTypeOptions = false }) {

                    @Composable
                    fun RadioMenuItem(
                        isSelected: Boolean,
                        title: @Composable () -> Unit,
                        onClick: () -> Unit
                    ) {
                        DropdownMenuItem(
                            modifier = Modifier.semantics {
                                role = Role.RadioButton
                            },
                            text = title,
                            onClick = {
                                showTypeOptions = false
                                onClick()
                            },
                            leadingIcon = {
                                RadioButton(
                                    modifier = Modifier.focusable(false),
                                    selected = isSelected,
                                    onClick = null
                                )
                            }
                        )
                    }

                    SearchType.values().forEach {
                        RadioMenuItem(
                            isSelected = it == searchType,
                            title = { Text(stringResource(id = it.strId)) },
                            onClick = { onSearchTypeChange(it) }
                        )
                    }

                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewRuleSearchTextField() {
    AppTheme {
        Column {
            Spacer(modifier = Modifier.height(64.dp))
            var value by remember { mutableStateOf("") }
            var searchType by remember { mutableStateOf(SearchType.NAME) }
            SearchTextField(
                value = value,
                onValueChange = { value = it },
                searchType = searchType,
                onSearchTypeChange = { searchType = it })
        }
    }
}