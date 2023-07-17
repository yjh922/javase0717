package org.sp.app0717.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.sp.app0717.controller.DeptTableModel;
import org.sp.app0717.controller.EmpTableModel;

public class TableApp extends JFrame implements ActionListener{
	JPanel p_north;
	JButton bt_emp;//사원 가져오기
	JButton bt_dept;//부서 가져오기
	JTable table;
	JScrollPane scroll;
	EmpTableModel model;
	DeptTableModel model2;
	
	public TableApp() {
		p_north = new JPanel();
		bt_emp = new JButton("사원");
		bt_dept = new JButton("부서");
		
		//디자인과 로직 및 데이터를 분리시켜 개발하기 위한 MVC 개발 패턴을 적용한
		//Table의 생성자에서 지원하는 TableModel 이용해보기
		model = new EmpTableModel();
		model2 = new DeptTableModel();
		
		table = new JTable();
		scroll = new JScrollPane(table);
		
		p_north.add(bt_emp);
		p_north.add(bt_dept);
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt_emp.addActionListener(this);
		bt_dept.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_emp) {//사원 출력
			table.setModel(model);
		}else if(obj==bt_dept) {//부서 출력
			table.setModel(model2);
		}
	}
	
	public static void main(String[] args) {
		new TableApp();
	}
}
