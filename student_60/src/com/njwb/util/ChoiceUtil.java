package com.njwb.util;

import java.util.Scanner;
import java.util.Set;

public class ChoiceUtil {
	/**
	 * 规范化获取选项 （整数，0-给定范围）
	 * 
	 * @param sc
	 * @param range
	 * @return
	 */
	public static int getRangeChoice(Scanner sc, int range) {
		int choice = 0;
		boolean flag = true;
		do {
			try {
				do {
					choice = sc.nextInt();
					if (choice > 0 && choice < range + 1) {
						flag = false;
					} else {
						System.out.println("选择错误！无此选项，请重新选择：");
					}
				} while (flag == true);
			} catch (Exception e) {
				System.out.println("输入错误！请重新输入正确的选项：");
				sc.next();
			}
		} while (flag == true);
		return choice;
	}

	
	
	/**
	 * 规范化获取选项 （指定集合之内）
	 * 
	 * @param sc
	 * @param choices
	 * @return
	 */
	public static int getRangeChoice(Scanner sc, Set<Integer> choices) {
		int choice = 0;
		boolean flag = true;
		do {
			try {
				do {
					choice = sc.nextInt();
					if (choices.contains(choice) == true) {
						flag = false;
					} else {
						System.out.println("无此选项，请重新输入:");
					}
				} while (flag == true);
			} catch (Exception e) {
				System.out.println("输入格式有误,重新输入：");
				sc.next();
			}

		} while (flag ==true);
		return choice;
	}
	
	
}
