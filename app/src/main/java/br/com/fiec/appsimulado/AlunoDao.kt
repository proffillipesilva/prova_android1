package br.com.fiec.appsimulado

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlunoDao {

    @Query("SELECT * FROM alunos")
    suspend fun getAll(): List<AlunoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(alunos: List<AlunoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(aluno: AlunoEntity)
}