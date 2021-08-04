package repository.enums;

public enum DropActionEnum {
    DROP_USER_TABLE("DROP TABLE IF EXISTS clients");

    private final String query;

    DropActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
