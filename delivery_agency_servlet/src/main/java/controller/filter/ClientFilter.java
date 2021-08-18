package controller.filter;

import dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@WebFilter("/clients")
public class ClientFilter implements Filter {
    private final ClientService clientService = new ClientServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (((HttpServletRequest) servletRequest).getMethod().equals("GET")) {
            try {
                clientService.findAll()
                        .stream()
                        .map(client -> modelMapper.map(client, ClientDto.class))
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException | SQLException e) {
                log.error(e.getMessage());
                response.sendError(HttpServletResponse.SC_CONFLICT, "There is no clients to show!!!");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
