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

import main.java.dao.MessageActionDaoImpl;
import org.json.simple.parser.ParseException;

import org.apache.log4j.Logger;

import main.java.MessageExchange.MessageExchange;
import main.java.Message.Message;

import main.java.dao.MessageActionDao;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet{
    private static Logger logger=Logger.getLogger(ChatServlet.class.getName());
    private MessageActionDao MADao;

    @Override
    public void init() throws ServletException{
        this.MADao=new MessageActionDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doGet.");
        String token = request.getParameter("token");
        logger.info("Token " + token);

        if (token != null && !"".equals(token)) {
            int indexMessages = MessageExchange.getIndexMessages(token);
            int indexActions = MessageExchange.getIndexActions(token);
            String serverResponse=MessageExchange.getResponse(indexMessages,indexActions,this.MADao);
            System.out.println(serverResponse);

            response.setContentType("application/json");

            response.setStatus((HttpServletResponse.SC_OK));
            PrintWriter out = response.getWriter();
            out.print(serverResponse);
            out.flush();
            logger.info("End of doGet.");
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
            this.MADao.addMessage(message);
            response.setStatus(HttpServletResponse.SC_OK);
            logger.info("End of doPost.");
        } catch (ParseException  e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        logger.info("doPut");

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String data = sb.toString();

        logger.info(data);

        try {
            Message action = MessageExchange.getClientMessage(data);
            this.MADao.addAction(action);
            response.setStatus(HttpServletResponse.SC_OK);
            logger.info("End of doPut.");
        } catch (ParseException  e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        logger.info("doDelete");

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String data = sb.toString();

        logger.info(data);

        try {
            Message action = MessageExchange.getClientMessage(data);
            this.MADao.addAction(action);
            response.setStatus(HttpServletResponse.SC_OK);
            logger.info("End of doDelete.");
        } catch (ParseException  e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}