package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AccountDAO {

    /**
     * 
     * @param Account account
     * @return an Account object with its generated autoincrement user id, null otherwise
     */
    public Account insertAccount(Account account){
        Connection connectionObject = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES (?, ?)";
            //make sure you use the proper prepareStatement function, with the Statement.RETURN_GENERATED_KEYS flag
            PreparedStatement preparedStatement = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

            //preparedStatement's setString methods to apply to sql statement
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate(); 
            // preparedStatement.execute();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            /*
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            int generatedAccountId = (int)pkeyResultSet.getLong(1);
            return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            */
            if(pkeyResultSet.next()){
                int generatedAccountId = (int)pkeyResultSet.getLong(1);
                // int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }
            // return account;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Finds an account in the database, given a username
     * @param String username
     * @return Account object with that username, null otherwise
     */
    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
