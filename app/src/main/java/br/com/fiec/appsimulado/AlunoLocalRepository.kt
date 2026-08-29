package br.com.fiec.appsimulado

class AlunoLocalRepository(private val alunoDao: AlunoDao) {
    suspend fun getAlunos(): List<Aluno> {
        return alunoDao.getAllAlunos().map { it.toDomain() }
    }

    suspend fun insertAluno(aluno: Aluno) {
        alunoDao.insertAluno(aluno.toEntity())
    }
}