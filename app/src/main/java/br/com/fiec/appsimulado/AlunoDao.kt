package com.example.alunoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.edu.provap1.coisas.AlunoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Query("SELECT * FROM aluno ORDER BY nome ASC")
    fun getAll(): Flow<List<AlunoEntity>>

    @Query("SELECT * FROM aluno WHERE id = :id")
    suspend fun getById(id: Int): AlunoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(aluno: AlunoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(alunos: List<AlunoEntity>)

    @Delete
    suspend fun delete(aluno: AlunoEntity)

    @Query("DELETE FROM aluno")
    suspend fun deleteAll()
}
