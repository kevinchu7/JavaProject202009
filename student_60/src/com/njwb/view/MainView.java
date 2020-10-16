package com.njwb.view;

import java.util.Scanner;

import com.njwb.factory.ObjectFactory;
import com.njwb.util.ChoiceUtil;
import com.njwb.view.UserView;;

/**系统主界面
 * @author  朱凯
 * No.60
 */
public class MainView {
	private static Scanner scanner=new Scanner(System.in);
	

	public void start(){
		mainView();
	}
	
	private void mainView(){
		int ch;
		while(true){
			System.out.println("*****航空订票系统******");
			System.out.println("1.航班查询");
			System.out.println("2.用户服务");
			System.out.println("3.管理员入口");
			System.out.println("4.退出系统");
			System.out.println("请选择：");
			
			ch=ChoiceUtil.getRangeChoice(scanner, 4);
			switch(ch){
			case 1:
				QueryView queryView=(QueryView)ObjectFactory.getObject("queryView");
				queryView.start();
				break;
			case 2:
				UserView userView=(UserView)ObjectFactory.getObject("userView");
				userView.start();
				break;
				
			case 3:
				AdminView adminView=(AdminView)ObjectFactory.getObject("adminView");
				adminView.start();
				break;
			case 4:
				scanner.close();
				System.out.println("感谢使用,再见！");
				return;
			}
		}
	}

}
