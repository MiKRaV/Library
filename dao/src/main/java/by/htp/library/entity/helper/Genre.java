package by.htp.library.entity.helper;

public enum Genre {
    FICTION("Fiction", "Художественная"),
    TECHNICAL("Technical", "Техническая"),
    PSYCHOLOGY("Psychology", "Психология");

    private String en;
    private String ru;

    Genre(String en, String ru) {
        this.en = en;
        this.ru = ru;
    }

    public String getEn() {
        return en;
    }

    public String getRu() {
        return ru;
    }
}
