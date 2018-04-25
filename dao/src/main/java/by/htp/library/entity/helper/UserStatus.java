package by.htp.library.entity.helper;

public enum UserStatus {
    ACTIVE(1), BLOCKED(2), DELETED(0);

    private int status;

    UserStatus(int status) {
        this.status = status;
    }
}
