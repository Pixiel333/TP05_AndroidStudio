package com.example.biblio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;


public class BiblioDAO {
    private SQLiteDatabase maBase;
    private BiblioHelper maBiblioHelper;

    public BiblioDAO(Context context){
        maBiblioHelper = new BiblioHelper(context);
        maBase = maBiblioHelper.getWritableDatabase();
    }

    public void close(){
        maBase.close();
    }

    public Cursor selectionnerTousLesLivres() {
        Cursor curseurContact = maBase.rawQuery("SELECT isbnLivre, titreLivre, anneeLivre, pagesLivre from Livre" , new String[] {});
        return curseurContact;
    }

    public Livre selectionnerUnLivre(String unISBN) {
        // création d'un curseur stockant le résultat de la requête
         Cursor curseurLivre = maBase.rawQuery("SELECT * FROM Livre WHERE isbnLivre = ?", new String[]{unISBN});
         // lecture du curseur
        curseurLivre.moveToFirst();
        Livre unLivre = new Livre();
        unLivre.setIsbn(curseurLivre.getString(0));
        unLivre.setTitre(curseurLivre.getString(1));
        unLivre.setAnnee(curseurLivre.getInt(2));
        unLivre.setAuteur(curseurLivre.getString(3));
        unLivre.setNbPages(curseurLivre.getInt(4));
        unLivre.setEditeur(curseurLivre.getString(5));
        // fermeture du curseur
        curseurLivre.close();
        return unLivre;
    }

    public void supprimerLivre(String unISBN) {
        maBase.delete("Livre", "isbnLivre = ?", new String[] {unISBN});
    }

    public void modifierLivre(Livre unLivre) {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("isbnLivre", unLivre.getIsbn());
        v.put("titreLivre", unLivre.getTitre());
        v.put("anneeLivre", unLivre.getAnnee());
        v.put("auteurLivre", unLivre.getAuteur());
        v.put("pagesLivre", unLivre.getNbPages());
        v.put("editeurLivre", unLivre.getEditeur());
        // modifie le livre dans la table
        maBase.update("Livre", v, "isbnLivre = ?", new String[]{unLivre.getIsbn()});
    }

    public void ajouterLivre(Livre unLivre) {
        //création d'un ContentValues
        ContentValues v = new ContentValues();
        // ajout des propriétés au ContentValues
        v.put("isbnLivre", unLivre.getIsbn());
        v.put("titreLivre", unLivre.getTitre());
        v.put("anneeLivre", unLivre.getAnnee());
        v.put("auteurLivre", unLivre.getAuteur());
        v.put("pagesLivre", unLivre.getNbPages());
        v.put("editeurLivre", unLivre.getEditeur());
        // ajout du livre dans la table
        maBase.insert("Livre", null, v);
    }
}
