package user_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import user_vo.Dialogue_listVO;

public class Dialogue_listDAO {

	// single-ton pattern: 
	// 객체1개만생성해서 지속적으로 서비스하자
	static Dialogue_listDAO single = null;

	public static Dialogue_listDAO getInstance() {
		//생성되지 않았으면 생성
		if (single == null)
			single = new Dialogue_listDAO();
		//생성된 객체정보를 반환
		return single;
	}
	public Dialogue_listVO selectOne(String id) {

		Dialogue_listVO selected_id = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT gubun FROM USER_TOTAL WHERE ID=?";

		try {
			//1.Connection얻어온다
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체정보를 얻어오기
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter 설정
			pstmt.setString(1, id);
			
			//4.결과행 처리객체 얻어오기
			rs = pstmt.executeQuery();

			if (rs.next()) {
				selected_id = new Dialogue_listVO();
				selected_id.setGubun(rs.getString("gubun"));
				//현재레코드값=>Vo저장

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return selected_id;
	}
	
	//로그인 된 아이디가 '학생' 인경우 선생님 계정 테이블에서 메시지 상대의 정보를 받아온다.
	public List<Dialogue_listVO> teacher_dial_info(String id) {
		List<Dialogue_listVO> list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.*, B.nickname, C.profile, C.addr FROM TALK_LIST A, USER_TOTAL B, USER_TEACHER C WHERE A.student_id = ? and B.id = A.teacher_id and c.id = A.teacher_id ORDER BY A.READ_INFO DESC, A.LAST_SEND_DATE DESC, A.LAST_SEND_TIME DESC";

		try {
			//1.Connection얻어온다
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체정보를 얻어오기
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			//3.결과행 처리객체 얻어오기
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo = new Dialogue_listVO();
				//현재레코드값=>Vo저장
				vo.setTalk_seq(rs.getInt("talk_seq"));
				vo.setTeacher_id(rs.getString("teacher_id"));
				vo.setStudent_id(rs.getString("student_id"));
				vo.setContent(rs.getString("content"));
				vo.setLast_send_date(rs.getString("last_send_date"));
				vo.setLast_send_time(rs.getString("last_send_time"));
				vo.setRead_info(rs.getInt("read_info"));
				vo.setLast_send_id(rs.getString("last_send_id"));
				vo.setProfile(rs.getString("profile"));
				vo.setAddr(rs.getString("addr"));
				vo.setNickname(rs.getString("nickname"));
				
				//ArrayList추가
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<Dialogue_listVO> wish_teacher_dial_info(String id) {
		List<Dialogue_listVO> wish_list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT A.*, B.nickname, C.profile, C.addr, C.subject FROM TALK_LIST A, USER_TOTAL B, USER_TEACHER C, TEACHER_WISHLIST D WHERE A.student_id = ? and B.id = A.teacher_id and c.id = A.teacher_id and D.TEACHER_ID = A.TEACHER_ID ORDER BY A.READ_INFO DESC, A.LAST_SEND_DATE DESC, A.LAST_SEND_TIME DESC";

		try {
			//1.Connection얻어온다
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체정보를 얻어오기
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			//3.결과행 처리객체 얻어오기
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo_w = new Dialogue_listVO();
				//현재레코드값=>Vo저장
				vo_w.setTalk_seq(rs.getInt("talk_seq"));
				vo_w.setTeacher_id(rs.getString("teacher_id"));
				vo_w.setStudent_id(rs.getString("student_id"));
				vo_w.setContent(rs.getString("content"));
				vo_w.setLast_send_date(rs.getString("last_send_date"));
				vo_w.setLast_send_time(rs.getString("last_send_time"));
				vo_w.setRead_info(rs.getInt("read_info"));
				vo_w.setLast_send_id(rs.getString("last_send_id"));
				vo_w.setProfile(rs.getString("profile"));
				vo_w.setAddr(rs.getString("addr"));
				vo_w.setNickname(rs.getString("nickname"));
				vo_w.setSubject(rs.getString("subject"));
				
				//ArrayList추가
				wish_list.add(vo_w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wish_list;
	}
	
	//로그인 된 아이디가 '선생님' 인경우 학생 계정 테이블에서 메시지 상대의 정보를 받아온다.
	public List<Dialogue_listVO> student_dial_info(String id) {
		List<Dialogue_listVO> list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.*, B.nickname, C.addr FROM TALK_LIST A, USER_TOTAL B, USER_STUDENT C WHERE A.teacher_id = ? and B.id = A.student_id and c.id = A.student_id ORDER BY A.READ_INFO DESC, A.LAST_SEND_DATE DESC, A.LAST_SEND_TIME DESC";

		try {
			//1.Connection얻어온다
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체정보를 얻어오기
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			//3.결과행 처리객체 얻어오기
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo = new Dialogue_listVO();
				//현재레코드값=>Vo저장
				vo.setTalk_seq(rs.getInt("talk_seq"));
				vo.setTeacher_id(rs.getString("teacher_id"));
				vo.setStudent_id(rs.getString("student_id"));
				vo.setContent(rs.getString("content"));
				vo.setLast_send_date(rs.getString("last_send_date"));
				vo.setLast_send_time(rs.getString("last_send_time"));
				vo.setRead_info(rs.getInt("read_info"));
				vo.setLast_send_id(rs.getString("last_send_id"));
				vo.setProfile("img/account.png");
				vo.setAddr(rs.getString("addr"));
				vo.setNickname(rs.getString("nickname"));
				
				//ArrayList추가
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	

	public int msg_delete(int talk_seq) {
		int res = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "{call TALK_DELETE_PROC(?) }";

		try {
			//1.Connection획득
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 획득
			pstmt = conn.prepareStatement(sql);

			//3.pstmt parameter 채우기
			pstmt.setInt(1, talk_seq);

			//4.DB로 전송(res:처리된행수)
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
	
	
//마이페이지에 찜한 선생 정보 받아오기
	public List<Dialogue_listVO> wish_teacher_info(String id) {
		List<Dialogue_listVO> wish_teacher_list = new ArrayList<Dialogue_listVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT B.id, B.nickname, C.profile, C.addr, C.subject FROM USER_TOTAL B, USER_TEACHER C, TEACHER_WISHLIST D WHERE D.student_id = ? and  D.TEACHER_ID = B.ID AND D.TEACHER_ID = C.ID";

		try {
			//1.Connection얻어온다
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체정보를 얻어오기
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			//3.결과행 처리객체 얻어오기
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dialogue_listVO vo_w = new Dialogue_listVO();
				//현재레코드값=>Vo저장
				vo_w.setId(rs.getString("id"));
				vo_w.setProfile(rs.getString("profile"));
				vo_w.setAddr(rs.getString("addr"));
				vo_w.setNickname(rs.getString("nickname"));
				vo_w.setSubject(rs.getString("subject"));
				
				//ArrayList추가
				wish_teacher_list.add(vo_w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wish_teacher_list;
	}
}
