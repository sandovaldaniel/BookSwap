package model;

public class Operation {
    public String operationId;
    public String type; // borrow / sell / exchange
    public String bookId;
    public String fromUserEmail;
    public String toUserEmail;
    public String date;

    public Operation() {}

    public Operation(String operationId, String type, String bookId, String fromUserEmail, String toUserEmail, String date) {
        this.operationId = operationId;
        this.type = type;
        this.bookId = bookId;
        this.fromUserEmail = fromUserEmail;
        this.toUserEmail = toUserEmail;
        this.date = date;
    }
}
