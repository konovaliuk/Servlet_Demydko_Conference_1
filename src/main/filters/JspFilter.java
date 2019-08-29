package filters;

import entity.User;
import servises.configManager.ConfigManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "*.jsp")
public class JspFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("user");

        String url = req.getRequestURI();
        String loginPath = req.getContextPath() + ConfigManager.getProperty("login");
        String registerPath = req.getContextPath() + ConfigManager.getProperty("register");
        String contextPath = req.getContextPath() + "/";


        if (user == null && (!url.equals(registerPath) && !url.equals(loginPath) && !url.equals(contextPath))) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(ConfigManager.getProperty("login"));
            dispatcher.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
