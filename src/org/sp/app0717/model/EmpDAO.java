package org.sp.app0717.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//사원과 관련된 데이터를 처리하는 점담객체
//DAO(Data Access Object)- 어플리케이션 설계 분야의 용어
//Create(=insert),Read(=select),U(=update), D(=delete) 를 수행하는 객체를 가리킴
public class EmpDAO {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";
	
	//Emp의 레코드 모두 가져오기
	//ResultSet을 반환하지 않고 배열을 반환하는 이유는?
	//Connection, PreparedStatement, ResultSet은 사용 후
	//반드시 닫아야 하므로 finally에서 rs가 닫히기 전에 rs를 그대로
	//표현해 놓은 배열에 옮겨심은 후 닫기 위함
	public String[][] selectAll() {
		Connection con=null;//접속 정보를 가진 객체
		PreparedStatement pstmt=null;//쿼리수행 객체
		ResultSet rs=null;//표를 표현한 객체
		String[][] data=null;//rs 대체자
		
		//1)드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			
			//2)접속
			con=DriverManager.getConnection(url, user, pass);
			
			if(con==null) {
				System.out.println("접속 실패");
			}else {
				System.out.println("접속 성공");
				
				//3)쿼리 수행
				String sql="select * from emp order by empno asc";
				pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs=pstmt.executeQuery();//select문의 경우
				
				//이차원 배열 생성하기 위한 레코드수 조사
				rs.last();
				int total=rs.getRow();
				data=new String[total][8];
				
				
				rs.beforeFirst();//커서 원상복귀
				//rs를 닫기 전에 여기서 배열에 옮겨심자
				int index=0;
				
				while(rs.next()) {//레코드가 있는 동안
					data[index][0]=Integer.toString(rs.getInt("empno"));
					data[index][1]=rs.getString("ename");
					data[index][2]=rs.getString("job");
					data[index][3]=Integer.toString(rs.getInt("mgr"));
					data[index][4]=rs.getString("hiredate");
					data[index][5]=Integer.toString(rs.getInt("sal"));
					data[index][6]=Integer.toString(rs.getInt("comm"));
					data[index][7]=Integer.toString(rs.getInt("deptno"));
					index++;
				}

				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
		
		//4)db관련 자원 해제
	}
}
