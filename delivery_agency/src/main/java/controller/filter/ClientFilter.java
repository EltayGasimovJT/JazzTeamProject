package controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@WebFilter
public class ClientFilter implements Filter {
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (((HttpServletRequest) servletRequest).getMethod().equals("GET")) {
            try {
                clientService.findAll();
            } catch (IllegalArgumentException | SQLException e) {
                response.setStatus(HttpStatus.SC_CONFLICT, e.getMessage());

                response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
            }
        }

        filterChain.doFilter(servletRequest, response);
    }
}
