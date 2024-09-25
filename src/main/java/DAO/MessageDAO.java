package DAO;

import java.sql.*;
import java.util.*;

import Model.Message;
import Util.*;

public class MessageDAO {
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message(posted_by,message_text,time_posted_epoch) values (?,?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }  

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    

    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessageByUser(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message deletedMessage = null;
        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement selectPrepStatement = connection.prepareStatement(sql);

            selectPrepStatement.setInt(1, message_id);
            ResultSet rs = selectPrepStatement.executeQuery();
            while(rs.next()){
                deletedMessage = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                
            }
            if (deletedMessage!=null) {
                String sql2 = "delete from message where message_id = ?";
                PreparedStatement deletePrepStatement = connection.prepareStatement(sql2);

                deletePrepStatement.setInt(1, message_id);
                deletePrepStatement.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return deletedMessage;
    }

    public Message updateMessageText(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text=? where message_id = ?";
            PreparedStatement updatePrepStatement = connection.prepareStatement(sql);
            updatePrepStatement.setString(1, message_text);
            updatePrepStatement.setInt(2, message_id);
            int updated=  updatePrepStatement.executeUpdate();
            
            if (updated>0) {
                String sql2 = "select * from message where message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
    
                preparedStatement.setInt(1, message_id);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getInt("time_posted_epoch"));
                    return message;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
