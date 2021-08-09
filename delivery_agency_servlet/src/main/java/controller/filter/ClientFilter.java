package controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ClientFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (((HttpServletRequest) servletRequest).getMethod().equals("POST")) {
            session.setAttribute("METHOD_TYPE", "POST");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
