package DAO;
import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account(username,password) values (?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername() );
            preparedStatement.setString(2, account.getPassword() );
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){               
                return new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // public boolean accountUsernameExist(String username){
    //     Connection connection = ConnectionUtil.getConnection();
    //     try {
    //         String sql = "select count(*) as r from account where username = ?";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //         preparedStatement.setString(1, username);
    //         ResultSet rs = preparedStatement.executeQuery();
    //         while(rs.next()){
    //             int value= rs.getInt("r");
    //             boolean response = value > 0;
    //             return response;
    //         }
    //     }catch(SQLException e){
    //         System.out.println(e.getMessage());
    //     }
    //     return false;
    // }




}
