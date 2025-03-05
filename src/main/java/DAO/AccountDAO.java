package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;
import kotlin.Result;

public class AccountDAO {
    
    public List<Account> getAllAccounts() {
        
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<Account>();

        try {
            String sql = "Select * From account";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet pkeyRS = ps.getGeneratedKeys();
            if (pkeyRS.next()) {
                int generated_account_id = (int) pkeyRS.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsernameAndPassword(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                System.out.println("Account Found. ID = " + account.getAccount_id());
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Account Found.");
        }
        return null;
    }
}
