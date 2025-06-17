package com.example;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class SearchServlet extends HttpServlet {

    // Map of title -> tutorial URL
    private LinkedHashMap<String, String> data;

    // Map of title -> YouTube video URL
    private LinkedHashMap<String, String> videoData;

    @Override
    public void init() throws ServletException {
        data = new LinkedHashMap<>();
        videoData = new LinkedHashMap<>();

        // Tutorials and guides (URL)
        data.put("Python Tutorial", "https://www.w3schools.com/python/");
        data.put("HTML Tutorial", "https://www.w3schools.com/html/");
        data.put("CSS Tutorial", "https://www.w3schools.com/css/");
        data.put("JavaScript Tutorial", "https://www.w3schools.com/js/");
        data.put("SQL Tutorial", "https://www.w3schools.com/sql/");
        data.put("Java Tutorial for Beginners", "https://www.w3schools.com/java/");
        data.put("PHP Tutorial", "https://www.w3schools.com/php/");
        data.put("Bootstrap Tutorial", "https://www.w3schools.com/bootstrap/");
        data.put("ReactJS Tutorial", "https://www.w3schools.com/react/");
        data.put("Angular Tutorial", "https://www.w3schools.com/angular/");
        data.put("Node.js Tutorial", "https://www.w3schools.com/nodejs/");
        data.put("jQuery Tutorial", "https://www.w3schools.com/jquery/");
        data.put("XML Tutorial", "https://www.w3schools.com/xml/");
        data.put("JSON Tutorial", "https://www.w3schools.com/js/js_json_intro.asp");
        data.put("Web Development Basics", "https://www.w3schools.com/whatis/");
        data.put("Advanced Java Programming", "https://www.javatpoint.com/advanced-java-tutorial");
        data.put("Servlets and JSP Guide", "https://www.javatpoint.com/servlet-tutorial");
        data.put("Understanding HTTP Protocol", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview");
        data.put("Building Web Applications with Java", "https://spring.io/guides/gs/serving-web-content/");
        data.put("Introduction to Spring Framework", "https://spring.io/projects/spring-framework");
        data.put("How Search Engines Work", "https://www.w3schools.com/whatis/whatis_search_engine.asp");
        data.put("Data Structures and Algorithms", "https://www.geeksforgeeks.org/data-structures/");
        data.put("RESTful Web Services", "https://restfulapi.net/");
        data.put("Java Design Patterns", "https://www.journaldev.com/1827/java-design-patterns-example-tutorial");
        data.put("Debugging Java Applications", "https://www.baeldung.com/java-debugging");
        data.put("Effective Java Tips", "https://www.baeldung.com/java-effective-tips");
        data.put("Web Security Basics", "https://owasp.org/www-project-top-ten/");
        data.put("Deploying Applications on Tomcat", "https://tomcat.apache.org/tomcat-10.0-doc/deployer-howto.html");
        data.put("Introduction to Microservices", "https://martinfowler.com/articles/microservices.html");

        // Technical terms (with Wikipedia or reliable URLs)
        data.put("Servlet - A Java program that runs on a server and handles web requests.", "https://en.wikipedia.org/wiki/Java_servlet");
        data.put("HTTP - The protocol used for transferring web pages on the internet.", "https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol");
        data.put("HTTPS - Secure version of HTTP using SSL/TLS.", "https://en.wikipedia.org/wiki/HTTPS");
        data.put("Web Server - Software that serves web pages to clients.", "https://en.wikipedia.org/wiki/Web_server");
        data.put("Tomcat - An open-source Java servlet container developed by the Apache Software Foundation.", "https://tomcat.apache.org/");
        data.put("Framework - A platform for developing software applications.", "https://en.wikipedia.org/wiki/Software_framework");
        data.put("Microservices - An architectural style that structures an application as a collection of loosely coupled services.", "https://en.wikipedia.org/wiki/Microservices");
        data.put("API - Interface that allows software applications to communicate with each other.", "https://en.wikipedia.org/wiki/API");
        data.put("Encapsulation - The bundling of data and methods into a single unit (class).", "https://en.wikipedia.org/wiki/Encapsulation_(computer_programming)");
        data.put("Abstraction - The concept of hiding complex implementation details and showing only the essentials.", "https://en.wikipedia.org/wiki/Abstraction_(computer_science)");
        data.put("Polymorphism - Ability of different classes to respond to the same method call in different ways.", "https://en.wikipedia.org/wiki/Polymorphism_(computer_science)");
        data.put("Inheritance - Mechanism where a new class inherits properties from an existing class.", "https://en.wikipedia.org/wiki/Inheritance_(object-oriented_programming)");
        data.put("Algorithm - A step-by-step procedure for solving a problem.", "https://en.wikipedia.org/wiki/Algorithm");
        data.put("Compilation - The process of converting source code into executable code.", "https://en.wikipedia.org/wiki/Compilation");
        data.put("Database - An organized collection of structured information.", "https://en.wikipedia.org/wiki/Database");
        data.put("Version Control - System that records changes to files over time.", "https://en.wikipedia.org/wiki/Version_control");
        data.put("Responsive Design - Design that works on desktops, tablets, and phones.", "https://en.wikipedia.org/wiki/Responsive_web_design");

        // Video tutorial links (YouTube) for some topics
        videoData.put("Python Tutorial", "https://www.youtube.com/watch?v=_uQrJ0TkZlc");
        videoData.put("HTML Tutorial", "https://www.youtube.com/watch?v=pQN-pnXPaVg");
        videoData.put("CSS Tutorial", "https://www.youtube.com/watch?v=yfoY53QXEnI");
        videoData.put("JavaScript Tutorial", "https://www.youtube.com/watch?v=W6NZfCO5SIk");
        videoData.put("Java Tutorial for Beginners", "https://www.youtube.com/watch?v=grEKMHGYyns");
        videoData.put("Servlets and JSP Guide", "https://www.youtube.com/watch?v=81DTiqOhriE");
        videoData.put("RESTful Web Services", "https://www.youtube.com/watch?v=Q-BpqyOT3a8");
        videoData.put("Spring Framework Introduction", "https://www.youtube.com/watch?v=5yBqYM3Ky0k");
        videoData.put("Data Structures and Algorithms", "https://www.youtube.com/watch?v=8hly31xKli0");
        videoData.put("Microservices - An architectural style that structures an application as a collection of loosely coupled services.", "https://www.youtube.com/watch?v=CZ3wIuvmHeM");
        videoData.put("API - Interface that allows software applications to communicate with each other.", "https://www.youtube.com/watch?v=s7wmiS2mSXY");
        videoData.put("Debugging Java Applications", "https://www.youtube.com/watch?v=0xQQ2Tb5S3c");
        videoData.put("Tomcat - An open-source Java servlet container developed by the Apache Software Foundation.", "https://www.youtube.com/watch?v=9bV2zXtLdOE");
        // Add more video links as desired...
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Start HTML document
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Search Results</title>");
        out.println("<style>");
        out.println("  body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f9f9f9; margin: 20px auto; max-width: 900px; color: #333; }");
        out.println("  h2 { text-align: center; color: #2c3e50; margin-bottom: 30px; }");
        out.println("  h3 { color: #34495e; margin-bottom: 20px; font-weight: 600; }");
        out.println("  ul { list-style: none; padding-left: 0; }");
        out.println("  ul li { background: #fff; border: 1px solid #ddd; margin-bottom: 12px; padding: 12px 18px; border-radius: 6px; transition: box-shadow 0.3s ease; display: flex; justify-content: space-between; align-items: center; }");
        out.println("  ul li:hover { box-shadow: 0 4px 10px rgba(0,0,0,0.1); }");
        out.println("  ul li a { text-decoration: none; color: #2980b9; font-weight: 500; }");
        out.println("  ul li a:hover { text-decoration: underline; color: #1c5980; }");
        out.println("  .video-link { font-size: 0.9rem; color: #e74c3c; margin-left: 15px; font-weight: 600; white-space: nowrap; }");
        out.println("  .video-link:hover { text-decoration: underline; }");
        out.println("  p { font-size: 1.1rem; color: #666; margin-top: 20px; }");
        out.println("  a.back-link { display: inline-block; margin-top: 30px; padding: 10px 16px; background-color: #2980b9; color: white; border-radius: 5px; text-decoration: none; font-weight: 600; }");
        out.println("  a.back-link:hover { background-color: #1c5980; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h2>Search Results</h2>");

        if (query == null || query.trim().isEmpty()) {
            out.println("<p>Please enter a search term.</p>");
        } else {
            List<String> results = search(query.trim());

            out.println("<h3>Results for: <em>" + escapeHtml(query) + "</em></h3>");

            if (results.isEmpty()) {
                out.println("<p>No results found.</p>");
            } else {
                out.println("<ul>");
                for (String title : results) {
                    String url = data.get(title);
                    String videoUrl = videoData.get(title);

                    // Highlight the matched query (case-insensitive) in the title
                    String highlightedTitle = escapeHtml(title).replaceAll("(?i)(" + Pattern.quote(query) + ")", "<mark>$1</mark>");

                    out.print("<li>");
                    out.print("<a href=\"" + url + "\" target=\"_blank\" rel=\"noopener noreferrer\">" + highlightedTitle + "</a>");
                    if (videoUrl != null) {
                        out.print("<a class='video-link' href=\"" + videoUrl + "\" target=\"_blank\" rel=\"noopener noreferrer\">Video Tutorial ▶</a>");
                    }
                    out.println("</li>");
                }

                out.println("</ul>");
            }
        }

        out.println("<a class='back-link' href='index.jsp'>Back to Search</a>");
        out.println("</body></html>");
    }

    private List<String> search(String query) {
        List<String> matched = new ArrayList<>();
        for (String title : data.keySet()) {
            if (title.toLowerCase().contains(query.toLowerCase())) {
                matched.add(title);
            }
        }
        return matched;
    }

    // Simple method to escape HTML entities in strings (to avoid XSS)
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
