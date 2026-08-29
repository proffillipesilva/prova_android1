package br.com.fiec.appsimulado

class AlunoLocalRepository(private val dao: AlunoDao) {

    suspend fun getAlunosEntities(): List<AlunoEntity> = dao.getAlunos()

    suspend fun insertEntity(aluno: AlunoEntity) {
        dao.insertAluno(aluno)
    }
}