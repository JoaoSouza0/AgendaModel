package com.example.agenda.dao;

import androidx.annotation.Nullable;

import com.example.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorIds = 1;

    public void salva(Aluno aluno) {
        aluno.setId(contadorIds);
        alunos.add(aluno);
        contadorIds++;
    }

    public void edita(Aluno aluno) {
        Aluno alunoEncontrado = searchAluno(aluno);
        if (alunoEncontrado != null) {
            int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoDoAluno, aluno);
        }
    }

    @Nullable
    private Aluno searchAluno(Aluno aluno) {
        Aluno alunoEncontrado = null;

        for (Aluno a : alunos) {
            if (a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }


    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        Aluno alunoEncontrado = searchAluno(aluno);
        if (alunoEncontrado != null) {
            alunos.remove(alunoEncontrado);

        }
    }
}
