package br.com.fiec.appsimulado

class AlunoLocalRepository(private val alunoDao: AlunoDao) {

    suspend fun getAlunos(): List<Aluno> {
        // Converte manualmente a lista de Entity para Aluno
        return alunoDao.getAlunos().map { entity ->
            Aluno(nome = entity.nome, foto = entity.foto)
        }
    }

    suspend fun insertAluno(aluno: Aluno) {
        // Converte manualmente o Aluno para Entity na hora de inserir
        val entity = AlunoEntity(nome = aluno.nome, foto = aluno.foto)
        alunoDao.insertAluno(entity)
    }
}