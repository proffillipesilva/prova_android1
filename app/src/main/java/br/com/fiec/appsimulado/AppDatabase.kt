package br.com.fiec.appsimulado

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

@Database(entities = [AlunoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alunoDao(): AlunoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder<AppDatabase>(
                    context = context.applicationContext,
                    name = "prova_database"
                )
                    .setDriver(BundledSQLiteDriver())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}