package br.com.fiec.appsimulado

import android.content.Context
import androidx.room3.Database

import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(
    entities = [AlunoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alunoDao(): AlunoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "aluno_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}