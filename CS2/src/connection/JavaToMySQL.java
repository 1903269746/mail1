package connection;//connection.JavaToMsSQL.java
import service.AddContacts;
import service.CheckContacts;
import service.DeleteContacts;
import service.UpdateContacts;

import java.sql.*;
import java.util.Scanner;

public class JavaToMySQL {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("");
        System.out.println("****欢迎来到个人通讯录系统:1.查看通讯录 2.增加联系人 3.删除联系人 4.修改联系人 5.结束****");
        Scanner in=new Scanner( System.in);
        int x=in.nextInt();
        for(;x!=5;){
            if(x==1){
                CheckContacts ch=new CheckContacts();
                CheckContacts.show();
            }
            if(x==2){
                AddContacts jia=new AddContacts();
                jia.ad();
            }
            if(x==3){
                DeleteContacts shan=new DeleteContacts();
                shan.de();
            }
            if(x==4){
                UpdateContacts Change=new UpdateContacts();
                Change.ch();
            }
            System.out.println("****欢迎来到个人通讯录系统:1.查看通讯录 2.增加联系人 3.删除联系人 4.修改联系人 5.结束****");
            x=in.nextInt();
        }
        in.close();
        System.out.println("已退出通讯录");



    }
}

