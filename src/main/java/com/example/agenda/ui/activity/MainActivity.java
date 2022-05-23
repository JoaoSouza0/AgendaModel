package com.example.agenda.ui.activity;

import static com.example.agenda.ui.activity.ConstantsActivities.KEY_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.dao.AlunoDao;
import com.example.agenda.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String APPBAR_TITLE = "Lista de Alunos";
    private final AlunoDao dao = new AlunoDao();
    private MainActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);
        configFabButton();
        configList();


    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.main_activity_menu_remove) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
            removeAluno(alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarAlunos();
    }

    private void atualizarAlunos() {
        adapter.atualizarAluno(dao.todos());
    }

    private void configFabButton() {
        FloatingActionButton btnNewClass = findViewById(R.id.activity_main_alunos_fab_novo_aluno);
        btnNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateForm();
            }
        });
    }

    private void openCreateForm() {
        startActivity(new Intent(this, FromClassActivity.class));
    }

    private void configList() {
        ListView alunosList = findViewById(R.id.activity_main_alunos_list);
        configAdapter(alunosList);
        configListenerItemClick(alunosList);
        registerForContextMenu(alunosList);
    }

    private void removeAluno(Aluno alunoEscolhido) {
        dao.remove(alunoEscolhido);
        adapter.remove(alunoEscolhido);
    }

    private void configListenerItemClick(ListView alunosList) {
        alunosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int elementPosition, long id ) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(elementPosition);
                openFormEditAluno(alunoEscolhido);
            }

            private void openFormEditAluno(Aluno alunoEscolhido) {
                Intent vaiParaFormularioActivity = new Intent(MainActivity.this, FromClassActivity.class);
                vaiParaFormularioActivity.putExtra(KEY_ALUNO, alunoEscolhido);
                startActivity(vaiParaFormularioActivity);
            }
        });
    }

    private void configAdapter(ListView alunosList) {
        adapter = new MainActivityAdapter(this);
        alunosList.setAdapter(adapter);
    }
}