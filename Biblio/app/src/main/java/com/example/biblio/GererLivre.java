package com.example.biblio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GererLivre extends AppCompatActivity {
    private EditText editISBN;
    private EditText editTitre;
    private EditText editAnnee;
    private EditText editAuteur;
    private EditText editPages;
    private EditText editEditeur;
    private Button btnValider;
    private Button btnDelete;
    private Button btnAnnuler;
    private Button btnModifier;
    private BiblioDAO maBDD;
    private String isbn="";
    private Livre leLivre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_livre);
        maBDD = new BiblioDAO(this);

        // obtention des références sur les vues de l'activité
        btnValider = (Button) findViewById(R.id.valider);
        btnDelete = (Button) findViewById(R.id.supprimer);
        btnAnnuler = (Button) findViewById(R.id.annuler);
        btnModifier = (Button) findViewById(R.id.modifier);
        editISBN = (EditText) findViewById(R.id.isbn);
        editAnnee = (EditText) findViewById(R.id.annee);
        editAuteur = (EditText) findViewById(R.id.auteur);
        editTitre = (EditText) findViewById(R.id.titre);
        editPages = (EditText) findViewById(R.id.nbPages);
        editEditeur = (EditText) findViewById(R.id.editeur);

        // rend inaccessible la vue
        editISBN.setEnabled(false);
        editAnnee.setEnabled(false);
        editAuteur.setEnabled(false);
        editTitre.setEnabled(false);
        editPages.setEnabled(false);
        editEditeur.setEnabled(false);

        // rend invisible la vue
        btnValider.setVisibility(View.INVISIBLE);
        btnAnnuler.setVisibility(View.INVISIBLE);

        // on récupère l'intent qui a lancé l'activité
        Intent i = getIntent();
        isbn = i.getStringExtra("ISBN");

        //ecouteur sur les boutons
        btnDelete.setOnClickListener(EcouteurBouton);
        btnModifier.setOnClickListener(EcouteurBouton);
        btnAnnuler.setOnClickListener(EcouteurBouton);
        btnValider.setOnClickListener(EcouteurBouton);

        leLivre = maBDD.selectionnerUnLivre(isbn);

        editISBN.setText(leLivre.getIsbn());
        editEditeur.setText(leLivre.getEditeur());
        editTitre.setText(leLivre.getTitre());
        editAuteur.setText(leLivre.getAuteur());
        editPages.setText(Integer.toString(leLivre.getNbPages()));
        editAnnee.setText(Integer.toString(leLivre.getAnnee()));
    }

    public View.OnClickListener EcouteurBouton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // quel bouton a été cliqué ?
            switch (view.getId()){
                case R.id.supprimer :
                    final AlertDialog.Builder boite = new AlertDialog.Builder(GererLivre.this);
                    boite.setTitle("Confirmation");
                    boite.setMessage("Voulez-vous supprimer ce livre ?");
                    boite.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            maBDD.supprimerLivre(isbn);
                            }
                    });
                    boite.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            }
                    });
                    boite.show();
                    break;
                case R.id.modifier :
                    btnValider.setVisibility(View.VISIBLE);
                    btnAnnuler.setVisibility(View.VISIBLE);
                    btnModifier.setVisibility(View.INVISIBLE);
                    btnDelete.setVisibility(View.INVISIBLE);
                    editAnnee.setEnabled(true);
                    editAuteur.setEnabled(true);
                    editTitre.setEnabled(true);
                    editPages.setEnabled(true);
                    editEditeur.setEnabled(true);
                    editTitre.requestFocus();
                    break;
                case R.id.annuler :
                    editISBN.setText(leLivre.getIsbn());
                    editEditeur.setText(leLivre.getEditeur());
                    editTitre.setText(leLivre.getTitre());
                    editAuteur.setText(leLivre.getAuteur());
                    editPages.setText(Integer.toString(leLivre.getNbPages()));
                    editAnnee.setText(Integer.toString(leLivre.getAnnee()));
                    btnValider.setVisibility(View.INVISIBLE);
                    btnAnnuler.setVisibility(View.INVISIBLE);
                    btnModifier.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.VISIBLE);
                    editAnnee.setEnabled(false);
                    editAuteur.setEnabled(false);
                    editTitre.setEnabled(false);
                    editPages.setEnabled(false);
                    editEditeur.setEnabled(false);
                    break;
                case R.id.valider :
                    leLivre.setTitre(editTitre.getText().toString());
                    leLivre.setAuteur(editAuteur.getText().toString());
                    leLivre.setAnnee(Integer.parseInt(editAnnee.getText().toString()));
                    leLivre.setNbPages(Integer.parseInt(editPages.getText().toString()));
                    leLivre.setEditeur(editEditeur.getText().toString());
                    maBDD.modifierLivre(leLivre);
                    editISBN.setText(leLivre.getIsbn());
                    editEditeur.setText(leLivre.getEditeur());
                    editTitre.setText(leLivre.getTitre());
                    editAuteur.setText(leLivre.getAuteur());
                    editPages.setText(Integer.toString(leLivre.getNbPages()));
                    editAnnee.setText(Integer.toString(leLivre.getAnnee()));
                    btnValider.setVisibility(View.INVISIBLE);
                    btnAnnuler.setVisibility(View.INVISIBLE);
                    btnModifier.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.VISIBLE);
                    editAnnee.setEnabled(false);
                    editAuteur.setEnabled(false);
                    editTitre.setEnabled(false);
                    editPages.setEnabled(false);
                    editEditeur.setEnabled(false);
                    break;

            }
        }
    };
}