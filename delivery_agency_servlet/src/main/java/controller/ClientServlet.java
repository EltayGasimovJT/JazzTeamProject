package controller;

import dto.ClientDTO;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ClientServlet extends HttpServlet {
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String passportId = req.getParameter("passportId");
        String phoneNumber = req.getParameter("phoneNumber");

        ClientDTO clientDTO = ClientDTO.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .passportID(passportId)
                .phoneNumber(phoneNumber)
                .build();

        clientService.save(clientDTO);

        PrintWriter out = resp.getWriter();
        out.println("<h1>" + clientDTO + " <h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
