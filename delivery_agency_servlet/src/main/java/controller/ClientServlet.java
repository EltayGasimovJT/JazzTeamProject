package controller;

import com.google.gson.Gson;
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
import java.net.UnknownHostException;
import java.util.List;

import static util.JsonUtil.getBody;

@Slf4j
@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = new ClientServiceImpl();
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ENCODING = "UTF-8";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<ClientDto> allClients = clientService.findAllClients();
        resp.setContentType(CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(ENCODING);
        try (PrintWriter out = resp.getWriter()) {
            for (ClientDto client : allClients) {
                String toJson = new Gson().toJson(client);
                out.println(toJson);
                out.flush();
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ClientDto clientDTO = getClientDTOFromPostRequest(req);
        resp.setContentType(CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(ENCODING);

        ClientDto savedClient = null;

        try {
            savedClient = clientService.save(clientDTO);
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            } catch (IOException ex) {
                log.error(e.getMessage());
            }
        }

        try (PrintWriter out = resp.getWriter()) {
            String toJson = new Gson().toJson(savedClient);
            out.println(toJson);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ClientDto clientDTO = getClientDTOFromPutRequest(req);

        resp.setContentType(CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(ENCODING);

        ClientDto update = null;
        try {
            update = clientService.update(clientDTO);
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }

        try (PrintWriter out = resp.getWriter()) {
            String toJson = new Gson().toJson(update);
            out.println(toJson);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            long id = Long.parseLong(req.getParameter("id"));
            try {
                clientService.delete(id);
            } catch (IllegalArgumentException e) {
                try {
                    resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            }
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
