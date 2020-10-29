package nba.serlvet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.service.PlayerService;

/**
 * Servlet implementation class QueryPlayerSeasonDataByPlayername
 */
@WebServlet("/QueryPlayerSeasonDataByPlayername")
public class QueryPlayerSeasonDataByPlayername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryPlayerSeasonDataByPlayername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String playername=request.getParameter("playername");
		PlayerService playerService=new PlayerService();
		String list=playerService.queryPlayerSeasonData(playername);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().println(list);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
