package entities;

public abstract class Catalog {
    private String ISBN;
    private String title;
    private int releaseDate;
    private int numberPages;

    public Catalog(String ISBN, String title, int releaseDate, int numberPages) {
        this.ISBN = ISBN;
        this.title = title;
        this.releaseDate = releaseDate;
        this.numberPages = numberPages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", numberPages=" + numberPages +
                '}';
    }
}
