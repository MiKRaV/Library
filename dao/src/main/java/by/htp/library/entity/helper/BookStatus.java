package by.htp.library.entity.helper;

public enum BookStatus {

    AVAILABLE(1), UNAVAILABLE(2);

    private int value;

    BookStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
