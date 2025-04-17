package model;

public class Operation {
    public String operationId;
    public String type; // borrow / sell / return / exchange
    public String bookId;
    public String fromUserId;
    public String toUserId;
    public String status; // pending / approved / completed
    public String date;

    public Operation() {}

    public Operation(String operationId, String type, String bookId, String fromUserId, String toUserId, String status, String date) {
        this.operationId = operationId;
        this.type = type;
        this.bookId = bookId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.date = date;
    }
}
