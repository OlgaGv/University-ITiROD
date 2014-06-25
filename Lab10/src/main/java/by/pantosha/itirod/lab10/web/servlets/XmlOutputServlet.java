package by.pantosha.itirod.lab10.web.servlets;

import by.pantosha.itirod.lab10.xml.XmlTaskWriter;
import by.pantosha.itirod.lab5.repository.SqlTaskRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;

// a. Написать сервлет, который выдаёт список объектов в форме XML (/output/xml)
@WebServlet(name = "XmlOutput", urlPatterns = "/output/*")
public class XmlOutputServlet extends HttpServlet {

    @Override
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        SqlTaskRepository taskRepository = new SqlTaskRepository("jdbc:mysql://localhost:3306/test");
        XmlTaskWriter xmlTaskWriter = new XmlTaskWriter(writer);
        try {
            xmlTaskWriter.write(taskRepository.readAll());
        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }
}