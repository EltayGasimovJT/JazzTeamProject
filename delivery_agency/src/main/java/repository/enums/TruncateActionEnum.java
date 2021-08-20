package repository.enums;

public enum TruncateActionEnum {
    TRUNCATE_CLIENTS_TABLE("TRUNCATE TABLE clients");

    private final String query;

    TruncateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}