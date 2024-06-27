package com.example.bpmtapper.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bpmtapper.model.SettingsData

@Database(entities = [SettingsData::class],
          version = 1)
abstract class SettingsDatabase: RoomDatabase()
{
	abstract val dao: SettingsDao
}