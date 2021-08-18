package controller;

import com.google.gson.Gson;
import dto.ClientDto;
import entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static util.JsonUtil.getBody;

@Slf4j
@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    private final ClientService clientService = new ClientServiceImpl();
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ENCODING = "UTF-8";
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            List<Client> allClients = clientService.findAll();
            List<ClientDto> allClientDtos = allClients.stream()
                    .map(client -> modelMapper.map(client, ClientDto.class))
                    .collect(Collectors.toList());
            resp.setContentType(CONTENT_TYPE_JSON);
            resp.setCharacterEncoding(ENCODING);
            for (ClientDto client : allClientDtos) {
                String toJson = new Gson().toJson(client);
                out.println(toJson);
                out.flush();
            }
        } catch (JSONException jsonException) {
            log.error(jsonException.getMessage());
        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ClientDto clientDTO = getClientDTOFromPostRequest(req);
        resp.setContentType(CONTENT_TYPE_JSON);
        resp.setCharacterEncoding(ENCODING);

        try (PrintWriter out = resp.getWriter()) {
            ClientDto savedClient = new ClientDto();
            try {
                savedClient = modelMapper.map(clientService.save(clientDTO), ClientDto.class);
            } catch (IllegalArgumentException | SQLException e) {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            }
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

        try (PrintWriter out = resp.getWriter()) {
            ClientDto updatedClient = new ClientDto();
            try {
                updatedClient = modelMapper.map(clientService.update(clientDTO), ClientDto.class);
            } catch (IllegalArgumentException | SQLException e) {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            }
            String toJson = new Gson().toJson(updatedClient);
            out.println(toJson);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            long id = Long.parseLong(req.getParameter("id"));
            try {
                clientService.delete(id);
            } catch (IllegalArgumentException | SQLException e) {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            }
            out.println("<h2>The client was successfully deleted <h2>");
        } catch (IOException | JSONException e) {
            log.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            try {
                resp.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
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
