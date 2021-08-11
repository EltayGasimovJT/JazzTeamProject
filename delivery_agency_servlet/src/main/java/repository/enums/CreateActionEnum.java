package repository.enums;

public enum CreateActionEnum {
    CREATE_CLIENTS_TABLE("CREATE TABLE clients\n" +
            "(\n" +
            "    id bigint(11) NOT NULL primary key auto_increment,\n" +
            "    name Varchar(50) NULL,\n" +
            "    surname VARCHAR(50) NULL,\n" +
            "    passportId VARCHAR(80) NULL,\n" +
            "    phone_number VARCHAR(50) NULL\n" +
            ");");

    private final String query;

    CreateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
