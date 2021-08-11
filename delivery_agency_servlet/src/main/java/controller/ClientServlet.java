package controller;

import dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static util.JsonUtil.getBody;

@Slf4j
@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<ClientDto> allClients = clientService.findAllClients();
        try (PrintWriter out = resp.getWriter()) {
            out.println("This is the doGet Method");
            for (ClientDto client : allClients) {
                out.println("<h3>" + client + " <h3>");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ClientDto clientDTO = getClientDTOFromPostRequest(req);

        if (clientService.findByPassportId(clientDTO.getPassportId())!= null) {
            try {
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Clients cannot have equals passportId!!!");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        ClientDto savedClient = clientService.save(clientDTO);

        try (PrintWriter out = resp.getWriter()) {
            out.println("This is the doPost Method");
            out.println("<h2>" + savedClient + " <h2>");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ClientDto clientDTO = getClientDTOFromPutRequest(req);

        ClientDto clientById = clientService.findById(clientDTO.getId());

        if (clientById == null) {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "There is no client to update!!!");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        ClientDto update = clientService.update(clientDTO);

        try (PrintWriter out = resp.getWriter()) {
            out.println("This is the doPut Method");
            out.println("<h2>" + update + " <h2>");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            long id = Long.parseLong(req.getParameter("id"));

            clientService.delete(id);

            out.println("This is the doDelete Method");
            out.println("<h2>The client was successfully deleted <h2>");
        } catch (IOException | JSONException e) {
            log.error(e.getMessage());
        }
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
