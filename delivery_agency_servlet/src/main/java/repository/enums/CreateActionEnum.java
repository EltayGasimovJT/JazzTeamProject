package repository.enums;

public enum CreateActionEnum {
    CREATE_CLIENT_TABLE("CREATE TABLE clients\n" +
            "(\n" +
            "    id       id bigint(11) NOT NULL primary key,\n" +
            "    name      VARCHAR(60) NOT NULL,\n" +
            "    surname      VARCHAR(60) NOT NULL,\n" +
            "    passportID     VARCHAR(60) NOT NULL,\n" +
            "    phone_number    VARCHAR(60) NOT NULL\n" +
            ");");

    private final String query;

    CreateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
