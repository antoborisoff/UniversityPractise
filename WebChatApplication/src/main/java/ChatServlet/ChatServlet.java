package main.java.ChatServlet;

import java.lang.Override;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

import org.json.simple.parser.ParseException;

import org.apache.log4j.Logger;

import main.java.MessageExchange.MessageExchange;
import main.java.Message.Message;
import main.java.Storage.MessageAndActionStorage;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet{
    private static Logger logger=Logger.getLogger(ChatServlet.class.getName());

    @Override
    public void init() throws ServletException{

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doGet");
        String token = request.getParameter("token");
        logger.info("Token " + token);

        if (token != null && !"".equals(token)) {
            int indexMessages = MessageExchange.getIndexMessages(token);
            int indexActions = MessageExchange.getIndexActions(token);
            String serverResponse=MessageExchange.getResponse(indexMessages,indexActions);

            response.setContentType("application/json");

            response.setStatus((HttpServletResponse.SC_OK));
            PrintWriter out = response.getWriter();
            out.print(serverResponse);
            out.flush();
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'token' parameter needed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPost");

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String data = sb.toString();

        logger.info(data);

        try {
            Message message = MessageExchange.getClientMessage(data);
            MessageAndActionStorage.addMessage(message);
            //XMLHistoryUtil.addData(task);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (ParseException /*| ParserConfigurationException | SAXException | TransformerException*/ e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}