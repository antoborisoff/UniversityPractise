package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import main.java.db.ConnectionManager;
import main.java.Message.Message;

public class MessageActionDaoImpl implements MessageActionDao {
    private static Logger logger = Logger.getLogger(MessageActionDaoImpl.class.getName());

    @Override
    public void addMessage(Message message){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO messages (id, messagetext,username,idClient) VALUES (?, ?, ?,?)");
            preparedStatement.setString(1, message.getId());
            preparedStatement.setString(2, message.getMessageText());
            preparedStatement.setString(3, message.getUsername());
            preparedStatement.setString(4, message.getIDClient());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    @Override
    public void addAction(Message action){
        Connection connection = null;
        Statement statement=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        int lastindex=0;
        try {
            connection = ConnectionManager.getConnection();
            if(action.getIsDeleteAction().equals("true")) {
                preparedStatement = connection.prepareStatement("INSERT INTO DEL (id) VALUES (?)");
                preparedStatement.setString(1, action.getId());
                preparedStatement.executeUpdate();

                statement=connection.createStatement();
                resultSet= statement.executeQuery("SELECT PID FROM DEL ORDER BY PID DESC LIMIT 1");
                resultSet.next();
                lastindex=resultSet.getInt("PID");

                preparedStatement = connection.prepareStatement("INSERT INTO actions (actiontype,id) VALUES (?,?)");
                preparedStatement.setString(1, "delete");
                preparedStatement.setInt(2, lastindex);
                preparedStatement.executeUpdate();
            }
            else{
                preparedStatement = connection.prepareStatement("INSERT INTO PUT (id,messagetext) VALUES (?,?)");
                preparedStatement.setString(1, action.getId());
                preparedStatement.setString(2, action.getMessageText());
                preparedStatement.executeUpdate();

                statement=connection.createStatement();
                resultSet = statement.executeQuery("SELECT PID FROM PUT ORDER BY PID DESC LIMIT 1");
                resultSet.next();
                lastindex = resultSet.getInt("PID");

                preparedStatement = connection.prepareStatement("INSERT INTO actions (actiontype,id) VALUES (?,?)");
                preparedStatement.setString(1, "put");
                preparedStatement.setInt(2, lastindex);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    @Override
    public List<Message> selectAllMessagesAfterIndex(int index){
        List<Message> messages = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            pstatement=connection.prepareStatement("SELECT * FROM messages WHERE PID>=(?)");
            pstatement.setInt(1,index+1);
            if(pstatement.execute()) {
                resultSet = pstatement.getResultSet();
            }
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String messagetext = resultSet.getString("messagetext");
                String username = resultSet.getString("username");
                String idClient = resultSet.getString("idClient");
                messages.add(new Message(id, messagetext, username, idClient, "false"));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (pstatement != null) {
                try {
                    pstatement.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return messages;
    }

    @Override
    public List<Message> selectAllActionsAfterIndex(int index){
        List<Message> actions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetForAction=null;
        String id = null;
        String messagetext = null;
        String username = null;
        String idClient = null;

        try {
            connection = ConnectionManager.getConnection();
            pstatement=connection.prepareStatement("SELECT * FROM actions WHERE PID>=(?)");
            pstatement.setInt(1, index + 1);
            if(pstatement.execute()){
                resultSet=pstatement.getResultSet();
            }
            while (resultSet.next()) {
                if(resultSet.getString("actiontype").equals("delete")) {
                    pstatement = connection.prepareStatement("SELECT * FROM DEL WHERE PID=(?)");
                    pstatement.setInt(1,resultSet.getInt("id"));
                    if(pstatement.execute()){
                        resultSetForAction=pstatement.getResultSet();
                    }
                    resultSetForAction.next();
                    id = resultSetForAction.getString("id");
                    System.out.println(id);
                    messagetext = null;
                    username = null;
                    idClient = null;
                    actions.add(new Message(id,messagetext,username,idClient,"true"));
                }
                else{
                    pstatement = connection.prepareStatement("SELECT * FROM PUT WHERE PID=(?)");
                    pstatement.setInt(1, resultSet.getInt("id"));
                    if(pstatement.execute()){
                        resultSetForAction=pstatement.getResultSet();
                    }
                    resultSetForAction.next();
                    id = resultSetForAction.getString("id");
                    System.out.println(id);
                    messagetext = resultSetForAction.getString("messagetext");
                    username = null;
                    idClient = null;
                    actions.add(new Message(id,messagetext,username,idClient,"false"));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (pstatement != null) {
                try {
                    pstatement.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return actions;
    }

}
