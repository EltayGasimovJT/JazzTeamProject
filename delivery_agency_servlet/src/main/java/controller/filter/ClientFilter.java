package controller.filter;

import dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@WebFilter("/clients")
public class ClientFilter implements Filter {
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (((HttpServletRequest) servletRequest).getMethod().equals("GET")) {
            List<ClientDto> clients = clientService.findAllClients();
            if (clients.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is no clients to show!!!");
            }
        } else if (((HttpServletRequest) servletRequest).getMethod().equals("DELETE")) {
            long id = Long.parseLong(servletRequest.getParameter("id"));
            if (clientService.findById(id) == null) {
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "There is no such client!!!");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
