package fr.lamsoent.glucoseapplication.controller;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebFilter(urlPatterns = {"/client/*", "/admin/*", "/utilisateur/*"})
public class AuthentificationFilter implements Filter {

        @Inject
        private AuthentificationController authentificationController;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            String ipLuc = "10.45.21.146";
            String remoteIP = request.getRemoteAddr();

            if(remoteIP.equals(ipLuc) || remoteIP.equals("192.168.137.176")){
                chain.doFilter(request, response);
                return;
            }

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // Exclure les ressources statiques et les pages publiques
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.contains("/javax.faces.resource/") ||
                    requestURI.contains("/index.xhtml") ||
                    requestURI.contains("/resources/") ||
                    requestURI.contains("/authentification.xhtml"))  {
                chain.doFilter(request, response);
                return;
            }

            // Vérifier si l'utilisateur est authentifié
            if (!authentificationController.isUserAuthenticated()) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/erreur.html");
            } else {
                chain.doFilter(request, response);
            }
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            // Initialisation
        }

        @Override
        public void destroy() {
            // Nettoyage
        }
}
