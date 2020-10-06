package com.example.biblio;

public class Livre {
    // attributs privés
    private String isbn;
    private String titre;
    private Integer annee;
    private String auteur;
    private Integer nbPages;
    private String editeur;

    // constructeurs
    public Livre() {}

    public Livre(String isbn, String titre, Integer annee, String auteur, Integer nbPages, String editeur) {
        this.isbn = isbn;
        this.titre = titre;
        this.annee = annee;
        this.auteur = auteur;
        this.nbPages = nbPages;
        this.editeur = editeur;
    }

    // getteurs - setteurs
    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitre() {return titre;}
    public void setTitre(String titre) {this.titre = titre;}
    public Integer getAnnee() {return annee;}
    public void setAnnee(Integer annee) {this.annee = annee;}
    public String getAuteur() {return auteur;}
    public void setAuteur(String auteur) {this.auteur = auteur;}
    public Integer getNbPages() {return nbPages;}
    public void setNbPages(Integer nbPages) {this.nbPages = nbPages;}
    public String getEditeur() {return this.editeur;}
    public void setEditeur(String editeur) {this.editeur = editeur;}

}
