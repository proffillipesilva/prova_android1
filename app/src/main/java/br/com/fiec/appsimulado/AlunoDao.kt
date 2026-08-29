package br.com.fiec.appsimulado



import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(aluno: AlunoEntity)

    @Query("SELECT * FROM alunos")
    fun listarTodos(): Flow<List<AlunoEntity>>

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun buscarPorId(id: Int): AlunoEntity?

    @Delete
    suspend fun deletar(aluno: AlunoEntity)
}