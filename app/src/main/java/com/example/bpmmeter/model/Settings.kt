package com.example.bpmmeter.model

import androidx.compose.material3.Typography
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bpmmeter.ui.theme.defaultType
import com.example.bpmmeter.ui.theme.MyColors
import com.example.bpmmeter.ui.theme.ThemeType

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