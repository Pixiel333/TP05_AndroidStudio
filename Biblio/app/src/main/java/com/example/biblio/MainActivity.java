package com.example.biblio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView lstLivres = null;
    private BiblioDAO maBDD;
    private ArrayList<HashMap<String,String>> lesLivres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstLivres = (ListView)findViewById(R.id.lv_livres);
        // construction entête ListView
        enteteLstLivres();
        // chargement de la ListView
        initLstLivres();

        // écouteur d'évènement sur la listView
        lstLivres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de l'item sélectionné
                HashMap<String, String> item = (HashMap<String, String>) lstLivres.getItemAtPosition(position);
                //on récupère l'isbn sélectionné et on l’affiche
                String isbnSelect = item.get("ISBN");
                Intent gererLivre = new Intent(MainActivity.this, GererLivre.class);
                gererLivre.putExtra("ISBN", isbnSelect);
                startActivity(gererLivre);

            }
        });

    }

    @Override
    protected void onResume () {
        super.onResume();
        initLstLivres();
    }

    // méthode qui construit l’entête de la liste des livres
    public void enteteLstLivres() {
        // ajout d'un header à la listView
        LayoutInflater layoutInflater = getLayoutInflater();
        View header = layoutInflater.inflate(R.layout.layout_header_lvlivre,null,false);
        lstLivres.addHeaderView(header,null,false);
    }

    // méthode qui charge la liste des livres
    public void initLstLivres() {
        // récupération des livres enregistrés dans la base et initialisation d'un tableau associatif
        maBDD = new BiblioDAO(this);
        Cursor curseurTous = maBDD.selectionnerTousLesLivres();
        lesLivres = new ArrayList<HashMap<String, String>>();
        for(curseurTous.moveToFirst(); !curseurTous.isAfterLast(); curseurTous.moveToNext()) {
            String isbn= curseurTous.getString(curseurTous.getColumnIndex("isbnLivre"));
            String titre = curseurTous.getString(curseurTous.getColumnIndex("titreLivre"));
            String pages = curseurTous.getString(curseurTous.getColumnIndex("pagesLivre"));
            String annee = curseurTous.getString(curseurTous.getColumnIndex("anneeLivre"));
            HashMap<String,String> unLivre = new HashMap<String, String>();
            unLivre.put("ISBN", isbn);
            unLivre.put("TITRE", titre);
            unLivre.put("PAGES", pages);
            unLivre.put("ANNEE", annee);
            lesLivres.add(unLivre);
        }
        curseurTous.close();
        maBDD.close();
        // remplissage de la listView en utilisant l'adapter personnalisé
        lstLivres.setAdapter(new SimpleAdapter( this,
                lesLivres,
                R.layout.layout_ligne_lvlivre,
                new String[] {"ISBN", "TITRE","PAGES", "ANNEE"},
                new int[] {R.id.col_isbnLivre, R.id.col_titreLivre,R.id.col_nbPages, R.id.col_anneeLivre}));
    }



}
