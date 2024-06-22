package com.example.bpmtapper.model

import androidx.compose.material3.Typography
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bpmtapper.ui.theme.defaultType
import com.example.bpmtapper.ui.theme.MyColors
import com.example.bpmtapper.ui.theme.ThemeType

data class SettingsState(val theme: ThemeType = ThemeType.Dark,
                         val color: MyColors = MyColors.Green,
                         val leftHanded: Boolean = true,
                         val spacing: String = "default",
                         val typography: Typography = defaultType)

@Entity
data class SettingsData(val theme: ThemeType = ThemeType.Dark,
                        val color: MyColors = MyColors.Green,
                        val leftHanded: Boolean = true,
                        val spacing: String = "default",
                        @PrimaryKey(autoGenerate = true)
                        val id: Int = 0)