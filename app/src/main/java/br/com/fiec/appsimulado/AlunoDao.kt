package br.com.fiec.appsimulado
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

private val room: Any

@Dao
    interface AlunoDao {

        @Query("SELECT * FROM alunos ORDER BY nome ASC")
        fun getAlunos(): Flow<List<Aluno>>

        @Query("SELECT * FROM alunos WHERE id = :id")
        suspend fun getAlunoById(id: Long): Aluno?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAluno(aluno: Aluno): Long

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAlunos(alunos: List<Aluno>)

        @Update
        suspend fun updateAluno(aluno: Aluno)

        @Delete
        suspend fun deleteAluno(aluno: Aluno)

        @Query("DELETE FROM alunos")
        suspend fun deleteAllAlunos()
    }
