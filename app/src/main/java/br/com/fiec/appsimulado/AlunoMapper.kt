package br.com.fiec.appsimulado


fun AlunoEntity.toAluno(): Aluno {
    return Aluno(
        id = this.id,
        nome = this.nome,
        foto = this.foto
    )
}

fun Aluno.toEntity(): AlunoEntity {
    return AlunoEntity(
        id = this.id,
        nome = this.nome,
        foto = this.foto
    )
}