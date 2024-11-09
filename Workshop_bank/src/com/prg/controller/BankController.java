package com.prg.controller;
import com.org.service.*;
import java.sql.SQLException;
import java.util.Scanner;

import com.org.model.Bank;

public class BankController {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		Bank n = new Bank();
		service sn = new ServiceImpl();
		try {
			n.db_connection();
			System.out.println("connection successful...");
			boolean exe = true;
			while(exe)
			{
			
			System.out.println("1. Create Account");
			System.out.println("2. View Account");
			System.out.println("3. Update Account Information");
			System.out.println("4. Deposit Amount");
			System.out.println("5. Withdraw Amount");
			System.out.println("6. Transaction Amount");
			System.out.println("7. View Transaction");
			System.out.println("8. Exit");
			int input = sc.nextInt();
			switch(input)
			{
			case 1:
				sn.createaccount();
				System.out.println("1");
				break;
			case 2:
				sn.viewaccount();
				System.out.println("2");
				break;
			case 3:
				sn.updateaccount();
				System.out.println("3");
				break;
			case 4:
				sn.deposit();
				System.out.println("4");
				break;
			case 5:
				sn.withdraw();
				System.out.println("5");
				break;
			case 6:
				sn.amount_trans();
				System.out.println("6");
				break;
			case 7:
				sn.view_trans();
				System.out.println("7");
				break;
			case 8:
				exe = false;
				System.out.println("8");
				break;
			default:
				System.out.println("Invalid choice");
				break;
			}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
