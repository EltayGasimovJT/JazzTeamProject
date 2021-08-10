package controller;

import dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

import static util.JsonUtil.getBody;

@Slf4j
@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        List<ClientDto> allClients = clientService.findAllClients();

        out.println("This is the doGet Method");
        for (ClientDto client : allClients) {
            out.println("<h3>" + client + " <h3>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDto clientDTO = getClientDTOFromPostRequest(req);
        ClientDto savedClient = clientService.save(clientDTO);

        PrintWriter out = resp.getWriter();

        String methodType = req.getMethod();

        out.println("This is the do" + methodType + " Method");
        out.println("<h2>" + savedClient + " <h2>");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDto clientDTO = getClientDTOFromPutRequest(req);

        ClientDto update = clientService.update(clientDTO);

        PrintWriter out = resp.getWriter();

        out.println("This is the doPut Method");
        out.println("<h2>" + update + " <h2>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject(getBody(req));
        Long id = jsonObject.getLong("id");

        clientService.delete(id);

        PrintWriter out = resp.getWriter();

        out.println("This is the doDelete Method");
        out.println("<h2>The client was successfully deleted <h2>");
    }

    private ClientDto getClientDTOFromPostRequest(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject(getBody(req));
        String name = jsonObject.getString("name");
        String surname = jsonObject.getString("surname");
        String passportId = jsonObject.getString("passportId");
        String phoneNumber = jsonObject.getString("phoneNumber");
        return ClientDto.builder()
                .name(name)
                .surname(surname)
                .passportId(passportId)
                .phoneNumber(phoneNumber)
                .build();
    }

    private ClientDto getClientDTOFromPutRequest(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject(getBody(req));
        Long id = jsonObject.getLong("id");
        String name = jsonObject.getString("name");
        String surname = jsonObject.getString("surname");
        String passportId = jsonObject.getString("passportId");
        String phoneNumber = jsonObject.getString("phoneNumber");
        return ClientDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .passportId(passportId)
                .phoneNumber(phoneNumber)
                .build();
    }
}
