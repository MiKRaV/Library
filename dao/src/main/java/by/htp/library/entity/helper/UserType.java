package by.htp.library.entity.helper;

public enum UserType {
    ADMIN(0), READER(1);

    private int type;

    UserType(int type) {
        this.type = type;
    }
}
