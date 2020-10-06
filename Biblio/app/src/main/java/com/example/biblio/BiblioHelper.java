package com.example.biblio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class BiblioHelper extends SQLiteOpenHelper {

    public BiblioHelper(Context context)
    {
        super(context, "baseBiblio.db", null, 2 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // création des tables de la base embarquée
        // création de la table LIVRE
        db.execSQL("CREATE TABLE Livre ("
                + "isbnLivre TEXT NOT NULL PRIMARY KEY,"
                + "titreLivre TEXT NOT NULL,"
                + "anneeLivre INTEGER NOT NULL,"
                + "auteurLivre TEXT NOT NULL,"
                + "pagesLivre INTEGER,"
                + "editeurLivre TEXT NOT NULL);");

        // création d'un jeu d'essai
        Livre lesLivres[]= {
                new Livre("2266","La fille de papier",2013,"Guillaume Musso",608," XO éditions"),
                new Livre("7333", "Petit pays", 2016, "Gaël Faye", 224,"Grasset"),
                new Livre("4208","Harry Potter et l''enfant maudit",2016,"J.K Rowling",352,"Gallimard Jeunesse")};

        for (Livre liv : lesLivres) {
            db.execSQL("INSERT INTO Livre " +
                    " VALUES ('"+liv.getIsbn()+"','"+liv.getTitre()+"','"+liv.getAnnee()+"','"
                    +liv.getAuteur()+"','"+liv.getNbPages() +"','"+liv.getEditeur()+"');");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Livre;");
        onCreate(db);
    }


}
