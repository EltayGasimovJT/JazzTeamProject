package controller;

import servicetest.TableService;
import servicetest.impl.TableServiceImpl;

import javax.servlet.http.HttpServlet;

public class StartupServlet extends HttpServlet {
    private final TableService tableService = new TableServiceImpl();

    @Override
    public void init() {
        tableService.dropTablesIfExists();
        tableService.createTables();
    }
}
