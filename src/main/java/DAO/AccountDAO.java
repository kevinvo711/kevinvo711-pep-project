package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountDAO {
    public Account insertAccount(Account account){
        Connection connectionObject = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connectionObject.prepareStatement(sql);

            //preparedStatement's setString methods to apply to sql statement
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate(); 
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                // int generatedAccountId = (int)pkeyResultSet.getLong();
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }
            // return account;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
