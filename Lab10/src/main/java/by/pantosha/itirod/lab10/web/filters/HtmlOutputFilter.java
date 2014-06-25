package by.pantosha.itirod.lab10.web.filters;

import by.pantosha.itirod.lab10.web.wrappers.CharResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@WebFilter(filterName = "HtmlOutput", urlPatterns = "/output/html")
public class HtmlOutputFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        String stylePath = filterConfig.getServletContext().getRealPath("/xml/TasksToHtml.xsl");
        Source styleSource = new StreamSource(stylePath);

        PrintWriter writer = servletResponse.getWriter();
        CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) servletResponse);
        chain.doFilter(servletRequest, wrapper);

        // Get response from servlet
        Source xmlSource = new StreamSource(new StringReader(wrapper.toString()));

        try {
            CharArrayWriter caw = new CharArrayWriter();
            StreamResult result = new StreamResult(caw);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(styleSource);
            transformer.transform(xmlSource, result);
            servletResponse.setContentLength(caw.toString().length());
            writer.write(caw.toString());
        } catch (Exception ex) {
            writer.println(ex.toString());
            writer.write(wrapper.toString());
        }
    }

    @Override
    public void destroy() {

    }
}
