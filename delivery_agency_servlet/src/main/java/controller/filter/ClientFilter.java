package controller.filter;

import dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@WebFilter("/client")
public class ClientFilter implements Filter {
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (((HttpServletRequest) servletRequest).getMethod().equals("POST")) {
            String passportId = request.getParameter("passportId");
            String phoneNumber = request.getParameter("phoneNumber");
            List<ClientDto> clients = clientService.findAllClients();
            for (ClientDto client : clients) {
                if (client.getPassportId().equals(passportId)) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Client with this passportId already exists!!!");
                }
                if (client.getPhoneNumber().equals(phoneNumber)) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Client with this phoneNumber already exists!!!");
                }
            }
        } else if (((HttpServletRequest) servletRequest).getMethod().equals("DELETE")
                || ((HttpServletRequest) servletRequest).getMethod().equals("PUT")) {
            JSONObject jsonObject = new JSONObject(getBody(request));
            long id = jsonObject.getLong("id");
            if (clientService.findById(id) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "There is no client with such Id!!!");
            }
        } else if(((HttpServletRequest) servletRequest).getMethod().equals("GET")){
            List<ClientDto> clients = clientService.findAllClients();
            if(clients.isEmpty()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "There is no clients to show!!!");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static String getBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            }
        }

        return stringBuilder.toString();
    }
}
