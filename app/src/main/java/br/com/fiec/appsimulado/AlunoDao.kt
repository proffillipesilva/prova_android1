package br.com.fiec.appsimulado

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(aluno: AlunoEntity)

    @Query("SELECT * FROM AlunoEntity")
    fun getAll(): Flow<List<AlunoEntity>>
}