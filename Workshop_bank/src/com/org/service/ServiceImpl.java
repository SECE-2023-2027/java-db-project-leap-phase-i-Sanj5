package com.org.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Timestamp;

import com.org.model.Bank;

public class ServiceImpl implements service{
	private static Scanner sc = new Scanner(System.in);
	public void createaccount()
	{
		try {
			
		Connection conn = Bank.db_connection();
		System.out.println("Enter customer id : ");
		int c_id = sc.nextInt();
		
		System.out.println("Enter account type (1. Savings  2. Current )");
		int id = sc.nextInt();
		String type;
		if(id == 1)
		{
			type = "savings";
		}else {
			type = "current";
		}
		System.out.println("Enter amount: ");
		double balance = sc.nextDouble();
		
		System.out.println("Enter your address: ");
		String address = sc.next();
		
		System.out.println("Enter mobile number: ");
		String mobile = sc.next();
		
		String sql = "insert into account (customer_id, account_type, balance, address, mobile) values(?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, c_id);
		pstmt.setString(2, type);
		pstmt.setDouble(3, balance);
		pstmt.setString(4, address);
		pstmt.setString(5, mobile);
		pstmt.executeUpdate();
		String query = "SELECT account_id from account where customer_id = ? and account_type = ?";
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1,  c_id);
		pstmt.setString(2,  type);
		ResultSet rs = pstmt.executeQuery();
		int accountID= rs.next() ? rs.getInt("account_id") : 0;

		if(type.equals("savings"))
		{
			String query2 = "INSERT INTO savings_account(account_id, interest_rate) values(?, ?)";
			pstmt = conn.prepareStatement(query2);
			pstmt.setInt(1,  accountID);
			pstmt.setDouble(2,  0.05);
			pstmt.executeUpdate();
		}else {
			query = "INSERT INTO current_account(account_id, overdraft_limit) values(?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  accountID);
			pstmt.setDouble(2,  1000.0);
			pstmt.executeUpdate();
			
		}
		System.out.println("Data inserted successfully");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Override
	public void viewaccount() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Enter account id:");
			int a_id = sc.nextInt();
			Connection conn = Bank.db_connection();
			String sql = "SELECT * from account where account_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  a_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				System.out.println("Account id: "+rs.getInt("account_id"));
				System.out.println("Customer id: "+rs.getInt("customer_id"));
				System.out.println("Balance: "+rs.getDouble("balance"));
				System.out.println("Account_Type: "+rs.getString("account_type"));
			}
			System.out.println();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateaccount() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Enter account id: ");
			int acc_id = sc.nextInt();
			System.out.println("Enter adrress: ");
			String addr = sc.next();
			System.out.println("Enter mobile number: ");
			String mob = sc.next();
			Connection conn = Bank.db_connection();
			String sql = "UPDATE account set address = ?, mobile = ? WHERE account_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(3,  acc_id);
			pstmt.setString(1,  addr);
			pstmt.setString(2,  mob);
			pstmt.executeUpdate();
			System.out.println("Account details updated successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void withdraw() {
		// TODO Auto-generated method stub
		try
		{
			Connection conn = Bank.db_connection();
			System.out.println("Enter account id:");
			int a_id = sc.nextInt();
			System.out.println("Enter withdraw amount:");
			double amount = sc.nextDouble();
			
			String query = "SELECT * from account where account_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, a_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				String account = rs.getString("account_type");
				if(!account.equals("Current"))
				{
					System.out.println(account);
					double balance = rs.getDouble("balance");
					if(balance >= amount)
					{
						query = "INSERT into transaction (account_id, transaction_type, amount) values(?, ?, ?)";
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, a_id);
						pstmt.setString(2,  "Withdrawal");
						pstmt.setDouble(3,  amount);
						pstmt.executeUpdate();
						
						query = "UPDATE account set balance = balance - ? where account_id = ?";
						PreparedStatement pstmt1 = conn.prepareStatement(query);
						pstmt1.setDouble(1, amount);
						pstmt1.setInt(2,  a_id);
						pstmt1.executeUpdate();
						System.out.println("Withdrawal successful");

						
					}
					else {
						System.out.println("Insufficient Balance");
					}
					
				}
				else
				{
					System.out.println(account);
					double balance = rs.getDouble("balance");
					double minimumBalance = 1000.0; // Minimum balance requirement for a current account (example)

				    // Check if balance is sufficient
				    if(balance - amount >= minimumBalance) {
				        // Proceed with the transaction if the balance after withdrawal is above the minimum balance
				        query = "INSERT into transaction (account_id, transaction_type, amount) values(?, ?, ?)";
				        pstmt = conn.prepareStatement(query);
				        pstmt.setInt(1, a_id);
				        pstmt.setString(2, "Withdrawal");
				        pstmt.setDouble(3, amount);
				        pstmt.executeUpdate();

				        query = "UPDATE account set balance = balance - ? where account_id = ?";
				        PreparedStatement pstmt1 = conn.prepareStatement(query);
				        pstmt1.setDouble(1, amount);
				        pstmt1.setInt(2, a_id);
				        pstmt1.executeUpdate();
				        System.out.println("Withdrawal successful from Current Account");

				    } else {
				        System.out.println("Insufficient balance. You must maintain a minimum balance of " + minimumBalance);
				    }
				    rs.close();

				}
				
			}
			
						
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void deposit() {
		// TODO Auto-generated method stub
		try
		{
			Connection conn = Bank.db_connection();
			System.out.println("Enter account id:");
			int a_id = sc.nextInt();
			System.out.println("Enter deposit amount:");
			double amount = sc.nextDouble();
			
			String query = "INSERT into transaction(account_id, transaction_type,amount) values(?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, a_id);
			pstmt.setString(2,  "Deposit");
			pstmt.setDouble(3,  amount);
			pstmt.executeUpdate();
			
			query = "UPDATE account set balance = balance + ? where account_id = ?";
			PreparedStatement pstmt1 = conn.prepareStatement(query);
			pstmt1.setDouble(1, amount);
			pstmt1.setInt(2,  a_id);
			pstmt1.executeUpdate();
			
			System.out.println("Deposit successful");
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void amount_trans() {
		// TODO Auto-generated method stub
		try
		{
			Connection conn = Bank.db_connection();
			System.out.println("Enter source account id: ");
			int source_id = sc.nextInt();
			
			System.out.println("Enter destination accound id: ");
			int dest_id = sc.nextInt();
			
			System.out.println("Enter transfer amount: ");
			double amount = sc.nextDouble();
			
			String query = "SELECT balance from account where account_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, source_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				double balance = rs.getDouble("balance");
				if(balance >= amount)
				{
					query = "INSERT INTO transaction(account_id, transaction_type, amount) values(?, ?, ?)";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, source_id);
					pstmt.setString(2,  "Transfer");
					pstmt.setDouble(3,  amount);
					pstmt.executeUpdate();
					
					query = "UPDATE account set balance = balance - ? where account_id = ?";
					pstmt = conn.prepareStatement(query);
					pstmt.setDouble(1,  amount);
					pstmt.setDouble(2, source_id);
					pstmt.executeUpdate();
					
					query = "INSERT INTO transaction(account_id, transaction_type, amount) values(?, ?, ?)";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1,dest_id);
					pstmt.setString(2,  "Transfer");
					pstmt.setDouble(3,  amount);
					pstmt.executeUpdate();
					
					try(FileWriter writer = new FileWriter("C:\\Users\\sanja\\eclipse-workspace\\Workshop_bank\\Transactionamount.txt", true);
							BufferedWriter bw = new BufferedWriter(writer))
					{
						String log = "Account ID: " + dest_id + ", Type: " + "Transfer" + ", Amount: " + amount + ", Date: " + new Timestamp(System.currentTimeMillis());
						bw.write(log);
						bw.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					query = "UPDATE account set balance = balance + ? where account_id = ?";
					pstmt = conn.prepareStatement(query);
					pstmt.setDouble(1,  amount);
					pstmt.setDouble(2, dest_id);
					pstmt.executeUpdate();
					
					System.out.println("Transfer successfull!");
					
				}else {
					System.out.println("Insufficient Balance!");
				}
			}else {
				System.out.println("Account not found");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void view_trans() {
		// TODO Auto-generated method stub
		try {
			Connection conn = Bank.db_connection();
			System.out.println("Enter account id:");
			int a_id = sc.nextInt();
			
			String query = "SELECT * FROM transaction where account_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  a_id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("Transaction ID: "+rs.getInt("transaction_id"));
				System.out.println("Account ID: "+rs.getInt("account_id"));
				System.out.println("Transaction Type: "+rs.getString("transaction_type"));
				System.out.println("Amount: "+rs.getDouble("amount"));
			}
			rs.close();
		
	}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
