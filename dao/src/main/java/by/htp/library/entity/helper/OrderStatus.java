package by.htp.library.entity.helper;

public enum OrderStatus {
    IN_PROCESSING(0), ACCEPTED_FOR_EXECUTION(1), READY_FOR_ISSUE(2), FULFILLED(3);

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }
}
