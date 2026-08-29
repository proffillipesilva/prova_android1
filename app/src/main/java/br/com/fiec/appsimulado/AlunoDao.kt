package br.com.fiec.appsimulado

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(aluno: AlunoEntity): Long

    @Query("SELECT * FROM alunos ORDER BY id DESC")
    fun getTodosAlunos(): Flow<List<AlunoEntity>>
}