package client;
import server.JDBCUtil;
import pojo.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View {


    private JFrame frame;
    private  JTextField tx_id;
    private JTextField tx_name;
    private JTextField tx_address;
    private JTextField tx_phone;
    private final JTable table = new JTable();
    private JDBCUtil jdbc = new JDBCUtil();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    View window = new View();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public View() {
        initialize();
    }

    private Object[] headTitle = {"编号","姓名", "电话", "地址"};
    private DefaultTableModel dtm = null;


    public Object[][] makeTable() {
        List<Person> list = jdbc.queryUserInfo();
        Object[][] data = new Object[list.size()+1][4];
        data[0][0] = "编号";
        data[0][1] = "姓名";
        data[0][2] = "电话";
        data[0][3] = "地址";
        for (int i = 1; i <= list.size(); i++) {
            data[i][0] = list.get(i-1).getId();
            data[i][1] = list.get(i-1).getName();
            data[i][2] = list.get(i-1).getPhone();
            data[i][3] = list.get(i-1).getAddress();
        }
        return data;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("个人通讯录系统");
        ImageIcon bg = new ImageIcon("D:\\workspace\\CS体系结构\\CS体系结构\\src\\main\\resources\\1.jpg");

        JLabel label= new JLabel(bg);
        label.setSize(bg.getIconWidth(),bg.getIconHeight());
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        JPanel pan = (JPanel)frame.getContentPane();
        pan.setOpaque(false);
        pan.setLayout(new FlowLayout());
        frame.setBounds(100, 100, 850, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);


        JButton btnNewButton = new JButton("添加联系人");
        btnNewButton.setBounds(14, 270, 130, 30);
        frame.getContentPane().add(btnNewButton);


        JButton btnNewButton_1 = new JButton("删除联系人");
        btnNewButton_1.setBounds(14, 310, 130, 30);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("更新联系人");
        btnNewButton_2.setBounds(14, 350, 130, 30);
        frame.getContentPane().add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("查询联系人");
        btnNewButton_3.setBounds(14,390,130,30);
        frame.getContentPane().add(btnNewButton_3);

        JButton btn_refresh = new JButton("刷新通讯录");
        btn_refresh.setBounds(14, 430, 130, 30);
        frame.getContentPane().add(btn_refresh);
        btn_refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dtm = new DefaultTableModel(makeTable(), headTitle);
                table.removeAll();
                table.setModel(dtm);
                table.setOpaque(false);
            }
        });
        tx_id = new JTextField();
        tx_id.setBounds(53, 23, 113, 24);
        frame.getContentPane().add(tx_id);
        tx_id.setColumns(10);

        tx_name = new JTextField();
        tx_name.setBounds(53, 71, 113, 24);
        frame.getContentPane().add(tx_name);
        tx_name.setColumns(10);

        tx_phone = new JTextField();
        tx_phone.setBounds(53, 125, 113, 24);
        frame.getContentPane().add(tx_phone);
        tx_phone.setColumns(10);

        tx_address = new JTextField();
        tx_address.setBounds(53, 186, 113, 24);
        frame.getContentPane().add(tx_address);
        tx_address.setColumns(10);

        JLabel lblNewLabel = new JLabel("编号");
        lblNewLabel.setBounds(10, 26, 72, 18);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("姓名");
        lblNewLabel_1.setBounds(10, 74, 72, 18);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("电话");
        lblNewLabel_2.setBounds(10, 128, 72, 18);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("地址");
        lblNewLabel_3.setBounds(10, 189, 72, 18);
        frame.getContentPane().add(lblNewLabel_3);

        table.setBounds(211, 13, 491, 467);
        frame.getContentPane().add(table);

        dtm = new DefaultTableModel(makeTable(),headTitle);
        table.setModel(dtm);
        table.setOpaque(false);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table.getSelectedRow();
                if (index > 0) {
                    tx_id.setText(table.getValueAt(index, 0).toString());
                    tx_name.setText(table.getValueAt(index, 1).toString());
                    tx_phone.setText(table.getValueAt(index, 2).toString());
                    tx_address.setText(table.getValueAt(index, 3).toString());
                }
            }
        });

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Person contactPerson = new Person(Integer.parseInt(tx_id.getText()),tx_name.getText(),tx_phone.getText(),tx_address.getText());
                boolean result = jdbc.addUserInfo(contactPerson);
                if (result) {
                    JOptionPane.showMessageDialog(null, "添加联系人成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                    btn_refresh.doClick();
                    tx_id.setText("");
                    tx_name.setText("");
                    tx_address.setText("");
                    tx_phone.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "添加联系人失败!", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean result = jdbc.deleteUserInfo(Integer.valueOf(tx_id.getText()));
                if (result) {
                    JOptionPane.showMessageDialog(null, "删除联系人成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                    btn_refresh.doClick();
                    tx_id.setText("");
                    tx_name.setText("");
                    tx_address.setText("");
                    tx_phone.setText("");

                } else {
                    JOptionPane.showMessageDialog(null, "删除联系人失败!", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Person contactPerson = new Person(Integer.valueOf(tx_id.getText()),tx_name.getText(),tx_phone.getText(),tx_address.getText());
                boolean result = jdbc.modifyUserInfo(contactPerson);
                if (result) {
                    JOptionPane.showMessageDialog(null, "修改联系人成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                    btn_refresh.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "修改联系人失败!", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name1=tx_name.getText();
                List<Person> list = jdbc.SelectInfo(name1);
                Object[][] data = new Object[list.size()+1][4];
                data[0][0] = "编号";
                data[0][1] = "姓名";
                data[0][2] = "电话";
                data[0][3] = "地址";
                for (int i = 1; i <= list.size(); i++) {
                    data[i][0] = list.get(i-1).getId();
                    data[i][1] = list.get(i-1).getName();
                    data[i][2] = list.get(i-1).getPhone();
                    data[i][3] = list.get(i-1).getAddress();
                }
                dtm=new DefaultTableModel(data,headTitle);
                table.removeAll();
                table.setModel(dtm);
            }
        });
    }
}