package br.com.fiec.appsimulado

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Extra (não pedido, mas necessário): é o que constrói o banco e entrega o AlunoDao.
 */
@Database(entities = [AlunoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alunoDao(): AlunoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "aluno_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
