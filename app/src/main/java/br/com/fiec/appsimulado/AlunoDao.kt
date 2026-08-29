package br.com.fiec.appsimulado


import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAluno(aluno: AlunoEntity)

    @Update
    suspend fun updateAluno(aluno: AlunoEntity)

    @Delete
    suspend fun deleteAluno(aluno: AlunoEntity)

    @Query("SELECT * FROM aluno")
    fun getAlunos(): Flow<List<AlunoEntity>>

    @Query("SELECT * FROM aluno WHERE id = :id")
    suspend fun getAlunoById(id: Int): AlunoEntity?
}