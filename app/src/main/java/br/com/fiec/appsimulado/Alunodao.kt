package br.com.fiec.appsimulado

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Query("SELECT * FROM aluno")
    fun getAlunos(): Flow<List<AlunoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAluno(aluno: AlunoEntity)

    // Bulk insert: útil para o repository salvar de uma vez a lista vinda da API.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlunos(alunos: List<AlunoEntity>)
}
