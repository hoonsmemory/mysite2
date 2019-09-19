package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;


public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.1.63:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "123123");
		
		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}
		
		return connection;
	}
	
	public Boolean wirte(BoardVo vo) {
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = " insert into board values(null, ?, ?, 0, now(), (SELECT IFNULL(MAX(b.g_no) + 1, 1) from board b), 1, 0, ?,'y')";
			
			pstmt = connection.prepareStatement(sql);		
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_no());
			
			int count = pstmt.executeUpdate();
			
			result = (count == 1);
			
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");
			if(rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	
	public List<BoardVo> getList() {
		List<BoardVo> result = new ArrayList<BoardVo>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = "select b.no, b.title, b.contents, b.hit, date_format(b.reg_date,'%Y-%m-%d %h:%i:%s') as reg_date, b.g_no, o_no, b.depth, b.user_no, u.name, b.state" + 
						 " from board b, user u" + 
						 " where b.user_no = u.no" +
						 " order by g_no desc";
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Long no         = rs.getLong(1);
				String title    = rs.getString(2);
				String contents = rs.getString(3);
				Long hit        = rs.getLong(4);
				String reg_date = rs.getString(5);
				Long g_no       = rs.getLong(6);
				Long o_no       = rs.getLong(7);
				Long depth      = rs.getLong(8);
				Long user_no    = rs.getLong(9);
				String user_name = rs.getString(10);
				String state = rs.getString(11);
				
				BoardVo vo= new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setUser_name(user_name);
				vo.setState(state);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}	
	
	public BoardVo getBoard(Long titleNo) {
		BoardVo result = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = "select no ,title, contents" + 
						 " from board" + 
						 " where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, titleNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				Long no         = rs.getLong(1);
				String title    = rs.getString(2);
				String contents = rs.getString(3);
		
				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);

			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}		
	
	public boolean modify(Long no, String title, String contents) {
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql = " update board" + 
					     " set title = ?" + 
					     " , contents = ?" + 
					     " , state = 'u'" + 
					     " where no = ?";		
			pstmt = connection.prepareStatement(sql);
				

			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, no);
			
			int count =  pstmt.executeUpdate();
			
			result = (count == 1);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
		
	public Boolean delete(Long no) {
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = getConnection();
			
			String sql = " update board" + 
					     " set state = 'n'" + 
					     " where no = ?";	
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
		
			int count =  pstmt.executeUpdate();
			
			result = (count == 1);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}	


}