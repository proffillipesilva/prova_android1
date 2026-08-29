package br.com.fiec.appsimulado

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [AlunoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alunoDao(): AlunoDao
}